package slot5;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class StudentJsonManager {
    private static final String JSON_FILE_PATH = "students.json";
    private static JsonArray studentList = new JsonArray();

    public static void main(String[] args) {
        try {
            // Fetch students from database
            List<Student> students = ConnectionDB.getStudents();

            // Convert students to JSON and save to file
            for (Student student : students) {
                JsonObject studentJson = new JsonObject();
                studentJson.addProperty("id", student.getId());
                studentJson.addProperty("name", student.getName());
                studentJson.addProperty("address", student.getAddress());
                studentJson.addProperty("email", student.getEmail());
                studentJson.addProperty("phone", student.getPhone());
                studentJson.addProperty("dob", student.getDob().toString());
                studentList.add(studentJson);
            }
            saveJsonFile();

            // Read JSON file to studentList
            try (FileReader reader = new FileReader(JSON_FILE_PATH)) {
                studentList = JsonParser.parseReader(reader).getAsJsonArray();
            } catch (IOException e) {
                System.err.println("Error reading JSON file: " + e.getMessage());
            }

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Choose an option:");
                System.out.println("1. Add new student");
                System.out.println("2. Display all students");
                System.out.println("3. Search by name");
                System.out.println("4. Search by email");
                System.out.println("5. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        addNewStudent(scanner);
                        break;
                    case 2:
                        displayAllStudents();
                        break;
                    case 3:
                        searchByName(scanner);
                        break;
                    case 4:
                        searchByEmail(scanner);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching data from the database: " + e.getMessage());
        }
    }

    private static void addNewStudent(Scanner scanner) {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        String dobString = scanner.nextLine();
        Date dob = Date.valueOf(dobString);

        Student newStudent = new Student(id, name, address, email, phone, dob);

        // Save new student to database
        try {
            ConnectionDB.addStudent(newStudent);
        } catch (SQLException e) {
            System.err.println("Error saving data to the database: " + e.getMessage());
            return;
        }

        // Add new student to JSON list
        JsonObject newStudentJson = new JsonObject();
        newStudentJson.addProperty("id", id);
        newStudentJson.addProperty("name", name);
        newStudentJson.addProperty("address", address);
        newStudentJson.addProperty("email", email);
        newStudentJson.addProperty("phone", phone);
        newStudentJson.addProperty("dob", dobString);
        studentList.add(newStudentJson);

        saveJsonFile();
    }

    private static void displayAllStudents() {
        for (int i = 0; i < studentList.size(); i++) {
            JsonObject student = studentList.get(i).getAsJsonObject();
            System.out.println(student);
        }
    }

    private static void searchByName(Scanner scanner) {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();
        for (int i = 0; i < studentList.size(); i++) {
            JsonObject student = studentList.get(i).getAsJsonObject();
            if (student.get("name").getAsString().equalsIgnoreCase(name)) {
                System.out.println(student);
            }
        }
    }

    private static void searchByEmail(Scanner scanner) {
        System.out.print("Enter email to search: ");
        String email = scanner.nextLine();
        for (int i = 0; i < studentList.size(); i++) {
            JsonObject student = studentList.get(i).getAsJsonObject();
            if (student.get("email").getAsString().equalsIgnoreCase(email)) {
                System.out.println(student);
            }
        }
    }

    private static void saveJsonFile() {
        try (FileWriter file = new FileWriter(JSON_FILE_PATH)) {
            file.write(studentList.toString());
            file.flush();
            System.out.println("Data has been saved to " + JSON_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error saving JSON file: " + e.getMessage());
        }
    }
}
