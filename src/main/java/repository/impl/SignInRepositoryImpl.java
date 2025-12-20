package repository.impl;

import model.entity.AdminEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.SignInRepository;
import util.HibernateUtil;

import java.util.List;

public class SignInRepositoryImpl implements SignInRepository {

    @Override
    public List<AdminEntity> getAll() {
        Transaction transaction = null;
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            List<AdminEntity> adminEntities = session.createQuery("FROM AdminEntity", AdminEntity.class).list();
            transaction.commit();

            return adminEntities;

        }catch (Exception e){
            if (transaction !=null) transaction.rollback();
            e.printStackTrace();
            return null;

        } finally {
            if(session != null) session.close();
        }
    }
}
