package hospitalMangementSystem;

import java.net.StandardSocketOptions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
//import com.hospital.jdbcUtil.*;

public class patient {
    private Connection con;

    private Scanner scanner;

    public patient(Connection connection , Scanner scanner){
        this.con = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.print("Enter patient Name👲: ");
        String name = scanner.next();
        System.out.print("Enter patient age😊: ");
        int age = scanner.nextInt();
        System.out.print("Enter patient gender👪: ");
        String gender = scanner.next();




        try {

            //Step 3: Prepare SQL Statement
            String SQL= "insert into patients(name,age,gender) values(?,?,?)";

            //Step 4: Create the JDBC Statement
            PreparedStatement ps = con.prepareStatement(SQL);

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);

            //Step 5: Submit SQL to DB

            int x=ps.executeUpdate();
            //Step 6: Process Results
            if(x==1) {
                System.out.println("Patient Record is Inserted Successfully👍");
            }else {
                System.out.println("Sorry, Patient Record is Not Inserted😓⁉️");
            }

        }catch(SQLException ex) {
            ex.printStackTrace();
        }


    }

    public void  viewPatients(){


        PreparedStatement ps=null;
        ResultSet rs = null;

        try {


            //Step 3: Prepare SQL Statement
            String SQL= "select * from patients";
            //Step 4: Create the JDBC Statement
            ps = con.prepareStatement(SQL);
            rs=ps.executeQuery();
            //Step 6: Process Results

            System.out.println("patients: ");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient ID | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.printf("|%-12s|%-20s|%-10s|%-12s|\n",id,name,age,gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }

        }catch(SQLException ex) {
            ex.printStackTrace();
        }


    }

    public boolean getPatientByID(int id){

        try {


            //Step 3: Prepare SQL Statement
            String SQL= "select * from patients where id = ?";
            //Step 4: Create the JDBC Statement
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            //Step 6: Process Results

            if (rs.next()){
                return true;
            }
            else {
                return false;
            }


        }catch(SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

}
