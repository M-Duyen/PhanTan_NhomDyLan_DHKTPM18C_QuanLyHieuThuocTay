package service.impl;

import dao.PrescriptionDAO;
import entity.Prescription;
import service.PrescriptionService;

import java.rmi.RemoteException;

public class PrescriptionServiceImpl extends GenericServiceImpl<Prescription, String> implements PrescriptionService {
    PrescriptionDAO prescriptionDAO;
    public PrescriptionServiceImpl(PrescriptionDAO prescriptionDAO) throws RemoteException {
        super(prescriptionDAO);
        this.prescriptionDAO = prescriptionDAO;
    }

}
