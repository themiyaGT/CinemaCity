package com.trollema.appdata.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * ServerResponse: contains the static data of all the server response codes and meanings
 * Created by themiya on 4/27/2016.
 */
public class ServerResponse {
    public static final Map<String, String> serverCodes = new HashMap<String, String>();
    static {
        serverCodes.put("CCAPEXCODEA1","Success account created!"+'\n'+"An email has been sent to verify account.");
        serverCodes.put("CCAPEXCODEA2","Sorry account already exists!");
        serverCodes.put("CCAPEXCODEP1","Please insert a valid email!");
        serverCodes.put("CCAPEXCODEP2","Please insert a valid password!"+'\n'+"(length must be at least 8 characters)");
        serverCodes.put("CCAPEXCODEP*","One or more fields are invalid!");
        serverCodes.put("CCAPEXCODEL1","Login Success!");
        serverCodes.put("CCAPEXCODEL2","Welcome Admin!");
        serverCodes.put("CCAPEXCODEL3","The account is not verified!"+'\n'+"An email has been sent to verify account.");
        serverCodes.put("CCAPEXCODEL4","The account is banned!"+'\n'+"Access has been revoked!");
        serverCodes.put("CCAPEXCODEL5","The account is currently logged on from another device!");
        serverCodes.put("CCAPEXCODEL6","Sorry the email and password entered is not corrrect"+'\n'+"Please try again!");
        serverCodes.put("CCAPEXCODEL7","Too many incorrect login attempts!"+'\n'+"The account is now banned!");
    }
}
