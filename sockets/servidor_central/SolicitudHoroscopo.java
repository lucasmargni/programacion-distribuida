package sockets.servidor_central;

import java.io.*;
import java.net.Socket;
import java.util.logging.*;

public class SolicitudHoroscopo implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String signo;
    private String[] buffer;

    public SolicitudHoroscopo(Socket socket, String signo, String[] buffer) {
        this.signo = signo;
        this.buffer = buffer; // para devolver al servidor la respuesta obtenida

        try {
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(SolicitudHoroscopo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        BufferedReader stdIn;

        try {
            System.out.println("Se envia solicitud del signo " + signo);
            out.writeUTF(signo);

            String respuesta = in.readUTF();
            System.out.println("El servidor horoscopo envia: " + respuesta);

            buffer[0] = respuesta;

        } catch (IOException ex) {
            Logger.getLogger(SolicitudHoroscopo.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.desconectar();        
    }

    private void desconectar() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SolicitudHoroscopo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

