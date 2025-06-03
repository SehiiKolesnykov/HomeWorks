package hw13;

import hw13.console.ConsoleHandler;
import hw13.util.LoggerUtil;

public class Main {
    public static void main(String[] args) {

        LoggerUtil.log("Application started");
        try (ConsoleHandler console = new ConsoleHandler()) {
            console.start();
        }

        LoggerUtil.log("Application terminated");
    }
}
