import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Write_JSON_Gson {
    public static void main(String[] args) throws IOException {
        //Tạo dòng ghi (String Write )
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("employee.json"));

        //Tạo đối tương Employee thonng Map
        Map<String , Object> employee = new HashMap<>();
        employee.put("id",1);
        employee.put("name","Khac thanh");
        employee.put("email","thanh@gmail.com");
        employee.put("age",18);
        // Tạo đối tượng Address
         Map<String,Object> address = new HashMap<>();
         address.put("street","Ton That Thuyet ");
         address.put("city","Hanoi");
         address.put("zipCode",10000);
         //Gán Address cho Employee
        employee.put("address",address);
        //Tạo project 1
        Map<String,Object> pro1 = new HashMap<>();
        pro1.put("title ","java and json");
        pro1.put("budget",2000);
        Map<String,Object> pro2 = new HashMap<>();
        pro2.put("title","Employee management");
        pro2.put("budget",5000);
        //Gán dự án cho Employee
        employee.put("projects", Arrays.asList(pro1,pro2));
        //Tao đối tươngj Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        writer.write(gson.toJson(employee));
        writer.close();
    }
}
