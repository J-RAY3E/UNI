package org.example.connection;

import java.util.logging.*;

public class NotificationManager {
    private static NotificationManager instance;
    private final Logger logger;

    private NotificationManager(String name) {
        logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);

        StreamHandler handler = new StreamHandler(System.out, new SimpleFormatter() {
            private static final String format = "%1$tF %1$tT %2$s %3$s%n";

            @Override
            public synchronized String format(LogRecord record) {
                return String.format(format,
                        record.getMillis(),
                        record.getLevel().getName(),
                        record.getMessage());
            }
        }) {
            @Override
            public synchronized void publish(LogRecord record) {
                super.publish(record);
                flush();
            }
        };

        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
    }

    public static synchronized NotificationManager getInstance(String name) {
        if (instance == null) {
            instance = new NotificationManager(name);
        }
        return instance;
    }

    public static NotificationManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("NotificationManager is not initialized yet");
        }
        return instance;
    }

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";

    public void pushMessage(String message, org.example.Enums.MessageType type) {
        String colored;
        Level level;
        switch (type) {
            case ERROR:
                colored = RED + message + RESET;
                level = Level.SEVERE;
                break;
            case WARNING:
                colored = YELLOW + message + RESET;
                level = Level.WARNING;
                break;
            case INFO:
            default:
                colored = BLUE + message + RESET;
                level = Level.INFO;
                break;
        }
        logger.log(level, colored);
    }
}
