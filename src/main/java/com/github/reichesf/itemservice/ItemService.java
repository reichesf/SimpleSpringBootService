package com.github.reichesf.itemservice;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Component
public final class ItemService
{
    public ItemService()
    {
        super();
    }

    public ItemList getItemList()
    {
        return itemDAO.getItemList();
    }

    public Item getItem(final String sUpc)
    {
        return itemDAO.getItem(sUpc);
    }

    public void removeItem(final String sUpc)
    {
       itemDAO.removeItem(sUpc);
    }

    public void updateItem(final Item item)
    {
       itemDAO.updateItem(item);
    }

    public void addItem(final Item item)
    {
        itemDAO.addItem(item);
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
        Item item1 = new Item("00000000004011", "Bananas", 3);
        Item item2 = new Item("00000000004065", "Red Onion", 1);

        itemDAO.addItem(item1);
        itemDAO.addItem(item2);
    }

}

