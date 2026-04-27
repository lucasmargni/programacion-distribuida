package rmi.servidor_central;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioPrediccion extends Remote {
    public String realizar_consulta(String consulta) throws RemoteException;
}