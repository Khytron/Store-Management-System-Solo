package model;

public class Outlet {
    private String outletCode;
    private String outletName;

    public Outlet(String outletCode, String outletName){
        this.outletCode = outletCode;
        this.outletName = outletName;
    }

    public String getOutletCode(){
        return this.outletCode;
    }

    public String getOutletName(){
        return this.outletName;
    }
    
}
