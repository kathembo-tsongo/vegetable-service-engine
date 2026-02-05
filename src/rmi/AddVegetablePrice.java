package rmi;

/**
 * Task to add a new vegetable price entry
 */
public class AddVegetablePrice implements Task<String> {
    private String vegetableName;
    private double price;
    
    public AddVegetablePrice(String vegetableName, double price) {
        this.vegetableName = vegetableName;
        this.price = price;
    }
    
    @Override
    public String execute() {
        if (VegetableComputeEngine.getVegetablePriceTable().containsKey(vegetableName)) {
            return "ERROR: Vegetable '" + vegetableName + "' already exists";
        }
        VegetableComputeEngine.getVegetablePriceTable().put(vegetableName, price);
        return "SUCCESS: Added " + vegetableName + " at price $" + price;
    }
}
