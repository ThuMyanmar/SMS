package sspd.sms.DAO;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface Taskdao <T>{

    public List<T> getAllTask();

    public void insertTask(T task);
    public void updateTask(T task);
    public void deleteTask(T task);
    public void bactchTask(List<T> list);

}
