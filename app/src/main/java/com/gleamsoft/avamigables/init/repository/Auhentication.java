    package com.gleamsoft.avamigables.init.repository;
     
    import com.gleamsoft.avamigables.util.State;
    import com.parse.LogInCallback;
    import com.parse.ParseException;
    import com.parse.ParseUser;
    import com.parse.SignUpCallback;

    /**
     * Created by Developer on 4/09/2017.
     */
    
    public class Auhentication {
    private boolean isValidate= false;
    public   boolean login(String username,String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
               
                if (user != null) {
                    isValidate = true;
                } else {
                    isValidate = false;
                }
            }
        });
        return isValidate;
    }
    public  boolean register(String username,String email, String password,String name,String phone, String plate){
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.put("name", name);
        user.put("phone", phone);
        user.put("plate", plate);
        user.put("state", State.active);
       
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    isValidate = true;
                } else {
                    isValidate = false;
                }
            }
        });
        return isValidate;
    }
    
    }
