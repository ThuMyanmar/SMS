package sspd.sms.DAO;

import sspd.sms.courseoptions.model.Course;

import java.util.List;

public interface DataAccessObject <T> {

    public List<T> getDataList();

    public int create(T t);

    int create(Course course);

    public int update(T t);

    public int delete(T t);

    public int delete(String code);






}
