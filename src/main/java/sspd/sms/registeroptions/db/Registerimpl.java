package sspd.sms.registeroptions.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import sspd.sms.DAO.Taskdao;
import sspd.sms.registeroptions.model.Register;
import sspd.sms.registeroptions.model.RegisterView;

import java.sql.Date;
import java.util.ArrayList;
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

            String hql = """
                    
                    SELECT r.re_date, s.stu_id, s.stu_name, c.class_name, co.course_name 
                                        FROM register r JOIN student s ON r.student.id= s.stu_id
                                        JOIN Classes c ON r.classes.id= c.class_id
                                        JOIN Course co ON c.course.id = co.course_id
                    
                    """;

            List<Object[]> results = session.createQuery(hql).list();

            List<RegisterView> registerViews = new ArrayList<>();

            for(Object[] row : results){

                Date re_date = (Date)row[0];
                String stu_id = (String) row[1];
                String stu_name = (String) row[2];
                String class_name = (String) row[3];
                String course_name = (String) row[4];
                RegisterView registerView = new RegisterView(re_date, stu_id, stu_name, class_name, course_name);
                registerViews.add(registerView);


            }

            return  registerViews;


        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }



    }



    @Override
    public void insertTask(Register task) {

    }

    public void insertTask(Register task,Session session){


        if(session ==null){

            try (Session newSession = sessionFactory.openSession()) {
                Transaction tx = newSession.beginTransaction();
                newSession.persist(task);
                tx.commit();
            }

        }else {
            session.persist(task);
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
