package org.example.connection;

import org.example.Enums.MessageType;

import java.util.logging.*;

/**
 * NotificationManager centraliza el envío de mensajes de log con distintos niveles y colores.
 */
public class NotificationManager {
    private static NotificationManager instance;
    private final Logger logger;

    private NotificationManager(String name) {
        logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);

        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter() {
            private static final String format = "%1$tF %1$tT %2$s %3$s%n";
            @Override
            public String format(LogRecord record) {
                return String.format(format,
                        record.getMillis(),
                        record.getLevel().getName(),
                        record.getMessage());
            }
        });
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
    }

    /**
     * Obtiene la instancia singleton, creando si hace falta.
     */
    public static synchronized NotificationManager getInstance(String name) {
        if (instance == null) {
            instance = new NotificationManager(name);
        }
        return instance;
    }

    /**
     * Obtiene la instancia ya creada.
     */
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

    /**
     * Envía un mensaje al log con color y nivel adecuados.
     * @param message El texto a registrar.
     * @param type    Tipo de mensaje (INFO, WARNING, ERROR).
     */
    public void pushMessage(String message, MessageType type) {
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
        logger.log(level,colored);
        System.out.flush();

    }

    /**
     * Permite deshabilitar la impresión de la ruta de clase en los logs.
     * @param showSource si es false, no incluye source class en el mensaje.
     */
    public void setShowSource(boolean showSource) {
        for (Handler h : logger.getHandlers()) {
            final boolean show = showSource;
            h.setFormatter(new SimpleFormatter() {
                private final String formatWithSource = "%1$tF %1$tT %2$s [%4$s] %3$s%n";
                private final String formatNoSource = "%1$tF %1$tT %2$s %3$s%n";
                @Override
                public synchronized String format(LogRecord record) {
                    String fmt = show ? formatWithSource : formatNoSource;
                    return String.format(fmt,
                            record.getMillis(),
                            record.getLevel().getName(),
                            record.getMessage(),
                            record.getSourceClassName());
                }
            });
        }
    };
}

