package com.github.reichesf.itemservice;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public final class ItemService
{
    public ItemService()
    {
        super();
    }

    public Set<String> getUpcList()
    {
        return itemDAO.getItemList();
    }

    public ItemData getItem(final String sUpc)
    {
        return itemDAO.getItem(sUpc);
    }

    public boolean removeItem(final String sUpc)
    {
       return itemDAO.removeItem(sUpc);
    }

    public boolean updateItem(final ItemData itemData)
    {
       return itemDAO.updateItem(itemData);
    }

    public boolean addItem(final ItemData itemData)
    {
        return itemDAO.addItem(itemData);
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

