package org.example.DataBaseManager.UserUtils;

import org.example.Enums.MessageType;
import org.example.connection.NotificationManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptation {
    public static String encryptationPassword(String password){
        try {
            MessageDigest encrypter = MessageDigest.getInstance("SHA-512");
            byte[] encrypted_password = encrypter.digest(password.getBytes());
            StringBuilder str_ec_password = new StringBuilder();
            for (byte byte_pass : encrypted_password) {
                String hex = Integer.toHexString(0xff & byte_pass);
                if (hex.length() == 1) str_ec_password.append("0");
                str_ec_password.append(hex);
            }
            return str_ec_password.toString();
        } catch (NoSuchAlgorithmException e){
            NotificationManager.getInstance().pushMessage("The encrypted type does not exit", MessageType.ERROR);
            return null;
        }

    }
}
