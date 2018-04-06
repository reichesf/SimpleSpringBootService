package com.github.reichesf.itemservice;

import java.util.Set;
import java.util.TreeSet;

public final class ItemDAO
{
    public ItemDAO()
    {
        this.itemStorage = new InMemoryDataStore<>();
    }

    public Set<String> getItemList()
    {
        TreeSet<String> items = new TreeSet<>();

        for (String key : itemStorage.keySet())
        {
            items.add(key);
        }
        return items;
    }

    public ItemData getItem(final String sUpc)
    {
        return this.itemStorage.get(ItemUtil.padUpc(sUpc));
    }

    public boolean removeItem(final String sUpc)
    {
        return this.itemStorage.remove(ItemUtil.padUpc(sUpc));
    }

    public boolean updateItem(final ItemData itemData)
    {
        return this.itemStorage.update(itemData.getUpc(), itemData);
    }

    public boolean addItem(final ItemData itemData)
    {
        return this.itemStorage.add(itemData.getUpc(), itemData);
    }

    public void updateItemDescription(final String sUpc, final String sDescription)
    {
        ItemData itemData = null;

        try
        {
            if (sUpc != null && sDescription != null)
            {
                itemData = this.itemStorage.get(ItemUtil.padUpc(sUpc));

                if (itemData != null)
                {
                    itemData.setDescription(sDescription);

                    this.itemStorage.update(ItemUtil.padUpc(sUpc), itemData);
                }
            }
        }
        finally
        {
            itemData = null;
        }
    }

    public void updateItemBalance(final String sUpc, final long nBalance)
    {
        ItemData itemData = null;

        try
        {
            if (sUpc != null)
            {
                itemData = this.itemStorage.get(ItemUtil.padUpc(sUpc));

                if (itemData != null)
                {
                    itemData.setBalance(nBalance);

                    this.itemStorage.update(ItemUtil.padUpc(sUpc), itemData);
                }
            }
        }
        finally
        {
            itemData = null;
        }
    }

    private InMemoryDataStore<String, ItemData> itemStorage = null;
}
