package ru.geekbrains.java2.client;

@FunctionalInterface
public interface AuthEvent {
    void authIsSuccessful(String nickname);
}
