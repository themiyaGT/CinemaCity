package com.trollema.core;
import com.trollema.appdata.constants.ServerResponse;
import com.trollema.controllers.RequestHandler;
import com.trollema.library.emailValidator.*;
import com.trollema.controllers.HTTPHandler;
import com.trollema.controllers.MovieDataController;
import com.trollema.controllers.StringValidator;
import com.trollema.controllers.StringValidator.*;
import com.trollema.appdata.models.Rating;

/**
 * Created by themiya on 2/15/2016.
 */
public class Start {
    public static void main(String[]args) {
        checkServerCodes();
    }

    public static void checkServerCodes(){
        System.out.println(ServerResponse.serverCodes.get("CCAPEXCODEA1"));
    }
}
