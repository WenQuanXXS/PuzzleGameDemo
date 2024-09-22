package util;

import java.util.Random;

public class CodeUtil {
    public static String createVerificationCode() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();

        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        for (int i = 0; i < 4; i++) {
            sb.append(letters.charAt(r.nextInt(letters.length())));
        }

        int randomIndex = r.nextInt(5);
        sb.insert(randomIndex, numbers.charAt(r.nextInt(numbers.length())));

        return sb.toString();
    }
}
