package service.impl;

import service.ServerService;
import utils.UtilStatics;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerServiceImpl extends UnicastRemoteObject implements ServerService {
    public static UtilStatics utilStatics;

    public ServerServiceImpl() throws RemoteException {
    };

    public void setAwaiKey() throws RemoteException{
        try {
            utilStatics.setAwaiKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getAwaiKey() throws RemoteException{
        try {
            return utilStatics.getAwaiKey();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
