import java.io.*;
import java.util.*;

public class StudentRecordManager {
    private static final String FILE_NAME = "students.csv";
    private final Map<Integer, Student> students = new LinkedHashMap<>();

    public StudentRecordManager() {
        loadRecords();
    }

    private void loadRecords() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double marks = Double.parseDouble(parts[2]);
                    students.put(id, new Student(id, name, marks));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Could not load records: " + e.getMessage());
        }
    }

    private void saveRecords() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student student : students.values()) {
                String safeName = student.getName().replace(",", " ");
                writer.println(student.getId() + "," + safeName + "," + student.getMarks());
            }
        } catch (IOException e) {
            System.out.println("Could not save records: " + e.getMessage());
        }
    }

    private void addStudent(Scanner scanner) {
        System.out.print("Student ID: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        if (students.containsKey(id)) {
            System.out.println("A student with this ID already exists.");
            return;
        }

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Marks: ");
        double marks = Double.parseDouble(scanner.nextLine().trim());

        if (name.isEmpty() || marks < 0 || marks > 100) {
            System.out.println("Enter a name and marks between 0 and 100.");
            return;
        }

        students.put(id, new Student(id, name, marks));
        saveRecords();
        System.out.println("Student added.");
    }

    private void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        students.values().forEach(System.out::println);
    }

    private void searchStudent(Scanner scanner) {
        System.out.print("Enter student ID: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Student student = students.get(id);
        System.out.println(student == null ? "Student not found." : student);
    }

    private void updateStudent(Scanner scanner) {
        System.out.print("Enter student ID to update: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        if (!students.containsKey(id)) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("New name: ");
        String name = scanner.nextLine().trim();
        System.out.print("New marks (0-100): ");
        double marks = Double.parseDouble(scanner.nextLine().trim());

        if (name.isEmpty() || marks < 0 || marks > 100) {
            System.out.println("Enter a name and marks between 0 and 100.");
            return;
        }

        students.put(id, new Student(id, name, marks));
        saveRecords();
        System.out.println("Student updated.");
    }

    private void deleteStudent(Scanner scanner) {
        System.out.print("Enter student ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        if (students.remove(id) != null) {
            saveRecords();
            System.out.println("Student deleted.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== Student Record Management ===");
                System.out.println("1. Add student");
                System.out.println("2. View all students");
                System.out.println("3. Search student");
                System.out.println("4. Update student");
                System.out.println("5. Delete student");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                String choice = scanner.nextLine().trim();
                try {
                    switch (choice) {
                        case "1": addStudent(scanner); break;
                        case "2": viewStudents(); break;
                        case "3": searchStudent(scanner); break;
                        case "4": updateStudent(scanner); break;
                        case "5": deleteStudent(scanner); break;
                        case "0": System.out.println("Goodbye!"); return;
                        default: System.out.println("Invalid option.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }
        }
    }

    public static void main(String[] args) {
        new StudentRecordManager().run();
    }
}
