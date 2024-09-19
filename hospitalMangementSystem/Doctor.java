package hospitalMangementSystem;
//import java.net.StandardSocketOptions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
//import com.hospital.jdbcUtil.*;

public class Doctor {
    private Connection con;
    private Scanner scanner;

    public Doctor(Connection connection ){
        this.con = connection;

    }

    public void  viewDoctors(){


        try {


            //Step 3: Prepare SQL Statement
            String SQL= "select * from doctors";
            //Step 4: Create the JDBC Statement
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs =ps.executeQuery();
            //Step 6: Process Results

            System.out.println("Doctors: ");
            System.out.println("+------------+-------------------+-------------------+");
            System.out.println("| Doctor ID  | Name              | Specialization    |");
            System.out.println("+------------+--------------------+------------------+");
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                System.out.printf("|%-12s|%-19s|%-19s|\n",id,name,specialization);
                System.out.println("+------------+-------------------+-------------------+");
            }

        }catch(SQLException ex) {
            ex.printStackTrace();
        }


    }

    public boolean getDoctorByID(int id){

        String SQL= "select * from doctors where id = ?";
        try {

            //Step 3: Prepare SQL Statement

            //Step 4: Create the JDBC Statement
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs =ps.executeQuery();
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

