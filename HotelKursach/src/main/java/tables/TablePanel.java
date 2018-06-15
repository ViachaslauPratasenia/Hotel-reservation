package tables;

import exceptions.MySQLException;
import gui.CancelReservation;
import database.DatabaseConnection;
import gui.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by user on 27.11.2017.
 */
public class TablePanel extends JPanel implements Runnable{
    private TableModel model = new TableModel();
    private JTable bookTable = new JTable(model);

    private JButton addButton = new JButton("Добавить");
    private JButton deleteButton = new JButton("Удалить");

    public TablePanel(){
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

        JScrollPane bookTableScrollPane = new JScrollPane(bookTable);
        bookTableScrollPane.setPreferredSize(new Dimension(400,400));

        addButton.addActionListener(new AddButtonActionListener());
        deleteButton.addActionListener(new DeleteButtonActionListener());

        add(bookTableScrollPane, new GridBagConstraints(0,0,4,1,1,1,GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, new Insets(1,1,1,1), 0,0));
        add(addButton, new GridBagConstraints(1,1,1,1,1,1,GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL, new Insets(1,1,1,1), 0,0));
        add(deleteButton, new GridBagConstraints(2,1,1,1,1,1,GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL, new Insets(1,1,1,1), 0,0));

    }

    @Override
    public void run() {
        while (true){
            try {
                model.addDate();
                repaint();
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class AddButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Reservation("Добавление жильца");
        }
    }

    public class DeleteButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new CancelReservation("Удаление из базы данных");
        }
    }
}
