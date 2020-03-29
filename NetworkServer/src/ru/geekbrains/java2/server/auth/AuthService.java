package ru.geekbrains.java2.server.auth;

public interface AuthService {

    String getUserNameByLoginAndPassword(String login, String password);
    boolean changeNickname(String login, String nickname);

    void start();
    void stop();

}
