package repository.impl;

import model.entity.EmployeeEntity;
import model.entity.ItemEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.EmployeeRepository;
import util.HibernateUtil;

import java.util.List;

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

    @Override
    public List<EmployeeEntity> getAllEmployees() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createQuery("FROM EmployeeEntity", EmployeeEntity.class).list();
    }

    @Override
    public boolean updateEmployee(EmployeeEntity employeeEntity) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            EmployeeEntity entity = session.find(EmployeeEntity.class, employeeEntity.getId());

            entity.setFirstName(employeeEntity.getFirstName());
            entity.setLastName(employeeEntity.getLastName());
            entity.setGender(employeeEntity.getGender());
            entity.setDob(employeeEntity.getDob());
            entity.setEmail(employeeEntity.getEmail());
            entity.setPhone(employeeEntity.getPhone());
            entity.setProvince(employeeEntity.getProvince());
            entity.setDistrict(employeeEntity.getDistrict());
            entity.setStreetAddress(employeeEntity.getStreetAddress());
            entity.setPostalCode(employeeEntity.getPostalCode());
            entity.setImgPath(employeeEntity.getImgPath());

            transaction.commit();
            return true;

        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException(e);
//            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean deleteEmployee(EmployeeEntity employeeEntity) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.remove(employeeEntity);

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
}
