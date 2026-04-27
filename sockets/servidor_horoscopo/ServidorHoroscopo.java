package sockets.servidor_horoscopo;

import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.Properties;
import java.util.HashMap;

public class ServidorHoroscopo {
    public static void main(String args[]) {
        ServerSocket ss;
        Properties prop = new Properties();

        System.out.print("Inicializando servidor... ");

        try {
            FileInputStream fis = new FileInputStream("sockets/servidor_horoscopo/config.properties");
            prop.load(fis);

            // Obtenemos las variables
            int PUERTO_HOROSCOPO = Integer.parseInt(prop.getProperty("PUERTO_HOROSCOPO"));


            ss = new ServerSocket(PUERTO_HOROSCOPO);
            System.out.println("\t[OK] Horóscopo");

            // Generamos un hash para guardar predicciones
            HashMap<String, Integer> prediccionesHoroscopo = new HashMap<>();

            while(true) {
                Socket socket = ss.accept();
                System.out.println("Nueva conexión entrante: " + socket);

                ServidorHoroscopoHilo serv = new ServidorHoroscopoHilo(socket, prediccionesHoroscopo);
                Thread servHilo = new Thread(serv);
                servHilo.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorHoroscopo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}