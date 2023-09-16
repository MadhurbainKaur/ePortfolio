package ePortfolio;
import java.util.*;

/**
 * Investment super class for common methods used by subclasses 
 */
public class Investment {
    protected String symbol = "";
    protected String name = "";
    protected int quantity;
    protected double price;
    protected double bookValue;

    //Constructors
    // /**
    //  * Empty Investment Constructor
    //  */
    // public Investment() {
    //     this.symbol = "";
    //     this.name = "";
    //     this.quantity = 0;
    //     this.price = 0.0;
    //     this.bookValue = 0.0;
    // }
    /**
     * Constructor for creating an Investment object
     * @param symbol
     * @param name
     * @param price
     */
    public Investment(String symbol, String name, double price, int quantity) {
        setSymbol(symbol);
        setName(name);
        setPrice(price);
        setQuantity(quantity);
    }
    /**
     * Constructor for creating an Investment object from a given file
     * @param inputFile
     */
    public Investment (Scanner inputFile){
        setSymbol(inputFile.nextLine());
        setName(inputFile.nextLine());
        setQuantity(Integer.parseInt(inputFile.nextLine()));
        setPrice(Double.parseDouble(inputFile.nextLine()));
        setBookValue(Double.parseDouble(inputFile.nextLine()));
    }
    
    //Getters
    /**
     * Get method for symbol
     * @return symbol String
     */
    public String getSymbol() {
        return this.symbol;
    }
    /**
     * Get method for name
     * @return name String
     */
    public String getName() {
        return this.name;
    }
    /**
     * Get method for quantity
     * @return quantity int
     */
    public int getQuantity() {
        return this.quantity;
    }
    /**
     * Get method for price
     * @return price double
     */
    public double getPrice() {
        return this.price;
    }
    /**
     * Get method for bookValue
     * @return bookValue double
     */
    public double getBookValue() {
        return this.bookValue;
    }

    //Setters
    /**
     * Set method for symbol
     * @param symbol
     * @return boolean
     */
    public boolean setSymbol(String symbol) {
        //check if input empty
        if ((symbol.equals(""))) {
            System.out.println("Please enter a symbol!");
            return false;
        }
        this.symbol = symbol;
        return true;
    }
    /**
     * Set method for name
     * @param name
     * @return boolean
     */
    public boolean setName(String name) {
        //check if input empty
        if (name.equals("")) {
            System.out.println("Please enter a name!");
            return false;
        }
        this.name = name;
        return true;
    }
    /**
     * Set method for quantity
     * @param quantity
     * @return boolean
     */
    public boolean setQuantity(int quantity) {
        //check if quanity valid
        if (quantity <= 0) {
            return false;
        }
        this.quantity = quantity;
        return true;
    }
    /**
     * Set method for price
     * @param price
     * @return boolean
     */
    public boolean setPrice(double price) {
        //check if price valid
        if (price <= 0.0) {
            return false;
        }
        this.price = price;
        return true;
    }
    /**
     * Set method for bookValue
     * @param bookValue
     */
    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    /**
     * Calculate gain / loss of an investment
     * @return double
     */
    public double calcGain() {
        //Calc current value of investment
        double currVal;
        currVal = quantity * price;
        return currVal - bookValue;
    }

    /**
     * toString method for all instance variables
     * @return String
     */
    public String toString() {
        return "\n\tSymbol: " + symbol + "\n\tName: " + name + "\n\tQuantity: " + quantity
        + "\n\tPrice: $" + price + "\n\tBook Value: $" + String.format("%,.2f",bookValue) + "\n\tGain: $ " + String.format("%,.2f", calcGain());
    }

    /**
     * Check if 2 objects are equal to eachother
     * @param other
     * @return boolean
     */
    public boolean equals(Object other) {
        //If object passed in does not exist
        if (other == null) {
            return false;
        }
        else if (other instanceof Stock) {
            Stock otherS = (Stock)other;
            //Compare each instance variable values
            return  symbol.equals(otherS.symbol) &&
                    name.equals(otherS.name) &&
                    quantity == otherS.quantity &&
                    price == otherS.price && 
                    bookValue == otherS.bookValue;
        }
        else if (other instanceof MutualFund) {
            MutualFund otherMF = (MutualFund)other;
            return  symbol.equals(otherMF.symbol) &&
                    name.equals(otherMF.name) &&
                    quantity == otherMF.quantity &&
                    price == otherMF.price && 
                    bookValue == otherMF.bookValue;
        }  
        else {
            return false;
        }
    }

}
