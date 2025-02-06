package sspd.sms.classoptions.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sspd.sms.DAO.Taskdao;
import sspd.sms.classoptions.db.ClasshasTeacherimpls;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.classoptions.model.ClasshasTeacher;
import sspd.sms.courseoptions.model.Course;
import sspd.sms.errorHandler.DataAccessException;
import sspd.sms.errorHandler.ServiceException;
import sspd.sms.teacheroptions.model.Teacher;
import sspd.sms.teacheroptions.model.TeacherSubject;
import sspd.sms.teacheroptions.services.TeacherServices;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClassHasTeacherService implements Taskdao<ClasshasTeacher> {


    private final ClasshasTeacherimpls ctdb;

    private final TeacherServices teacherServices;

    private final Validator validator;

    private final ClasshasTeacher classhasTeacher;

    private final ClassesService classService;

    public ClassHasTeacherService(ClasshasTeacherimpls ctdb, TeacherServices teacherServices, Validator validator, ClasshasTeacher classhasTeacher,ClassesService classService) {

        this.ctdb = ctdb;
        this.teacherServices = teacherServices;
        this.validator = validator;
        this.classhasTeacher = classhasTeacher;
        this.classService = classService;
    }


    @Override
    public List<ClasshasTeacher> getAllTask() {
        return ctdb.getAllTask();
    }

    public List<Teacher> getAvailableTeachers(int classId) {


        List<TeacherSubject> teacherList = teacherServices.getTeacherSubjects();


        Set<Integer> assignedTeacherIds = setAssignedTeachers(classId).stream()
                .map(Teacher::getTeacher_id)
                .collect(Collectors.toSet());

        return teacherList.stream()
                .map(TeacherSubject::getTeacher)
                .filter(teacher -> !assignedTeacherIds.contains(teacher.getTeacher_id())) // O(1) contains check
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Teacher::getTeacher_id))))
                .stream().toList();
    }

    public List<Teacher>setAssignedTeachers(int classId) {


        List<ClasshasTeacher> teacherAll =getAllTask();


        return new ArrayList<>(teacherAll.stream()
                .filter(classhasTeacher1 -> classhasTeacher1.getClasses().getClass_id() == classId)
                .map(ClasshasTeacher::getTeacher).toList());

    }

    public int getTeacherhasClassID(int classId , int teacherId) {

        List<ClasshasTeacher> teacherAll = getAllTask();

       return teacherAll.stream()
                .filter(classhasTeacher -> classhasTeacher.getClasses().getClass_id() == classId && classhasTeacher.getTeacher().getTeacher_id() == teacherId)
                .map(ClasshasTeacher::getTeacher_classid).findFirst().orElse(-1);


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

        Set<ConstraintViolation<ClasshasTeacher>> violations = validator.validate(task);
        if (!violations.isEmpty()) {
            String errors = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining("\n"));
            throw new ServiceException("Validation errors:" + errors);
        }

        else {


            try {
                ctdb.deleteTask(task);
            } catch (ConstraintViolationException e) {
                throw new ServiceException("Validation failed for: " + e.getConstraintName(), e);
            } catch (DataAccessException e) {
                throw new ServiceException("Database error", e);
            }

        }

    }

    @Override
    public void bactchTask(List<ClasshasTeacher> list) {

    }






}
