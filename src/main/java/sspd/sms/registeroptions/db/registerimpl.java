package sspd.sms.registeroptions.db;

import jakarta.validation.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import sspd.sms.DAO.DataAccessObject;
import sspd.sms.courseoptions.model.Course;
import sspd.sms.registeroptions.Models.Register;

import java.util.List;

public class registerimpl implements DataAccessObject<Register> {

    private final SessionFactory sessionFactory;

    private final Validator validator;


    public registerimpl(SessionFactory sessionFactory, Validator validator) {
        this.sessionFactory = sessionFactory;
        this.validator = validator;
    }

    @Override
    public List<Register> getDataList() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("from register").list();
        return List.of();
    }

    @Override
    public int create(Register register) {
        return 0;
    }

    @Override
    public int create(Course course) {
        return 0;
    }

    @Override
    public int update(Register register) {
        return 0;
    }

    @Override
    public int delete(Register register) {
        return 0;
    }

    @Override
    public int delete(String code) {
        return 0;
    }
}
