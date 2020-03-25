package ru.geekbrains.java2.server.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaseAuthService implements AuthService{

    private static class UserData{
        private String login;
        private String password;
        private String userName;

        private UserData(String login, String password, String userName) {
            this.login = login;
            this.password = password;
            this.userName = userName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserData userData = (UserData) o;
            return Objects.equals(login, userData.login) &&
                    Objects.equals(password, userData.password) &&
                    Objects.equals(userName, userData.userName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(login, password, userName);
        }
    }

    private static final List<UserData> USER_DATA = new ArrayList<>();//заполним массив
    {
        USER_DATA.add(new UserData("login1", "pass1","username1"));
        USER_DATA.add(new UserData("login2", "pass2","username2"));
        USER_DATA.add(new UserData("login3", "pass3","username3"));
    }

    @Override
    public String getUserNameByLoginAndPassword(String login, String password) {
        for (UserData userData: USER_DATA) {
            if(userData.login.equals(login) && userData.password.equals(password)){
                return  userData.userName;
            }
        }

        return null;
    }

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");

    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
    }
}
