/**
 * Nauman Shahzad
 * 109813732
 * Homework #7 - CSE 214
 * Thursday: R04
 * Gustavo Poscidonio
 * Mahsa Torkaman
 * @author Nauman
 */

import java.io.*;
import java.util.Scanner;

public class SigmaAirDriver
{
    public static SigmaAir airlineSetUp(SigmaAir airline)
    {
        try {
            FileInputStream file = new FileInputStream("library.obj");
            ObjectInputStream fin  = new ObjectInputStream(file);
            airline = (SigmaAir) fin.readObject(); //readObject() returns Object, so must typecast to HashedLibrary
            fin.close();
            System.out.println("The library obj has been loaded in.");
            return airline;
        } catch(IOException e){
            System.out.println("The library obj file has not been found.");
            return new SigmaAir();
        }catch(ClassNotFoundException ex){ return new SigmaAir(); }
    }

    public static void menu()
    {
        Scanner input = new Scanner(System.in);
        SigmaAir airline = new SigmaAir();
        airline = airlineSetUp(airline);
        String choice = "", choiceTwo = "";

        while(!choice.equalsIgnoreCase("Q"))
        {
            System.out.println("(A) Add City\n(B) Add Connection\n(C) Load All Cities\n(D) Load all connections\n(E) Print all Cities\n(F) Print all Connections\n(G) Remove Connection\n(H) Find Shortest Path\n(Q) Quit");

            System.out.print("\nEnter a selection: ");
            choice = input.next();

            if(choice.equalsIgnoreCase("A"))
            {
                System.out.print("Enter the name of the city: ");
                input.nextLine();
                String cityName = input.nextLine();
                airline.addCity(cityName);
            }

            if(choice.equalsIgnoreCase("B"))
            {
                System.out.print("Enter source city: ");
                input.nextLine();
                String source = input.nextLine();
                System.out.print("Enter destination city: ");
                String dest = input.nextLine();

                airline.addConnection(source, dest);
            }

            if(choice.equalsIgnoreCase("C"))
            {
                System.out.print("Enter the file name: ");
                String fileName = input.next();
                airline.loadAllCities(fileName);
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
                input.nextLine();
                String source = input.nextLine();
                System.out.print("Enter destination city: ");
                String dest = input.nextLine();

                airline.removeConnection(source, dest);
            }

            if(choice.equalsIgnoreCase("H"))
            {
                System.out.print("Enter source city: ");
                input.nextLine();
                String source = input.nextLine();
                System.out.print("Enter destination city: ");
                String dest = input.nextLine();

                System.out.println(airline.shortestPath(source, dest));
            }

        }

        try {
            FileOutputStream file = new FileOutputStream("library.obj");
            ObjectOutputStream fout = new ObjectOutputStream(file);
            fout.writeObject(airline); //Writes airline to library.obj
            fout.close();
            System.out.println("The SigmaAir airline has now been saved into the library.obj.");
        } catch (IOException e){
            // handle IO exceptions here
        }

    }
    public static void main(String[] args) {
        menu();
        System.out.println("Program terminating...");
    }
}
