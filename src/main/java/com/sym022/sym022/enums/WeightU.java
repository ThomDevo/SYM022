package com.sym022.sym022.enums;

import java.util.Arrays;

public enum WeightU {

    KG ("kg"),
    POUNDS ("pounds");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    WeightU(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getWeightU(){
        return type;
    }

    public static WeightU strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(WeightU.values())
                .filter(s -> s.getWeightU().equals(type))
                .findFirst()
                .orElse(null);
    }

}
