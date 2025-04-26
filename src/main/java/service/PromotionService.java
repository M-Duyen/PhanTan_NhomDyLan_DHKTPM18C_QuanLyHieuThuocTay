package service;

import model.Promotion;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface PromotionService extends GenericService<Promotion, String> {
    ArrayList<Promotion> getPromotionListByStatus(boolean status) throws RemoteException;
    String createPromotionID(String startDate, String endDate) throws RemoteException;
    ArrayList<Promotion> getPromotionListByCriterous(boolean criterious, ArrayList<Promotion> proList)throws RemoteException;
    boolean updatePromotionStatus() throws RemoteException;
}
