package rmi.servidor_horoscopo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioHoroscopo extends Remote {
    public String predecirHoroscopo(String signo) throws RemoteException;
}