package com.github.reichesf.itemservice;

import javax.xml.bind.DatatypeConverter;
import java.util.Calendar;
import java.util.TimeZone;

public final class ItemData
{
    public ItemData()
    {
        this.dateTime = ItemData.getDateTimeAsString(System.currentTimeMillis());
    }

    public ItemData(final String sUpc, final String sDescription, final long nBalance)
    {
        this.upc = ItemUtil.padUpc(sUpc);
        this.description = sDescription;
        this.balance = nBalance;
        this.dateTime = ItemData.getDateTimeAsString(System.currentTimeMillis());
    }

    public String getUpc()
    {
        return this.upc;
    }

    public void setUpc(final String sUpc)
    {
        this.upc = ItemUtil.padUpc(sUpc);
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(final String sDescription)
    {
        this.description = sDescription;
    }

    public long getBalance()
    {
        return balance;
    }

    public void setBalance(final long nBalance)
    {
        this.balance = nBalance;
    }

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
