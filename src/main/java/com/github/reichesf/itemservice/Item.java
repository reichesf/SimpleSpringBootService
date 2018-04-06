package com.github.reichesf.itemservice;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

// Identify the class for JAXB (rendering as XML) requires
// at a minimum the @XmlRootElement annotation. The name
// attribute is optional as the name of the class (lower case)
// is the default. Additional XML annotations
// may be supplied at the method and class level.
//
// For JAXB (XML) the default (no-arg) constructor is
// required.
//
// @XmlType with propOrder of 1 element is trivial but is
// included to show it's use.

@XmlType(propOrder = {"upc", "description", "balance"})

@XmlRootElement(name = "Item")
public final class Item
{
    public Item()
    {
    }

    public Item(final ItemData itemData)
    {
        this.upc = itemData.getUpc();
        this.description = itemData.getDescription();
        this.balance = itemData.getBalance();
        this.dateTime = itemData.getDateTime();
    }

    @XmlElement(name="upc")
    public String getUpc()
    {
        return this.upc;
    }

    public void setUpc(final String sUpc)
    {
        this.upc = ItemUtil.padUpc(sUpc);
    }

    @XmlElement(name="description")
    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(final String sDescription)
    {
        this.description = sDescription;
    }

    @XmlElement(name="balance")
    public long getBalance()
    {
        return balance;
    }

    public void setBalance(final long nBalance)
    {
        this.balance = nBalance;
    }

    @XmlAttribute(name="dateTime")
    public String getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(final String dateTime)
    {
        this.dateTime = dateTime;
    }
    private String dateTime = null;
    private String upc = null;
    private String description = null;
    private long balance = 0;
}
