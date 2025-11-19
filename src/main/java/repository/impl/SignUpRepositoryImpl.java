package repository.impl;

import model.entity.AdminEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.SignUpRepository;
import util.HibernateUtil;

public class SignUpRepositoryImpl implements SignUpRepository {

    Session session;
    Transaction transaction;

    @Override
    public void save(AdminEntity adminEntity) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        session.persist(adminEntity);

        transaction.commit();
        session.close();
    }
}
