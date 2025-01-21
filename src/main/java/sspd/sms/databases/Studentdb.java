package sspd.sms.databases;

import sspd.sms.DAO.DataAccessObject;
import sspd.sms.DAO.DatabaseConnect;
import sspd.sms.courseoptions.module.Course;
import sspd.sms.student.module.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Studentdb implements DataAccessObject<Student> {

    private Connection con = DatabaseConnect.getInstance().getConnection();

    @Override
    public List<Student> getDataList() {

        String sql ="SELECT * FROM `student` WHERE 1";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();

            List<Student> list = new ArrayList<>();

            while(rs.next()) {

                Student st = new Student(
                    rs.getString("stu_id"),
                    rs.getString("stu_name"),
                    rs.getDate("stu_dob"),
                    rs.getString("gender"),
                    rs.getString("contact"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("photo_path")



                );
                list.add(st);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public int create(Student student) {

        String sql = "INSERT INTO `student`(`stu_id`, `stu_name`, `stu_dob`, `gender`, `contact`, `email`, `address`, `photo_path`) VALUES (?,?,?,?,?,?,?,?)";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, student.getStu_id());
            pst.setString(2, student.getStu_name());
            pst.setDate(3, (Date) student.getStu_dob());
            pst.setString(4, student.getGender());
            pst.setString(5, student.getContact());
            pst.setString(6, student.getEmail());
            pst.setString(7, student.getAddress());
            pst.setString(8, student.getPhoto_path());

            return pst.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public int create(Course course) {
        return 0;
    }


    @Override
    public int update(Student student) {

       String sql = "UPDATE `student` SET `stu_name`=?,`stu_dob`=?,`gender`=?,`contact`=?,`email`=?,`address`=?,`photo_path`=? WHERE stu_id=?";
       try(PreparedStatement pst = con.prepareStatement(sql)) {


           pst.setString(1, student.getStu_name());
           pst.setDate(2, (Date) student.getStu_dob());
           pst.setString(3, student.getGender());
           pst.setString(4, student.getContact());
           pst.setString(5, student.getEmail());
           pst.setString(6, student.getAddress());
           pst.setString(7, student.getPhoto_path());
           pst.setString(8, student.getStu_id());

           return pst.executeUpdate();


       } catch (SQLException e) {
           throw new RuntimeException(e);
       }


    }

    @Override
    public int delete(Student student) {

        String sql = "DELETE FROM `student` WHERE stu_id = ?";


        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, student.getStu_id());
            return pst.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(String code) {

        String sql = "DELETE FROM `student` WHERE stu_id = ?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, code);
            return pst.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
