package sspd.sms.studentoptions.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import sspd.sms.DAO.Taskdao;
import sspd.sms.registeroptions.model.RegisterView;
import sspd.sms.studentoptions.model.Student;
import java.util.List;

@Repository
public class Studentimpl implements Taskdao<Student> {

    private final SessionFactory sessionFactory;

    public Studentimpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }


    public List<RegisterView> getActiveStudents(){

            String hql = """
                    
                
                    
                    
                    
                    
                    """;


    }


    @Override
    public List<Student> getAllTask() {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM student", Student.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public void insertTask(Student task) {



    }


    public void insertTask(Student task, Session session) {



        if (session == null) {

            try (Session newSession = sessionFactory.openSession()) {
                Transaction tx = newSession.beginTransaction();
                newSession.persist(task);
                tx.commit();
            }
        } else {

            session.persist(task);
        }
    }



    @Override
    public void updateTask(Student task) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(task);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();

        }


    }

    @Override
    public void deleteTask(Student task) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(task);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();

        }


    }

    @Override
    public void bactchTask(List<Student> list) {

    }
}
