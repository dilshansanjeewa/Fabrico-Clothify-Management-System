package repository.impl;

import model.entity.EmployeeEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.EmployeeRepository;
import util.HibernateUtil;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Override
    public boolean save(EmployeeEntity employeeEntity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.persist(employeeEntity);

            transaction.commit();
            return true;

        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            return false;

        }finally {
            session.close();
        }

    }

    @Override
    public EmployeeEntity getLastEmployee() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        EmployeeEntity employeeEntity = session.createQuery("FROM EmployeeEntity ORDER BY id DESC", EmployeeEntity.class).setMaxResults(1).uniqueResult();

        return employeeEntity;
    }
}
