# Hospital Patient Record System

A menu-driven Java console application to manage hospital patient records.
Developed as a Data Structures and Algorithms (DSA) project (PS No. 36).

**Submitted by:** Shailesh Kumar Singh (ERP ID: RU-25-11328)
**Guide:** Mr. Surya Mani Verma, Technical Trainer
**University:** Rungta International Skills University, Bhilai, Chhattisgarh
**Session:** 2025-2029

## Features

1. **Register Patient**: name, age (0 to 120) and disease, with an auto-generated ID (PT101, PT102, ...)
2. **Display All**: lists every patient record
3. **Search by Name**: case-insensitive, partial names allowed
4. **Sort by Age**: low to high
5. **Exit**: saves all records to `patients.txt`

Records are loaded from `patients.txt` when the program starts, so data is kept between runs.

## Concepts Used

- Java, `ArrayList` of `Patient` objects
- Linear search and insertion sort (written manually, no built-in sort or search)
- File handling (`patients.txt`)
- Input validation

## How to Run

Requires JDK 17 or later.

```
javac HospitalPatientRecordSystem.java
java HospitalPatientRecordSystem
```

## Sample Output

```
Enter choice: 4
Sorted by age (3 patients):
1. PT102 | Rohan Gupta | 30 | Asthma
2. PT103 | Anita Sharma | 30 | Fever
3. PT101 | Sunita Devi | 45 | Diabetes
```

## Time Complexity

| Operation | Time |
|---|---|
| Display all | O(n) |
| Search by name | O(n x m) |
| Sort by age | O(n^2) worst case, O(n) best case |

## Project Files

- `HospitalPatientRecordSystem.java`: source code
- `docs/`: final report (PDF) and presentation (PPT)
