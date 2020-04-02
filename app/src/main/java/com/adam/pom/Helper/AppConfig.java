package com.adam.pom.Helper;

public class AppConfig {

    //URL for adding user
    public static String URL_REGISTER = "http://192.168.0.17/pom/add_user.php";

    //URL for reading users table
    public static final String URL_GET_USERS = "http://192.168.0.17/pom/get_all_users.php";

    //URL for getting a users data.
    public static final String URL_GET_USER_DETAILS = "http://192.168.0.17/pom/get_user_details.php";

    //URL for checking matches, when user swiped 'yes'.
    public static final String URL_CHECK_TRUE = "http://192.168.0.17/pom/check_match_true.php";

    //URL for checking matches when user swiped 'no'.
    public static final String URL_CHECK_FALSE = "http://192.168.0.17/pom/check_match_false.php";

    //URL for adding a match to the users matched list.
    public static final String URL_ADD_TO_MATCHED = "http://192.168.0.17/pom/add_to_matches.php";

    //URL for getting matches.
    public static final String URL_GET_MATCHES = "http://192.168.0.17/pom/get_matches.php";
}
