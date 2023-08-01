package com.sym022.sym022.enums;

import java.util.Arrays;

public enum RoleLabel {
    DATA_MANAGER ("Data Manager"),
    CRA ("CRA"),
    SITE ("Site"),
    SAFETY ("Safety"),
    MEDICAL_REVIEWER ("Medical Reviewer"),
    READ_ONLY ("Read Only");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    RoleLabel(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getRoleLabel(){
        return type;
    }

    public static RoleLabel strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(RoleLabel.values())
                .filter(s -> s.getRoleLabel().equals(type))
                .findFirst()
                .orElse(null);
    }

}
