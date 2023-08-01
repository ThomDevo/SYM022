package com.sym022.sym022.enums;

import java.util.Arrays;

public enum Cmdosu {

    G ("g"),
    MG ("mg"),
    MCG ("mcg"),
    MG_KG ("mg/kg"),
    G_M2 ("g/m²"),
    IU ("IU"),
    ML ("ml"),
    MMOL ("mmol"),
    L_MIN ("l/min"),
    APPLICATION ("application"),
    PATCH ("patch"),
    CAPSULE ("capsule"),
    DROPS ("drops"),
    PUFF ("puff"),
    SUPPOSITORY ("suppository"),
    TABLET ("tablet"),
    AMPULE ("ampule"),
    TABLE_SPOON ("table spoon"),
    TEA_SPOON ("tea spoon"),
    VOL_PERC ("Vol %"),
    UNKNOWN ("Unknown"),
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
    Cmdosu(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getCmdosu(){
        return type;
    }

    public static Cmdosu strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(Cmdosu.values())
                .filter(s -> s.getCmdosu().equals(type))
                .findFirst()
                .orElse(null);
    }
}
