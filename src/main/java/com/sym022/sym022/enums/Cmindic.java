package com.sym022.sym022.enums;

import java.util.Arrays;

public enum Cmindic {
    ADVERSE_EVENT ("Adverse Event"),
    CONDITION_UNDER_STUDY ("Condition Under Study"),
    PROPHYLAXIS ("Prophylaxis"),
    DIAGNOSTIC_OR_EXPLORATORY_PROCEDURE ("Diagnostic Or Exploratory Procedure"),
    NOT_REPORTED ("Not Reported"),
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
    Cmindic(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getCmindic(){
        return type;
    }

    public static Cmindic strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(Cmindic.values())
                .filter(s -> s.getCmindic().equals(type))
                .findFirst()
                .orElse(null);
    }
}
