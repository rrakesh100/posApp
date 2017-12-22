package com.pos.commons;

/**
 * Created by rrampall on 22/12/17.
 */
public enum  EntityType {

    Employee("employee"),
    Customer("customer"),
    Items("items"),
    Sales("sales"),
    Suppliers("suppliers"),
    Procurements("procurements");

    private final String value;

     EntityType(String value){
         this.value=value;
     }

    public String getValue() {
        return value;
    }


}
