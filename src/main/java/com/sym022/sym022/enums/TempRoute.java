package com.sym022.sym022.enums;

import java.util.Arrays;

public enum TempRoute {

    ORAL ("Oral"),
    RECTAL ("Rectal"),
    AXILLARY ("Axillary"),
    TYMPANIC ("Tympanic"),
    UNKNOWN ("Unknown");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    TempRoute(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getTempRoute(){
        return type;
    }

    public static TempRoute strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(TempRoute.values())
                .filter(s -> s.getTempRoute().equals(type))
                .findFirst()
                .orElse(null);
    }

}
