package rmi.servidor_clima;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.*;

import rmi.servidor_central.SolicitudClima;

public class ServicioClimaImp extends UnicastRemoteObject implements ServicioClima {
    private static String[] prediccionesClima = {
        "se espera lluvia ligera con una temperatura máxima de 17°C",
        "habrá la lluvia ligera y la temperatura subirá hasta los 23°C",
        "el cielo estará parcialmente soleado con una máxima de 21°C", 
        "se prevé un día soleado con una temperatura de 26°C", 
        "estará parcialmente soleado y la temperatura alcanzará los 27°C",
        "se vuela todo"
    };

    private HashMap<String, Integer> predGuardadas;
    private static Semaphore mutex = new Semaphore(1);

    protected ServicioClimaImp(HashMap<String, Integer> predGuardadas) throws RemoteException {
        super();
        this.predGuardadas = predGuardadas;
    }

    @Override
    public String predecirClima(String fecha) throws RemoteException {  
        Random random = new Random();  
        int numPrediccion = 0;    

        try {
            mutex.acquire();
                
            if(predGuardadas.containsKey(fecha)) {
                // ya se realizó una prediccion sobre esta fecha
                numPrediccion = predGuardadas.get(fecha);
            } else {
                numPrediccion = random.nextInt(prediccionesClima.length);

                // nueva prediccion, la guardamos en el hash
                predGuardadas.put(fecha, numPrediccion);
            }

            mutex.release();
        } catch (Exception ex) {
            Logger.getLogger(ServicioClimaImp.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        String prediccion = "Prediccion de la fecha " + fecha + ": " + prediccionesClima[numPrediccion];

        System.out.println("Se envia al cliente '" + prediccion + "'");

        return prediccion; 
    }
}