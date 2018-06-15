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
 * Created by user on 20.11.2017.
 */
public class Reservation extends JFrame {
    private Font font = new Font(Font.SANS_SERIF,Font.ITALIC,20);
    private JLabel name, surname, identifyNumber, room;
    private JTextField tfName, tfSurname, tfIdentifyNumber, tfRoom;
    private JButton res, clear;
    private String strName, strSurname;
    private int intRoom, intIdentify;
    public Reservation(String s){
        super(s);

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraints.insets = new Insets(10,0,0,0);
        gridBagConstraints.ipadx = 0;
        gridBagConstraints.ipady = 0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;

        name = new JLabel("Имя : ");
        name.setFont(font);
        name.setHorizontalAlignment(SwingConstants.CENTER);
        surname = new JLabel("Фамилия : ");
        surname.setFont(font);
        surname.setHorizontalAlignment(SwingConstants.CENTER);
        identifyNumber = new JLabel("Номер паспорта : ");
        identifyNumber.setFont(font);
        identifyNumber.setHorizontalAlignment(SwingConstants.CENTER);
        room = new JLabel("Номер комнаты : ");
        room.setFont(font);
        room.setHorizontalAlignment(SwingConstants.CENTER);

        tfName = new JTextField(20);
        tfName.setFont(font);
        tfName.setPreferredSize(new Dimension(250,25));
        tfSurname = new JTextField(20);
        tfSurname.setFont(font);
        tfSurname.setPreferredSize(new Dimension(250,25));
        tfIdentifyNumber = new JTextField(20);
        tfIdentifyNumber.setFont(font);
        tfIdentifyNumber.setPreferredSize(new Dimension(250,25));
        tfRoom = new JTextField(20);
        tfRoom.setFont(font);
        tfRoom.setPreferredSize(new Dimension(250,25));

        res = new JButton("Забронировать");
        res.setFont(font);
        clear = new JButton("Очистить");
        clear.setFont(font);

        res.addActionListener(new ResButtonActionListener());
        clear.addActionListener(new ClearButtonActionListener());

        add(name, gridBagConstraints);
        add(tfName, gridBagConstraints);
        add(surname, gridBagConstraints);
        add(tfSurname, gridBagConstraints);
        add(identifyNumber, gridBagConstraints);
        add(tfIdentifyNumber, gridBagConstraints);
        add(room, gridBagConstraints);
        add(tfRoom, gridBagConstraints);
        add(res, gridBagConstraints);
        add(clear, gridBagConstraints);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public class ResButtonActionListener implements ActionListener {
        private boolean checkEmployment, checkRoom, checkIdentify;
        @Override
        public void actionPerformed(ActionEvent e) {
            strName = tfName.getText();
            strSurname = tfSurname.getText();
            try {
                intIdentify = Integer.parseInt(tfIdentifyNumber.getText());
                intRoom = Integer.parseInt(tfRoom.getText());
                String queryUsers = "INSERT INTO users(identify, name, surname, room)values(" + intIdentify + ",'" + strName + "','" +
                        strSurname  + "'," + intRoom + ")";
                String updateIdentify = "UPDATE rooms SET identify = " + intIdentify + " WHERE number = " + intRoom + "";
                String queryUpdate = "UPDATE rooms SET employment = 1 WHERE number =" + intRoom + "";
                String checking = "SELECT * FROM rooms WHERE number = " + intRoom + "";
                DatabaseConnection databaseConnection = new DatabaseConnection();
                try {
                    databaseConnection.connection();
                    checkEmployment = isRoomExist(checking);
                    checkRoom = isRoomExist(intRoom);
                    checkIdentify = isNumberPositive(intIdentify);
                } catch (MySQLException e1) {
                    JOptionPane.showMessageDialog(null, "SQL error");
                    return;
                }
                if (checkEmployment && checkRoom && checkIdentify) {
                    databaseConnection.addInfo(queryUsers);
                    databaseConnection.addInfo(updateIdentify);
                    databaseConnection.updateInfo(queryUpdate);
                    JOptionPane.showMessageDialog(null, "Комната забронирована");
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Данная комната уже занята или отсутствует");
                    tfRoom.setText(null);
                }
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(null, "Некорректный ввод данных");
                tfName.setText(null);
                tfSurname.setText(null);
                tfIdentifyNumber.setText(null);
                tfRoom.setText(null);
            }
        }
    }
    public class ClearButtonActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            tfName.setText(null);
            tfSurname.setText(null);
            tfIdentifyNumber.setText(null);
            tfRoom.setText(null);
        }
    }

    public boolean isRoomExist(int room) throws MySQLException{
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.connection();
            ResultSet resultSet = databaseConnection.getStatement().executeQuery("SELECT * FROM rooms");
            while(resultSet.next()){
                int databaseRoom = Integer.parseInt(resultSet.getString("number"));
                if(room == databaseRoom){
                    return true;
                }
            }
        }catch (MySQLException ex){
            throw new MySQLException("SQL exception");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isRoomExist(String query) throws MySQLException {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            databaseConnection.connection();
            ResultSet resultSet = databaseConnection.getStatement().executeQuery(query);
            while (resultSet.next()) {
                String check = resultSet.getString("employment");
                if (check.equals("1")) {
                    return false;
                }
            }
            resultSet.close();
        } catch (MySQLException ex) {
            throw new MySQLException("SQL exception");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isNumberPositive(int num){
        if(num <= 0) return false;
        else return true;
    }
}
