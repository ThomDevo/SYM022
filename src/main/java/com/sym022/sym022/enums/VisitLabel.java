package com.sym022.sym022.enums;

import java.util.Arrays;

public enum VisitLabel {
    SCREENING ("Screening"),
    MOIS_1 ("Mois 1"),
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
    VisitLabel(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getVisitLabel(){
        return type;
    }

    public static VisitLabel strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(VisitLabel.values())
                .filter(s -> s.getVisitLabel().toLowerCase().equals(type))
                .findFirst()
                .orElse(null);
    }

}
