package com.tarighi.register;

import android.util.Log;

import com.orhanobut.hawk.Hawk;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class UserController {
    private static List<UserInfo> Users;
    public static final UserController INSTANCE;

    static {
        INSTANCE = new UserController();
    }

    private UserController() {
        init();
    }

    @Nullable
    public final List<UserInfo> getUsers() {
        if (!Hawk.contains(Constant.USERLIST)) {
            Users = new ArrayList<UserInfo>();
        }
        Users = Hawk.get(Constant.USERLIST);
        if (Users == null) {
            Users = new ArrayList<UserInfo>();
        }
        return Users;
    }

    private final void setUsers(List<UserInfo> users) {
        Users = users;
    }

    private final void addUser(UserInfo user) {
        user.Id = Users.size() + 1;
        Users.add(user);

        Hawk.put(Constant.USERLIST, Users);
    }

    public final void addOrUpdateUser(@NotNull UserInfo newuser) {
        if (newuser == null) {
            return;
        }
        UserInfo oldUser = this.findUser(newuser);
        if (oldUser != null) {
            Users.set(oldUser.Id-1, newuser);
            Log.d("aliT", newuser.toString());
            Hawk.put(Constant.USERLIST, Users);
        } else {
            this.addUser(newuser);
        }

    }

    public final void signIn(@NotNull UserInfo currentUser) {
        if (currentUser == null) {
            return;
        }
        Hawk.put(Constant.CURRENTUSER, currentUser);
    }

    public final void signOut() {
        Hawk.delete(Constant.CURRENTUSER);
    }

    public final UserInfo getCurrentUser() {
        if (Hawk.contains(Constant.CURRENTUSER)) {
            return Hawk.get(Constant.CURRENTUSER);
        } else
            return null;
    }

    @Nullable
    public final UserInfo findUser(@NotNull UserInfo user) {
        if (user == null) {
            return null;
        }
        List<UserInfo> users = this.getUsers();
        for (UserInfo it : users) {
            if (it.Id==user.Id)
                return it;
        }
        return null;
    }

    private final void init() {

//        Hawk.deleteAll();
        List<UserInfo> users = this.getUsers();
        if (users.size() <= 0) {
            Users = new ArrayList<UserInfo>();
            UserInfo admin = new UserInfo();
            admin.FirstName = "Ali";
            admin.Family = "Tarighi";
            admin.Mobile = 1;
            admin.Id = 1;
            admin.City = "Tehran";
            this.addUser(admin);
        }
    }


}
