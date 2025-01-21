package sspd.sms.databases;

import sspd.sms.DAO.DataAccessObject;
import sspd.sms.courseoptions.module.Course;
import sspd.sms.useroptions.model.User;

import java.util.List;

public class Userdb implements DataAccessObject<User> {



    @Override
    public List<User> getDataList() {
        return List.of();
    }

    @Override
    public int create(User user) {
        return 0;
    }

    @Override
    public int create(Course course) {
        return 0;
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int delete(User user) {
        return 0;
    }

    @Override
    public int delete(String code) {
        return 0;
    }


}
