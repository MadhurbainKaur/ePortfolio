package ePortfolio;
import java.util.*;
import java.io.*;

/**
 * The Main class that contains the main method for testing this program
 * @author Madhurbain Kaur
 * @version 2.0
 */
public class Main {
    
    /**
     * The main function
     * @param args includes filename for investments
     */
    public static void main(String[]args) {
        String filename = "";
        //no filename specified
        if (args.length == 0) {
            System.out.println("No file name specified. Starting with no investments.");
        }
        //filename specified
        else if (args.length == 1) {
            //store filename
            filename = args[0];

            Scanner inputFile = null;
            //try to open file
            try {
                inputFile = new Scanner(new File(filename));
            } catch (Exception e) {
                System.out.println("Error occured while trying to load file");
                return;
            }
            String type = "";
            Investment inv = null;
            
            //load file contents (following recommended format)
            while (inputFile.hasNextLine()) {
                type = inputFile.nextLine();
                if (type.equalsIgnoreCase("stock")) {
                    //create a new stock object given file
                    inv = new Stock(inputFile);
                }
                else if (type.equalsIgnoreCase("mutualfund")) {
                    //create a new mutual fund object
                    inv = new MutualFund(inputFile);
                }
                else {
                    System.out.println("Invalid format of file");
                }

                //add to arraylist
                Portfolio.investments.add(inv);

                //put keywords in hashmap
                Scanner keywords = new Scanner(inv.getName());
                while (keywords.hasNext()) {
                    String key = keywords.next().toLowerCase();
                    if (!Portfolio.keywordsMap.containsKey(key)) {
                        //map to right investment
                        Portfolio.keywordsMap.put(key, new ArrayList<>());
                    }
                    //add to keywords map
                    Portfolio.keywordsMap.get(key).add(inv);
                }
            }
            //close file
            inputFile.close();
        }
        //exit if user enters incorrect number of arguments
        else {
            System.out.println("Please enter a the filename as the first argument or leave blank");
            System.out.println("Ex. 'java ePortfolio.Main ePortfolio/investments.txt");
            System.exit(0);
        }
        

        int choice = 0;
        // Scanner keyboard = new Scanner(System.in);      
        
        while (choice != 6) {
            System.out.println("1. Buy Investment");
            System.out.println("2. Sell Investment");
            System.out.println("3. Update Investments");
            System.out.println("4. Get gain of Investments");
            System.out.println("5. Search Investments");
            System.out.println("6. Quit");
        
            System.out.print("Enter your choice (1-6): ");
            choice = Portfolio.getIntVal();

            if (choice == 1) {
               Portfolio.buyInv();
            }
            else if (choice == 2) {
                Portfolio.sellInv();
            }
            else if (choice == 3) {
                Portfolio.updateInv();
            }
            else if (choice == 4) {
                Portfolio.calcTotalGains();
            }
            else if (choice == 5) {
                Portfolio.searchInvestments();
            }
            else if (choice == 6) {
                //write the investments back to file
                PrintWriter outputFile = null;
                //no filename specified
                if (args.length == 0) {
                    //create filename
                    filename = "investments.txt";
                }
                //try to create or find file
                try{
                    outputFile = new PrintWriter(new File(filename));
                } catch (Exception e){
                    System.out.println("Error occured while trying to create or open file");
                }
                //loop through each investment
                for (Investment i : Portfolio.investments) {
                    //write stock or mutualfund
                    if (i instanceof Stock) {
                        outputFile.println("stock");
                    } else if (i instanceof MutualFund) {
                        outputFile.println("mutualfund");
                    }
                    //write contents of arraylist
                    outputFile.println(i.getSymbol());
                    outputFile.println(i.getName());
                    outputFile.println(i.getQuantity());
                    outputFile.println(i.getPrice());
                    outputFile.println(i.getBookValue());
                }
                outputFile.close();
                System.out.println("Data written to " + filename);
            }
            // //TESTING
            // else if (choice == 7) {
            //     System.out.println(Portfolio.investments);
            // }
            else {
                System.out.println("Please enter a number between 1 - 6!");
            }
        }
        
    }
}
