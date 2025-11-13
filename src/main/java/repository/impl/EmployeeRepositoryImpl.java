package repository.impl;

import model.entity.EmployeeEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.EmployeeRepository;
import util.HibernateUtil;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Override
    public boolean save(EmployeeEntity employeeEntity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(employeeEntity);

        transaction.commit();
        session.close();
        HibernateUtil.shutdown();
        return true;
    }
}
