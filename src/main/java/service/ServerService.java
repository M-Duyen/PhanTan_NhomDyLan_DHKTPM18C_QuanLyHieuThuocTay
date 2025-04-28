package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerService  extends Remote{
    void setAwaiKey(boolean status) throws RemoteException;
    boolean getAwaiKey() throws RemoteException;
}
