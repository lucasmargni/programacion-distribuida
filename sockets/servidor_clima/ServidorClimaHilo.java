package servidor_clima;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.*;

public class ServidorClimaHilo implements Runnable {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private HashMap<String, Integer> predGuardadas;
    private static Semaphore mutex = new Semaphore(1);

    private static String[] prediccionesClima = {
        "se espera lluvia ligera con una temperatura máxima de 17°C",
        "habrá la lluvia ligera y la temperatura subirá hasta los 23°C",
        "el cielo estará parcialmente soleado con una máxima de 21°C", 
        "se prevé un día soleado con una temperatura de 26°C", 
        "estará parcialmente soleado y la temperatura alcanzará los 27°C",
        "se vuela todo"
    };

    public ServidorClimaHilo(Socket socket, HashMap<String, Integer> predGuardadas) {
        this.socket = socket;
        this.predGuardadas = predGuardadas;

        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServidorClimaHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        String fecha;
        Random random = new Random();

        try {
            // recibe la fecha de la cual se quiere realizar una prediccion
            fecha = in.readUTF();
            int numPrediccion;

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
            
            String prediccion = "El dia " + fecha + " " + prediccionesClima[numPrediccion];

            System.out.println("Se envia al cliente '" + prediccion + "'");
            out.writeUTF(prediccion);
        } catch (IOException ex) {
            Logger.getLogger(ServidorClimaHilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServidorClimaHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.desconectar();
    }

    private void desconectar() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorClimaHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}