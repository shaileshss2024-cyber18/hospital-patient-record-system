import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hospital Patient Record System (PS No. 36)
 * Concepts: ArrayList, Searching, Sorting, Methods, File Handling
 * Menu: 1-Register Patient, 2-Display All, 3-Search by Name,
 *       4-Sort by Age (low to high), 5-Exit
 * Data file: patients.txt   (format: ID|Name|Age|Disease)
 * Sorting and searching are written manually (no built-in sort/search).
 */
public class HospitalPatientRecordSystem {

    private static final String FILE_NAME = "patients.txt";
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 120;
    private static final int FIRST_ID = 101;

    private static final ArrayList<Patient> patients = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    // ------------------------------------------------------------------
    // Patient class: one record = one object
    // ------------------------------------------------------------------
    static class Patient {
        private final String id;
        private final String name;
        private final int age;
        private final String disease;

        Patient(String id, String name, int age, String disease) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.disease = disease;
        }

        String getId()      { return id; }
        String getName()    { return name; }
        int getAge()        { return age; }
        String getDisease() { return disease; }

        @Override
        public String toString() {
            return id + " | " + name + " | " + age + " | " + disease;
        }

        String toFileLine() {
            return id + "|" + name + "|" + age + "|" + disease;
        }
    }

    // ------------------------------------------------------------------
    // main
    // ------------------------------------------------------------------
    public static void main(String[] args) {
        loadFromFile();

        while (true) {
            showMenu();
            String choice = sc.hasNextLine() ? sc.nextLine().trim() : "5";

            if (choice.equals("1")) {
                registerPatient();
            } else if (choice.equals("2")) {
                displayAll();
            } else if (choice.equals("3")) {
                searchByName();
            } else if (choice.equals("4")) {
                sortByAge();
            } else if (choice.equals("5")) {
                saveToFile();
                System.out.println("Data saved. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1-5.");
            }
        }
    }

    static void showMenu() {
        System.out.println();
        System.out.println("===== HOSPITAL PATIENT RECORD SYSTEM =====");
        System.out.println("1. Register Patient");
        System.out.println("2. Display All");
        System.out.println("3. Search by Name");
        System.out.println("4. Sort by Age (low to high)");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
    }

    // ------------------------------------------------------------------
    // 1. Register
    // ------------------------------------------------------------------
    static void registerPatient() {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        if (hasPipe(name)) {
            System.out.println("Name cannot contain the '|' character.");
            return;
        }

        System.out.print("Age: ");
        String ageText = sc.nextLine().trim();
        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid age. Age must be between " + MIN_AGE + " and " + MAX_AGE + ".");
            return;
        }
        if (age < MIN_AGE || age > MAX_AGE) {
            System.out.println("Invalid age. Age must be between " + MIN_AGE + " and " + MAX_AGE + ".");
            return;
        }

        System.out.print("Disease: ");
        String disease = sc.nextLine().trim();
        if (disease.isEmpty()) {
            System.out.println("Disease cannot be empty.");
            return;
        }
        if (hasPipe(disease)) {
            System.out.println("Disease cannot contain the '|' character.");
            return;
        }

        Patient p = new Patient(generateId(), name, age, disease);
        patients.add(p);
        saveToFile();
        System.out.println("Patient registered. ID: " + p);
    }

    /** Next ID = highest existing number + 1 (PT101 when the list is empty). */
    static String generateId() {
        int max = FIRST_ID - 1;
        for (int i = 0; i < patients.size(); i++) {
            String id = patients.get(i).getId();
            try {
                int num = Integer.parseInt(id.substring(2));
                if (num > max) {
                    max = num;
                }
            } catch (NumberFormatException e) {
                // ignore malformed id
            }
        }
        return "PT" + (max + 1);
    }

    static boolean hasPipe(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '|') {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // 2. Display all
    // ------------------------------------------------------------------
    static void displayAll() {
        if (patients.isEmpty()) {
            System.out.println("No patient records available.");
            return;
        }
        System.out.println("All patients (" + patients.size() + "):");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i + 1) + ". " + patients.get(i));
        }
    }

    // ------------------------------------------------------------------
    // 3. Search by name (manual linear search, case-insensitive, partial match)
    // ------------------------------------------------------------------
    static void searchByName() {
        System.out.print("Enter name to search: ");
        String key = sc.nextLine().trim();
        if (key.isEmpty()) {
            System.out.println("Search name cannot be empty.");
            return;
        }

        boolean found = false;
        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            if (containsIgnoreCase(p.getName(), key)) {
                System.out.println("Found: " + p);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Patient '" + key + "' not found.");
        }
    }

    /** Manual substring check (does not use String.contains / indexOf). */
    static boolean containsIgnoreCase(String text, String key) {
        int n = text.length();
        int m = key.length();
        if (m > n) {
            return false;
        }
        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m && Character.toLowerCase(text.charAt(i + j))
                         == Character.toLowerCase(key.charAt(j))) {
                j++;
            }
            if (j == m) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // 4. Sort by age (manual insertion sort, stable, ascending)
    // ------------------------------------------------------------------
    static void sortByAge() {
        if (patients.isEmpty()) {
            System.out.println("No patient records available.");
            return;
        }

        // Sort a copy so the stored (registration) order is not changed
        ArrayList<Patient> sorted = new ArrayList<>(patients);
        for (int i = 1; i < sorted.size(); i++) {
            Patient key = sorted.get(i);
            int j = i - 1;
            while (j >= 0 && sorted.get(j).getAge() > key.getAge()) {
                sorted.set(j + 1, sorted.get(j));
                j--;
            }
            sorted.set(j + 1, key);
        }

        System.out.println("Sorted by age (" + sorted.size()
                + (sorted.size() == 1 ? " patient):" : " patients):"));
        for (int i = 0; i < sorted.size(); i++) {
            System.out.println((i + 1) + ". " + sorted.get(i));
        }
    }

    // ------------------------------------------------------------------
    // File handling
    // ------------------------------------------------------------------
    static void loadFromFile() {
        File f = new File(FILE_NAME);
        if (!f.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length != 4) {
                    continue; // skip corrupted line
                }
                try {
                    int age = Integer.parseInt(parts[2].trim());
                    if (age < MIN_AGE || age > MAX_AGE) {
                        continue;
                    }
                    patients.add(new Patient(parts[0].trim(), parts[1].trim(), age, parts[3].trim()));
                } catch (NumberFormatException e) {
                    // skip line with bad age
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read " + FILE_NAME + ": " + e.getMessage());
        }
    }

    static void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < patients.size(); i++) {
                pw.println(patients.get(i).toFileLine());
            }
        } catch (IOException e) {
            System.out.println("Could not save to " + FILE_NAME + ": " + e.getMessage());
        }
    }
}
