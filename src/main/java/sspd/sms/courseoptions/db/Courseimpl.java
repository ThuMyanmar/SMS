package sspd.sms.courseoptions.db;

import jakarta.validation.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sspd.sms.DAO.Taskdao;
import sspd.sms.courseoptions.model.Course;

import java.util.List;

@Repository
public class Courseimpl implements Taskdao<Course> {


    private SessionFactory sessionFactory;

    private Validator validator;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }
    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }




    @Override
    public List<Course> getAllTask() {

        Session session = sessionFactory.openSession() ;
        session.beginTransaction();
        return session.createQuery("from Course c order by c.id desc ").list();

    }

    @Override
    public void insertTask(Course task) {

    }

    @Override
    public void updateTask(Course task) {

    }

    @Override
    public void deleteTask(Course task) {

    }

    @Override
    public void bactchTask(List<Course> list) {

    }
}
