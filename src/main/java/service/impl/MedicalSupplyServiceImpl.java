package service.impl;

import dao.MedicalSuppliesDAO;
import model.MedicalSupply;
import service.MedicalSuppliesService;

import java.rmi.RemoteException;

public class MedicalSupplyServiceImpl extends GenericServiceImpl<MedicalSupply, String> implements MedicalSuppliesService {
    private MedicalSuppliesDAO medicalSuppliesDAO;

    public MedicalSupplyServiceImpl(MedicalSuppliesDAO medicalSuppliesDAO) throws RemoteException {
        super(medicalSuppliesDAO);
        this.medicalSuppliesDAO = medicalSuppliesDAO;
    }
}
