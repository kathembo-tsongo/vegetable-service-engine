package servlet;

import rmi.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet that handles HTTP requests and delegates to RMI server
 */
public class VegetableServiceServlet extends HttpServlet {
    
    private VegetableComputeTaskRegistry registry;
    
    @Override
    public void init() throws ServletException {
        try {
            registry = new VegetableComputeTaskRegistry();
            System.out.println("Servlet initialized and connected to RMI server");
        } catch (Exception e) {
            throw new ServletException("Failed to connect to RMI server", e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        
        String action = request.getParameter("action");
        
        try {
            String result = "";
            
            switch (action) {
                case "add":
                    String addVeg = request.getParameter("vegetable");
                    double addPrice = Double.parseDouble(request.getParameter("price"));
                    result = registry.executeTask(new AddVegetablePrice(addVeg, addPrice));
                    break;
                    
                case "update":
                    String updateVeg = request.getParameter("vegetable");
                    double updatePrice = Double.parseDouble(request.getParameter("price"));
                    result = registry.executeTask(new UpdateVegetablePrice(updateVeg, updatePrice));
                    break;
                    
                case "delete":
                    String deleteVeg = request.getParameter("vegetable");
                    result = registry.executeTask(new DeleteVegetablePrice(deleteVeg));
                    break;
                    
                case "calculate":
                    String calcVeg = request.getParameter("vegetable");
                    double quantity = Double.parseDouble(request.getParameter("quantity"));
                    result = registry.executeTask(new CalVegetableCost(calcVeg, quantity));
                    break;
                    
                case "receipt":
                    Map<String, Double> purchases = new HashMap<>();
                    String[] vegetables = request.getParameterValues("vegetables[]");
                    String[] quantities = request.getParameterValues("quantities[]");
                    
                    for (int i = 0; i < vegetables.length; i++) {
                        purchases.put(vegetables[i], Double.parseDouble(quantities[i]));
                    }
                    
                    double amountGiven = Double.parseDouble(request.getParameter("amountGiven"));
                    String cashier = request.getParameter("cashier");
                    
                    result = registry.executeTask(new CalculateCost(purchases, amountGiven, cashier));
                    break;
                    
                default:
                    result = "ERROR: Unknown action";
            }
            
            out.println(result);
            
        } catch (Exception e) {
            out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>Vegetable Service Engine</h1>");
        out.println("<p>Use POST requests to interact with the service</p>");
    }
}
