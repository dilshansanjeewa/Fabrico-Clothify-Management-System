package repository.impl;

import model.entity.SupplierEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.SupplierRepository;
import util.HibernateUtil;

import java.util.List;

public class SupplierRepositoryImpl implements SupplierRepository {
    @Override
    public boolean save(SupplierEntity supplierEntity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.persist(supplierEntity);
            transaction.commit();
            return true;

        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            return false;

        } finally {
            session.close();
        }

    }

    @Override
    public List<SupplierEntity> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("FROM SupplierEntity", SupplierEntity.class).list();
    }

    @Override
    public boolean update(SupplierEntity supplierEntity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            SupplierEntity entity = session.find(SupplierEntity.class, supplierEntity.getId());

            entity.setName(supplierEntity.getName());
            entity.setEmail(supplierEntity.getEmail());
            entity.setPhone(supplierEntity.getPhone());
            entity.setAddress(supplierEntity.getAddress());

            transaction.commit();
            return true;

        }catch (Exception e){
            if(transaction != null) {
                transaction.rollback();
            }
            return false;

        }finally {
            session.close();
        }
    }

    @Override
    public boolean delete(SupplierEntity supplierEntity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.remove(supplierEntity);

            transaction.commit();
            return true;

        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
                return false;
            }

        } finally {
            session.close();
        }
        return false;
    }
}
