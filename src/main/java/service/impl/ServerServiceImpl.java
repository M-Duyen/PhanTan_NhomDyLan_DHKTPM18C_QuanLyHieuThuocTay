package service.impl;

import service.ServerService;
import utils.UtilStatics;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerServiceImpl extends UnicastRemoteObject implements ServerService {

    public ServerServiceImpl() throws RemoteException {
    };

    public void setAwaiKey() throws RemoteException{
        try {
            UtilStatics.setAwaiKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getAwaiKey() throws RemoteException{
        try {
            return UtilStatics.getAwaiKey();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
