package service.impl;

import service.ServerService;
import utils.UtilStatics;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerServiceImpl extends UnicastRemoteObject implements ServerService {
    public static UtilStatics utilStatics;

    public ServerServiceImpl() throws RemoteException {
    };

    public synchronized void  setAwaiKey(boolean status) throws RemoteException{
        try {
            UtilStatics.setAwaiKey(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean getAwaiKey() throws RemoteException{
        try {
            return UtilStatics.getAwaiKey();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
