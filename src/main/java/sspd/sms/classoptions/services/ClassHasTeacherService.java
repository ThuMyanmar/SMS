package sspd.sms.classoptions.services;

import org.springframework.stereotype.Service;
import sspd.sms.DAO.Taskdao;
import sspd.sms.classoptions.db.ClasshasTeacherimpls;
import sspd.sms.classoptions.model.ClasshasTeacher;

import java.util.List;

@Service
public class ClassHasTeacherService implements Taskdao<ClasshasTeacher> {


    private final ClasshasTeacherimpls ctdb;

    public ClassHasTeacherService(ClasshasTeacherimpls ctdb) {
        this.ctdb = ctdb;
    }

    @Override
    public List<ClasshasTeacher> getAllTask() {
        return ctdb.getAllTask();
    }

    @Override
    public void insertTask(ClasshasTeacher task) {

        ctdb.insertTask(task);

    }

    @Override
    public void updateTask(ClasshasTeacher task) {

        ctdb.deleteTask(task);


    }

    @Override
    public void deleteTask(ClasshasTeacher task) {

        ctdb.updateTask(task);

    }

    @Override
    public void bactchTask(List<ClasshasTeacher> list) {

    }
}
