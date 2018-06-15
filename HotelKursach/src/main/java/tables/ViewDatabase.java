package tables;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 21.11.2017.
 */
public class ViewDatabase  extends JFrame{
    public ViewDatabase(String s){
        super(s);

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        TablePanel tablePanel = new TablePanel();
        tablePanel.init();
        add(tablePanel, BorderLayout.CENTER);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
