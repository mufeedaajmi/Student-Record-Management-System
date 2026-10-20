# Student Record Management System

A console-based Java application to manage student records using object-oriented programming and file handling.

## Features
- Add, view, search, update, and delete student records
- Validate marks between 0 and 100
- Persist records in a local CSV file (`students.csv`)
- Prevent duplicate student IDs

## Requirements
- Java JDK 11 or later

## Run
From the project root:

```bash
javac -d out src/*.java
java -cp out StudentRecordManager
```

The application creates `students.csv` in the directory from which it is run when records are saved.

## Concepts demonstrated
Classes and objects, encapsulation, collections (`LinkedHashMap`), exception handling, and file I/O.

## Example workflow
1. Choose `1` to add a student.
2. Enter an ID, name, and marks.
3. Choose `2` to view saved records.
4. Restart the program to confirm persistence.

## Future improvements
- Use a database instead of CSV
- Add unit tests
- Support quoted commas in student names
