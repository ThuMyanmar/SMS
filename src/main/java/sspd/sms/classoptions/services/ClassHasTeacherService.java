package sspd.sms.classoptions.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sspd.sms.DAO.Taskdao;
import sspd.sms.classoptions.db.ClasshasTeacherimpls;
import sspd.sms.classoptions.model.ClasshasTeacher;
import sspd.sms.teacheroptions.model.Teacher;
import sspd.sms.teacheroptions.model.TeacherSubject;
import sspd.sms.teacheroptions.services.TeacherServices;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ClassHasTeacherService implements Taskdao<ClasshasTeacher> {


    private final ClasshasTeacherimpls ctdb;

    private final TeacherServices teacherServices;

    public ClassHasTeacherService(ClasshasTeacherimpls ctdb, TeacherServices teacherServices) {

        this.ctdb = ctdb;
        this.teacherServices = teacherServices;

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



        ctdb.updateTask(task);


    }

    @Override
    public void deleteTask(ClasshasTeacher task) {

        ctdb.deleteTask(task);

    }

    @Override
    public void bactchTask(List<ClasshasTeacher> list) {

    }
    public List<Teacher>getFilteredTeachers(int courseId) {

        return teacherServices.getTeacherSubjects().stream()
                .filter(
                        teacherSubject -> teacherSubject!=null &&
                        teacherSubject.getCourse()!=null &&
                        teacherSubject.getCourse().getCourse_id()==courseId)
                .map(TeacherSubject::getTeacher)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());



    }
    public class ServiceException extends RuntimeException {
        public ServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }


}
