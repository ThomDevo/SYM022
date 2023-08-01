package com.sym022.sym022.enums;

import java.util.Arrays;

public enum FormLabel {
    DATE_OF_VISIT ("Date of Visit"),
    DEMOGRAPHY ("Demography"),
    INFORMED_CONSENT ("Informed Consent"),
    VITAL_SIGN ("Vital Sign"),
    TUMORAL_EVALUATION ("Tumoral Evaluation"),
    ADVERSE_EVENT ("Adverse Event"),
    CONCOMITANT_MEDICATION ("Concomitant Medication");


    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    FormLabel(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getFormLabel(){
        return type;
    }

    public static FormLabel strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(FormLabel.values())
                .filter(s -> s.getFormLabel().equals(type))
                .findFirst()
                .orElse(null);
    }

}
