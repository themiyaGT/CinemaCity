package com.trollema.controllers;
import com.trollema.library.emailValidator.*;
import com.trollema.appdata.constants.*;
import java.util.Arrays;
import java.lang.Exception;
/**
 * Created by themiya on 3/27/2016.
 */
public class StringValidator {
    /**
     * Takes a string and determines if the string is a valid major
     * @param major String input
     * @return true if accepted major, otherwise false
     * @author Jinming Yu
     */
    public static boolean isMajor(String major){
        major = major.toLowerCase();
        return Arrays.asList(MajorData.validMajor).contains(major);
    }

    /**
     * Validates a string and determines if the string is a valid email
     * @param email String input
     * @return true if accepted email, otherwise false
     * @throws Exception
     * @author Themiya Chandraratna
     */
    public static boolean isEmail(String email) throws Exception{
        try {
            ValidEmail emailChecker = new ValidEmail();
            return emailChecker.validateEmail(email);
        }
        catch (Exception e) {
            throw new Exception("Failed to Validate Email!");
        }
    }
}
