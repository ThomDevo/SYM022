package com.sym022.sym022.enums;

import java.util.Arrays;

public enum Cmroute {

    ORAL ("Oral"),
    INTRAVENOUS ("Intravenous"),
    INTRAMUSCULAR ("Intramuscular"),
    INTRALESIONAL ("Intralesional"),
    INTRAPERITONEAL ("Intraperitoneal"),
    SUBCUTANEOUS ("Subcutaneous"),
    SUBLINGUAL ("Sublingual"),
    OPHTHALMIC ("Ophthalmic"),
    AURICULAR ("Auricular"),
    NASAL ("Nasal"),
    RESPIRATORY ("Respiratory"),
    TOPICAL ("Topical"),
    TRANSDERMAL ("Transdermal"),
    RECTAL ("Rectal"),
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
    Cmroute(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getCmroute(){
        return type;
    }

    public static Cmroute strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(Cmroute.values())
                .filter(s -> s.getCmroute().equals(type))
                .findFirst()
                .orElse(null);
    }
}
