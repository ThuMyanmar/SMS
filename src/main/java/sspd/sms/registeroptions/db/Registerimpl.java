package sspd.sms.registeroptions.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import sspd.sms.DAO.Taskdao;
import sspd.sms.registeroptions.model.Register;
import sspd.sms.registeroptions.model.RegisterView;

import java.util.List;

@Repository
public class Registerimpl implements Taskdao<Register> {

    private final SessionFactory sessionFactory;

    public Registerimpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    @Override
    public List<Register> getAllTask() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM register ", Register.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<RegisterView>getRegisterView(){


        try (Session session = sessionFactory.openSession()) {

            String hql = "SELECT r.re_date, s.stu_id, s.stu_name, c.class_name, co.course_name " +
                    "FROM register r " +
                    "JOIN student s ON r.student.id= s.stu_id " +
                    "JOIN Classes c ON r.classes.id= c.class_id " +
                    "JOIN Course co ON c.course.id = co.course_id";


            return session.createQuery(hql).list();

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }



    }



    @Override
    public void insertTask(Register task) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(task);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();

        }


    }

    @Override
    public void updateTask(Register task) {

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
    public void deleteTask(Register task) {

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
    public void bactchTask(List<Register> list) {

    }
}
