package com.sym022.sym022.enums;

import java.util.Arrays;

public enum HeightU {
    CM ("cm"),
    INCHES ("inches");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    HeightU(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getHeightU(){
        return type;
    }

    public static HeightU strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(HeightU.values())
                .filter(s -> s.getHeightU().equals(type))
                .findFirst()
                .orElse(null);
    }

}
