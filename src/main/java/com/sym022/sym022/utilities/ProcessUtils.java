package com.sym022.sym022.utilities;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.sym022.sym022.entities.UserEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessUtils {
    private ProcessUtils(){}

    /**
     * function to check regex with data
     * @return
     */
    public static boolean checKDataWithRegex(String data, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        return matcher.find();
    }

    /**
     * date and time today method
     * @return
     */
    public static LocalDateTime getDateTimeNow()
    {
        return LocalDateTime.now();
    }


    /**
     * function to convert a localdatetime to a string.
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String localdatetimeInPattern (LocalDateTime localDateTime, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * function to convert a localdatetime to a string format FR.
     * @param localDateTime
     * @return
     */
    public static String localdatetimeInPattern (LocalDateTime localDateTime){
        return ProcessUtils.localdatetimeInPattern(localDateTime, "dd/MM/yyyy");
    }

    /**
     * function to convert a localdatetime to a date.
     * @param localDateTime
     * @return
     */
    public static Date castLocalDateTimeToDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }


    /**
     * function to vonvert a date to a localdatetime
     */
    public static LocalDateTime castDateToLocalDateTime(Date dateConvert)
    {
        return LocalDateTime.ofInstant(dateConvert.toInstant(), ZoneId.systemDefault());
    }


    /**
     * function to debug a string in console.
     * @param message
     * @return
     */
    public static void debug (String message){
        Logger log = Logger.getAnonymousLogger();
        log.info("debug ----------------> "+message);
    }



    public static String floatToStrTwoDigit(float number){
        String outStr = String.valueOf(Math.floor(number*100));
        return outStr.substring(0, outStr.length()-4)+"."+outStr.substring(outStr.length()-4, outStr.length()-2);
    }

    /**
     * Verification password method
     * @param password
     * @return
     */
    public static boolean checkPassword (String password, UserEntity user){
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        return result.verified;
    }


    /**
     * Crypt Password method
     * @param password
     * @return
     */
    public static String cryptPassword(String password){
        return BCrypt.withDefaults().hashToString(10,password.toCharArray());
    }

    /**
     * Méthode booléenne de vérification si valeur est bien un int
     * @param value
     * @return true ou false
     */
    public static boolean isCheckValueIsint(String value) {

        if (value.matches("\\d+$")) {

            return true;
        }

        return false;
    }

    /**
     * Méthode booléenne de vérification si c'est null ou vide
     * @param value
     * @return true ou false
     */
    public static boolean isCheckValueIsEmptyorNull(String value) {

        if (value == null || value.equals("")) {

            return true;
        }

        return false;
    }

    /**
     * Méthode booléenne de vérification si valeur est bien un numéro de téléphone
     * @param telephone
     * @return true ou false
     */
    public static boolean isCheckTelephoneFormat(String telephone) {

        if (telephone.matches("^[0-9]{9,17}$")) {

            return true;
        }

        return false;
    }
}
