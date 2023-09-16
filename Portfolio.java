package ePortfolio;
import java.util.*;

/**
 * Portfolio class
 */
public class Portfolio {
    //Array lists to store stocks and mutual funds
    protected static ArrayList<Investment> investments = new ArrayList<Investment>();

    //HashMap for searching keywords
    protected static HashMap<String, ArrayList<Investment>> keywordsMap = new HashMap<>();

    public static Scanner keyboard = new Scanner(System.in);  

    /**
     * Buy an Investment
     */
    public static void buyInv() {
        String symbol;
        String invName;
        Investment inv;
        int invQuantity;
        double invPrice;

        //User enter investment kind and symbol
        System.out.print("Enter the type of investment (S = Stock or MF = Mutual Fund): ");
        String type = keyboard.nextLine();

        //error trap for empty or incorrect input
        while (type.equals("") || (!type.equalsIgnoreCase("S") && !type.equalsIgnoreCase("MF"))) {
            System.out.print("Try Again! Enter 'S' or 'MF': ");
            type = keyboard.nextLine();
        } 
        
        //get symmbol
        System.out.print("Enter symbol: ");
        symbol = keyboard.nextLine();
        //error trap for empty input
        while (symbol.equals("")) {
            System.out.print("Try Again! Enter symbol: ");
            symbol = keyboard.nextLine();
        }

        //Search if the investment already exists
        inv = searchInv(symbol);

        //if investment doesn't exist
        if (inv == null) {
            System.out.println("Enter Information for a new Investment");

            //get name
            System.out.print("Enter name: ");
            invName = keyboard.nextLine();
            //error trap for empty input
            while (invName.equals("")) {
                System.out.print("Try Again! Enter name: ");
                invName = keyboard.nextLine();
            }

            //get quantity
            System.out.print("Enter quantity: ");
            invQuantity = getIntVal();
            while(invQuantity <= 0) {
                System.out.print("Try Again! Enter quantity above 0: ");
                invQuantity = getIntVal();
            }

            //get price
            System.out.print("Enter price: ");
            invPrice = getDoubleVal();
            while (invPrice <= 0) {
                System.out.print("Try Again! Enter price above 0: ");
                invPrice = getDoubleVal();
            }

            //create a new stock object
            if(type.equalsIgnoreCase("S")) {
                Stock newS = new Stock(symbol, invName, invQuantity, invPrice);
                //add to arraylist
                investments.add(newS);
                System.out.println("Added Stock to Investments!");
            }
            //create a new mutual fund object
            else {
                MutualFund newMF = new MutualFund(symbol, invName, invQuantity, invPrice);
                investments.add(newMF);
                System.out.println("Added Mutual Fund to Investments!");
            } 

        }
        //investment already exists
        else {
            //display current investment
            System.out.println("Current Investment: ");
            System.out.println(inv);
            //get more quantity
            System.out.print("Enter quantity: ");
            invQuantity = getIntVal();
            while(invQuantity <= 0) {
                System.out.print("Try Again! Enter quantity above 0: ");
                invQuantity = getIntVal();
            }
            //update quantity
            int previousQuantity = inv.getQuantity();
            inv.setQuantity(inv.getQuantity() + invQuantity);
            //get price (at which they are buying new quantity at)
            System.out.print("Enter price: ");
            invPrice = getDoubleVal();
            while(invPrice <= 0) {
                System.out.print("Try Again! Enter price above 0: ");
                invPrice = getIntVal();
            }
            //calculate new price
            inv.setPrice( ((previousQuantity*inv.getPrice()) + (invQuantity*invPrice)) / (inv.getQuantity()) );
            //update bookvalue
            if (type.equalsIgnoreCase("S")) {
                inv.setBookValue(inv.getBookValue() + (invQuantity*invPrice + Stock.COM));
            }
            else {
                inv.setBookValue(inv.getBookValue() + (invQuantity*invPrice));
            }

            System.out.println("New investment updated!");
        }

    }

    /**
     * Sell an Investment
     */
    public static void sellInv() {
        String symbol;
        Investment inv;
        int sellQ;
        double sellVal;

        //get symmbol
        System.out.print("Enter symbol: ");
        symbol = keyboard.nextLine();
        //error trap for empty input
        while (symbol.equals("")) {
            System.out.print("Try Again! Enter symbol: ");
            symbol = keyboard.nextLine();
        }

        //Search if the investment already exists
        inv = searchInv(symbol);
        
        if (inv == null) {
            System.out.println("Investment does not exist.");
            return;
        }
        //display investment
        System.out.println("Current Investment: ");
        System.out.println(inv);
        //get sell quantity
        System.out.print("How many shares would you like to sell? ");
        sellQ = getIntVal();

        //error trap while sell quantity is <= 0 or greater than current quantity
        while (sellQ <= 0 || sellQ > inv.getQuantity()) {
            System.out.print("Try Again! Quantity must be positive and less than or equal to existing quantity: ");
            sellQ = getIntVal();
        }

        //calculate sell value
        //for stock
        if (inv instanceof Stock) {
           sellVal = sellQ * inv.getPrice() - Stock.COM;
        }
        //mutual fund
        else {
            sellVal = sellQ * inv.getPrice() - MutualFund.FEE;
        }

        //update quantity and book value
        int previousQuantity = inv.getQuantity();
        inv.setQuantity(previousQuantity - sellQ);
        inv.setBookValue(inv.getBookValue() * (inv.getQuantity() / previousQuantity));

        System.out.println("Sold " + sellQ + " shares valued at " + sellVal);
        if (inv.getQuantity() == 0) {
            System.out.println("Entire investment has been sold.");
            investments.remove(inv);

            //Delete investment from keywords map
            Scanner keywords = new Scanner(inv.getName());
            //loop through hashmap
            while (keywords.hasNext()) {
                String key = keywords.next().toLowerCase();
                if (keywordsMap.containsKey(key)) {
                    keywordsMap.get(key).remove(inv); //remove
                }
            }
        }        

    }
    
    /**
     * Update the prices of all existing investments
     */
    public static void updateInv() {
        //go through all investments
        for (Investment i : investments) {
            System.out.println(i.toString());
            System.out.print("Enter updated price: ");
            double newPrice = getDoubleVal();

            while (newPrice <= 0) {
                System.out.println("Try Again! Enter a positive price: ");
                newPrice = getDoubleVal();
            }

            //Update price
            i.setPrice(newPrice);
        }
        System.out.println("Prices for all investments updated");
    }

    /**
     * Calculate the total gain of all investments 
     */
    public static void calcTotalGains() {
        double totalGains = 0;
        //loop trough investments
        for (Investment i : investments) {
            //Calculate total gain
            totalGains += i.calcGain();
        }

        System.out.println("Total Investments Gain: $" + String.format("%,.2f", totalGains));
    }
    
    /**
     * Search Investment given symbol
     */
    public static void searchInvestments() {
        String symbol, keywords;
        System.out.print("Enter Investment symbol (leave blank for all investments): ");
        symbol = keyboard.nextLine();
        System.out.print("Enter keywords (separate each keyword by space, or leave blank for all investments): ");
        keywords = keyboard.nextLine();

        //Create new arraylist to store results
        ArrayList<Investment> invResults = new ArrayList<>();

        //Filter by symbol
        //if no symbol, add all
        if (symbol.equals("")) {
            invResults.addAll(investments);
        }
        //otherwise search for investment with symbol 
        else {
            for (Investment i : investments) {
                if (symbol.equalsIgnoreCase(i.getSymbol())) {
                    invResults.add(i);
                }
            }
        }

        //filter by keyword
        ArrayList<Investment> newResults = new ArrayList<>();
        if (keywords.isEmpty()) {
            newResults.addAll(invResults);
        } 
        else {
            for (Investment i : newResults) {
                Scanner keywordScanner = new Scanner(keywords);
                while (keywordScanner.hasNext()) {
                    String keyword = keywordScanner.next().toLowerCase();
                    if (keywordsMap.containsKey(keyword) || keywordsMap.get(keyword).contains(i)) {
                    newResults.add(i); 
                    }
                }                
            }
        }

        invResults.clear();
        invResults.addAll(newResults);

        // Display the results
        System.out.println("Search Result: ");
        for (Investment i : invResults) {
            System.out.println(i);
        }
    }

    /**
     * Search if an investment exists in Arraylist
     * @param symbol
     * @return Investment
     */
    public static Investment searchInv(String symbol) {
        //go through all investments
        for (Investment i : investments) {
            //if symbol exists
            if (i.getSymbol().equalsIgnoreCase(symbol)) {
                return i;
            }
        }
        //if doesn't exists
        return null;
    }

    /**
     * Ensure that user enters integer value
     * @return int
     */
    public static int getIntVal() {
        while (true) {
            //check if input is int and try to return
            try {
                return Integer.parseInt(keyboard.nextLine());
            } catch (Exception e) {
                System.out.print("Please enter an integer: ");
            }
        }
    }

    /**
     * Ensure that user enters decimal value
     * @return double
     */
    public static double getDoubleVal() {
        while (true) {
            try {
                return Double.parseDouble(keyboard.nextLine());
            } catch (Exception e) {
                System.out.print("Please enter a decimal: ");
            }
        }
    }

}
