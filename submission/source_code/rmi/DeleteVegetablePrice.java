package rmi;

/**
 * Task to delete a vegetable price entry
 */
public class DeleteVegetablePrice implements Task<String> {
    private String vegetableName;
    
    public DeleteVegetablePrice(String vegetableName) {
        this.vegetableName = vegetableName;
    }
    
    @Override
    public String execute() {
        if (!VegetableComputeEngine.getVegetablePriceTable().containsKey(vegetableName)) {
            return "ERROR: Vegetable '" + vegetableName + "' not found";
        }
        VegetableComputeEngine.getVegetablePriceTable().remove(vegetableName);
        return "SUCCESS: Deleted " + vegetableName;
    }
}
