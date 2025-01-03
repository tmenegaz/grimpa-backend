package com.grimpa.site;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class PasswordConverter {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite 'cod' para codificar ou 'dec' para decodificar seguido da senha:");
        String input = scanner.nextLine();

        if (input.startsWith("cod ")) {
            String passwordToEncode = input.substring(4);
            String encodedPassword = encoder.encode(passwordToEncode);
            System.out.println("Senha codificada: " + encodedPassword);
        } else if (input.startsWith("dec ")) {
            String encodedPassword = input.substring(4);
            System.out.println("Digite a senha original para comparar:");
            String originalPassword = scanner.nextLine();
            boolean matches = encoder.matches(originalPassword, encodedPassword);
            if (matches) {
                System.out.println("A senha corresponde!");
            } else {
                System.out.println("A senha não corresponde.");
            }
        } else {
            System.out.println("Comando inválido. Use 'cod' para codificar ou 'dec' para decodificar.");
        }

        scanner.close();
    }
}
