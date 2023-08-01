package com.sym022.sym022.enums;

import java.util.Arrays;

public enum IeNotMet {
    INCL_01 ("Incl01"),
    INCL_02 ("Incl02"),
    INCL_03 ("Incl03"),
    INCL_04 ("Incl04"),
    INCL_05 ("Incl05"),
    EXCL_01 ("Excl01"),
    EXCL_02 ("Excl02"),
    EXCL_03 ("Excl03"),
    NA ("Not Available");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    IeNotMet(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getIeNotMet(){
        return type;
    }

    public static IeNotMet strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(IeNotMet.values())
                .filter(s -> s.getIeNotMet().equals(type))
                .findFirst()
                .orElse(null);
    }
}
