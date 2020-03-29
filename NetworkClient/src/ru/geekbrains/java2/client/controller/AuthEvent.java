package ru.geekbrains.java2.client.controller;

import java.io.IOException;

@FunctionalInterface
public interface AuthEvent {
    void authIsSuccessful(String login, String nickname) throws IOException;
}
