/**
 * Nauman Shahzad
 * 109813732
 * Homework #7 - CSE 214
 * Thursday: R04
 * Gustavo Poscidonio
 * Mahsa Torkaman
 * @author Nauman
 */

import java.util.Comparator;

public class LatComparator implements Comparator
{
    public int compareTo(City one, City two)
    {
        if(one.getLocation().getLat() == two.getLocation().getLat())
            return 0;

        else if(one.getLocation().getLat() > two.getLocation().getLat())
            return 1;

        else
            return -1;
    }
}
