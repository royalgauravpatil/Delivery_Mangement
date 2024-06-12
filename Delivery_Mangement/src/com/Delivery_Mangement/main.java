package com.Delivery_Mangement;

import java.util.HashMap;
import java.util.Map;

class Node<E> {
    E key;
    E value;
    Node<E> prev, next;

    Node(E key, E value) {
        this.key = key;
        this.value = value;
    }
}

public class main<E> {
    private final int capacity;
    private final Map<E, Node<E>> cache;
    private Node<E> head, tail;

    public main(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    public E get(E key) {
        Node<E> node = cache.get(key);
        if (node == null) {
            return null;
        }
        moveToHead(node);
        return node.value;
    }

    public void put(E key, E value) {
        Node<E> node = cache.get(key);
        if (node == null) {
            node = new Node<>(key, value);
            cache.put(key, node);
            addNode(node);
            if (cache.size() > capacity) {
                Node<E> tail = popTail();
                cache.remove(tail.key);
            }
        } else {
            node.value = value;
            moveToHead(node);
        }
    }

    private void addNode(Node<E> node) {
        node.prev = null;
        node.next = head;
        if (head != null) {
            head.prev = node;
        }
        head = node;
        if (tail == null) {
            tail = node;
        }
    }

    private void removeNode(Node<E> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    private void moveToHead(Node<E> node) {
        removeNode(node);
        addNode(node);
    }

    private Node<E> popTail() {
        Node<E> res = tail;
        removeNode(tail);
        return res;
    }

    public static void main(String[] args) {
        main<String> cache = new main<>(2);
        cache.put("item1", "Pepsi");
        cache.put("item2", "Sprite");
        System.out.println("Get item1: " + cache.get("item1")); 
        cache.put("item3", "Coke");
        System.out.println("Get item2: " + cache.get("item2")); 
        System.out.println("Get item3: " + cache.get("item3"));
    }
}
