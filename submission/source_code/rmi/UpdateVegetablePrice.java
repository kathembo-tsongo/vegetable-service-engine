package rmi;

/**
 * Task to update an existing vegetable price
 */
public class UpdateVegetablePrice implements Task<String> {
    private String vegetableName;
    private double newPrice;
    
    public UpdateVegetablePrice(String vegetableName, double newPrice) {
        this.vegetableName = vegetableName;
        this.newPrice = newPrice;
    }
    
    @Override
    public String execute() {
        if (!VegetableComputeEngine.getVegetablePriceTable().containsKey(vegetableName)) {
            return "ERROR: Vegetable '" + vegetableName + "' not found";
        }
        VegetableComputeEngine.getVegetablePriceTable().put(vegetableName, newPrice);
        return "SUCCESS: Updated " + vegetableName + " to price $" + newPrice;
    }
}
