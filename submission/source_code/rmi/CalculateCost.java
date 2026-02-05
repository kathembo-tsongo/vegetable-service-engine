package rmi;

import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Task to generate a receipt for the transaction
 */
public class CalculateCost implements Task<String> {
    private Map<String, Double> purchases; // vegetable name -> quantity
    private double amountGiven;
    private String cashierName;
    
    public CalculateCost(Map<String, Double> purchases, double amountGiven, String cashierName) {
        this.purchases = purchases;
        this.amountGiven = amountGiven;
        this.cashierName = cashierName;
    }
    
    @Override
    public String execute() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("\n========== VEGETABLE STORE RECEIPT ==========\n");
        receipt.append("Date: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n");
        receipt.append("Cashier: ").append(cashierName).append("\n");
        receipt.append("=============================================\n\n");
        
        double totalCost = 0.0;
        
        for (Map.Entry<String, Double> entry : purchases.entrySet()) {
            String vegetable = entry.getKey();
            double quantity = entry.getValue();
            
            if (!VegetableComputeEngine.getVegetablePriceTable().containsKey(vegetable)) {
                receipt.append("ERROR: ").append(vegetable).append(" not found\n");
                continue;
            }
            
            double price = VegetableComputeEngine.getVegetablePriceTable().get(vegetable);
            double cost = price * quantity;
            totalCost += cost;
            
            receipt.append(String.format("%-15s %6.2f kg x $%6.2f = $%8.2f\n", 
                                       vegetable, quantity, price, cost));
        }
        
        receipt.append("\n=============================================\n");
        receipt.append(String.format("Total Cost:          $%8.2f\n", totalCost));
        receipt.append(String.format("Amount Given:        $%8.2f\n", amountGiven));
        receipt.append(String.format("Change Due:          $%8.2f\n", amountGiven - totalCost));
        receipt.append("=============================================\n");
        
        return receipt.toString();
    }
}
