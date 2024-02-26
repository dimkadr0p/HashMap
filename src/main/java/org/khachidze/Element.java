package org.khachidze;

public class Element<K, V> {

    private K key;
    private V value;

    public Element(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Element{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
