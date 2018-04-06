package com.github.reichesf.itemservice;

import java.util.HashMap;
import java.util.Set;

public class InMemoryDataStore<K,V>
{
    public InMemoryDataStore()
    {
        dataStore = new HashMap<>();
    }

    public V get(K key)
    {
        V value = null;

        if (key != null)
        {
            value = dataStore.get(key);
        }
        return value;
    }

    public boolean add(K key, V value)
    {
        boolean bRet = false;

        if (key != null && value != null)
        {
            if (dataStore.containsKey(key) == false)
            {
                dataStore.put(key, value);
                bRet = true;
            }
        }
        return bRet;
    }

    public boolean update(K key, V value)
    {
        boolean bRet = false;

        if (key != null && value != null)
        {
            if (dataStore.containsKey(key))
            {
                dataStore.replace(key, value);
                bRet = true;
            }
        }
        return bRet;
    }

    public boolean remove(K key)
    {
        boolean bRet = false;


        if (key != null)
        {
            bRet = (dataStore.remove(key) != null ? true : false);
        }
        return bRet;
    }

    public Set<K> keySet()
    {
        return dataStore.keySet();
    }

    private HashMap<K,V> dataStore = null;
}
