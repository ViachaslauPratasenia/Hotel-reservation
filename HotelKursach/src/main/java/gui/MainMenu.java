package gui;

import tables.ViewDatabase;
import tables.ViewRoomDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by user on 20.11.2017.
 */
public class MainMenu extends JFrame {
    private JButton viewRoomsButton, adminButton, reservationButton, cancelReservationButton, exitButton;
    private Font font = new Font(Font.SANS_SERIF, Font.ITALIC, 20);
    public MainMenu(String s, boolean admin){
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
        gridBagConstraints.insets = new Insets(5,0,0,0);
        gridBagConstraints.ipadx = 0;
        gridBagConstraints.ipady = 0;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.0;

        adminButton = new JButton("Просмотр жильцов");
        adminButton.setFont(font);
        adminButton.setPreferredSize(new Dimension(450,50));
        viewRoomsButton = new JButton("Просмотр комнат");
        viewRoomsButton.setFont(font);
        viewRoomsButton.setPreferredSize(new Dimension(450,50));
        reservationButton = new JButton("Забронировать комнату");
        reservationButton.setFont(font);
        reservationButton.setPreferredSize(new Dimension(450,50));
        cancelReservationButton = new JButton("Отмена брони");
        cancelReservationButton.setFont(font);
        cancelReservationButton.setPreferredSize(new Dimension(450,50));
        exitButton = new JButton("Выход из программы");
        exitButton.setFont(font);
        exitButton.setPreferredSize(new Dimension(450,50));

        viewRoomsButton.addActionListener(new ViewRoomsButtonActionListener());
        exitButton.addActionListener(new ExitButtonListener());
        reservationButton.addActionListener(new ReservationButtonListener());
        cancelReservationButton.addActionListener(new CancelReservationButtonListener());
        adminButton.addActionListener(new AdminButtonActionListener());

        gridBagLayout.setConstraints(adminButton, gridBagConstraints);
        gridBagLayout.setConstraints(viewRoomsButton, gridBagConstraints);
        gridBagLayout.setConstraints(reservationButton, gridBagConstraints);
        gridBagLayout.setConstraints(cancelReservationButton, gridBagConstraints);
        gridBagLayout.setConstraints(exitButton, gridBagConstraints);

        if(admin) add(adminButton);
        add(viewRoomsButton);
        add(reservationButton);
        add(cancelReservationButton);
        add(exitButton);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null);
    }
    public class ViewRoomsButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new ViewRoomDatabase("Комнаты отеля");
        }
    }

    public class ExitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }

    public class ReservationButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new Reservation("Бронирование номера");
        }
    }

    public class CancelReservationButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new CancelReservation("Отмена бронирования");
        }
    }

    public class AdminButtonActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            new ViewDatabase("База данных проживающих");
        }
    }
}
