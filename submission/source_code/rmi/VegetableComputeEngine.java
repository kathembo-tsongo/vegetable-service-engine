package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * RMI Server that manages vegetable prices and processes client tasks
 */
public class VegetableComputeEngine extends UnicastRemoteObject implements Compute {
    
    // In-memory storage for vegetable prices
    private static Map<String, Double> vegetablePriceTable = new HashMap<>();
    
    public VegetableComputeEngine() throws RemoteException {
        super();
        // Initialize with some sample data
        vegetablePriceTable.put("tomato", 2.5);
        vegetablePriceTable.put("potato", 1.8);
        vegetablePriceTable.put("onion", 1.5);
    }
    
    @Override
    public <T> T executeTask(Task<T> task) throws RemoteException {
        return task.execute();
    }
    
    // Static method to access the vegetable price table from tasks
    public static Map<String, Double> getVegetablePriceTable() {
        return vegetablePriceTable;
    }
    
    public static void main(String[] args) {
        try {
            // Create and export a registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Create the compute engine
            VegetableComputeEngine engine = new VegetableComputeEngine();
            
            // Bind the remote object in the registry
            registry.rebind("VegetableComputeEngine", engine);
            
            System.out.println("VegetableComputeEngine is running on port 1099...");
            System.out.println("Initial vegetable prices loaded.");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
