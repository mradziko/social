package com.airnauts.social.model.parse;

import com.bartoszlipinski.parsemodel.ParseWrapperClass;
import com.parse.ParseUser;

@ParseWrapperClass(ParseUser.class)
public class User{
    String fbBirthday;
    String fbId;
    String name;
    int age;

}
