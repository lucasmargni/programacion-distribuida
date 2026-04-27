package sockets.servidor_central;

import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.Properties;

public class ServidorCentral {
    public static void main(String args[]) {
        ServerSocket ss;
        Properties prop = new Properties();
        System.out.print("Inicializando servidor... ");

        try {
            FileInputStream fis = new FileInputStream("sockets/servidor_central/config.properties");
            prop.load(fis);

            // Obtenemos las variables
            int PUERTO_CENTRAL = Integer.parseInt(prop.getProperty("PUERTO_CENTRAL"));

            ss = new ServerSocket(PUERTO_CENTRAL);
            System.out.println("\t[OK] Central");

            while (true) {
                Socket socket = ss.accept();
                System.out.println("Nueva conexión entrante: " + socket);

                ServidorCentralHilo serv = new ServidorCentralHilo(socket);
                Thread servHilo = new Thread(serv);
                servHilo.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}