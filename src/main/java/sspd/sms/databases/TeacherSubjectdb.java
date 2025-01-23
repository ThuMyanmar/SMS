package sspd.sms.databases;

import sspd.sms.DAO.DataAccessObject;
import sspd.sms.DAO.DatabaseConnect;
import sspd.sms.courseoptions.model.Course;
import sspd.sms.teacher_subjectoptions.model.TeacherSubject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherSubjectdb implements DataAccessObject<TeacherSubject> {

    private Connection con = DatabaseConnect.getInstance().getConnection();

    @Override
    public List<TeacherSubject> getDataList() {

        String sql = "SELECT * FROM `teacher_subjects` WHERE 1";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            List<TeacherSubject> list = new ArrayList<TeacherSubject>();

            while(rs.next()) {

              TeacherSubject tsu = new TeacherSubject(
                      rs.getInt("tsid"),
                      rs.getInt("teacher_id"),
                      rs.getString("subject")

              );

             list.add(tsu);

            }

            return list;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int create(TeacherSubject teacherSubject) {

        String sql = "INSERT INTO `teacher_subjects`( `teacher_id`, `subject`) VALUES (?,?)";

        try(PreparedStatement pst = con.prepareStatement(sql)) {


          pst.setInt(1, teacherSubject.getTeacher_id());
          pst.setString(2,teacherSubject.getSubject());

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
    public int update(TeacherSubject teacherSubject){

      String sql = "UPDATE `teacher_subjects` SET `subject`=? WHERE 'tsid'=?";
        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, teacherSubject.getSubject());
            pst.setInt(2, teacherSubject.getTsid());
            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int update(int tsid, String subject) {

        String sql = "UPDATE `teacher_subjects` SET `subject`=? WHERE `tsid`=? ";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, subject);
            pst.setInt(2, tsid);
            return pst.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public int delete(TeacherSubject teacherSubject) {


        String sql = "DELETE FROM `teacher_subjects` WHERE `tsid`=?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, teacherSubject.getTsid());
            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int delete(String code) {

        String sql = "DELETE FROM `teacher_subjects` WHERE `tsid`=?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, code);
            return pst.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
