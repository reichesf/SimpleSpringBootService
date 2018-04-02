package com.github.reichesf.itemservice;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Calendar;
import java.util.TimeZone;

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

@XmlRootElement(name = "ItemData")
public final class ItemData
{
    public ItemData()
    {
        this.dateTime = ItemData.getDateTimeAsString(System.currentTimeMillis());
    }

    public ItemData(final String sUpc, final String sDescription, final long nBalance)
    {
        this.upc = sUpc;
        this.description = sDescription;
        this.balance = nBalance;
        this.dateTime = ItemData.getDateTimeAsString(System.currentTimeMillis());
    }

    @XmlElement(name="upc")
    public String getUpc()
    {
        return this.upc;
    }

    public void setUpc(final String sUpc)
    {
        this.upc = sUpc;
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

    public void setDateTimeValue(final long nMillis)
    {
        this.dateTime = ItemData.getDateTimeAsString(nMillis);
    }

    private static String getDateTimeAsString(final long nMillis)
    {
        String sRet = null;

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        cal.setTimeInMillis(nMillis);

        sRet = DatatypeConverter.printDateTime(cal);

        return sRet;
    }

    private String dateTime = null;
    private String upc = null;
    private String description = null;
    private long balance = 0;
}
