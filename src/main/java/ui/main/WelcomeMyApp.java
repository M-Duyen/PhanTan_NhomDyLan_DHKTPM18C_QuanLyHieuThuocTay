package ui.main;


import dao.Employee_DAO;
import entity.Employee;
import entity.StaticProcess;

import javax.swing.*;

import static entity.StaticProcess.*;

public class WelcomeMyApp {
    public static boolean doneLoading = false;
    public static void main(String[] args) {
        Loading load = new Loading();
        login = new Login_GUI();
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                Loading.main(load);
                while (!doneLoading) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {

                        Login_GUI.main(login);
                        while (!loginSuccess) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        if (loginSuccess) {
                            HomePage.setRole(userlogin);
                            HomePage.main(null);
                        }
                    }
                };
                worker.execute();

            }
        };
        worker.execute();

    }

    public static void hideLogin(){
        login.setVisible(false);
    }

    public static Employee getEmployeeLogin(){
        return Employee_DAO.getInstance().getEmployee_ByID(userlogin);
    }

}