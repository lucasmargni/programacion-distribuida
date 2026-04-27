package rmi.servidor_horoscopo;

import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.*;

import java.util.Properties;
import java.util.HashMap;

public class ServidorHoroscopo {
    public static void main(String args[]) {
        Properties prop = new Properties();
        System.out.print("Inicializando servidor horoscopo... ");

        try {
            FileInputStream fis = new FileInputStream("rmi/servidor_horoscopo/config.properties");
            prop.load(fis);

            // Obtenemos la variable
            int PUERTO_HOROSCOPO = Integer.parseInt(prop.getProperty("PUERTO_HOROSCOPO"));
            String IP_HOROSCOPO = prop.getProperty("IP_HOROSCOPO");
            System.out.println("\t[OK] Horoscopo");

            // Generamos un hash para guardar predicciones
            HashMap<String, Integer> prediccionesHoroscopo = new HashMap<>();
            
            LocateRegistry.createRegistry(PUERTO_HOROSCOPO);
            ServicioHoroscopo servicioHoroscopoImp = new ServicioHoroscopoImp(prediccionesHoroscopo);
            Naming.rebind("rmi://" + IP_HOROSCOPO + ":" + PUERTO_HOROSCOPO + "/ServicioHoroscopoImp", servicioHoroscopoImp);
            
        } catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1); }
        catch (Exception e) {
            System.err.println("Excepcion en ServicioHoroscopo:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}