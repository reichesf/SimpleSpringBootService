package com.github.reichesf.itemservice;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ItemListResponse")
public final class ItemListResponse
{
    public ItemListResponse()
    {
        this.itemListResponse = new ArrayList<>();
    }

    @XmlElement(name = "ItemResponse")
    public List<ItemResponse> getItemListResponse()
    {
        return itemListResponse;
    }

    public void add(final ItemResponse itemResponse)
    {
        int ndx = this.getItemIndex(itemResponse);

        if (ndx < 0)
        {
            itemListResponse.add(itemResponse);
        }
    }

    public boolean isEmpty()
    {
        return itemListResponse.isEmpty();
    }

    private int getItemIndex(final ItemResponse itemResponse)
    {
        int nRet = -1;

        if (itemResponse != null)
        {
            nRet = this.getItemIndex(itemResponse.getUpc());
        }
        return nRet;
    }

    private int getItemIndex(final String sUpc)
    {
        int nRet = -1;
        String sItemUpc = null;

        if (sUpc != null)
        {
            for (int i = 0; i < itemListResponse.size() && nRet < 0; ++i)
            {
                if (sUpc.equalsIgnoreCase(itemListResponse.get(i).getUpc()))
                {
                    nRet = i;
                }
            }
        }
        return nRet;
    }

    private List<ItemResponse> itemListResponse = null;
}
