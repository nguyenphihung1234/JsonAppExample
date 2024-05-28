
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//connect to MySQL Database
//Get data from table
public class ConnectionDB {
    private static final String JDBC_URL="jdbc:mysql://localhost:3306/fptemployee1";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";
    public static List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        Statement stm = conn.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT * FROM users");
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            users.add(new User(id,name,email));
        }
        return users;
    }
}