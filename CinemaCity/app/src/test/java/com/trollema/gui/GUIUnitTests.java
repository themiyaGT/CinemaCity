package com.trollema.gui;

import com.trollema.appdata.models.User;
import com.trollema.cinemacity.ControllerUnitTests;
import com.trollema.controllers.MovieDataController;
import com.trollema.controllers.RequestHandler;
import com.trollema.controllers.UserDataController;
import com.trollema.gui.Accounts;
import com.trollema.gui.AdminPanel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.InputMismatchException;

import static org.junit.Assert.*;

/**
 * Project 10 unit tests.
 * @author Maxim Mints
 */
public class GUIUnitTests {

    public static final int TIMEOUT = 200;

    private ControllerUnitTests.RequestHandlerMock handler;

    @Before
    public void setUp() {
        handler = new ControllerUnitTests().new RequestHandlerMock();
    }

    /**
     * Tests AdminPanel.getUsers( )
     */
    @Test(timeout = TIMEOUT)
    public void test_AdminPanel_getUsers() {
        AdminPanel panel = new AdminPanel();

        // test no users
        assertTrue(panel.getUsers(handler).isEmpty());

        // test with users
        ArrayList<User> expected = new ArrayList<>();
        expected.add(new User("test@test.com", "bob",
                "computerscience", "1", "himynameisbob", "0", "0", "0"));
        expected.add(new User("admin@cinemacity.us", "bob",
                "computerscience", "1", "himynameisbob", "0", "0", "0"));
        expected.add(new User("alex@test.com", "bob",
                "computerscience", "1", "himynameisbob", "0", "1", "0"));

        handler.createUser("{\"email\":\"test@test.com\",\"password\":\"test\","
                + "\"isActive\":\"0\",\"isBanned\":\"0\"}", "");
        handler.createUser("{\"email\":\"admin@cinemacity.us\",\"password\":\"test\","
                + "\"isActive\":\"0\",\"isBanned\":\"0\"}", "");
        handler.createUser("{\"email\":\"alex@test.com\",\"password\":\"test\","
                + "\"isActive\":\"1\",\"isBanned\":\"0\"}", "");

        ArrayList<User> result = panel.getUsers(handler);

        assertEquals(expected.size(), result.size());

        for (int i = 0; i < expected.size(); i++) {
            assertTrue(expected.get(i).equals(result.get(i)));
        }
    }

    /**
     * Tests Accounts.MyRegistration.doInBackground( )
     */
    @Test(timeout = TIMEOUT)
    public void test_Accounts_MyRegistration_doInBackground() {
        Accounts accounts = new Accounts();
        Accounts.MyRegistration registration = accounts.new MyRegistration();
        UserDataController origController = MainActivity.myUserDataController;
        MainActivity.myUserDataController = new UserDataController(new User(), handler);

        // test controller error for empty result data from handler
        registration.doInBackground("test", "password");
        assertEquals("Database Connection Error!", accounts.msg);

        // test creating an existing user
        handler.createUser("test0", "password");
        handler.createUser("test1", "password");
        handler.createUser("test2", "password");

        String alreadyExists = "Sorry an Account with that Email already exists!";

        registration.doInBackground("test0", "password");
        assertEquals(alreadyExists, accounts.msg);
        registration.doInBackground("test1", "password");
        assertEquals(alreadyExists, accounts.msg);
        registration.doInBackground("test2", "password");
        assertEquals(alreadyExists, accounts.msg);

        // test successfully create user
        registration.doInBackground("test3", "password");
        assertEquals("Account Created!", accounts.msg);

        MainActivity.myUserDataController = origController;
    }

    /**
     * Tests Profile.UpdateProfile.doInBackground( )
     */
    @Test(timeout = TIMEOUT)
    public void test_Profile_UpdateProfile_doInBackground() {
        Profile profile = new Profile();
        Profile.UpdateProfile updateProfile
                = profile.new UpdateProfile(handler);

        // test for exception handling
        handler.setUpdateProfile(true, true);
        updateProfile.doInBackground("a", "b", "c", "d", "e", "f");
        assertEquals("Database Connection Error!", profile.msg);

        // test change successful
        handler.setUpdateProfile(true, false);
        updateProfile.doInBackground("a", "b", "c", "d", "e", "f");
        assertEquals("Profile changes saved!", profile.msg);

        // test change unsuccessful
        handler.setUpdateProfile(false, false);
        updateProfile.doInBackground("a", "b", "c", "d", "e", "f");
        assertEquals("Sorry changes to the profile were not changed!", profile.msg);
    }
}