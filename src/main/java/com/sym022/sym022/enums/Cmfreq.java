package com.sym022.sym022.enums;

import java.util.Arrays;

public enum Cmfreq {

    ONCE_PER_DAY ("Once Per Day"),
    TWICE_PER_DAY ("Twice Per Day"),
    THREE_TIMES_PER_DAY ("Three Times Per Day"),
    FOUR_TIMES_PER_DAY ("Four Times Per Day"),
    EVERY_OTHER_DAY ("Every Other Day"),
    EVERY_MORNING ("Every Morning"),
    EVERY_NIGHT ("Every Night"),
    ONCE_PER_WEEK ("Once Per Week"),
    TWICE_PER_WEEK ("Twice Per Week"),
    THREE_TIMES_PER_WEEK ("Three Times Per Week"),
    EVERY_TWO_WEEKS ("Every Two Weeks"),
    ONCE ("Once"),
    CONTINUOUS ("Continuous"),
    AS_NEEDED ("As Needed"),
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
    Cmfreq(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getCmfreq(){
        return type;
    }

    public static Cmfreq strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(Cmfreq.values())
                .filter(s -> s.getCmfreq().equals(type))
                .findFirst()
                .orElse(null);
    }
}
