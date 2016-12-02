package com.example.ak_bappy.json;

/**
 * Created by BAPPY on 5/3/2016.
 */
public class Contact {
    public String id = "";
    public String name = "";
    public String email = "";
    public String address = "";
    public String gender = "";
    public String mobile = "";
    public String home = "";
    public String office = "" ;

    public String covertToString()
    {
        String str = "ID :"+id+"\nName :"+name+"\nEmail :"+email+"\nAddress :"
                +address+"\nGender :"+gender+"\nMobile :"+mobile+"\nHome :"+home+"\nOffice :"+office+"\n\n";
        return str;
    }
}
