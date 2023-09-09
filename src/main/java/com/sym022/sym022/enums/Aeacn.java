package com.sym022.sym022.enums;

import java.util.Arrays;

public enum Aeacn {
    DOSE_NOT_CHANGED ("Dose Not Changed"),
    DOSE_INCREASED ("Dose increased"),
    DOSE_REDUCED ("Dose Reduced"),
    DRUG_INTERRUPTED ("Drug interrupted"),
    DRUG_WITHDRAWN ("Drug Withdrawn"),
    NOT_APPLICABLE("Not Applicable/Not Available");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    Aeacn(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getAeacn(){
        return type;
    }

    public static Aeacn strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(Aeacn.values())
                .filter(s -> s.getAeacn().equals(type))
                .findFirst()
                .orElse(null);
    }
}
