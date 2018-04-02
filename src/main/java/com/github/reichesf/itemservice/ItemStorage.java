package com.github.reichesf.itemservice;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ItemList")
public final class ItemStorage
{
    public ItemStorage()
    {
        this.itemDataList = new ArrayList<>();
    }

    @XmlElement(name = "ItemData")
    public List<ItemData> getItemDataList()
    {
        return itemDataList;
    }

    public ItemData getItem(final String sUpc)
    {
        ItemData retItemData = null;

        if (sUpc != null)
        {
            for (ItemData itemData : itemDataList)
            {
                if (itemData.getUpc().equalsIgnoreCase(this.padItem(sUpc)))
                {
                    retItemData = itemData;
                }
            }
        }
        return retItemData;
    }

    public void removeItem(final String sUpc)
    {
        int ndx = this.getItemIndex(sUpc);

        if (ndx >= 0)
        {
            itemDataList.remove(ndx);
        }
    }

    public void updateItem(final ItemData itemData)
    {
        int ndx = this.getItemIndex(itemData);

        if (ndx >= 0)
        {
            itemDataList.get(ndx).setDescription(itemData.getDescription());
            itemDataList.get(ndx).setBalance(itemData.getBalance());
        }
    }

    public void addItem(final ItemData itemData)
    {
        int ndx = this.getItemIndex(itemData);

        if (ndx < 0)
        {
            itemData.setUpc(padItem(itemData.getUpc()));
            itemDataList.add(itemData);
        }
    }

    public void updateItemDescription(final String sUpc, final String sDescription)
    {
        int ndx = this.getItemIndex(sUpc);

        if (ndx >= 0)
        {
            itemDataList.get(ndx).setDescription(sDescription);
        }
    }

    public void updateItemBalance(final String sUpc, final long nBalance)
    {
        int ndx = this.getItemIndex(sUpc);

        if (ndx >= 0)
        {
            itemDataList.get(ndx).setBalance(nBalance);
        }
    }

    private int getItemIndex(final ItemData itemData)
    {
        int nRet = -1;

        if (itemData != null)
        {
            nRet = this.getItemIndex(itemData.getUpc());
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

            for (int i = 0; i < itemDataList.size() && nRet < 0; ++i)
            {
                if (sItemUpc.equalsIgnoreCase(itemDataList.get(i).getUpc()))
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

    private List<ItemData> itemDataList = null;
}
