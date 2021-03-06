/**
 * Nauman Shahzad
 * 109813732
 * Homework #7 - CSE 214
 * Thursday: R04
 * Gustavo Poscidonio
 * Mahsa Torkaman
 * @author Nauman
 */

import java.io.Serializable;
import java.util.Comparator;

public class LngComparator implements Comparator, Serializable
{
    /**
     * Method to sort by longitude..
     * @param cityOne First City to compare with.
     * @param cityTwo Second City to compare with.
     * @return Returns the comparison made.
     * <dt><b>Preconditions:</b></dd> Both objects must already be instantiated.
     */
    public int compare(Object cityOne, Object cityTwo)
    {
        City one = (City) cityOne;
        City two = (City) cityTwo;

        if(one.getLocation().getLng() == two.getLocation().getLng())
            return 0;

        else if(one.getLocation().getLng() > two.getLocation().getLng())
            return 1;

        else
            return -1;
    }
}
