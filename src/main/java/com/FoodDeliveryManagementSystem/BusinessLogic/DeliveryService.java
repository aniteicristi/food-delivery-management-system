package com.FoodDeliveryManagementSystem.BusinessLogic;

import com.FoodDeliveryManagementSystem.DataAccess.Serializator;
import com.FoodDeliveryManagementSystem.Util.Tuple;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The delivery service class is a singleton which holds one single item of type DeliveryService and makes it
 * available to all the presentations of the application.
 *
 * @invariant WellFormed()
 */
public class DeliveryService extends Observable implements IDeliveryServiceProcessing {

    /**
     * The single instance
     */
    private static final DeliveryService service = new DeliveryService();
    /**
     * The products are stored in a hashset of MenuItems to avoid duplicates.
     * All the products are lazily loaded when needed.
     */
    private HashSet<MenuItem> products = null;
    /**
     * The users registered with our application.
     * All users are lazily loaded when the application needs them.
     */
    private ArrayList<User> users = null;
    /**
     * The orders are stored inside a hash map which takes as key the order object and maps it to an arraylist of
     * menu items which represent the ordered products.
     * All orders are lazily loaded when needed.
     */
    private HashMap<Order, ArrayList<MenuItem>> orders = null;

    /**
     * @return the single instance of the Delivery Service class
     */
    public static DeliveryService getService(){
        return service;
    }

    /**
     * @param products a collection of menu items.
     */
    public void setProducts( Collection<? extends MenuItem> products) {
        assert products != null && products.size() > 0;
        if(service.products == null) {
            service.products = new HashSet<>(products);
        }
        else {
            service.products.clear();
            service.products.addAll(products);
        }
        Serializator.persistTo("products.txt", new ArrayList<>(products));
    }

    /**
     *
     * @return the products of the delivery service
     */
    public HashSet<MenuItem> getProducts() {
        if(service.products == null){
            service.products = new HashSet<>(Serializator.loadFrom("products.txt", MenuItem.class));
        }
        System.out.println(service.products.size());
        return service.products;
    }

    /**
     * @param e the product to be inserted
     */
    public void AddProduct(MenuItem e){
        assert e != null;
        if(service.products == null){
            System.out.println("loaded");
            service.products = new HashSet<>(Serializator.loadFrom("products.txt", MenuItem.class));
        }
        System.out.println(e);
        service.products.add(e);
        Serializator.persistTo("products.txt", new ArrayList<>(service.products));
        assert service.getProducts().contains(e);
    }

    /**
     * @return the users of the delivery service
     */
    public ArrayList<User> getUsers() {
        if(service.users == null)
            service.users = Serializator.loadFrom("accounts.txt", User.class);
        return service.users;
    }

    /**
     * @param e the user to be added.
     */
    public void AddUser(User e){
        assert e != null;
        if(service.users == null)
            service.users = Serializator.loadFrom("accounts.txt", User.class);
        service.users.add(e);
        Serializator.persistTo("accounts.txt", new ArrayList<>(users));
        assert getUsers().contains(e);
    }

    /**
     * @param order the order to be added.
     * @param cartItems the associated items, ordered
     */
    public void AddOrder(Order order, ArrayList<MenuItem> cartItems) {
        assert order.getTotal() == cartItems.stream().mapToLong(MenuItem::getPrice).sum();

        assert service.WellFormed();
        //verify that the order is "well formed"
        if(service.orders == null)
            service.orders = Serializator.loadMapFrom("orders.txt");
        service.orders.put(order, cartItems);
        Serializator.persistMapTo("orders.txt", service.orders);
        service.setChanged();
        service.notifyObservers(new Tuple<>(order, cartItems));
        assert service.WellFormed();
    }

    /**
     * The wellFormed method verifies if all the entries in the order map are corespondent to the right
     * menuItems by checking the sum of the item's cost.
     * @return true if the class is well formed.
     */
    private boolean WellFormed() {
        boolean itIs = true;
        for(Map.Entry<Order, ArrayList<MenuItem>> e:service.orders.entrySet()){
            itIs &= e.getKey().getTotal() == e.getValue().stream().mapToLong(MenuItem::getPrice).sum();
        }
        return itIs;
    }

    /**
     * Returns the orders-menuItems map. If the set is null, then it is deserialized from orders.txt
     * @return the orders-menuItems map
     */
    public HashMap<Order, ArrayList<MenuItem>> getOrders() {
        if(service.orders == null)
            service.orders = Serializator.loadMapFrom("orders.txt");
        return service.orders;
    }

    /**
     * @param item the item to be removed
     */
    public void RemoveProduct(MenuItem item) {
        assert item != null;
        if(service.products == null)
            service.products = new HashSet<>(Serializator.loadFrom("products.txt", MenuItem.class));
        service.products.removeIf(i -> i.equals(item));
        assert service.getProducts().stream().noneMatch(item::equals);
    }

    /**
     * Adds the specified observer to the observer list of this object
     * @param observer the observer to be added
     */
    public void Subscribe(Observer observer){
        service.addObserver(observer);
    }

    /**
     * @param from the lower bound of the hourly interval
     * @param to the upper bound of the hourly interval
     * @return the list or orders
     */
    public List<Order> reportOrderTimeInterval(int from, int to) {
        assert from >= 0 && to <= 24;
        return service.orders.keySet().stream()
                .filter(order -> order.getOrderDate().getHour() >= from)
                .filter(order -> order.getOrderDate().getHour() <= to)
                .collect(Collectors.toList());
    }

    /**
     * @param times the number of times the items were ordered.
     * @return the list of menuitems ordered "times" times.
     */
    public List<MenuItem> reportNumberOfTimes(int times){
        assert times > 0;
        return service.products.stream()
                .filter(menuItem -> //get all products which match
                    service.orders.values().stream() //get all order values: array of products
                            .mapToLong(value -> value.stream() //for each array of products
                                    .filter(menuItem::equals) //take only the products equal to menu item
                                    .count() //count them
                            )
                            .sum() >= times //sum all of them and compare to times
                )
                .collect(Collectors.toList());
    }

    /**
     * @param times the total times the user ordered.
     * @param value the minimal value of the order
     * @return the users who ordered more than the times specified and the value was greater than value.
     */
    public List<User> reportNumberOfClients(int times, int value) {
        assert times >= 0 && value > 0;
        return service.users.stream()
                .filter(user -> user.getRole().equals("client"))
                .filter(user ->
                    service.orders.keySet().stream()
                        .filter(order -> order.getTotal() >= value && order.getClientEmail().equals(user.getEmail()))
                        .count() >= times
                )
                .collect(Collectors.toList());
    }

    public List<Tuple<String, Long>> reportProductsInADay(LocalDate time){
        assert time != null;
        return service.orders.keySet().stream()
                .filter(order -> order.getOrderDate().toLocalDate().equals(time))
                .map(order -> service.orders.get(order))
                .reduce(new ArrayList<MenuItem>(), (sub,next) -> {sub.addAll(next); return sub;})
                .stream()
                .collect(Collectors.groupingBy(MenuItem::getTitle, Collectors.counting()))
                .entrySet().stream()
                .map(stringLongEntry -> new Tuple<>(stringLongEntry.getKey(), stringLongEntry.getValue()))
                .collect(Collectors.toList());
    }
}
