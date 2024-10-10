package com.cursosalura.conversor;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Main {
    private static final Conversor conversor = new Conversor(); // Marcar como final
    private static final String[] codigosMonedas = {"ARS", "BOB", "BRL", "CLP", "COP", "USD", "DOP", "EUR"}; // Marcar como final
    private static Map<String, Double> tasasFiltradas;

    public static void main(String[] args) {
        String monedaBase = "USD"; // Moneda base
        tasasFiltradas = conversor.obtenerTasasFiltradas(monedaBase, codigosMonedas);

        if (tasasFiltradas == null) {
            JOptionPane.showMessageDialog(null, "No se pudieron obtener las tasas de cambio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear la ventana principal
        JFrame frame = new JFrame("Conversor de Monedas");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Crear el menú principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton btnVerTasas = new JButton("Ver tasas de cambio");
        JButton btnConvertir = new JButton("Convertir monedas");
        JButton btnVerMonedas = new JButton("Ver tipos de moneda");
        JButton btnSalir = new JButton("Salir");

        panel.add(btnVerTasas);
        panel.add(btnConvertir);
        panel.add(btnVerMonedas);
        panel.add(btnSalir);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Acción para ver tasas de cambio usando lambda
        btnVerTasas.addActionListener(e -> {
            StringBuilder tasas = new StringBuilder();
            tasasFiltradas.forEach((key, value) -> tasas.append(key).append(": ").append(value).append("\n"));
            JOptionPane.showMessageDialog(frame, tasas.toString(), "Tasas de Cambio", JOptionPane.INFORMATION_MESSAGE);
        });

        // Acción para convertir monedas usando lambda
        btnConvertir.addActionListener(e -> realizarConversion(frame));

        // Acción para ver los tipos de moneda usando lambda
        btnVerMonedas.addActionListener(e -> mostrarTiposMoneda(frame));

        // Acción para salir usando lambda
        btnSalir.addActionListener(e -> frame.dispose()); // Cierra la aplicación
    }

    private static void realizarConversion(JFrame frame) {
        JTextField montoField = new JTextField();

        // Crear listas desplegables (ComboBox) para las monedas
        JComboBox<String> monedaOrigenBox = new JComboBox<>(codigosMonedas);
        JComboBox<String> monedaDestinoBox = new JComboBox<>(codigosMonedas);

        Object[] inputs = {
                "Ingrese el monto a convertir:", montoField,
                "Seleccione la moneda de origen:", monedaOrigenBox,
                "Seleccione la moneda de destino:", monedaDestinoBox
        };

        int option = JOptionPane.showConfirmDialog(frame, inputs, "Conversión de Moneda", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                double monto = Double.parseDouble(montoField.getText());
                String monedaOrigen = (String) monedaOrigenBox.getSelectedItem();
                String monedaDestino = (String) monedaDestinoBox.getSelectedItem();

                double resultado = conversor.convertir(monto, monedaOrigen, monedaDestino, tasasFiltradas);
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(frame, String.format("El monto convertido de %s a %s es: %.2f", monedaOrigen, monedaDestino, resultado));
                } else {
                    JOptionPane.showMessageDialog(frame, "La conversión no se pudo realizar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese un número válido para el monto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void mostrarTiposMoneda(JFrame frame) {
        StringBuilder tiposDeMoneda = new StringBuilder("Tipos de moneda disponibles:\n");
        for (String codigoMoneda : codigosMonedas) {
            tiposDeMoneda.append(codigoMoneda).append("\n");
        }
        JOptionPane.showMessageDialog(frame, tiposDeMoneda.toString(), "Tipos de Moneda", JOptionPane.INFORMATION_MESSAGE);
    }
}

