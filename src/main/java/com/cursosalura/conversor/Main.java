package com.cursosalura.conversor;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String monedaBase = "USD"; // Cambia esto por la moneda base que quieras
        String[] codigosMonedas = {"ARS", "BOB", "BRL", "CLP", "COP", "USD", "DOP", "EUR"}; // Códigos de moneda a filtrar
        Map<String, Double> tasasFiltradas = Conversor.obtenerTasasFiltradas(monedaBase, codigosMonedas);

        if (tasasFiltradas == null) {
            System.out.println("No se pudieron obtener las tasas de cambio.");
            return;
        }

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n*** Menú de Conversor de Monedas ***");
            System.out.println("1. Ver tasas de cambio");
            System.out.println("2. Convertir monedas");
            System.out.println("3. Ver tipos de moneda");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    Conversor.mostrarTasas(tasasFiltradas);
                    break;
                case 2:
                    realizarConversion(scanner, tasasFiltradas);
                    break;
                case 3:
                    Conversor.mostrarTiposMoneda();
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        System.out.println("Gracias por usar el conversor de monedas. ¡Hasta luego!");
        scanner.close();
    }

    public static void realizarConversion(Scanner scanner, Map<String, Double> tasasFiltradas) {
        System.out.print("Ingrese el monto a convertir: ");
        double monto = scanner.nextDouble();
        System.out.print("Ingrese el código de la moneda de origen: ");
        String monedaOrigen = scanner.next().toUpperCase();
        System.out.print("Ingrese el código de la moneda de destino: ");
        String monedaDestino = scanner.next().toUpperCase();

        double resultado = Conversor.convertir(monto, monedaOrigen, monedaDestino, tasasFiltradas);
        if (resultado > 0) {
            System.out.printf("El monto convertido de %s a %s es: %.2f%n", monedaOrigen, monedaDestino, resultado);
        }
    }
}
