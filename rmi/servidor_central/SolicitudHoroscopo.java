package rmi.servidor_central;

import java.io.*;
import java.rmi.Naming;
import java.util.logging.*;

import rmi.servidor_horoscopo.ServicioHoroscopo;

public class SolicitudHoroscopo implements Runnable {
    private String ip_horoscopo;
    private int puerto_horoscopo;
    private String signo;
    private String[] buffer;

    public SolicitudHoroscopo(String ip_horoscopo, int puerto_horoscopo, String signo, String[] buffer) {
        this.ip_horoscopo = ip_horoscopo;
        this.puerto_horoscopo = puerto_horoscopo;
        this.signo = signo;
        this.buffer = buffer; // para devolver al servidor la respuesta obtenida
    }

    @Override
    public void run() {
        String respuesta = "";

        try {
            ServicioHoroscopo servicioHoroscopo = (ServicioHoroscopo) Naming.lookup("//"+this.ip_horoscopo+":"+this.puerto_horoscopo+"/ServicioHoroscopoImp");
            respuesta = servicioHoroscopo.predecirHoroscopo(this.signo);
            System.out.println("El servidor horoscopo envia: " + respuesta);

            buffer[0] = respuesta;

        } catch (Exception ex) {
            Logger.getLogger(SolicitudHoroscopo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

