package com.sym022.sym022.enums;

import java.util.Arrays;

public enum Culture {
    AMERICAN_INDIAN_OR_ALASKA_NATIVE ("American Indian or Alaska native"),
    ASIAN ("Asian"),
    BLACK_OR_AFRICAN_AMERICAN ("Black or African American"),
    NATIVE_HAWAIIAN_OR_OTHER_PACIFIC_ISLANDER ("Native Hawaiian or Other Pacific Islander"),
    WHITE ("White"),
    NOT_REPORTED ("Not Reported"),
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
    Culture(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getCulture(){
        return type;
    }

    public static Culture strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(Culture.values())
                .filter(s -> s.getCulture().equals(type))
                .findFirst()
                .orElse(null);
    }

}
