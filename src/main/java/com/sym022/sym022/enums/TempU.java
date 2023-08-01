package com.sym022.sym022.enums;

import java.util.Arrays;

public enum TempU {
    C ("C°"),
    T ("T°");


    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    TempU(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getTempU(){
        return type;
    }

    public static TempU strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(TempU.values())
                .filter(s -> s.getTempU().equals(type))
                .findFirst()
                .orElse(null);
    }
}
