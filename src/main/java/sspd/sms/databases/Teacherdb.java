package sspd.sms.databases;

import sspd.sms.DAO.DataAccessObject;
import sspd.sms.DAO.DatabaseConnect;
import sspd.sms.teacheroptions.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Teacherdb implements DataAccessObject<Teacher> {

    private Connection con  = DatabaseConnect.getInstance().getConnection();

    @Override
    public List<Teacher> getDataList() {

        String sql = "SELECT * FROM `teacher` WHERE 1";

        try(PreparedStatement pst = con.prepareStatement(sql)) {


            ResultSet rs = pst.executeQuery();
            List<Teacher> list = new ArrayList<>();

            while(rs.next()) {

                Teacher t = new Teacher(

                        rs.getInt("teacher_id"),
                        rs.getString("name"),
                        rs.getString("qualification"),
                        rs.getString("contact"),
                        rs.getString("email")


                );

                list.add(t);


            }
            return list;


        } catch (SQLException e) {



            throw new RuntimeException(e);
        }



    }

    @Override
    public int create(Teacher teacher) {

        String sql = "INSERT INTO `teacher`( `name`, `qualification`, `contact`, `email`) VALUES (?,?,?,?)";


        try(PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, teacher.getName());
            pst.setString(2, teacher.getQualification());
            pst.setString(3, teacher.getContact());
            pst.setString(4, teacher.getEmail());
            return pst.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public int update(Teacher teacher) {
        return 0;
    }

    @Override
    public int delete(Teacher teacher) {
        return 0;
    }

    @Override
    public int delete(String code) {
        return 0;
    }
}
