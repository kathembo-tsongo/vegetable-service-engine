package rmi;

import java.util.Map;

/**
 * Task to list all vegetables and their prices
 */
public class ListAllVegetables implements Task<String> {
    
    @Override
    public String execute() {
        Map<String, Double> table = VegetableComputeEngine.getVegetablePriceTable();
        
        if (table.isEmpty()) {
            return "No vegetables in inventory";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("========== INVENTORY LIST ==========\n");
        result.append(String.format("%-20s %10s\n", "Vegetable", "Price/kg"));
        result.append("====================================\n");
        
        for (Map.Entry<String, Double> entry : table.entrySet()) {
            result.append(String.format("%-20s $%9.2f\n", 
                entry.getKey(), entry.getValue()));
        }
        
        result.append("====================================\n");
        result.append(String.format("Total items: %d\n", table.size()));
        
        return result.toString();
    }
}