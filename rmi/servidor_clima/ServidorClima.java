package rmi.servidor_clima;

import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.*;

import java.util.Properties;
import java.util.HashMap;

public class ServidorClima {
    public static void main(String args[]) {
        Properties prop = new Properties();
        System.out.print("Inicializando servidor clima... ");

        try {
            FileInputStream fis = new FileInputStream("rmi/servidor_clima/config.properties");
            prop.load(fis);

            // Obtenemos la variable
            int PUERTO_CLIMA = Integer.parseInt(prop.getProperty("PUERTO_CLIMA"));
            String IP_CLIMA = prop.getProperty("IP_CLIMA");
            System.out.println("\t[OK] Clima");

            // Generamos un hash para guardar predicciones
            HashMap<String, Integer> prediccionesClima = new HashMap<>();
            
            LocateRegistry.createRegistry(PUERTO_CLIMA);
            ServicioClima servicioClimaImp = new ServicioClimaImp(prediccionesClima);
            Naming.rebind("rmi://" + IP_CLIMA + ":" + PUERTO_CLIMA + "/ServicioClimaImp", servicioClimaImp);
            
        } catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1); }
        catch (Exception e) {
            System.err.println("Excepcion en ServicioClima:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}