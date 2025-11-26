package repository.impl;

import model.entity.BrandEntity;
import model.entity.SupplierEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.BrandRepository;
import util.HibernateUtil;

import java.util.List;

public class BrandRepositoryImpl implements BrandRepository {

    @Override
    public boolean save(BrandEntity brandEntity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.persist(brandEntity);
            transaction.commit();

            return true;

        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            return false;

        } finally {
            session.close();
        }
    }

    @Override
    public List<BrandEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("FROM BrandEntity", BrandEntity.class).list();
    }
}
