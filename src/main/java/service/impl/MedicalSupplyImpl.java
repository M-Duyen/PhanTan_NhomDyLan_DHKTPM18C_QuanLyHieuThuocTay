package service.impl;

import dao.GenericDAO;
import dao.MedicalSuppliesDAO;
import model.MedicalSupply;
import service.MedicalSuppliesService;

import java.rmi.RemoteException;

public class MedicalSupplyImpl extends GenericServiceImpl<MedicalSupply, String> implements MedicalSuppliesService {
    private MedicalSuppliesDAO medicalSuppliesDAO;

    public MedicalSupplyImpl(MedicalSuppliesDAO medicalSuppliesDAO) throws RemoteException {
        super(medicalSuppliesDAO);
        this.medicalSuppliesDAO = medicalSuppliesDAO;
    }
}
