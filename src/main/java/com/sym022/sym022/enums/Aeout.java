package com.sym022.sym022.enums;
import java.util.Arrays;

public enum Aeout {
    NOT_RECOVERED_NOT_RESOLVED ("Not recovered/Not resolved"),
    RECOVERED_RESOLVED("Recovered/Resolved"),
    RECOVERED_RESOLVED_WITH_SEQUELAE("Recovered/Resolved with squelae"),
    RECOVERING_RESOLVING("Recovering/Resolving"),
    FATAL("Fatal"),
    UNKNOWN("Unknown");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    Aeout(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getAeout(){
        return type;
    }

    public static Aeout strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(Aeout.values())
                .filter(s -> s.getAeout().toLowerCase().equals(type))
                .findFirst()
                .orElse(null);
    }
}
