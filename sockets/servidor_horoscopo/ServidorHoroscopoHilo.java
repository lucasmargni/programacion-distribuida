package servidor_horoscopo;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.logging.*;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class ServidorHoroscopoHilo implements Runnable {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private HashMap<String, Integer> predGuardadas;
    private static Semaphore mutex = new Semaphore(1);

    private static String[] prediccionesHoroscopo = {
        "Un encuentro inesperado hoy podría cambiar tu perspectiva sobre un proyecto que tenías pausado",
        "Confía en tu intuición antes de tomar esa decisión financiera que te genera dudas",
        "Es un momento ideal para soltar viejos hábitos y dejar espacio para nuevas energías en tu hogar",
        "Alguien cercano buscará tu consejo. Escucha con atención pero evita involucrarte emocionalmente de más",
        "Hoy la creatividad estará de tu lado, aprovecha para resolver ese problema que parecía estancado",
        "Recibirás una noticia que te dará la tranquilidad que estuviste buscando durante toda la semana",
        "Mantené la calma ante los imprevistos, tu capacidad de adaptación será tu mejor herramienta hoy",
        "Vas a aprobar distribuidos"
    };

    public ServidorHoroscopoHilo(Socket socket, HashMap<String, Integer> predGuardadas) {
        this.socket = socket;
        this.predGuardadas = predGuardadas;

        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServidorHoroscopoHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        String signo;
        Random random = new Random();

        try {
            // recibe el signo de la cual se quiere realizar una prediccion
            signo = in.readUTF();
            int numPrediccion;

            mutex.acquire();
            
            if(predGuardadas.containsKey(signo)) {
                // ya se realizó una prediccion sobre este signo
                numPrediccion = predGuardadas.get(signo);
            } else {
                numPrediccion = random.nextInt(prediccionesHoroscopo.length);

                // nueva prediccion, la guardamos en el hash
                predGuardadas.put(signo, numPrediccion);
            }

            mutex.release();
            
            String prediccion = "Prediccion del signo " + signo + ": " + prediccionesHoroscopo[numPrediccion];

            System.out.println("Se envia al cliente '" + prediccion + "'");
            out.writeUTF(prediccion);
        } catch (IOException ex) {
            Logger.getLogger(ServidorHoroscopoHilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServidorHoroscopoHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.desconectar();
    }

    private void desconectar() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorHoroscopoHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}