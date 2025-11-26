package repository.impl;

import model.entity.ItemEntity;
import model.entity.SupplierEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.ItemRepository;
import util.HibernateUtil;

import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {
    @Override
    public boolean save(ItemEntity itemEntity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.persist(itemEntity);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            return false;

        } finally {
            session.close();
        }
    }

    @Override
    public List<ItemEntity> getAllItems() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("FROM ItemEntity", ItemEntity.class).list();
    }

    @Override
    public boolean update(ItemEntity itemEntity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            ItemEntity entity = session.find(ItemEntity.class, itemEntity.getId());

            entity.setName(itemEntity.getName());
            entity.setCategory(itemEntity.getCategory());
            entity.setSubCategory(itemEntity.getSubCategory());
            entity.setBrand(itemEntity.getBrand());
            entity.setSize(itemEntity.getSize());
            entity.setColor(itemEntity.getColor());
            entity.setQty(itemEntity.getQty());
            entity.setCostPrice(itemEntity.getCostPrice());
            entity.setSellingPrice(itemEntity.getSellingPrice());
            entity.setDescription(itemEntity.getDescription());
            entity.setImgPath(itemEntity.getImgPath());

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
}
