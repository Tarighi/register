/*
package com.tarighi.register

import com.orhanobut.hawk.Hawk
import java.util.ArrayList

 object UserController {
    @JvmStatic
    private var Users: MutableList<UserInfo>? = null
    fun getUsers(): List<UserInfo>? {
        Users = Hawk.get<MutableList<UserInfo>>(Constant.USERLIST);
        if(this.Users==null)
            this.Users = ArrayList();
        return this.Users;
    }

    private fun setUsers(users: MutableList<UserInfo>) {
        Users = users
    }

    private fun addUser(user: UserInfo) {
        Users!!.add(user)
        Hawk.put(Constant.USERLIST, Users);
    }

//    fun getUser(UserName: String): UserInfo {
//        return UserInfo()
//        //        return Users.stream().filter(x-> x.UserName.equalsIgnoreCase(UserName)).findFirst().get();
//    }

    fun addOrUpdateUser(newuser: UserInfo) {

        var oldUser=findUser(newuser);
        if(oldUser!=null)
        {
            oldUser=newuser;
            Hawk.put(Constant.USERLIST, Users);
        }
        else
        {
            addUser(newuser);
        }
    }
    fun signIn(currentUser: UserInfo) {
        Hawk.put(Constant.CURRENTUSER, currentUser);
    }
    fun signOut() {
        Hawk.delete(Constant.CURRENTUSER);
    }

    fun findUser( user: UserInfo): UserInfo? {
        var list = getUsers();
        list!!.forEach {
            if (it.equals(user)) {
                return it;

            }
        }
        return null
    }


    private fun init() {
        if (getUsers()!!.size > 0)
            return

        Users = ArrayList();
        var admin = UserInfo();
        admin.FirstName = "Ali";
        admin.Family = "Tarighi";
        admin.Mobile = 1;

        addUser(admin);

    }

}
*/
