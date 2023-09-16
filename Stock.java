package ePortfolio;
import java.util.*;

/**
 * Stock subclass of Investment class
 */
public class Stock extends Investment{
    /**
     * Constant commission fee of $9.99
     */
    public static final double COM = 9.99; //commission

    //Constructors
    /**
     * Constructor for creating Stock object
     * @param symbol
     * @param name
     * @param quantity
     * @param price
     */
    public Stock(String symbol, String name, int quantity, double price) {
        //from Investment class
        super(symbol, name, price, quantity);
        //update bookValue and quantity
        bookValue += (quantity * getPrice() + COM);
        quantity += quantity;
    }
    /**
     * Constructor for creating a Stock object from input file
     * @param inputFile
     * @throws Exception if file is invalid
     */
    public Stock(Scanner inputFile){
        super(inputFile);
    }

    /**
     * toString method for Stock
     */
    @Override
    public String toString() {
        return "\tType: Stock " + super.toString();
    }

}
