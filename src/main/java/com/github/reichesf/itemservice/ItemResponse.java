package com.github.reichesf.itemservice;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"upc", "description", "balance", "statusCode", "status"})

@XmlRootElement(name = "ItemResponse")
public final class ItemResponse
{
    public ItemResponse()
    {
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

    @XmlElement(name="statusCode")
    public int getStatusCode()
    {
        return this.statusCode;
    }

    public void setStatusCode(final int nStatusCode)
    {
        this.statusCode = nStatusCode;
    }

    @XmlElement(name="status")
    public String getStatus()
    {
        return this.status;
    }

    public void setStatus(final String sStatus)
    {
        this.status = sStatus;
    }

    private String dateTime = null;
    private String upc = null;
    private String description = null;
    private int statusCode = 0;
    private String status = null;
    private long balance = 0;
}
