package slot8;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class StudentJsonManager {
    private static final String FILE_PATH = "Student.json";
    private static List<Student> students = new ArrayList<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException {
        loadStudents();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Display all students");
            System.out.println("2. Add new student");
            System.out.println("3. Edit student");
            System.out.println("4. Search student by name or ID");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline
            switch (choice) {
                case 1:
                    displayStudents();
                    break;
                case 2:
                    addStudent(scanner);
                    break;
                case 3:
                    editStudent(scanner);
                    break;
                case 4:
                    searchStudent(scanner);
                    break;
                case 5:
                    saveStudents();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void loadStudents() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            students = gson.fromJson(reader, new TypeToken<List<Student>>(){}.getType());
            if (students == null) {
                students = new ArrayList<>(); // Khởi tạo danh sách nếu nó null
            }
        } catch (FileNotFoundException e) {
            students = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveStudents() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(students, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayStudents() {
        students.forEach(System.out::println);
    }
    private static void addStudent(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        // Nhập số lượng môn học
        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();
        scanner.nextLine(); // consume the newline

        List<Subject> subjects = new ArrayList<>();
        for (int i = 0; i < numSubjects; i++) {
            System.out.println("Enter information for subject " + (i + 1) + ":");
            System.out.print("Enter subject ID: ");
            String subjectId = scanner.nextLine();
            System.out.print("Enter subject name: ");
            String subjectName = scanner.nextLine();
            List<Integer> scores = new ArrayList<>();
            while (true) {
                System.out.print("Enter score (or 'done' to finish): ");
                String scoreInput = scanner.nextLine();
                if (scoreInput.equalsIgnoreCase("done")) {
                    break;
                }
                try {
                    int score = Integer.parseInt(scoreInput);
                    scores.add(score);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid score, please enter a number.");
                }
            }
            subjects.add(new Subject(subjectId, subjectName, scores));
        }

        Student newStudent = new Student(id, name, subjects);
        students.add(newStudent);
        saveStudents();
        System.out.println("Student added successfully.");
    }




    private static void editStudent(Scanner scanner) {
        System.out.print("Enter student ID to edit: ");
        String id = scanner.nextLine();
        Student student = findStudentById(id);
        if (student != null) {
            System.out.print("Enter new name (or press Enter to keep current name): ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                student.setName(name);
            }
            List<Subject> subjects = student.getSubjects();
            while (true) {
                System.out.print("Enter subject ID to edit (or 'done' to finish): ");
                String subjectId = scanner.nextLine();
                if (subjectId.equalsIgnoreCase("done")) {
                    break;
                }
                System.out.print("Enter new subject name (or press Enter to keep current name): ");
                String subjectName = scanner.nextLine();
                List<Integer> scores = new ArrayList<>();
                while (true) {
                    System.out.print("Enter score (or 'done' to finish): ");
                    String scoreInput = scanner.nextLine();
                    if (scoreInput.equalsIgnoreCase("done")) {
                        break;
                    }
                    try {
                        int score = Integer.parseInt(scoreInput);
                        scores.add(score);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid score, please enter a number.");
                    }
                }
                boolean subjectFound = false;
                for (Subject subject : subjects) {
                    if (subject.getId().equalsIgnoreCase(subjectId)) {
                        if (!subjectName.isEmpty()) {
                            subject.setName(subjectName);
                        }
                        subject.setScores(scores);
                        subjectFound = true;
                        break;
                    }
                }
                if (!subjectFound) {
                    subjects.add(new Subject(subjectId, subjectName, scores));
                }
            }
            saveStudents();
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Student not found!");
        }
    }

    private static void searchStudent(Scanner scanner) {
        System.out.print("Enter student name or ID to search: ");
        String query = scanner.nextLine();
        students.stream()
                .filter(student -> student.getId().equalsIgnoreCase(query) || student.getName().equalsIgnoreCase(query))
                .forEach(System.out::println);
    }

    private static Student findStudentById(String id) {
        return students.stream()
                .filter(student -> student.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
}
