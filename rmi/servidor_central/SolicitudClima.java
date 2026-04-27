package rmi.servidor_central;

import java.io.*;
import java.rmi.Naming;
import java.util.logging.*;

import rmi.servidor_clima.ServicioClima;

public class SolicitudClima implements Runnable {
    private String ip_clima;
    private int puerto_clima;
    private String fecha;
    private String[] buffer;

    public SolicitudClima(String ip_clima, int puerto_clima, String fecha, String[] buffer) {
        this.ip_clima = ip_clima;
        this.puerto_clima = puerto_clima;
        this.fecha = fecha;
        this.buffer = buffer; // para devolver al servidor la respuesta obtenida
    }

    @Override
    public void run() {
        String respuesta = "";

        try {
            ServicioClima servicioClima = (ServicioClima) Naming.lookup("//"+this.ip_clima+":"+this.puerto_clima+"/ServicioClimaImp");
            respuesta = servicioClima.predecirClima(this.fecha);
            System.out.println("El servidor clima envia: " + respuesta);

            buffer[0] = respuesta;

        } catch (Exception ex) {
            Logger.getLogger(SolicitudClima.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

