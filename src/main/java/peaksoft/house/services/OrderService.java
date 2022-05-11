package peaksoft.house.services;

import jakarta.persistence.EntityManagerFactory;
import peaksoft.house.configuration.Configuration;
import peaksoft.house.models.Customer;
import peaksoft.house.models.Order;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * @author Beksultan
 */
public class OrderService implements AutoCloseable{
    public final EntityManagerFactory entityManagerFactory = Configuration.createEntityManagerFactory();


    public void save(Order newOrder) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.detach(newOrder);
    entityManager.getTransaction().commit();
    entityManager.close();

    }

    public void update(Long orderId, Order newOrder) {
        // update order with id = orderId
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Order o set o.date = :date , o.price = :price, o.status = :status where o.id = :id")
                .setParameter("date", newOrder.getDate())
                .setParameter("price", newOrder.getPrice())
                .setParameter("id", orderId).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Order> findAllOrders() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Order> orders = (List<Order>) entityManager.createNativeQuery("select o from Order o", Order.class);
        entityManager.getTransaction().commit();
        entityManager.close();
        return orders;
    }

    public Order  findById(Long orderId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Order order = entityManager.createQuery("select o from Order o where o.id = ?1", Order.class)
                .setParameter(1, orderId)
                .getSingleResult();

        entityManager.getTransaction().commit();

        entityManager.close();

        return order;
    }

    public void deleteById(Long orderId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Order.class,orderId));
        entityManager.getTransaction().commit();
        System.out.println("You've successfully removed user from users table");
        entityManager.close();

    }

    @Override
    public void close() throws Exception {

    }
}
