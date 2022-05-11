package peaksoft.house.services;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import peaksoft.house.configuration.Configuration;
import peaksoft.house.enums.OrderStatus;
import peaksoft.house.enums.SupplierStatus;
import peaksoft.house.models.Order;
import peaksoft.house.models.Supplier;

import java.util.List;

import static peaksoft.house.enums.SupplierStatus.BUSY;

/**
 * @author Beksultan
 */
public class SupplierService implements AutoCloseable{
    public final EntityManagerFactory entityManagerFactory = Configuration.createEntityManagerFactory();

    // create objects of repositories

    public void save(Supplier newSupplier) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(newSupplier);
        entityManager.getTransaction().commit();
        entityManager.close();
        // write your code here
    }

    public void update(Long supplierId, Supplier newSupplier) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Supplier s SET s.fullName = :fulName, s.phoneNumber = :phoneNumber, s.status = :status where s.id = :id")
                .setParameter("fullName", newSupplier.getFullName())
                .setParameter("phoneNumber", newSupplier.getPhoneNumber())
                .setParameter("id", supplierId).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
        // write your code here
        // you should find supplier with id = :supplierId
        // and replace with newSupplier
    }

    public List<Supplier> findAllSuppliers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Supplier> suppliers = (List<Supplier>) entityManager.createNativeQuery
                ("select s from Supplier s", Supplier.class);
        entityManager.getTransaction().commit();
        entityManager.close();
        // write your code here
        // you should find all suppliers and return them
        return suppliers;
    }

    public Supplier findById(Long supplierId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Supplier supplier = (Supplier) entityManager.createNativeQuery("select s from Supplier s where c.id = :supplierId",
                        Supplier.class)
                .setParameter("id", supplierId)
                .getSingleResult();

        entityManager.getTransaction().commit();

        entityManager.close();

        return supplier;
        // write your code here
        // you should return supplier with id = :supplierId

    }

    public void getOrder(Long supplierId, Long orderId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Supplier supplier = entityManager.find(Supplier.class, supplierId);
        Order order = entityManager.find(Order.class, orderId);
        if (supplier.getStatus().equals(SupplierStatus.FREE) && order.getStatus().equals(OrderStatus.REQUEST)) {
            supplier.addOrder(order);
            Supplier supplier1 = entityManager.find(Supplier.class, supplierId);
            supplier1.setStatus(BUSY);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        // give free order to supplier with id = :supplierId
    }

    public void deleteById(Long supplierId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Supplier.class, supplierId));
        entityManager.getTransaction().commit();
        entityManager.close();
        // write your code here
        // you should delete supplier with id = :supplierId
    }

    public List<Supplier> findAllOrders(Long supplierId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Supplier> suppliers = (List<Supplier>) entityManager.createNativeQuery(
                "select s from Supplier s",Supplier.class );
        entityManager.getTransaction().commit();
        entityManager.close();

        return suppliers;
    }

    public List<Supplier> findAllBusySuppliers() {
         EntityManager entityManager = entityManagerFactory.createEntityManager();
         entityManager.getTransaction().begin();
        List<Supplier> busy = entityManager.createQuery("select s from Supplier s where s.status = ?1", Supplier.class)
                .setParameter(1, BUSY).getResultList();
        entityManager.getTransaction().commit();
         entityManager.close();
        // find all busy suppliers
        return busy;
    }

    @Override
    public void close() throws Exception {

    }
}
