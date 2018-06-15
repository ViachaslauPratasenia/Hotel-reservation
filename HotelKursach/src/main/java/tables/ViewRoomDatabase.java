package tables;

import javax.swing.*;
import java.awt.*;

/**
 * Created by user on 26.11.2017.
 */
public class ViewRoomDatabase extends JFrame {
    public ViewRoomDatabase(String s){
        super(s);

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        RoomTablePanel roomTablePanel = new RoomTablePanel();
        roomTablePanel.init();
        add(roomTablePanel, BorderLayout.CENTER);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
