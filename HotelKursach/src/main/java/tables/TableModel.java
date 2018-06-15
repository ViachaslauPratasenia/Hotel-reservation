package tables;

import database.DatabaseConnection;
import exceptions.MySQLException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by user on 27.11.2017.
 */
public class TableModel extends AbstractTableModel {

    private int columnCount = 4;
    private ArrayList<String[]> dataArrayList;

    public TableModel(){
        dataArrayList = new ArrayList<String[]>();
        for(int i = 0; i < dataArrayList.size(); i++){
            dataArrayList.add(new String[getColumnCount()]);
        }
    }

    public int getRowCount() {
        return dataArrayList.size();
    }

    public int getColumnCount() {
        return columnCount;
    }

    public String getColumnName(int columnIndex){
        switch (columnIndex){
            case 0: return "identify";
            case 1: return "name";
            case 2: return "surname";
            case 3: return "room";
        }
        return "";
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        String[]rows = dataArrayList.get(rowIndex);
        return rows[columnIndex];
    }

    public void addDate(String[] row){
        String[] rowTable = new String[getColumnCount()];
        rowTable = row;
        dataArrayList.add(rowTable);
    }

    public void addDate(){
        DatabaseConnection connectionDB = new DatabaseConnection();
        try {
            connectionDB.connection();
        } catch (MySQLException e) {
            JOptionPane.showMessageDialog(null, "SQL error");
            return;
        }
        ResultSet resultSet = connectionDB.showInfo("SELECT * FROM users");
        dataArrayList.clear();
        try {
            while (resultSet.next()){
                String id = resultSet.getString("identify");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String room = resultSet.getString("room");

                String []row = {id, name, surname, room};

                addDate(row);
            }
            resultSet.close();
            connectionDB.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
