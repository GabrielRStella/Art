package com.ralitski.art.api;

import java.util.Map;

public class Pair<K, V> implements Map.Entry<K, V> {
    
    private K key;
    private V value;
    
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }
    
    public K setKey(K key) {
        K prevKey = this.key;
        this.key = key;
        return prevKey;
    }

    @Override
    public V setValue(V value) {
        V prevValue = this.value;
        this.value = value;
        return prevValue;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(o instanceof Map.Entry) {
            Map.Entry<?, ?> other = (Map.Entry<?, ?>)o;
            return other.getKey().equals(key) && other.getValue().equals(value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.key.hashCode();
        hash = 37 * hash + this.value.hashCode();
        return hash;
    }
}
