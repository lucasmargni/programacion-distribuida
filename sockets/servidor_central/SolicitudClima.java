package servidor_central;

import java.io.*;
import java.net.Socket;
import java.util.logging.*;

public class SolicitudClima implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String fecha;
    private String[] buffer;

    public SolicitudClima(Socket socket, String fecha, String[] buffer) {
        this.fecha = fecha;
        this.buffer = buffer; // para devolver al servidor la respuesta obtenida

        try {
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(SolicitudClima.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        BufferedReader stdIn;

        try {
            System.out.println("Se envia solicitud de la fecha " + fecha);
            out.writeUTF(fecha);

            String respuesta = in.readUTF();
            System.out.println("El servidor clima envia: " + respuesta);

            buffer[0] = respuesta;

        } catch (IOException ex) {
            Logger.getLogger(SolicitudClima.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.desconectar();        
    }

    private void desconectar() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SolicitudClima.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

