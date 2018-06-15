package tables;

import database.DatabaseConnection;
import exceptions.MySQLException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by user on 26.11.2017.
 */
public class RoomTableModel extends AbstractTableModel {

    private int columnCount = 3;
    private ArrayList<String[]> dataArrayList;

    public RoomTableModel(){
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
            case 0: return "number";
            case 1: return "employment";
            case 2: return "identify";
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
        ResultSet resultSet = connectionDB.showInfo("SELECT * FROM rooms");
        dataArrayList.clear();
        try {
            while (resultSet.next()){
                String number = resultSet.getString("number");
                String employment = resultSet.getString("employment");
                if(employment.equals("1")) employment = "Yes";
                else employment = "No";
                String identify = resultSet.getString("identify");

                String []row = {number, employment, identify};

                addDate(row);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
