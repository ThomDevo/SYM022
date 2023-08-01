package com.sym022.sym022.enums;

import java.util.Arrays;

public enum Aesev {

    MILD ("Mild"),
    MODERATE ("Moderate"),
    SEVERE ("Severe"),
    NA("NA");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    Aesev(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getAesev(){
        return type;
    }

    public static Aesev strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(Aesev.values())
                .filter(s -> s.getAesev().equals(type))
                .findFirst()
                .orElse(null);
    }
}
