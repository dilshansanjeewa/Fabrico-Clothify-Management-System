package repository.impl;

import model.entity.OrderDetailEntity;
import model.entity.OrderEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.ItemRepository;
import repository.OrderRepository;
import util.HibernateUtil;

import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {

    ItemRepository itemRepository = new ItemRepositoryImpl();

    @Override
    public Long getLastId() {
        return HibernateUtil.getSessionFactory().openSession()
                .createQuery("SELECT MAX(o.id) FROM OrderEntity o",Long.class).uniqueResult();
    }

    @Override
    public boolean savOrder(OrderEntity orderEntity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {

            for (OrderDetailEntity orderDetailEntity: orderEntity.getOrderDetailEntities()){
                itemRepository.updateQty(session, orderDetailEntity);
            }

            session.persist(orderEntity);
            transaction.commit();
            return true;

        }catch (Exception e){

            transaction.rollback();
            throw new RuntimeException(e);
//            return false;

        }finally {
            session.close();
        }
    }

    @Override
    public List<OrderEntity> getAllOrders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("From OrderEntity", OrderEntity.class).list();
    }

    @Override
    public OrderEntity getOrder(String visibleId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<OrderEntity> query = session.createQuery("SELECT o FROM OrderEntity o WHERE o.orderCode = :oid", OrderEntity.class);
        Query<OrderEntity> oid = query.setParameter("oid", visibleId);
        OrderEntity singleResult = oid.getSingleResult();

        return singleResult;
    }

}
