package service.impl;

import dao.PrescriptionDAO;
import model.Prescription;
import service.PrescriptionService;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class PrescriptionServiceImpl extends GenericServiceImpl<Prescription, String> implements PrescriptionService {
    PrescriptionDAO prescriptionDAO;

    public PrescriptionServiceImpl(PrescriptionDAO prescriptionDAO) throws RemoteException {
        super(prescriptionDAO);
        this.prescriptionDAO = prescriptionDAO;
    }

    @Override
    public LocalDateTime convertStringToLacalDateTime(String date) throws RemoteException {
        return prescriptionDAO.convertStringToLacalDateTime(date);
    }
}
