package ru.geekbrains.java2.client;

import java.io.IOException;

@FunctionalInterface
public interface AuthEvent {
    void authIsSuccessful(String nickname) throws IOException;
}
