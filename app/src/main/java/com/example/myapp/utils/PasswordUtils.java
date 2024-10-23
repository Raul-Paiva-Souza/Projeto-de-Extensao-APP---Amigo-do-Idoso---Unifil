package com.example.myapp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    private static final String SALT = "randomSalt"; // Use um salt gerado aleatoriamente em produção e armazene-o de forma segura

    // Método para gerar o hash da senha usando SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(SALT.getBytes()); // Adiciona o salt
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b)); // Converte byte em string hexadecimal
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para verificar se a senha fornecida corresponde ao hash armazenado
    public static boolean verifyPassword(String providedPassword, String storedHashedPassword) {
        // Hash da senha fornecida
        String hashedProvidedPassword = hashPassword(providedPassword);

        // Comparar o hash gerado com o hash armazenado
        return hashedProvidedPassword.equals(storedHashedPassword);
    }
}
