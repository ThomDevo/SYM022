package com.sym022.sym022.enums;

import java.util.Arrays;

public enum Sex {
    MALE ("Male"),
    FEMALE ("Female"),
    PREFER_NOT_TO_ANSWER ("Prefer Not To answer"),
    OTHER ("Other");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    Sex(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getSex(){
        return type;
    }

    public static Sex strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(Sex.values())
                .filter(s -> s.getSex().equals(type))
                .findFirst()
                .orElse(null);
    }
}
