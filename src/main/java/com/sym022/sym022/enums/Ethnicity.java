package com.sym022.sym022.enums;

import java.util.Arrays;

public enum Ethnicity {
    NOT_REPORTED ("Not Reported"),
    HISPANIC_OR_LATINO_A ("Hispanic or Latino/a"),
    NOT_HISPANIC_OR_LATINO_A ("Not Hispanic or Latino/a"),
    NOT_APPLICABLE_NOT_PERMITTED ("Not Applicable/Not Permitted");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    Ethnicity(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getEthnicity(){
        return type;
    }

    public static Ethnicity strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(Ethnicity.values())
                .filter(s -> s.getEthnicity().equals(type))
                .findFirst()
                .orElse(null);
    }
}
