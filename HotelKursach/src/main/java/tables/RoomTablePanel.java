package tables;

import database.DatabaseConnection;
import exceptions.MySQLException;
import gui.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by user on 26.11.2017.
 */
public class RoomTablePanel extends JPanel implements Runnable {
    private RoomTableModel model = new RoomTableModel();
    private JTable roomTable = new JTable(model);

    private JButton addButton = new JButton("Забронировать");

    public RoomTablePanel(){
        DatabaseConnection connectionDB = new DatabaseConnection();
        try {
            connectionDB.connection();
        } catch (MySQLException e) {
            JOptionPane.showMessageDialog(null, "SQL error");
            return;
        }
        setLayout(new GridBagLayout());
        (new Thread(this)).start();
    }

    public void init(){

        JScrollPane roomTableScrollPane = new JScrollPane(roomTable);
        roomTableScrollPane.setPreferredSize(new Dimension(400,400));

        addButton.addActionListener(new AddButtonActionListener());

        add(roomTableScrollPane, new GridBagConstraints(0,0,4,1,1,1,GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, new Insets(1,1,1,1), 0,0));
        add(addButton, new GridBagConstraints(1,1,1,1,1,1,GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL, new Insets(1,1,1,1), 0,0));
    }

    @Override
    public void run() {
        while (true){
            try {
                model.addDate();
                repaint();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Runnable error(InterruptedException");
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Runnable error(Exception");
            }
        }
    }

    public class AddButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Reservation("Бронирование");
        }
    }
}
