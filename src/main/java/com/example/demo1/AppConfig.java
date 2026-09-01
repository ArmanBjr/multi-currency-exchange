package com.example.demo1;

/**
 * Runtime configuration loaded from environment variables.
 * Copy {@code .env.example} and export the values before running.
 */
public final class AppConfig {
    private AppConfig() {
    }

    public static String dbUrl() {
        return env("DB_URL", "jdbc:mysql://localhost:3306/exchange");
    }

    public static String onlineDbUrl() {
        return env("ONLINE_DB_URL", "jdbc:mysql://localhost:3306/onlineusers");
    }

    public static String dbUser() {
        return env("DB_USER", "root");
    }

    public static String dbPassword() {
        return env("DB_PASSWORD", "");
    }

    public static String smtpHost() {
        return env("SMTP_HOST", "smtp.gmail.com");
    }

    public static String smtpPort() {
        return env("SMTP_PORT", "587");
    }

    public static String smtpUser() {
        return env("SMTP_USER", "");
    }

    public static String smtpAppPassword() {
        return env("SMTP_APP_PASSWORD", "");
    }

    public static boolean isSmtpConfigured() {
        return !smtpUser().isBlank() && !smtpAppPassword().isBlank();
    }

    private static String env(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return value;
    }
}
