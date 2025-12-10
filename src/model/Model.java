package model;

public class Model {
    // Attributes
    private String modelName;
    private String modelPrice;
    private String stockC60;
    private String stockC61;
    private String stockC62;
    private String stockC63;
    private String stockC64;
    private String stockC65;
    private String stockC66;
    private String stockC67;
    private String stockC68;
    private String stockC69;

    public Model(String modelName, String modelPrice, String C60, String C61, String C62, String C63, String C64, String C65, String C66, String C67, String C68, String C69){
        this.modelName = modelName;
        this.modelPrice = modelPrice;
        this.stockC60 = C60;
        this.stockC61 = C61;
        this.stockC62 = C62;
        this.stockC63 = C63;
        this.stockC64 = C64;
        this.stockC65 = C65;
        this.stockC66 = C66;
        this.stockC67 = C67;
        this.stockC68 = C68;
        this.stockC69 = C69;
    }

    public String getModelName(){
        return this.modelName;
    }

    public String getModelPrice(){
        return this.modelPrice;
    }

    public String getStockC60(){
        return this.stockC60;
    }

    public String getStockC61(){
        return this.stockC61;
    }

    public String getStockC62(){
        return this.stockC62;
    }

    public String getStockC63(){
        return this.stockC63;
    }

    public String getStockC64(){
        return this.stockC64;
    }

    public String getStockC65(){
        return this.stockC65;
    }

    public String getStockC66(){
        return this.stockC66;
    }

    public String getStockC67(){
        return this.stockC67;
    }

    public String getStockC68(){
        return this.stockC68;
    }

    public String getStockC69(){
        return this.stockC69;
    }

}
