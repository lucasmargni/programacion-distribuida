package servidor_clima;

import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.Properties;
import java.util.HashMap;

public class ServidorClima {
    public static void main(String args[]) {
        ServerSocket ss;
        Properties prop = new Properties();

        System.out.print("Inicializando servidor... ");

        try {
            FileInputStream fis = new FileInputStream("servidor_clima/config.properties");
            prop.load(fis);

            // Obtenemos las variables
            int PUERTO_CLIMA = Integer.parseInt(prop.getProperty("PUERTO_CLIMA"));


            ss = new ServerSocket(PUERTO_CLIMA);
            System.out.println("\t[OK] Clima");

            // Generamos un hash para guardar predicciones
            HashMap<String, Integer> prediccionesClima = new HashMap<>();

            while(true) {
                Socket socket = ss.accept();
                System.out.println("Nueva conexión entrante: " + socket);

                ServidorClimaHilo serv = new ServidorClimaHilo(socket, prediccionesClima);
                Thread servHilo = new Thread(serv);
                servHilo.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorClima.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}