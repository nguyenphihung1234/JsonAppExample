package slot8;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class StudentManagement {
    private static final String FILE_PATH = "Student.json";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Display all students");
            System.out.println("2. Add a student");
            System.out.println("3. Edit student information");
            System.out.println("4. Search for a student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayStudents();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    editStudent(studentsArray);
                    break;
                case 4:
                    searchStudent();
                    break;
                case 5:
                    System.out.println("Exiting program.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void displayStudents() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void addStudent() {
        try {
            JsonObject newStudent = new JsonObject();

            System.out.print("Enter student ID: ");
            String id = scanner.nextLine().trim();
            newStudent.put("ID", id);

            System.out.print("Enter student Name: ");
            String name = scanner.nextLine().trim();
            newStudent.put("Name", name);

            JsonArray subjectsArray = new JsonArray();
            System.out.print("Enter the number of subjects: ");
            int numSubjects = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            for (int i = 0; i < numSubjects; i++) {
                JsonObject subjectObject = new JsonObject();

                System.out.print("Enter subject name for subject " + (i + 1) + ": ");
                String subjectName = scanner.nextLine().trim();
                subjectObject.put("Name", subjectName);

                System.out.print("Enter score for subject " + (i + 1) + ": ");
                double score = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                subjectObject.put("Score", score);

                subjectsArray.add(subjectObject);
            }

            newStudent.put("Subjects", subjectsArray);

            FileWriter writer = new FileWriter(FILE_PATH, true);
            writer.write(newStudent.toString() + "\n");
            writer.close();

            System.out.println("Student added successfully.");
        } catch (IOException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    private static void editStudent(JsonArray studentsArray) {
        try {
            System.out.print("Enter ID of student to edit: ");
            String searchId = scanner.nextLine();
            JsonArray studentsArray = new JsonArray();

            boolean found = false;
            for (Object obj : studentsArray) {
                JsonObject student = (JsonObject) obj;
                if (student.get("ID").equals(searchId)) {
                    System.out.println("Student found. Enter new information:");

                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine().trim();
                    student.put("Name", newName);

                    JsonArray subjectsArray = (JsonArray) student.get("Subjects");
                    System.out.print("Do you want to edit subjects? (yes/no): ");
                    String editSubjectsChoice = scanner.nextLine().trim().toLowerCase();

                    if (editSubjectsChoice.equals("yes")) {
                        subjectsArray.clear(); // Clear existing subjects

                        System.out.print("Enter the number of subjects: ");
                        int numSubjects = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        for (int i = 0; i < numSubjects; i++) {
                            JsonObject subjectObject = new JsonObject();

                            System.out.print("Enter subject name for subject " + (i + 1) + ": ");
                            String subjectName = scanner.nextLine().trim();
                            subjectObject.put("Name", subjectName);

                            System.out.print("Enter score for subject " + (i + 1) + ": ");
                            double score = scanner.nextDouble();
                            scanner.nextLine(); // Consume newline
                            subjectObject.put("Score", score);

                            subjectsArray.add(subjectObject);
                        }
                    }

                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Student not found.");
                return;
            }

            editStudent(studentsArray);
            System.out.println("Student information updated successfully.");
        } catch (IOException | ParseException e) {
            System.out.println("Error editing student: " + e.getMessage());
        }
    }

    private static void searchStudent() {
        try {
            System.out.print("Enter Name or ID to search: ");
            String searchKey = scanner.nextLine();
            JsonArray studentsArray = new JsonArray();
            boolean found = false;
            for (Object obj : studentsArray) {
                JsonObject student = (JsonObject) obj;
                if (student.get("ID").equals(searchKey) || student.get("Name").equals(searchKey)) {
                    System.out.println("Student found:");
                    System.out.println(student.toString());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Student not found.");
            }
        } catch (ParseException e) {
            System.out.println("Error searching student: " + e.getMessage());
        }
    }

}

