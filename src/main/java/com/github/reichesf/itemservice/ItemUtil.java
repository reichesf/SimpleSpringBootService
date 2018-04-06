package com.github.reichesf.itemservice;

public final class ItemUtil
{
    static String padUpc(final String sUpc)
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
}
