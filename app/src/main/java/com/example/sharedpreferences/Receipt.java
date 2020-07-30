package com.example.sharedpreferences;

public class Receipt {

    private String name;
    private int total;

    public Receipt(String name, int total){
        this.name = name;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


}
