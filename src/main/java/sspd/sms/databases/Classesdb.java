package sspd.sms.databases;

import sspd.sms.DAO.DataAccessObject;
import sspd.sms.DAO.DatabaseConnect;
import sspd.sms.classoptions.module.Classes;
import sspd.sms.courseoptions.module.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Classesdb implements DataAccessObject<Classes> {

    private Connection con = DatabaseConnect.getInstance().getConnection();



    @Override
    public List<Classes> getDataList() {

        String sql = "SELECT * FROM `classes` WHERE 1";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();

            List<Classes> list = new ArrayList<>();
            while(rs.next()) {
                Classes css =new Classes(
                  rs.getInt("class_id"),
                  rs.getString("class_name"),
                  rs.getInt("course_id"),
                  rs.getInt("teacher_id"),
                  rs.getString("scedule"),
                  rs.getInt("limit_stu")

                );

                list.add(css);

            }
            return list;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int create(Classes classes) {

        String sql = "INSERT INTO `classes`(`class_name`, `course_id`, `teacher_id`, `scedule`, `limit_stu`) VALUES (?,?,?,?,?)";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

           pst.setString(1, classes.getClass_name());
           pst.setInt(2, classes.getCourse_id());
           pst.setInt(3, classes.getTeacher_id());
           pst.setString(4, classes.getScedule());
           pst.setInt(5, classes.getLimit_stu());

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
    public int update(Classes classes) {

        String sql = "UPDATE `classes` SET `class_name`=?,`course_id`=?,`teacher_id`=?,`scedule`=?,`limit_stu`=? WHERE `class_id`=?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, classes.getClass_name());
            pst.setInt(2, classes.getCourse_id());
            pst.setInt(3, classes.getTeacher_id());
            pst.setString(4, classes.getScedule());
            pst.setInt(5, classes.getLimit_stu());
            pst.setInt(6,classes.getClass_id());

            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int delete(Classes classes) {

        String sql = "DELETE FROM `classes` WHERE `class_id`=?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, classes.getClass_id());
            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(String code) {

        String sql = "DELETE FROM `classes` WHERE `class_id`=?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, code);
            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
