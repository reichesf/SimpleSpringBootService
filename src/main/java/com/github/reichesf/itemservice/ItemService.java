package com.github.reichesf.itemservice;

import org.springframework.stereotype.Component;

@Component
public final class ItemService
{
    public ItemService()
    {
        super();
    }

    public ItemStorage getItemList()
    {
        return itemDAO.getItemList();
    }

    public ItemData getItem(final String sUpc)
    {
        return itemDAO.getItem(sUpc);
    }

    public void removeItem(final String sUpc)
    {
       itemDAO.removeItem(sUpc);
    }

    public void updateItem(final ItemData itemData)
    {
       itemDAO.updateItem(itemData);
    }

    public void addItem(final ItemData itemData)
    {
        itemDAO.addItem(itemData);
    }

    public void updateItemDescription(final String sUpc, final String sDescription)
    {
        itemDAO.updateItemDescription(sUpc, sDescription);
    }

    public void updateItemBalance(final String sUpc, final long nBalance)
    {
        itemDAO.updateItemBalance(sUpc, nBalance);
    }

    private static ItemDAO itemDAO = new ItemDAO();

    {
        ItemData itemData1 = new ItemData("00000000004011", "Bananas", 3);
        ItemData itemData2 = new ItemData("00000000004065", "Red Onion", 1);

        itemDAO.addItem(itemData1);
        itemDAO.addItem(itemData2);
    }

}

