package ru.geekbrains.java2.server.auth;

import java.sql.*;
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

        /*for (UserData userData: USER_DATA) {
            if(userData.login.equals(login) && userData.password.equals(password)){
                return  userData.userName;
            }
        }*/

        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            //connection = DriverManager.getConnection("jdbc:sqlite:D:\\Java_code\\java_2\\users.s3db");
            connection = DriverManager.getConnection("jdbc:sqlite:users.s3db");
            PreparedStatement statement = connection.prepareStatement("SELECT username FROM logins where login = ? and password = ?;");
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("username");
            }else{
                return null;
            }
        } catch (Exception e) {
            System.out.println("Ошибка подключения к базе!");
            e.printStackTrace();
            return null;
        }finally {
            try {
                if(connection!=null) {connection.close();}
                System.out.println("Подключения к базе закрыто!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean changeNickname(String login, String nickname) {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            //connection = DriverManager.getConnection("jdbc:sqlite:D:\\Java_code\\java_2\\users.s3db");
            connection = DriverManager.getConnection("jdbc:sqlite:users.s3db");
            PreparedStatement statement = connection.prepareStatement("UPDATE logins SET username = ? WHERE login = ?;");
            statement.setString(1, nickname);
            statement.setString(2, login);
            if (statement.executeUpdate()!=0) return true;
        } catch (Exception e) {
            System.out.println("Ошибка подключения к базе!");
            e.printStackTrace();
            return false;
        }finally {
            try {
                if(connection!=null) {connection.close();}
                System.out.println("Подключения к базе закрыто!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
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
