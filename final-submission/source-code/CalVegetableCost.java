package rmi;

/**
 * Task to calculate the cost of vegetables based on quantity
 */
public class CalVegetableCost implements Task<String> {
    private String vegetableName;
    private double quantity;
    
    public CalVegetableCost(String vegetableName, double quantity) {
        this.vegetableName = vegetableName;
        this.quantity = quantity;
    }
    
    @Override
    public String execute() {
        if (!VegetableComputeEngine.getVegetablePriceTable().containsKey(vegetableName)) {
            return "ERROR: Vegetable '" + vegetableName + "' not found";
        }
        double price = VegetableComputeEngine.getVegetablePriceTable().get(vegetableName);
        double totalCost = price * quantity;
        return String.format("Vegetable: %s, Quantity: %.2f kg, Price: $%.2f/kg, Total Cost: $%.2f", 
                           vegetableName, quantity, price, totalCost);
    }
}
