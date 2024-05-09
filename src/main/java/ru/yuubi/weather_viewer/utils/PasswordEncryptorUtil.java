package ru.yuubi.weather_viewer.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptorUtil {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
