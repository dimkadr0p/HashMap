package org.khachidze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class HashMap<K, V> {

    private int capacity = 16;

    private int size;

    private ArrayList<List<Element<K, V>>> arr;

    public HashMap() {
        arr = new ArrayList<>(capacity);
        arrInitialization();
    }

    public HashMap(int capacity) {
        this.capacity = capacity;
        arr = new ArrayList<>(capacity);
        arrInitialization();
    }


    public void put(K key, V value) {

        if (checkFilling()) {
            resize();
        }

        int index = getIndexCalculation(key);

        if (arr.get(index) == null) {
            arr.set(index, new LinkedList<>());
        }

        Optional<Element<K, V>> existingElement = arr.get(index).stream()
                .filter(el -> el.getKey().equals(key)).findFirst();

        if (existingElement.isPresent()) {
            existingElement.get().setValue(value);
        } else {
            arr.get(index).add(new Element<>(key, value));
            size++;
        }

    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < capacity; i++) {
            arr.set(i, null);
        }
    }

    public V get(K key) {

        int index = getIndexCalculation(key);

        List<Element<K, V>> list = arr.get(index);

        if (list != null) {
            return list.stream()
                    .filter(el -> el.getKey().equals(key))
                    .map(Element::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public boolean remove(K key) {

        int index = getIndexCalculation(key);

        List<Element<K, V>> list = arr.get(index);

        if (list != null && list.removeIf(k -> k.getKey().equals(key))) {
            if (list.isEmpty()) arr.set(index, null);
            size--;
            return true;
        }

        return false;
    }


    public void display() {

        for (int i = 0; i < arr.size(); i++) {
            System.out.print("Бакет №" + i + " -> ");
            if (arr.get(i) == null) {
                System.out.println("empty ");
            } else {
                for (Element<K, V> el : arr.get(i)) {
                    System.out.print(el + " ");
                }
            }
            System.out.println();
        }
    }

    private int getIndexCalculation(K key) {
        return getHash(key) % capacity;
    }

    private int getHash(K key) {
        return key.hashCode() & 0x7fffffff;
    }

    private void resize() {

        HashMap<K, V> newHashMap = new HashMap<>(capacity * 2);

        for (List<Element<K, V>> elements : arr) {
            if (elements != null) {
                for (Element<K, V> el : elements) {
                    newHashMap.put(el.getKey(), el.getValue());
                }
            }
        }

        this.capacity = newHashMap.capacity;
        this.size = newHashMap.size;
        this.arr = newHashMap.arr;

    }

    private void arrInitialization() {
        for (int i = 0; i < capacity; i++) {
            arr.add(null);
        }
    }

    private boolean checkFilling() {
        return (size * 100) / capacity >= 70;
    }

}
