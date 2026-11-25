HOSPITAL PATIENT RECORD SYSTEM  (PS No. 36)
Data Structures and Algorithms (DSA) - Java console project
Submitted by: Shailesh Kumar Singh  |  ERP ID: RU-25-11328

HOW TO RUN
  1. Install JDK 17 or later (JDK 21 recommended).
  2. Keep HospitalPatientRecordSystem.java in an empty folder.
  3. Open Command Prompt / Terminal in that folder and run:
         javac HospitalPatientRecordSystem.java
         java HospitalPatientRecordSystem
     (or, with JDK 11+, in one step:  java HospitalPatientRecordSystem.java)

MENU
  1. Register Patient       - name, age (0-120), disease; ID is generated (PT101, PT102, ...)
  2. Display All
  3. Search by Name         - not case-sensitive, partial names allowed
  4. Sort by Age (low to high)
  5. Exit                   - saves all records to patients.txt

DATA FILE
  patients.txt is created in the same folder (format: ID|Name|Age|Disease)
  and is loaded automatically the next time the program starts.
  Delete patients.txt to start with an empty record list.

NOTES
  - Searching and sorting are written manually (linear search, insertion sort);
    no built-in sort/search methods are used.
  - The characters '|' are not allowed in name or disease (used as file separator).
