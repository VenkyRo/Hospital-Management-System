package hospitalMangementSystem;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystemDriver {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";

    private static final String username = "root";

    private static final String password = "Vk@18172";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        Scanner scanner= new Scanner(System.in);
        try {
            Connection con = DriverManager.getConnection(url,username,password);
            patient patient = new patient(con,scanner);
            Doctor doctor = new Doctor(con);


            while (true){
                System.out.println(" HOSPITAL MANAGEMENT SYSTEM 💝🩺🏥🩺💝");
                System.out.println(" 1.Add Patient ");
                System.out.println(" 2.View Patient ");
                System.out.println(" 3.View Doctors");
                System.out.println(" 4.Book Appointment");
                System.out.println(" EXIT ");

                System.out.println(" Enter Your Choice :");

                int choice = scanner.nextInt();

                switch (choice){
                    case 1:
                        //Add patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        //View patient
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        // View doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        // Book Appointment
                        bookAppointment(patient,doctor,con,scanner);
                        System.out.println();
                        break;
                    case 5:
                        // Exit
                        System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM!!!😊");
                        return;

                    default:
                        System.out.println("Enter Valid Choice!!⁉️🫡");
                        break;
                }

            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void bookAppointment( patient patient,Doctor doctor,  Connection connection,Scanner scanner){

        System.out.println(" Enter Patient ID:  ");
        int patientId = scanner.nextInt();
        System.out.println(" Enter Doctor ID: ");
        int doctorID = scanner.nextInt();

        System.out.println(" Enter Appointment Date(YYY-MM-DD):");

        String appointmentDate = scanner.next();

        if (patient.getPatientByID(patientId) && doctor.getDoctorByID(doctorID)){
            if (checkDoctorAvailability(doctorID,appointmentDate ,connection )){
                String appointmentQuery = "INSERT INTO appointments(patient_id,doctor_id,appintment_date) VALUES(?,?,?)";
                try {
                    PreparedStatement ps = connection.prepareStatement(appointmentQuery);
                    ps.setInt(1,patientId);
                    ps.setInt(2,doctorID);
                    ps.setString(3,appointmentDate);
                    int rowsAffected = ps.executeUpdate();

                    if (rowsAffected > 0){
                        System.out.println("Appointment Booked!👍");
                    }else {
                        System.out.println("Failed to Appointment Book!!😓");
                    }


                }catch (SQLException ex){
                    ex.printStackTrace();
                }
            }

        }else {
            System.out.println(" Doctor or Patient Doesn't Exist!!⁉️");
        }

    }

    public static boolean checkDoctorAvailability(int doctorID, String appointmentDate ,Connection connection){
        String Query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appintment_date = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(Query);
            ps.setInt(1,doctorID);
            ps.setString(2,appointmentDate);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                int count = rs.getInt(1);
                if (count == 0){
                    return true;
                }
                else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}
