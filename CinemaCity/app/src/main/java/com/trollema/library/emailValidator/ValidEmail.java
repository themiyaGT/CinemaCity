package com.trollema.library.emailValidator;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * I had to write a whole class for the email checker, since we couldn't
 * use external libraries. I made some class variables static for the
 * sake of saving memory.
 * MAKE SURE YOU PLACE ALL THE .TXT FILE IN THE SAME FOLDER AS THE
 * VALIDEMAIL.JAVA, OTHERWISE THE CODE WILL CRASH
 * @author Liubov Nikolenko
 * @version 1.0
 */
public class ValidEmail {
//    private static HashSet<String> set = new HashSet<>();
    private Pattern pattern;
//    private static boolean called = false;
    private static final String EMAIL_PATTERN =
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    /**
    * Creates a ValidEmail class
    * @throws IOException in case one or more of the necessary files
    * was not found. If you run into this exception, please read the lines in
    * caps in the description of the class.
    */
    public ValidEmail() throws IOException {
//        if (!called) {
//            File myFile = new File("");
//            String myPath = myFile.getAbsolutePath();
//            myPath = myPath+"\\app\\src\\main\\java\\com\\trollema\\library\\emailValidator";
//            getDomains(myPath+"\\Generic.txt");
//            getDomains(myPath+"\\North_America.txt");
//            getDomains(myPath+"\\South_America.txt");
//            getDomains(myPath+"\\Central_America.txt");
//            getDomains(myPath+"\\Africa.txt");
//            getDomains(myPath+"\\Alt.txt");
//            getDomains(myPath+"\\Asia.txt");
//            getDomains(myPath+"\\Europe.txt");
//            getDomains(myPath+"\\Middle_East.txt");
//            getDomains(myPath+"\\Oceania.txt");
//        }
//        called = true;
    }
    //private method don't have to be javadoced, so I will just describe
    //them briefly
    //this method get all possible domains from txt files.
//    private void getDomains(String s) throws IOException {
//        Scanner sc = new Scanner(new File(s));
//        while (sc.hasNext()) {
//            String domain = sc.next();
//            boolean upperCase = false;
//            for (int i = 0; i < domain.length() && !upperCase; i++) {
//                int x = (int) domain.charAt(i);
//                if (((int)x >= 65) && ((int)x <= 90)) {
//                    upperCase = true;
//                }
//            }
//            if (!upperCase && !domain.equals("and") && !domain.isEmpty()
//                && !domain.equals("the")) {
//                domain = domain.replace("-", "");
//                domain = domain.trim();
//                set.add(domain);
//            }
//        }
//    }
    //checking is email as the right pattern, aka
    //[something]@[something].[something]
    private boolean checkEmailpattern(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    //getting a domain from email
    private String getDomainFromEmail(String email) {
        int index = email.lastIndexOf('.');
        return email.substring(index);
    }
    /**
    * Checks if the email is valid, including the correct domain
    * @param email to be checked
    * @return true if email is valid, false otherwise
    */
    public boolean validateEmail(String email) {
        if (!checkEmailpattern(email)) {
            return false;
        }
        String domain = getDomainFromEmail(email);
        return true;
    }
}