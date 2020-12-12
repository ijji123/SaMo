package com.example.samo20;

public interface IBluetooth extends ThreadController.IController {
    @Override
    void connectToBluetooth();
    void sendCommand(String command);
    boolean getStatus();
}
