package sspd.sms.databases;

import sspd.sms.DAO.DataAccessObject;
import sspd.sms.DAO.DatabaseConnect;
import sspd.sms.certificate_templatesoptions.module.Certificatetemplates;
import sspd.sms.courseoptions.module.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Certifiactetemplatesdb implements DataAccessObject<Certificatetemplates> {

    private Connection con = DatabaseConnect.getInstance().getConnection();

    @Override
    public List<Certificatetemplates> getDataList() {

        String sql = "SELECT * FROM `certificate_templates` WHERE 1";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();

            List<Certificatetemplates> list = new ArrayList<>();

            while(rs.next()) {

                Certificatetemplates  crt = new Certificatetemplates(

                        rs.getInt("ctid"),
                        rs.getInt("course_id"),
                        rs.getString("template")

                );
                list.add(crt);

            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public int create(Certificatetemplates certificatetemplates) {

        String sql = "INSERT INTO `certificate_templates`(`course_id`,`template`) VALUES (?,?)";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1,certificatetemplates.getCourse_id());
            pst.setString(2,certificatetemplates.getTemplate());
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
    public int update(Certificatetemplates certificatetemplates) {

        String sql = "UPDATE `certificate_templates` SET `course_id`=?,`template`=? WHERE `ctid`=?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1,certificatetemplates.getCourse_id());
            pst.setString(2,certificatetemplates.getTemplate());
            pst.setInt(3,certificatetemplates.getCtid());
            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public int delete(Certificatetemplates certificatetemplates) {

        String sql = "DELETE FROM `certificate_templates` WHERE `ctid`=?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1,certificatetemplates.getCtid());
            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int delete(String code) {

        String sql = "DELETE FROM `certificate_templates` WHERE `ctid`=?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1,code);
            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
