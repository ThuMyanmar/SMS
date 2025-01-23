package sspd.sms.databases;

import sspd.sms.DAO.DataAccessObject;
import sspd.sms.DAO.DatabaseConnect;
import sspd.sms.courseoptions.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Coursedb implements DataAccessObject<Course> {

        private Connection con = DatabaseConnect.getInstance().getConnection();
    @Override
    public List<Course> getDataList() {

        String sql ="SELECT * FROM `course` WHERE 1";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            List<Course> list = new ArrayList<>();
            while(rs.next()) {

                Course course = new Course(

                rs.getInt("course_id"),
                rs.getString("course_name"),
                rs.getString("description"),
                rs.getDouble("duration"),
                rs.getInt("fee")

                );

                list.add(course);
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int create(Course course) {

        String sql = "INSERT INTO `course`(`course_name`, `description`, `duration`, `fee`) VALUES (?,?,?,?)";

        try(PreparedStatement pst = con.prepareStatement(sql)) {


            pst.setString(1, course.getCourse_name());
            pst.setString(2, course.getDescription());
            pst.setDouble(3, course.getDuration());
            pst.setInt(4, course.getFee());

            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(Course course) {

        String sql = "UPDATE `course` SET `course_name`=?,`description`=?,`duration`=?,`fee`=? WHERE `course_id`=?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, course.getCourse_name());
            pst.setString(2, course.getDescription());
            pst.setDouble(3, course.getDuration());
            pst.setInt(4, course.getFee());
            pst.setInt(5, course.getCourse_id());

            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int delete(Course course) {
    String sql = "DELETE FROM `course` WHERE `course_id`=?";

    try(PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, course.getCourse_id());
        return pst.executeUpdate();

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }

    @Override
    public int delete(String code) {
        String sql = "DELETE FROM `course` WHERE `course_id`=?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, code);

            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
