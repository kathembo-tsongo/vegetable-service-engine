package client;

import rmi.*;
import java.util.HashMap;
import java.util.Map;

public class TestRMIClient {
    public static void main(String[] args) {
        try {
            VegetableComputeTaskRegistry registry = new VegetableComputeTaskRegistry();
            
            // Test 1: Add vegetable
            System.out.println("\n--- Test 1: Add Vegetable ---");
            String result1 = registry.executeTask(new AddVegetablePrice("carrot", 2.0));
            System.out.println(result1);
            
            // Test 2: Update vegetable
            System.out.println("\n--- Test 2: Update Vegetable ---");
            String result2 = registry.executeTask(new UpdateVegetablePrice("tomato", 3.0));
            System.out.println(result2);
            
            // Test 3: Calculate cost
            System.out.println("\n--- Test 3: Calculate Cost ---");
            String result3 = registry.executeTask(new CalVegetableCost("carrot", 5.0));
            System.out.println(result3);
            
            // Test 4: Generate receipt
            System.out.println("\n--- Test 4: Generate Receipt ---");
            Map<String, Double> purchases = new HashMap<>();
            purchases.put("tomato", 2.5);
            purchases.put("carrot", 3.0);
            purchases.put("onion", 1.5);
            String result4 = registry.executeTask(new CalculateCost(purchases, 50.0, "John Doe"));
            System.out.println(result4);
            
            // Test 5: Delete vegetable
            System.out.println("\n--- Test 5: Delete Vegetable ---");
            String result5 = registry.executeTask(new DeleteVegetablePrice("carrot"));
            System.out.println(result5);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
