package sockets.cliente;
import java.io.*;
import java.net.Socket;
import java.util.logging.*;
import java.util.Properties;

public class Cliente {
    public static void main(String[] args) {
        Socket socket;
        DataOutputStream out;
        DataInputStream in;
        BufferedReader stdIn;

        Properties prop = new Properties();

        try {
            FileInputStream fis = new FileInputStream("sockets/cliente/config.properties");
            prop.load(fis);

            // Obtenemos las variables
            int PUERTO_CENTRAL = Integer.parseInt(prop.getProperty("PUERTO_CENTRAL"));
            String IP_CENTRAL = prop.getProperty("IP_CENTRAL");

            socket = new Socket(IP_CENTRAL, PUERTO_CENTRAL);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Ingrese la fecha para realizar predicción del clima: ");
            String mensajeClima = stdIn.readLine();
            System.out.println(mensajeClima);
            out.writeUTF(mensajeClima);

            System.out.print("Ingrese el signo para realizar predicción del horóscopo: ");
            String mensajeHoroscopo = stdIn.readLine();
            System.out.println(mensajeHoroscopo);
            out.writeUTF(mensajeHoroscopo);

            String respuestaClima = in.readUTF();
            String respuestaHoroscopo = in.readUTF();

            System.out.println("El servidor envia: \n" + respuestaClima + "\n" + respuestaHoroscopo);

            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
}

