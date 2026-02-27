package rmi;

import java.io.Serializable;

/**
 * Task to get all vegetables and their prices
 */
public class GetAllVegetables implements Task<String>, Serializable {
    
    public GetAllVegetables() {
    }
    
    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ALL VEGETABLES IN STORAGE ===\n\n");
        
        if (VegetableComputeEngine.getVegetablePriceTable().isEmpty()) {
            sb.append("No vegetables in storage.\n");
        } else {
            for (java.util.Map.Entry<String, Double> entry : VegetableComputeEngine.getVegetablePriceTable().entrySet()) {
                sb.append(String.format("%-15s : $%.2f per kg\n", entry.getKey(), entry.getValue()));
            }
        }
        return sb.toString();
    }
}
