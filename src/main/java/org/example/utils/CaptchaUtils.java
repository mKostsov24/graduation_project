package org.example.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class CaptchaUtils {

    public static String getImageBase64(String code) {
        int width = 100, height = 35;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Times New Roman", Font.PLAIN, 14);
        g2d.setFont(font);
        g2d.setColor(new Color(169, 169, 169));
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(new Color(255, 255, 255));
        g2d.drawString(code, 20, 25);

        String base64EncodedImage = "";

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(img, "png", os);
            base64EncodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return base64EncodedImage;
    }

    public static String generateRandomString(int length) {
        return new Random()
                .ints(48, 122 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
