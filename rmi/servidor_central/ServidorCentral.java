package rmi.servidor_central;

import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

public class ServidorCentral {
    public static void main(String args[]) {
        Properties prop = new Properties();
        System.out.print("Inicializando servidor central... ");

        try {
            FileInputStream fis = new FileInputStream("rmi/servidor_central/config.properties");
            prop.load(fis);

            // Obtenemos la variable
            int PUERTO_CENTRAL = Integer.parseInt(prop.getProperty("PUERTO_CENTRAL"));
            String IP_CENTRAL = prop.getProperty("IP_CENTRAL");
            System.out.println("\t[OK] Central");
            
            LocateRegistry.createRegistry(PUERTO_CENTRAL);
            ServicioPrediccion servicioPrediccionImp = new ServicioPrediccionImp();
            Naming.rebind("rmi://" + IP_CENTRAL + ":" + PUERTO_CENTRAL + "/ServicioPrediccionImp", servicioPrediccionImp);
            
        } catch (RemoteException e) {
            System.err.println("Error de comunicacion: " + e.toString());
            System.exit(1); 
        } catch (Exception e) {
            System.err.println("Excepcion en ServidorPrediccion:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}