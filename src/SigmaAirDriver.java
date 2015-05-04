/**
 * Nauman Shahzad
 * 109813732
 * Homework #7 - CSE 214
 * Thursday: R04
 * Gustavo Poscidonio
 * Mahsa Torkaman
 * @author Nauman
 */

import java.util.Scanner;

public class SigmaAirDriver
{
    public static void menu()
    {
        Scanner input = new Scanner(System.in);
        SigmaAir airline = new SigmaAir();
        String choice = "", choiceTwo = "";

        while(!choice.equalsIgnoreCase("Q"))
        {
            System.out.println("(A) Add City\n(B) Add Connection\n(C) Load All Cities\n(D) Load all connections\n(E) Print all Cities\n(F) Print all Connections\n(G) Remove Connection\n(H) Find Shortest Path\n(Q) Quit");

            System.out.print("\nEnter a selection: ");
            choice = input.next();

            if(choice.equalsIgnoreCase("A"))
            {
                System.out.print("Enter the name of the city: ");
                airline.addCity(input.next());
            }

            if(choice.equalsIgnoreCase("B"))
            {
                System.out.print("Enter source city: ");
                String source = input.next();
                System.out.print("Enter destination city: ");
                String dest = input.next();

                airline.addConnection(source, dest);
            }

            if(choice.equalsIgnoreCase("C"))
            {
                System.out.print("Enter the file name: ");
                airline.loadAllCities(input.next());
            }

            if(choice.equalsIgnoreCase("D"))
            {
                System.out.print("Enter the file name: ");
                airline.loadAllConnections(input.next());
            }

            if(choice.equalsIgnoreCase("E"))
            {
                System.out.println("(EA) Sort Cities by Name\n(EB) Sort Cities by Latitude\n(EC) Sort Cities by Longitude\n(Q) Quit");
                System.out.print("\nEnter a selection: ");
                choiceTwo = input.next();

                if(choiceTwo.equalsIgnoreCase("EA"))
                {
                    airline.printAllCities("name");
                }

                if(choiceTwo.equalsIgnoreCase("EB"))
                {
                    airline.printAllCities("lat");
                }

                if(choiceTwo.equalsIgnoreCase("EC"))
                {
                    airline.printAllCities("lng");
                }
            }

            if(choice.equalsIgnoreCase("F"))
            {
                airline.printAllConnections();
            }

            if(choice.equalsIgnoreCase("G"))
            {
                System.out.print("Enter source city: ");
                String source = input.next();
                System.out.print("Enter destination city: ");
                String dest = input.next();

                airline.removeConnection(source, dest);
            }

            if(choice.equalsIgnoreCase("H"))
            {
                System.out.print("Enter source city: ");
                String source = input.next();
                System.out.print("Enter destination city: ");
                String dest = input.next();

                airline.shortestPath(source, dest);
            }
        }

    }
    public static void main(String[] args) {
        menu();
        System.out.println("Program terminating...");
    }
}
