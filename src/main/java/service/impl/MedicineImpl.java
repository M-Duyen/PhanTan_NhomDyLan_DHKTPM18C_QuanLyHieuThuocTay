package service.impl;

import dao.GenericDAO;
import dao.MedicineDAO;
import model.Medicine;
import service.MedicineService;

import java.rmi.RemoteException;

public class MedicineImpl extends GenericServiceImpl<Medicine, String> implements MedicineService {
    private MedicineDAO medicineDAO;

    public MedicineImpl(MedicineDAO medicineDAO) throws RemoteException {
        super(medicineDAO);
        this.medicineDAO = medicineDAO;
    }
}
