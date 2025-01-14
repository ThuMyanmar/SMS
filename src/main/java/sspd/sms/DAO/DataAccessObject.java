package sspd.sms.DAO;

import java.util.List;

public interface DataAccessObject <T> {

    public List<T> getDataList();

    public int create(T t);

    public int update(T t);

    public int delete(T t);

    public int delete(String code);






}
