package com.example.demo1;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public final class SendEmail {
    private SendEmail() {
    }

    public static void emailSender(String email, String text, String subject) throws MessagingException {
        if (!AppConfig.isSmtpConfigured()) {
            throw new IllegalStateException(
                    "SMTP is not configured. Set SMTP_USER and SMTP_APP_PASSWORD environment variables."
            );
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", AppConfig.smtpHost());
        props.put("mail.smtp.port", AppConfig.smtpPort());

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AppConfig.smtpUser(), AppConfig.smtpAppPassword());
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(AppConfig.smtpUser()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);
    }
}
