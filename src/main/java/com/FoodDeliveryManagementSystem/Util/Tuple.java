package com.FoodDeliveryManagementSystem.Util;

/**
 * tuple class
 * @param <T> the first element type
 * @param <E> the second element type
 */
public class Tuple<T,E> {
    /**
     * first element
     */
    private T first;
    /**
     * second element
     */
    private E second;

    /**
     * constructs a tuple from two elements
     * @param first the first element
     * @param second the second element
     */
    public Tuple(T first, E second) {
        this.first = first;
        this.second = second;
    }

    /**
     * getter for the first.
     * @return getter for the first
     */
    public T getFirst() {
        return first;
    }

    /**
     * getter for the Second
     * @return getter for the second
     */
    public E getSecond() {
        return second;
    }

}
