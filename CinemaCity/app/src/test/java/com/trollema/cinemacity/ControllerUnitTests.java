package com.trollema.cinemacity;

import com.trollema.appdata.models.User;
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
public class ControllerUnitTests {

    public static final int TIMEOUT = 200;

    public class RequestHandlerMock implements RequestHandler {

        private String movieData;
        private ArrayList<String> users = new ArrayList<>();

        private boolean useCreateUserCustomReturn = false;
        private boolean createUserCustomReturn;

        private boolean useLoginCustomReturn = false;
        private boolean loginCustomReturn;

        private boolean updateProfileReturnVal = false;
        private boolean updateProfileThrowExc = false;

        @Override
        public ArrayList<String> getUsers() {
            return users;
        }

        @Override
        public String getProfile(String username) {
            return "{\"email\":\"" + username
                    + "\",\"name\":\"bob\",\"major\":\"computerscience\","
                    + "\"year\":\"1\",\"bio\":\"himynameisbob\","
                    + "\"img_id\":\"0\",\"isBanned\":\"0\"}";
        }

        public void setLoginCustomReturn(boolean value) {
            useLoginCustomReturn = true;
            loginCustomReturn = value;
        }

        @Override
        public boolean login(String username) {
            if (!useLoginCustomReturn) {
                for (String val: users) {
                    if (val.contains(username)) {
                        return true;
                    }
                }

                return false;
            } else {
                return loginCustomReturn;
            }
        }

        @Override
        public boolean logout(String username) {
            return false;
        }

        public void setCreateUserCustomReturn(boolean value) {
            useCreateUserCustomReturn = true;
            createUserCustomReturn = value;
        }

        @Override
        public boolean createUser(String username, String password) {
            if (!useCreateUserCustomReturn) {
                return users.add(username);
            } else {
                return createUserCustomReturn;
            }
        }

        @Override
        public boolean createProfile(String username) {
            return users.contains(username);
        }

        public void setUpdateProfile(boolean returnVal, boolean throwExc) {
            updateProfileReturnVal = returnVal;
            updateProfileThrowExc = throwExc;
        }

        @Override
        public boolean updateProfile(String email, String name, String major,
                                     String year, String bio, String img) {
            if (updateProfileThrowExc) {
                throw new RuntimeException("mocking something going wrong with the connection");
            }

            return updateProfileReturnVal;
        }

        @Override
        public void banUser(String username) {

        }

        @Override
        public void undoBan(String username) {

        }

        @Override
        public boolean isAdmin(String username) {
            return false;
        }

        @Override
        public String searchMovies(String givenTitle) {
            return movieData;
        }

        public void setMovieData(String movieData) {
            this.movieData = movieData;
        }
    }

    private RequestHandlerMock handler;

    @Before
    public void setUp() {
        handler = new RequestHandlerMock();
    }


    /**
     * Tests MovieDataController.getMovieTitles( )
     */
    @Test(timeout = TIMEOUT)
    public void test_MovieDataController_getMovieTitles() {
        // test error response (no movies)
        handler.setMovieData("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}");
        ArrayList<String> emptyResult = MovieDataController.getMovieTitles("test", handler);
        assertTrue(emptyResult.isEmpty());

        // test normal response with several movies
        handler.setMovieData("{\"Search\":[{\"Title\":\"test0\",\"Year\":...data here will be cut off...},"
                + "{\"Title\":\"test1\",\"Year\":...data here will be cut off as well...},"
                + "{\"Title\":\"test2\",\"Year\":asdf.....");
        ArrayList<String> result = MovieDataController.getMovieTitles("test", handler);
        assertEquals(3, result.size());
        assertEquals("test0", result.get(0));
        assertEquals("test1", result.get(1));
        assertEquals("test2", result.get(2));

        //test normal response with 1 movie
        handler.setMovieData("{\"Search\":[{\"Title\":\"test0\",\"Year\":...data here will be cut off...");
        result = MovieDataController.getMovieTitles("test", handler);
        assertEquals(1, result.size());
        assertEquals("test0", result.get(0));
    }

    /**
     * Tests UserDataController.createUser( )
     */
    @Test(timeout = TIMEOUT)
    public void test_UserDataController_createUser() throws Exception {
        RequestHandlerMock localHandler = new RequestHandlerMock();
        UserDataController controller = new UserDataController(new User(), localHandler);

        // test error for empty result data
        try {
            controller.createUser("test", "password");
            fail("expected exception");
        } catch(Exception exc) { }

        // test creating an existing user
        localHandler.createUser("test0", "password");
        localHandler.createUser("test1", "password");
        localHandler.createUser("test2", "password");

        assertFalse(controller.createUser("test0", "password"));
        assertFalse(controller.createUser("test1", "password"));
        assertFalse(controller.createUser("test2", "password"));

        // test creating a valid new user
        assertTrue(controller.createUser("test3", "password"));

        // test failing to create valid user
        localHandler.setCreateUserCustomReturn(false);
        assertFalse(controller.createUser("test4", "password"));
    }

    /**
     * Tests UserDataController.loginUser( )
     */
    @Test(timeout = TIMEOUT)
    public void test_UserDataController_loginUser() throws Exception {
        // test result data empty
        RequestHandlerMock localHandler = new RequestHandlerMock();
        UserDataController controller = new UserDataController(new User(), localHandler);

        // test error for empty result data
        try {
            controller.loginUser("test", "password");
            fail("expected exception");
        } catch(Exception exc) { }

        // test logging in an un-existing user
        localHandler.createUser("{\"email\":\"test@test.com\",\"password\":\"test\","
                + "\"isActive\":\"0\",\"isBanned\":\"0\"}", "");
        localHandler.createUser("{\"email\":\"admin@cinemacity.us\",\"password\":\"test\","
                + "\"isActive\":\"0\",\"isBanned\":\"0\"}", "");
        assertFalse(controller.loginUser("MrAsdf", "password"));

        // test logging in with wrong password
        assertFalse(controller.loginUser("test@test.com", "wrongPass"));

        // test logging in a user who's already active
        localHandler.createUser("{\"email\":\"alex@test.com\",\"password\":\"test\","
                + "\"isActive\":\"1\",\"isBanned\":\"0\"}", "");
        try {
            controller.loginUser("alex@test.com", "test");
            fail("expected exception");
        } catch (InputMismatchException exc) { }

        // test valid login
        assertTrue(controller.loginUser("test@test.com", "test"));
        assertTrue(controller.loginUser("admin@cinemacity.us", "test"));

        // test login interruption from bad internet
        localHandler.setLoginCustomReturn(false);
        assertFalse(controller.loginUser("test@test.com", "test"));
        assertFalse(controller.loginUser("admin@cinemacity.us", "test"));
    }
}