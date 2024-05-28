package slot5;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConnectionDB {
    private static final String JDBC_URL="jdbc:mysql://localhost:3306/employeefpt";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public static List<Student> getStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT * FROM students");
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            String email = resultSet.getString("email");
            String phone = resultSet.getString("phone");
            Date dob = resultSet.getDate("dob");
            students.add(new Student(id, name, address, email, phone, (java.sql.Date) dob));
        }
        resultSet.close();
        stm.close();
        conn.close();
        return students;
    }

    public static void addStudent(Student student) throws SQLException {
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        String sql = "INSERT INTO students (id, name, address, email, phone, dob) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, student.getId());
        pstmt.setString(2, student.getName());
        pstmt.setString(3, student.getAddress());
        pstmt.setString(4, student.getEmail());
        pstmt.setString(5, student.getPhone());
        pstmt.setDate(6, student.getDob());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
}
