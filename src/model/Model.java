package model;

import java.util.HashMap;
import java.util.Map;

public class Model {
    // Attributes
    private String modelName;
    private String modelPrice;
    private Map<String, Integer> stockInOutlet = new HashMap<>();

    public Model(String modelName, String modelPrice) {
        this.modelName = modelName;
        this.modelPrice = modelPrice;
    }

    // Add stock for an outlet
    public void setStock(String outletCode, int quantity) {
        stockInOutlet.put(outletCode, quantity);
    }

    // Get stock for an outlet
    public int getStock(String outletCode) {
        return stockInOutlet.getOrDefault(outletCode, 0);
    }

    // Get all stock data
    public Map<String, Integer> getAllStock() {
        return stockInOutlet;
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getModelPrice() {
        return this.modelPrice;
    }
}
