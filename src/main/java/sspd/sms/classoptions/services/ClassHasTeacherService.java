package sspd.sms.classoptions.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sspd.sms.DAO.Taskdao;
import sspd.sms.classoptions.db.ClasshasTeacherimpls;
import sspd.sms.classoptions.model.ClasshasTeacher;
import sspd.sms.errorHandler.DataAccessException;
import sspd.sms.errorHandler.ServiceException;
import sspd.sms.teacheroptions.model.Teacher;
import sspd.sms.teacheroptions.model.TeacherSubject;
import sspd.sms.teacheroptions.services.TeacherServices;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClassHasTeacherService implements Taskdao<ClasshasTeacher> {


    private final ClasshasTeacherimpls ctdb;

    private final TeacherServices teacherServices;

    private final Validator validator;
    private final ClasshasTeacher classhasTeacher;

    public ClassHasTeacherService(ClasshasTeacherimpls ctdb, TeacherServices teacherServices, Validator validator, ClasshasTeacher classhasTeacher) {

        this.ctdb = ctdb;
        this.teacherServices = teacherServices;
        this.validator = validator;
        this.classhasTeacher = classhasTeacher;
    }


    @Override
    public List<ClasshasTeacher> getAllTask() {
        return ctdb.getAllTask();
    }

    public List<Teacher> getclassFilterTeacher(int classId) {

        List<ClasshasTeacher> teacherAll = getAllTask();


        return teacherAll.stream()
                .filter(classhasTeacher -> classhasTeacher.getClasses().getClass_id() == classId
                        && getclassFilterTeacherList(classId).stream()
                        .noneMatch(ct -> ct.getTeacher_id() == classhasTeacher.getTeacher().getTeacher_id()))
                .map(ClasshasTeacher::getTeacher)
                .collect(Collectors.toList());
    }


    public List<Teacher>getclassFilterTeacherList(int classId) {


        List<ClasshasTeacher> teacherAll =getAllTask();

        List<Teacher> getTeacherLiist = new ArrayList<>(teacherAll.stream()
                .filter(classhasTeacher -> classhasTeacher.getClasses().getClass_id() == classId)
                .map(ClasshasTeacher::getTeacher).toList());



        return getTeacherLiist;



    }

    @Override
    public void insertTask(ClasshasTeacher task) {

        Set<ConstraintViolation<ClasshasTeacher>> violations = validator.validate(task);
        if (!violations.isEmpty()) {
            String errors = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining("\n"));
            throw new ServiceException("Validation errors:" + errors);
        }

        else {

            try {
                ctdb.insertTask(task);
            } catch (ConstraintViolationException e) {
                throw new ServiceException("Validation failed for: " + e.getConstraintName(), e);
            } catch (DataAccessException e) {
                throw new ServiceException("Database error", e);
            }

        }

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




}
