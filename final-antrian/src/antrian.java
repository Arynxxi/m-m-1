import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;

public class antrian {
    private JTextField tfJumlahPelanggan;
    private JTextField tfZO;
    private JTextField tfA;
    private JTextField tfM;
    private JTextField tfC;
    private JButton btnProses;
    private JButton btnReset;
    private JTable tblAntrian;
    private JPanel panelAntrian;
    private JScrollPane tblDisplay;
    private JLabel averageQueueingTimeLabel;
    private JLabel averageSistemProcessTimeLabel;
    private JLabel averageQueueLengthLabel;
    private JLabel averageNumberInTheLabel;
    private JLabel servicePointIdleTimeLabel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("penjualan");
        frame.setContentPane(new antrian().panelAntrian);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.pack();
        frame.setVisible(true);

    }
    private JTable resultTable;
    private DefaultTableModel tableModel;
    public antrian() {

        btnProses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    int jmlPelanggan = Integer.parseInt(tfJumlahPelanggan.getText());
                    int z0 = Integer.parseInt(tfZO.getText());
                    double a = Double.parseDouble(tfA.getText());
                    double m = Double.parseDouble(tfM.getText());
                    double c = Double.parseDouble(tfC.getText());

                    double currentTime = 0;
                    int no = 1;
                    double prevArrivalTime = 0;
                    double prevFinishTime;
                    double totalQueueingTime = 0;
                    double totalSystemProcessTime = 0;
                    double totalQueueLength = 0;
                    double totalIdleTime = 0;
                    double totalServiceTime = 0;

                    while (no <= jmlPelanggan) {

                        z0 = (int) ((a * z0 + c) % m);
                        double r = (double) (z0 / m);
                        String fomattedR = String.format("%.3f", r);
                        double iat = -60 * Math.log(r);
                        long roundIat = Math.round(iat);
                        int arrivalTime = (int) (prevArrivalTime + roundIat);
                        double serviceTime = -40 * Math.log(r);
                        long roundServiceTime = Math.round(serviceTime);
                        int startTime = (int) Math.max(arrivalTime, currentTime);
                        int fromTime = (int) (startTime + serviceTime);

                            if (arrivalTime > fromTime) {

                                startTime = arrivalTime;

                                if (arrivalTime < fromTime){

                                    startTime = fromTime;

                                }

                            } else {


                            }

                            int waitingTime = startTime - arrivalTime;
                            int spIdleTime = (int) (startTime - currentTime);
                            int systemProcessTime = (int) Math.ceil(serviceTime + waitingTime);

                            Object[] rowData = {no, z0, fomattedR, roundIat, arrivalTime, roundServiceTime, startTime, fromTime, waitingTime, spIdleTime, systemProcessTime};
                            tableModel.addRow(rowData);

                            no++;
                            prevArrivalTime = arrivalTime;
                            prevFinishTime = arrivalTime + systemProcessTime;
                            currentTime = Math.max(prevFinishTime, iat);

                            totalQueueingTime += waitingTime;
                            totalSystemProcessTime += systemProcessTime;
                            totalQueueLength = totalQueueingTime;
                            totalIdleTime += (startTime - currentTime);
                            totalServiceTime = startTime + serviceTime;
                            totalIdleTime += spIdleTime;

                            int averageWaitingTime = (int) (totalQueueingTime/ jmlPelanggan);

                            averageQueueingTimeLabel.setText("Average Queueing Time\t\t: " + averageWaitingTime + " detik");

                            int averageSystemProcessTime = (int) (totalSystemProcessTime/ jmlPelanggan);
                            DecimalFormat decimalFormat2 = new DecimalFormat("#.##");
                            String formattedAverage2 = decimalFormat2.format(averageSystemProcessTime);

                            averageSistemProcessTimeLabel.setText("Average Sistem Process Time\t: " + formattedAverage2 + " detik");

                            double averageQueueLength = totalQueueLength/ fromTime;
                            DecimalFormat decimalFormat3 = new DecimalFormat("#.##");
                            String formattedAverage3 = decimalFormat3.format(averageQueueLength);

                            averageQueueLengthLabel.setText("Average Queue Length\t\t: " +formattedAverage3);

                            double averageNumberInSystem = totalSystemProcessTime/ fromTime;
                            DecimalFormat decimalFormat4 = new DecimalFormat("#.##");
                            String formattedAverage4 = decimalFormat4.format(averageNumberInSystem);

                            averageNumberInTheLabel.setText("Average Sistem Process Time\t: " +formattedAverage4);

                            int idleTimeRatio = (int) (totalIdleTime / currentTime);

                            servicePointIdleTimeLabel.setText("Service Point Idle Time\t: " + idleTimeRatio + "%");

                    }


                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(tblAntrian, "Invalid input. Please enter valid numbers.");

                }

            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tfJumlahPelanggan.setText(null);
                tfZO.setText(null);
                tfA.setText(null);
                tfM.setText(null);
                tfC.setText(null);
                tfJumlahPelanggan.requestFocus();

                tableModel.setRowCount(0);

            }
        });

    }

    private void createUIComponents() {
        Object[] columnNames = {"No", "Z", "R", "IAT", "Arrival Time", "Service Time", "Intro Time", "From Time", "Queueing Time", "SP Idle Time", "System Process Time"};
        Object[][] data = {};

        tableModel = new DefaultTableModel(data, columnNames);

        tblAntrian = new JTable(tableModel);

        tblAntrian.getColumnModel().getColumn(0).setPreferredWidth(70);
        tblAntrian.getColumnModel().getColumn(1).setPreferredWidth(70);
        tblAntrian.getColumnModel().getColumn(2).setPreferredWidth(70);
        tblAntrian.getColumnModel().getColumn(3).setPreferredWidth(70);
        tblAntrian.getColumnModel().getColumn(4).setPreferredWidth(200);
        tblAntrian.getColumnModel().getColumn(5).setPreferredWidth(200);
        tblAntrian.getColumnModel().getColumn(6).setPreferredWidth(200);
        tblAntrian.getColumnModel().getColumn(7).setPreferredWidth(200);
        tblAntrian.getColumnModel().getColumn(8).setPreferredWidth(200);
        tblAntrian.getColumnModel().getColumn(9).setPreferredWidth(200);
        tblAntrian.getColumnModel().getColumn(10).setPreferredWidth(200);
    }
}
