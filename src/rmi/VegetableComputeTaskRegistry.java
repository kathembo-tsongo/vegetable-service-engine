package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Client-side registry to lookup and execute tasks on the compute engine
 */
public class VegetableComputeTaskRegistry {
    
    private Compute computeEngine;
    
    public VegetableComputeTaskRegistry() throws Exception {
        // Lookup the remote object from the registry
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        computeEngine = (Compute) registry.lookup("VegetableComputeEngine");
        System.out.println("Connected to VegetableComputeEngine");
    }
    
    public <T> T executeTask(Task<T> task) throws Exception {
        return computeEngine.executeTask(task);
    }
}
