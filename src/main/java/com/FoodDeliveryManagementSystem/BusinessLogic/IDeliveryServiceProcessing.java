package com.FoodDeliveryManagementSystem.BusinessLogic;

import com.FoodDeliveryManagementSystem.Util.Tuple;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * The IDeliveryServiceProcessing interface defines the main
 * methods a delivery service should implement, as well as
 * the pre and post conditions these methods must meet in order
 * to be deemed well formed.
 */
public interface IDeliveryServiceProcessing {

    /**
     * Returns all the available products on the instance.
     * @return the available products.
     */
    HashSet<MenuItem> getProducts();

    /**
     * Sets the products to the specified collection.
     * @param products a collection of menu items.
     * @pre  products != null && products.size() > 0
     */
    void setProducts(Collection<? extends MenuItem> products);

    /**
     * Adds the specified product to the set of products.
     * @param e the product to be inserted
     * @pre e != null
     * @post getProducts().contains(e)
     */
    void AddProduct(MenuItem e);

    /**
     * Removes the specified product from the set of products.
     * @param item the item to be removed
     * @pre item != null
     * @post getProducts().noneMatch(item::equals)
     */
    void RemoveProduct(MenuItem item);

    /**
     * Returns the list of registered users.
     * @return the list of registered users
     */
    ArrayList<User> getUsers();

    /**
     * Adds the specified user to the collection of users
     * @param e the user to be added.
     * @pre e != null
     * @post getUser().contains(e)
     */
    void AddUser(User e);

    /**
     * Adds a new entry inside the order map.
     * @param order the order to be added.
     * @param cartItems the associated items, ordered
     * @pre order.getTotal() == cartItems.stream().mapToInt(MenuItem::getPrice).sum()
     */
    void AddOrder(Order order, ArrayList<MenuItem> cartItems);

    /**
     * Returns a list of all the orders ordered between the hourly interval from and to
     * @param from the lower bound of the hourly interval
     * @param to the upper bound of the hourly interval
     * @return the list
     * @pre from >= 0 && to <= 24
     */
    List<Order> reportOrderTimeInterval(int from, int to);

    /**
     * Returns the menu items ordered more than the specified number of times
     * @param times the number of times the items were ordered.
     * @return the list of menu items.
     * @pre times > 0
     * @post @forall k: @result @
     *      getProducts().contains(k)
     */
    List<MenuItem> reportNumberOfTimes(int times);

    /**
     * Returns the users who have ordered more times than the number specified and their
     * order was greater in value than the value specified
     * @param times the total times the user ordered.
     * @param value the minimal value of the order
     * @return the list of users
     * @pre times >= 0 && value > 0
     */
    List<User> reportNumberOfClients(int times, int value);

    /**
     * Returns a tuple of the title of products ordered in a date, together with the amount they were orderd
     * @param time the date on which the products were ordered
     * @return a list of tuples containing a title, amount ordered.
     * @pre time != null
     */
    List<Tuple<String, Long>> reportProductsInADay(LocalDate time);
}
