package peaksoft.house.services;

import peaksoft.house.configuration.Configuration;
import peaksoft.house.enums.OrderStatus;
import peaksoft.house.models.Customer;
import peaksoft.house.models.Order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 * @author Beksultan
 */
public class CustomerService implements AutoCloseable{
    public final EntityManagerFactory entityManagerFactory = Configuration.createEntityManagerFactory();
    public void save(Customer newCustomer) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    entityManager.persist(newCustomer);
    entityManager.getTransaction().commit();
    entityManager.close();
        // save a newCustomer to database
    }

    public void makeAnOrder(Long customerId, Order newOrder) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Customer customer = entityManager.find(Customer.class, customerId);
        customer.setOrder(newOrder);
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        entityManager.close();

        // find customer and give order to customer
    }

    public void cancelOrder(Long customerId, Long orderId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Order o set o.status = :status where o.customer.id=:id and o.id=:oId")
                        .setParameter("status",OrderStatus.CANCELED)
                                .setParameter("id", customerId)
                                        .setParameter("oId",orderId).executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();

        // find customer with :customerId and find customer's order with id = :orderId
        // and setOrder status CANCELED
    }

    public void update(Long customerId, Customer newCustomer) {
        // update customer with id = :customerId to newCustomer
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Customer c set c.fullName=:fullName,c.phoneNumber = :phoneNumber where c.id = :id")
                        .setParameter("fullName",newCustomer.getFullName())
                        .setParameter("phoneNumber",newCustomer.getPhoneNumber())
                                .setParameter("id",customerId).executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Order> findAllOrders(Long customerId, OrderStatus status) {
        // find all orders by :orderStatus where customer id = :customerId
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Order> orders = entityManager.createQuery("select o from Order o join Customer c on o.id = c.id where c.id = :id and o.status = :status",Order.class)
                .setParameter("id",customerId)
                .setParameter("status",status).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return null;
    }

    public List<Customer> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Customer> customers =entityManager.createQuery("select c from Customer c",Customer.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();

        return customers;
    }

    public Customer findById(Long customerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Customer customer = (Customer) entityManager.createNativeQuery("select c from Customer c where c.id = :customerId",
                        Customer.class)
                .setParameter("id", customerId)
                .getSingleResult();

        entityManager.getTransaction().commit();

        entityManager.close();

        return customer;
    }

    public void deleteById(Long customerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Customer.class,customerId));
        entityManager.getTransaction().commit();
        System.out.println("You've successfully removed user from users table");
        entityManager.close();
    }

    @Override
    public void close() throws Exception {
        entityManagerFactory.close();
    }
}
