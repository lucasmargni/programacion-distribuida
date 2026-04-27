package rmi.servidor_central;

import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.logging.*;

public class ServicioPrediccionImp extends UnicastRemoteObject implements ServicioPrediccion {
    private int puerto_clima;
    private String ip_clima;
    private int puerto_horoscopo;
    private String ip_horoscopo;

    protected ServicioPrediccionImp() throws Exception {
        super();

        Properties prop = new Properties();

        FileInputStream fis = new FileInputStream("rmi/servidor_central/config.properties");
        prop.load(fis);

        // Obtenemos las variables
        this.puerto_clima = Integer.parseInt(prop.getProperty("PUERTO_CLIMA"));
        this.ip_clima = prop.getProperty("IP_CLIMA");

        this.puerto_horoscopo = Integer.parseInt(prop.getProperty("PUERTO_HOROSCOPO"));
        this.ip_horoscopo = prop.getProperty("IP_HOROSCOPO");
    }
    
    public String realizar_consulta(String consulta) throws RemoteException {
        String[] consultas_separadas = separarConsultas(consulta);
        String[] respuestaClima = new String[1];
        String[] respuestaHoroscopo = new String[1];

        try {
            // Solicita prediccion del clima
            SolicitudClima solC = new SolicitudClima(this.ip_clima, this.puerto_clima, consultas_separadas[0], respuestaClima);
            Thread hiloSolC = new Thread(solC);
            hiloSolC.start();

            // Solicita prediccion del horoscopo
            SolicitudHoroscopo solH = new SolicitudHoroscopo(this.ip_horoscopo, this.puerto_horoscopo, consultas_separadas[1], respuestaHoroscopo);
            Thread hiloSolH = new Thread(solH);
            hiloSolH.start();

            // Espera respuesta de las solicitudes
            hiloSolC.join();
            hiloSolH.join();

        } catch (Exception ex) {
            Logger.getLogger(ServidorCentral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return respuestaClima[0] + "~" + respuestaHoroscopo[0];
    }
    
    private String[] separarConsultas(String consultas){
        String[] consultasSeparadas = consultas.toLowerCase().split("~");
        return consultasSeparadas;
    }
}