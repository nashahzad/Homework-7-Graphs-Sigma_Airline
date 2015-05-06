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

public class LatComparator implements Comparator, Serializable
{
    /**
     * Method to sort by latitude.
     * @param cityOne First City to compare with.
     * @param cityTwo Second City to compare with.
     * @return Returns the comparison made.
     * <dt><b>Preconditions:</b></dd> Both objects must already be instantiated.
     */
    public int compare(Object cityOne, Object cityTwo)
    {
        City one = (City) cityOne;
        City two = (City) cityTwo;

        if(one.getLocation().getLat() == two.getLocation().getLat())
            return 0;

        else if(one.getLocation().getLat() > two.getLocation().getLat())
            return 1;

        else
            return -1;
    }
}
