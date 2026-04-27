package servidor_central;

import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.Properties;

public class ServidorCentralHilo implements Runnable {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public ServidorCentralHilo(Socket socket) {
        this.socket = socket;

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        String fecha, signo;
        String[] respuestaClima = new String[1];
        String[] respuestaHoroscopo = new String[1];
        Properties prop = new Properties();

        try {
            FileInputStream fis = new FileInputStream("servidor_central/config.properties");
            prop.load(fis);

            // recibe datos de cliente (IMPORTA EL ORDEN)
            fecha = in.readUTF();
            signo = in.readUTF();

            // Obtenemos las variables
            int PUERTO_CLIMA = Integer.parseInt(prop.getProperty("PUERTO_CLIMA"));
            String IP_CLIMA = prop.getProperty("IP_CLIMA");

            int PUERTO_HOROSCOPO = Integer.parseInt(prop.getProperty("PUERTO_HOROSCOPO"));
            String IP_HOROSCOPO = prop.getProperty("IP_HOROSCOPO");

            // Solicita prediccion del clima
            Socket socketClima = new Socket(IP_CLIMA, PUERTO_CLIMA);
            SolicitudClima solC = new SolicitudClima(socketClima, fecha, respuestaClima);
            Thread hiloSolC = new Thread(solC);
            hiloSolC.start();

            // Solicita prediccion del horoscopo
            Socket socketHoroscopo = new Socket(IP_HOROSCOPO, PUERTO_HOROSCOPO);
            SolicitudHoroscopo solH = new SolicitudHoroscopo(socketHoroscopo, signo, respuestaHoroscopo);
            Thread hiloSolH = new Thread(solH);
            hiloSolH.start();

            // Espera respuesta de las solicitudes
            hiloSolC.join();
            hiloSolH.join();

            // Enviamos respuesta a cliente
            out.writeUTF(respuestaClima[0]);
            out.writeUTF(respuestaHoroscopo[0]);

        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        this.desconectar();
    }

    private void desconectar() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorCentralHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}