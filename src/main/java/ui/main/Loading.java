package ui.main;

import javax.swing.JOptionPane;

public class Loading extends javax.swing.JFrame {

    public Loading() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PageLoading = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        barLoading = new javax.swing.JProgressBar();
        loadText = new javax.swing.JLabel();
        loadPercent = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        PageLoading.setBackground(new java.awt.Color(255, 255, 255));
        PageLoading.setPreferredSize(new java.awt.Dimension(900, 500));

        jLabel1.setIcon(new javax.swing.ImageIcon("src/main/java/ui/menu/background.png")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 1, 40)); // NOI18N
        jLabel2.setText("HỆ THỐNG QUẢN LÝ NHÀ THUỐC DNGH");

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setIcon(new javax.swing.ImageIcon("src/main/java/ui/menu/line.png")); // NOI18N
        jLabel3.setText("jLabel3");

        barLoading.setPreferredSize(new java.awt.Dimension(146, 4));

        loadText.setText("Loading...");

        loadPercent.setText("0%");

        javax.swing.GroupLayout PageLoadingLayout = new javax.swing.GroupLayout(PageLoading);
        PageLoading.setLayout(PageLoadingLayout);
        PageLoadingLayout.setHorizontalGroup(
                PageLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PageLoadingLayout.createSequentialGroup()
                                .addGroup(PageLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(PageLoadingLayout.createSequentialGroup()
                                                .addGap(52, 52, 52)
                                                .addComponent(jLabel2))
                                        .addGroup(PageLoadingLayout.createSequentialGroup()
                                                .addGap(171, 171, 171)
                                                .addGroup(PageLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(PageLoadingLayout.createSequentialGroup()
                                                                .addComponent(loadText)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(loadPercent))
                                                        .addComponent(barLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(71, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PageLoadingLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(PageLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PageLoadingLayout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(259, 259, 259))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PageLoadingLayout.createSequentialGroup()
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(209, 209, 209))))
        );
        PageLoadingLayout.setVerticalGroup(
                PageLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(PageLoadingLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addGroup(PageLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(loadText)
                                        .addComponent(loadPercent))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(barLoading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(PageLoading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(PageLoading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void runLoading() {
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(40);
                    barLoading.setValue(i);
                    loadPercent.setText(i + "%");

                    if (i == 10) loadText.setText("Turning Modules...");
                    if (i == 20) loadText.setText("Loading Modules...");
                    if (i == 50) loadText.setText("Connecting to Database...");
                    if (i == 70) loadText.setText("Connection Successful !");
                    if (i == 80) loadText.setText("Launching Application...");

                    if (i == 100) {
                        new Loading().setVisible(false);
                        dispose();
                        WelcomeMyApp.doneLoading = true;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public static void main(Loading loading) {
        java.awt.EventQueue.invokeLater(() -> {
            Loading load = new Loading();
            load.setVisible(true);
            load.runLoading();
        });
    }

    private javax.swing.JPanel PageLoading;
    private javax.swing.JProgressBar barLoading;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel loadPercent;
    private javax.swing.JLabel loadText;
}
