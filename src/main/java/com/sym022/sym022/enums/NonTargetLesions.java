package com.sym022.sym022.enums;

import java.util.Arrays;

public enum NonTargetLesions {
    COMPLETE_RESPONSE ("Complete Response"),
    NON_COMPLETE_RESPONSE_NON_PROGRESSIVE_DISEASE ("Non Complete Response/Non Progressive Disease"),
    PROGRESSIVE_DISEASE ("Progressive disease"),
    NOT_ALL_EVALUATED ("Not All Evaluated"),
    NOT_EVALUABLE ("Not Evaluable");
    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    NonTargetLesions(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getNonTargetLesions(){
        return type;
    }

    public static NonTargetLesions strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(NonTargetLesions.values())
                .filter(s -> s.getNonTargetLesions().equals(type))
                .findFirst()
                .orElse(null);
    }
}
