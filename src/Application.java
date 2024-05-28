import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws SQLException, IOException {
        //List users from database
        List<User> users = ConnectionDB.getUsers();
        //Ghi users to json file
        String filePath = "users.json";
        JsonWriteData.writeUserToJson(users,filePath);
        System.out.println(users);
        System.out.println("Data has been written to "+ filePath);
    }
}
