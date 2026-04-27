package rmi.cliente;

import java.io.*;
import java.rmi.Naming;
import java.util.logging.*;
import java.util.Properties;
import java.util.Scanner;

import rmi.servidor_central.ServicioPrediccion;

public class Cliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Properties prop = new Properties();

        try {
            FileInputStream fis = new FileInputStream("rmi/cliente/config.properties");
            prop.load(fis);

            // Obtenemos las variables
            int PUERTO_CENTRAL = Integer.parseInt(prop.getProperty("PUERTO_CENTRAL"));
            String IP_CENTRAL = prop.getProperty("IP_CENTRAL");

            ServicioPrediccion servicioPrediccion = (ServicioPrediccion)Naming.lookup ("//"+IP_CENTRAL+":"+PUERTO_CENTRAL+"/ServicioPrediccionImp");

            System.out.print("Ingrese la fecha para realizar predicción del clima: ");
            String mensajeClima = sc.nextLine();

            System.out.print("Ingrese el signo para realizar predicción del horóscopo: ");
            String mensajeHoroscopo = sc.nextLine();

            String mensaje = mensajeClima + "~" + mensajeHoroscopo;

            String[] respuestas = separarRespuestas(servicioPrediccion.realizar_consulta(mensaje));

            System.out.println("El servidor envia: ");
            System.out.println(respuestas[0]);
            System.out.println(respuestas[1]);

        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String[] separarRespuestas(String respuestas){
        String[] respuestasSeparadas = respuestas.split("~");
        return respuestasSeparadas;
    }
}