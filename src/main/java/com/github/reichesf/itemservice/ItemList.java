package com.github.reichesf.itemservice;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ItemList")
public final class ItemList
{
    public ItemList()
    {
        this.itemList = new ArrayList<>();
    }

    @XmlElement(name = "Item")
    public List<Item> getItemList()
    {
        return itemList;
    }

    public Item getItem(final String sUpc)
    {
        Item retItem = null;

        if (sUpc != null)
        {
            for (Item item : itemList)
            {
                if (item.getUpc().equalsIgnoreCase(this.padItem(sUpc)))
                {
                    retItem = item;
                }
            }
        }
        return retItem;
    }

    public void removeItem(final String sUpc)
    {
        int ndx = this.getItemIndex(sUpc);

        if (ndx >= 0)
        {
            itemList.remove(ndx);
        }
    }

    public void updateItem(final Item item)
    {
        int ndx = this.getItemIndex(item);

        if (ndx >= 0)
        {
            itemList.get(ndx).setDescription(item.getDescription());
            itemList.get(ndx).setBalance(item.getBalance());
        }
    }

    public void addItem(final Item item)
    {
        int ndx = this.getItemIndex(item);

        if (ndx < 0)
        {
            item.setUpc(padItem(item.getUpc()));
            itemList.add(item);
        }
    }

    public void updateItemDescription(final String sUpc, final String sDescription)
    {
        int ndx = this.getItemIndex(sUpc);

        if (ndx >= 0)
        {
            itemList.get(ndx).setDescription(sDescription);
        }
    }

    public void updateItemBalance(final String sUpc, final long nBalance)
    {
        int ndx = this.getItemIndex(sUpc);

        if (ndx >= 0)
        {
            itemList.get(ndx).setBalance(nBalance);
        }
    }

    private int getItemIndex(final Item item)
    {
        int nRet = -1;

        if (item != null)
        {
            nRet = this.getItemIndex(item.getUpc());
        }
        return nRet;
    }

    private int getItemIndex(final String sUpc)
    {
        int nRet = -1;
        String sItemUpc = null;

        if (sUpc != null)
        {
            sItemUpc = this.padItem(sUpc);

            for (int i = 0; i < itemList.size() && nRet < 0; ++i)
            {
                if (sItemUpc.equalsIgnoreCase(itemList.get(i).getUpc()))
                {
                    nRet = i;
                }
            }
        }
        return nRet;
    }

    private String padItem(final String sUpc)
    {
        String sPadded = null;
        final String sZeros = "00000000000000";

        if (sUpc == null)
        {
            sPadded = sZeros;
        }
        else if (sUpc.length() < sZeros.length())
        {
            sPadded = sZeros.substring(sUpc.length()) + sUpc;
        }
        else
        {
            sPadded = sUpc;
        }
        return sPadded;

    }

    private List<Item> itemList = null;
}
