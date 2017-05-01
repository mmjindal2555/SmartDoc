package com.silk.smartdoc.Controller;

import com.google.firebase.database.DataSnapshot;
import com.silk.smartdoc.Model.Person;

/**
 * Created by Manish on 4/22/2017.
 */
public class LoggingController {

    public Person isValid(DataSnapshot snapshot, String username, String password){
        Person pers = null;
        for(DataSnapshot users: snapshot.getChildren()){
            Person person = users.getValue(Person.class);
            String dataUsername = person.getEmail();
            String dataPassword = person.getPassword();
            if(username.equals(dataUsername) && password.equals(dataPassword)){
                pers = person;
                break;
            }
        }
        return pers;
    }
    
}
