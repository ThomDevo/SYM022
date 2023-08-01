package com.sym022.sym022.enums;

import java.util.Arrays;

public enum QueryStatus {

    OPENED ("Opened"),
    ANSWERED ("Answered"),
    CLOSED ("Closed");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    QueryStatus(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getQueryStatus(){
        return type;
    }

    public static QueryStatus strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(QueryStatus.values())
                .filter(s -> s.getQueryStatus().equals(type))
                .findFirst()
                .orElse(null);
    }
}
