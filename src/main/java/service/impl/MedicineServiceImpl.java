package service.impl;

import dao.MedicineDAO;
import model.Medicine;
import service.MedicineService;

import java.rmi.RemoteException;

public class MedicineServiceImpl extends GenericServiceImpl<Medicine, String> implements MedicineService {
    private MedicineDAO medicineDAO;

    public MedicineServiceImpl(MedicineDAO medicineDAO) throws RemoteException {
        super(medicineDAO);
        this.medicineDAO = medicineDAO;
    }
}
