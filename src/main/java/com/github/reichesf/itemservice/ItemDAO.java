package com.github.reichesf.itemservice;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO
{
    public ItemDAO()
    {
        this.itemList = new ItemList();
    }
    public ItemList getItemList()
    {
        return this.itemList;
    }

    public Item getItem(final String sUpc)
    {
        return this.itemList.getItem(sUpc);
    }

    public void removeItem(final String sUpc)
    {
        this.itemList.removeItem(sUpc);
    }

    public void updateItem(final Item item)
    {
        this.itemList.updateItem(item);
    }
    public void addItem(final Item item)
    {
        this.itemList.addItem(item);
    }

    public void updateItemDescription(final String sUpc, final String sDescription)
    {
        this.itemList.updateItemDescription(sUpc, sDescription);
    }

    public void updateItemBalance(final String sUpc, final long nBalance)
    {
        this.itemList.updateItemBalance(sUpc, nBalance);
    }

    private ItemList itemList = null;
}
