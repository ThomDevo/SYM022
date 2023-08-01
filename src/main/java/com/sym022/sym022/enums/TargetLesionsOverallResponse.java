package com.sym022.sym022.enums;

import java.util.Arrays;

public enum TargetLesionsOverallResponse {
    COMPLETE_RESPONSE ("Complete Response"),
    PARTIAL_RESPONSE ("Partial Response"),
    STABLE_DISEASE ("Stable disease"),
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
    TargetLesionsOverallResponse(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getTargetLesionsOverallResponse(){
        return type;
    }

    public static TargetLesionsOverallResponse strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(TargetLesionsOverallResponse.values())
                .filter(s -> s.getTargetLesionsOverallResponse().equals(type))
                .findFirst()
                .orElse(null);
    }
}
