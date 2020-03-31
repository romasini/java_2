package ru.geekbrains.java2.client;

public enum CommandType {
    AUTH,
    AUTH_ERROR,
    CHANGE_NICKNAME,
    PRIVATE_MESSAGE,
    BROADCAST_MESSAGE,
    MESSAGE,
    UPDATE_USERS_LIST,
    ERROR,
    END,
}
