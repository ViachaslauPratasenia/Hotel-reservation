package gui;

import database.DatabaseConnection;
import exceptions.MySQLException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by user on 21.11.2017.
 */
public class CancelReservation extends JFrame {
    private Font font = new Font(Font.SANS_SERIF,Font.ITALIC,20);
    private JButton buttonCancel;
    private JLabel labelData;
    private JTextField textData;
    private int identify = 0;
    public CancelReservation(String s){
        super(s);

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraints.insets = new Insets(15,0,0,0);
        gridBagConstraints.ipadx = 0;
        gridBagConstraints.ipady = 0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;

        buttonCancel = new JButton("Отменить бронирование");
        buttonCancel.setFont(font);
        buttonCancel.setPreferredSize(new Dimension(300,50));

        labelData = new JLabel("Введите Ваш номер паспорта");
        labelData.setFont(font);
        labelData.setPreferredSize(new Dimension(300,50));

        textData = new JTextField(10);
        textData.setFont(font);
        textData.setPreferredSize(new Dimension(100,50));

        buttonCancel.addActionListener(new CancelButtonListener());

        add(labelData, gridBagConstraints);
        add(textData, gridBagConstraints);
        add(buttonCancel, gridBagConstraints);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public class CancelButtonListener implements ActionListener{
        private boolean checkIdentify;
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                identify = Integer.parseInt(textData.getText());
                String query = "DELETE FROM users WHERE identify = " + identify + "";
                String queryUpdateEmployment = "UPDATE rooms SET " + "employment = 0 WHERE identify =" + identify + "";
                String queryUpdateIdentify = "UPDATE rooms SET " + "identify = 0 WHERE identify =" + identify + "";
                DatabaseConnection databaseConnection = new DatabaseConnection();
                try {
                    databaseConnection.connection();
                    checkIdentify = isIdentifyExist(identify);
                } catch (MySQLException e1) {
                    JOptionPane.showMessageDialog(null, "SQL error");
                    return;
                }
                databaseConnection.addInfo(query);
                databaseConnection.updateInfo(queryUpdateEmployment);
                databaseConnection.updateInfo(queryUpdateIdentify);
                JOptionPane.showMessageDialog(null, "Бронирование отменено");
                setVisible(false);
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(null, "Некорректный ввод данных");
                textData.setText(null);
            }
        }
    }

    public boolean isIdentifyExist(int identify) throws MySQLException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.connection();
            ResultSet resultSet = databaseConnection.getStatement().executeQuery("SELECT * FROM rooms");
            while (resultSet.next()) {
                int databaseIdentify = Integer.parseInt(resultSet.getString("identify"));
                if (identify == databaseIdentify) {
                    return true;
                }
            }
        } catch (MySQLException ex){
            throw new MySQLException("SQL exception");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}