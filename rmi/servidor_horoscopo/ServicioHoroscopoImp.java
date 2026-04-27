package rmi.servidor_horoscopo;

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

public class ServicioHoroscopoImp extends UnicastRemoteObject implements ServicioHoroscopo {
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

    private HashMap<String, Integer> predGuardadas;
    private static Semaphore mutex = new Semaphore(1);

    protected ServicioHoroscopoImp(HashMap<String, Integer> predGuardadas) throws RemoteException {
        super();
        this.predGuardadas = predGuardadas;
    }

    @Override
    public String predecirHoroscopo(String signo) throws RemoteException {  
        Random random = new Random();  
        int numPrediccion = 0;  

        try {

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

        } catch (Exception ex) {
            Logger.getLogger(ServicioHoroscopoImp.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        String prediccion = "Prediccion del signo " + signo + ": " + prediccionesHoroscopo[numPrediccion];

        System.out.println("Se envia al cliente '" + prediccion + "'");

        return prediccion; 
    }
}