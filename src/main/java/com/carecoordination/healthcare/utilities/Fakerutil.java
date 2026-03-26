package com.carecoordination.healthcare.utilities;

import com.github.javafaker.Faker;

public class Fakerutil {


    private static Faker faker = new Faker();


    public static String getFirstName(){
        return faker.name().firstName();
    }

    public static String getLastName(){
        return faker.name().lastName();
    }

    public static  String getEmail(String firstname, String roleName){

        String cleanName = firstname.toLowerCase().replace("[^a-z]", "");
        String cleanRole = roleName.toLowerCase().replaceAll("\\s", "");

        return cleanName +"_"+ cleanRole + "@yopmail.com";
    }

    public static String getPhone(){
        return "9" + (100000000 + faker.number().numberBetween(0, 899999999));
    }


}
