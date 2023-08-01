package com.sym022.sym022.enums;

import java.util.Arrays;

public enum Aerel {
    NOT_RELATED ("Not Related"),
    UNLIKELY_RELATED ("Unlikely Related"),
    POSSIBLY_RELATED ("Possibly Related"),
    PROBABLY_RELATED ("Probably Related"),
    DEFINITELY_RELATED ("Definitely Related"),
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
    Aerel(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getAerel(){
        return type;
    }

    public static Aerel strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(Aerel.values())
                .filter(s -> s.getAerel().equals(type))
                .findFirst()
                .orElse(null);
    }
}
