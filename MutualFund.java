package ePortfolio;
import java.util.*;

/**
 * MutualFund subclass of Investment class
 */
public class MutualFund extends Investment{
    /**
     * Constant selling fee of $45
     */
    public static final double FEE = 45.0; //selling fee

    //Constructors
    /**
     * Constructor for creating a MutualFund object
     * @param symbol
     * @param name
     * @param quantity
     * @param price
     */
    public MutualFund(String symbol, String name, int quantity, double price) {
        //from Investment class
        super(symbol, name, price, quantity);
        //Update bookValue and price
        bookValue += (quantity * getPrice());
        quantity += quantity;
    }
    /**
     * Constructor for creating a MutualFund object from input file
     * @param inputFile
     * @throws Exception if file is invalid
     */
    public MutualFund(Scanner inputFile){
        super(inputFile);
    }

    /**
     * toString method for MutualFund
     */
    @Override
    public String toString() {
        return "\tType: Mutual Fund " + super.toString();
    }

}
