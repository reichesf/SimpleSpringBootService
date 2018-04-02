package com.github.reichesf.itemservice;

public final class ItemDAO
{
    public ItemDAO()
    {
        this.itemStorage = new ItemStorage();
    }
    public ItemStorage getItemList()
    {
        return this.itemStorage;
    }

    public ItemData getItem(final String sUpc)
    {
        return this.itemStorage.getItem(sUpc);
    }

    public void removeItem(final String sUpc)
    {
        this.itemStorage.removeItem(sUpc);
    }

    public void updateItem(final ItemData itemData)
    {
        this.itemStorage.updateItem(itemData);
    }
    public void addItem(final ItemData itemData)
    {
        this.itemStorage.addItem(itemData);
    }

    public void updateItemDescription(final String sUpc, final String sDescription)
    {
        this.itemStorage.updateItemDescription(sUpc, sDescription);
    }

    public void updateItemBalance(final String sUpc, final long nBalance)
    {
        this.itemStorage.updateItemBalance(sUpc, nBalance);
    }

    private ItemStorage itemStorage = null;
}
