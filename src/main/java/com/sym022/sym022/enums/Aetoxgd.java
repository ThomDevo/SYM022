package com.sym022.sym022.enums;

import java.util.Arrays;

public enum Aetoxgd {
    GRADE_1 ("Grade 1"),
    GRADE_2 ("Grade 2"),
    GRADE_3 ("Grade 3"),
    GRADE_4 ("Grade 4"),
    GRADE_5 ("Grade 5"),
    NA("NA");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    Aetoxgd(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getAetoxgd(){
        return type;
    }

    public static Aetoxgd strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(Aetoxgd.values())
                .filter(s -> s.getAetoxgd().equals(type))
                .findFirst()
                .orElse(null);
    }
}
