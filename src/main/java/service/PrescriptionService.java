package service;

import model.Prescription;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface PrescriptionService extends GenericService<Prescription, String> {
    LocalDateTime convertStringToLacalDateTime(String date) throws RemoteException;

}
