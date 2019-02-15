package com.tarighi.register;

public class UserInfo {
    public int Id=0;
    public String FirstName;
    public String Family;
    public String GetFullName() {return FirstName+" " +Family;};
    public int Age;
    public String Email;
    public long Mobile;//09334445586
    public String AVATAR;
    public String City;
    public String RoleName;
    //to display object as a string in spinner
    @Override
    public String toString() {
        return GetFullName();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserInfo){
            UserInfo c = (UserInfo )obj;
            if(c.GetFullName().equals(GetFullName()) && c.Mobile==Mobile ) return true;
        }

        return false;
    }
}

