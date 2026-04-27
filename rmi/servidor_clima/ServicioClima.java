package rmi.servidor_clima;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioClima extends Remote {
    public String predecirClima(String fecha) throws RemoteException;
}