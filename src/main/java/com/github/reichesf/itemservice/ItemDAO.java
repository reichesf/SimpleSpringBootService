package com.github.reichesf.itemservice;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO
{
    public ItemDAO()
    {
        super();
    }
    public ItemList getItemList()
    {
        return itemList;
    }

    public Item getItem(final String sUpc)
    {
        return itemList.getItem(sUpc);
    }

    public void removeItem(final String sUpc)
    {
        itemList.removeItem(sUpc);
    }

    public void updateItem(final Item item)
    {
        itemList.updateItem(item);
    }
    public void addItem(final Item item)
    {
        itemList.addItem(item);
    }

    public void updateItemDescription(final String sUpc, final String sDescription)
    {
        itemList.updateItemDescription(sUpc, sDescription);
    }

    public void updateItemBalance(final String sUpc, final long nBalance)
    {
        itemList.updateItemBalance(sUpc, nBalance);
    }

    private static ItemList itemList = new ItemList();
    {
        Item item1 = new Item("00000000004011", "Bananas", 3);
        Item item2 = new Item("00000000004065", "Red Onion", 1);

        itemList.addItem(item1);
        itemList.addItem(item2);
    }
}
