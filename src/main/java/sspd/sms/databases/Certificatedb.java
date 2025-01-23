package sspd.sms.databases;

import sspd.sms.DAO.DataAccessObject;
import sspd.sms.DAO.DatabaseConnect;
import sspd.sms.certificate.model.Certificate;
import sspd.sms.courseoptions.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Certificatedb implements DataAccessObject<Certificate>{

    private Connection con = DatabaseConnect.getInstance().getConnection();

    @Override
    public List<Certificate> getDataList() {

        String sql = "SELECT * FROM `certificate` WHERE 1";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();

            List<Certificate> list = new ArrayList<>();
            while(rs.next()) {

                Certificate cate = new Certificate(

                        rs.getString("cer-id"),
                        rs.getString("stuid"),
                        rs.getDate("issue_date"),
                        rs.getInt("ctid")

                );
                list.add(cate);

            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int create(Certificate certificate) {

        String sql = "INSERT INTO `certificate`(`cer_id`, `stu_id`, `issue_date`, `ctid`) VALUES (?,?,?,?)";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, certificate.getCer_id());
            pst.setString(2, certificate.getStu_id());
            pst.setDate(3,(Date) certificate.setIssue_date());
            pst.setInt(4, certificate.getCtid());
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
    public int update(Certificate certificate) {

        String sql = "UPDATE `certificate` SET`stu_id` = ?,`issue_date` = ?,`ctid` = ? WHERE `cer_id` = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, certificate.getStu_id());
            pst.setDate(2, (Date) certificate.getIssue_date()); // Assuming getIssue_date() returns a java.sql.Date
            pst.setInt(3, certificate.getCtid());
            pst.setString(4, certificate.getCer_id());
            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int delete(Certificate certificate) {

        String sql = "DELETE FROM `certificate` WHERE `cer_id`=?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, certificate.getCer_id());

            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int delete(String code) {

        String sql = "DELETE FROM `certificate` WHERE `cer_id`=?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, code);
            return pst.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
