package src.utils;

import src.model.enums.Genero;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputUtils {
    private static Scanner scanner = new Scanner(System.in);

    public static LocalDate lerData(String tipoData){
        while (true) {
            try {
                System.out.println("Data de " + tipoData + " (yyyy-mm-dd): ");
                String input = scanner.nextLine();
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido! Por favor, insira a data no formato yyyy-mm-dd.");
            }
        }
    }

    public static Genero lerGenero(){
        while(true) {
            try{
                System.out.print("Gênero (MASC/FEM/OUTRO): ");
                return Genero.valueOf(scanner.nextLine().toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Entrada inválida! Por favor, insira um gênero válido (MASC/FEM/OUTRO).");
            }
        }
    }

//    public static String lerTurma(){
//
//    }

    public static <T extends Enum<T>> T lerEnum(Class<T> enumClass) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                // Exibe as opções disponíveis no enum
                System.out.print("Opções disponíveis: ");
                for (T value : enumClass.getEnumConstants()) {
                    System.out.print(value.name() + " ");
                }
                System.out.println();

                // Solicita ao usuário uma entrada
                System.out.print("Escolha uma opção: ");
                String entrada = scanner.nextLine().toUpperCase();

                // Retorna o enum correspondente
                return Enum.valueOf(enumClass, entrada);
            } catch (IllegalArgumentException e) {
                System.out.println("Entrada inválida! Por favor, insira um valor válido.");
            }
        }
    }

    public static Boolean lerBool(String mensagem){
        System.out.println(mensagem);
        while (true) {
            String entrada = scanner.nextLine().trim().toUpperCase();
            if (entrada.equals("SIM")) {
                return true;
            } else if (entrada.equals("NÃO") || !entrada.equals("NAO")) {
                return false;
            }
            System.out.println("Resposta inválida! Por favor, digite 'SIM' ou 'NÃO'.");
        }
    }

    public static String lerString(String mensagem){
        System.out.println(mensagem);

        while(true){
            String entrada = scanner.nextLine().trim();
            if(!entrada.isBlank()){
                return entrada;
            }
            System.out.println("Entrada inválida! Por favor, insira um valor não vazio.");
        }
    }

    public static Integer lerInteger(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, insira um número inteiro.");
            }
        }
    }


}
