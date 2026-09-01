package com.example.demo1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaApp extends JFrame {
    //@Override
    public static boolean isVerified = false;
    private String captchaString;
    private BufferedImage captchaImage;
    private JTextField inputField;
    private JLabel captchaLabel;

    public CaptchaApp() {
        setTitle("CAPTCHA Verification");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        captchaLabel = new JLabel();
        generateCaptcha();

        inputField = new JTextField();
        JButton verifyButton = new JButton("Verify");
        JButton changeButton = new JButton("Change CAPTCHA");

        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputField.getText().equals(captchaString)) {
                    JOptionPane.showMessageDialog(CaptchaApp.this, "CAPTCHA Verified!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    isVerified = true;
                    dispose();
                    synchronized (CaptchaApp.class) {
                        CaptchaApp.class.notify();
                    }
                } else {
                    JOptionPane.showMessageDialog(CaptchaApp.this, "Incorrect CAPTCHA. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    generateCaptcha();
                }
            }
        });

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateCaptcha();
            }
        });

        panel.add(captchaLabel, BorderLayout.NORTH);
        panel.add(inputField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(verifyButton);
        buttonPanel.add(changeButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
    }

    private void generateCaptcha() {
        captchaString = generateRandomString(6);
        captchaImage = createCaptchaImage(captchaString);
        captchaLabel.setIcon(new ImageIcon(captchaImage));
    }

    private String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        while (sb.length() < length) {
            int index = (int) (rnd.nextFloat() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    private BufferedImage createCaptchaImage(String captchaText) {
        int width = 160;
        int height = 40;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        Font font = new Font("Georgia", Font.BOLD, 18);
        g2d.setFont(font);

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);

        GradientPaint gp = new GradientPaint(0, 0,
                Color.blue, 0, height / 2, Color.lightGray, true);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(new Color(255, 153, 0));

        Random r = new Random();
        int x = 0;
        int y = 0;

        for (int i = 0; i < captchaText.length(); i++) {
            x += 10 + (Math.abs(r.nextInt()) % 15);
            y = 20 + Math.abs(r.nextInt()) % 20;
            g2d.drawChars(captchaText.toCharArray(), i, 1, x, y);
        }

        g2d.dispose();

        return bufferedImage;
    }
    public static void showCaptcha() {
        final boolean[] result = {false};
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CaptchaApp captchaApp = new CaptchaApp();
                captchaApp.setVisible(true);
            }
        });

        synchronized(CaptchaApp.class) {
            try {
                CaptchaApp.class.wait();
                result[0] = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}