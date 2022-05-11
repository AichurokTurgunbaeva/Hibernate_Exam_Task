package peaksoft.house;

import peaksoft.house.enums.OrderStatus;
import peaksoft.house.enums.SupplierStatus;
import peaksoft.house.models.Address;
import peaksoft.house.models.Customer;
import peaksoft.house.models.Order;
import peaksoft.house.models.Supplier;
import peaksoft.house.services.CustomerService;
import peaksoft.house.services.OrderService;
import peaksoft.house.services.SupplierService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static peaksoft.house.enums.OrderStatus.DELIVERED;
import static peaksoft.house.enums.OrderStatus.REQUEST;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        CustomerService customerService = new CustomerService();
        OrderService orderService = new OrderService();
        SupplierService supplierService = new SupplierService();

//        customerService.save(new Customer("Aichurok",996220009093L));
//        customerService.save(new Customer("Aziza",996556242415L));
//        customerService.save(new Customer("Chynara",996999554466L));
//        customerService.save(new Customer("Kunzaada",996559363636L));

//        customerService.makeAnOrder(1L,new Order(LocalDateTime.now(), BigDecimal.valueOf(3000),OrderStatus.DELIVERED));

//        customerService.cancelOrder(1L,1L);

//        customerService.deleteById(1L);

//        customerService.update(2L,new Customer("Klara",996220151515L));

//        customerService.findAll().forEach(System.out::println);

//        customerService.findAllOrders(1L, OrderStatus.DELIVERED);
//        System.out.println( "Found" );

//        orderService.save(new Order(LocalDateTime.now(), BigDecimal.valueOf(25500), DELIVERED));
//        System.out.println(orderService.findById(2L));

        Address address = new Address("Kyrgyzstan","Razzakov","Batken","Vostochnaya 29/1");
        Order order = new Order(LocalDateTime.now(), BigDecimal.valueOf(5000), REQUEST);
        Supplier supplier = new Supplier("Dilbara",755020201, SupplierStatus.FREE, List.of(order));
//
        Customer customer = new Customer("Dinara",996771252970L,List.of(order),address);
        Supplier supplier1 = new Supplier("Azat",777047445,SupplierStatus.FREE,List.of(order));
        Customer customer1 = new Customer("Ulan",9967712978570L,List.of(order),address);
        Order order1 = new Order(LocalDateTime.now(),BigDecimal.valueOf(1000), REQUEST,new Address("Kyrgyzstan","Talas","Talas","Manas-Ata/56"),
                new Address("USA","Houston","Texas","Grove avenue, 21"),customer,supplier);
//        customerService.save(customer1);
//        supplierService.save(supplier1);
//        orderService.save(order1);

    }
}
