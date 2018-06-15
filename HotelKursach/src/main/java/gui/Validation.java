package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 * Created by user on 20.11.2017.
 */
public class Validation extends JFrame {
    private Font font = new Font(Font.SANS_SERIF,Font.ITALIC,20);
    private JButton adminButton, guestButton;

    public Validation(String s){
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

        adminButton = new JButton("Войти как администратор");
        adminButton.setFont(font);
        adminButton.setPreferredSize(new Dimension(450,50));
        guestButton = new JButton("Войти как гость");
        guestButton.setFont(font);
        guestButton.setPreferredSize(new Dimension(450,50));

        adminButton.addActionListener(new AdminButtonActionListener());
        guestButton.addActionListener(new GuestButtonActionListener());

        gridBagLayout.setConstraints(adminButton, gridBagConstraints);
        gridBagLayout.setConstraints(guestButton, gridBagConstraints);

        add(adminButton);
        add(guestButton);


        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public class AdminButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            setVisible(false);
            new AdminValidation("Ввод пароля");

        }
    }

    public class GuestButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            setVisible(false);
            new MainMenu("Главное меню", false);
        }
    }

    public class AdminValidation extends JFrame {
        private JPasswordField passwordField;
        private JLabel labelPassword;
        private JButton buttonEnter;
        public AdminValidation(String s){
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

            labelPassword = new JLabel("Введите пароль");
            labelPassword.setFont(font);
            labelPassword.setPreferredSize(new Dimension(175,50));

            buttonEnter = new JButton("Войти");
            buttonEnter.setFont(font);
            buttonEnter.setPreferredSize(new Dimension(250,50));

            passwordField = new JPasswordField(10);
            passwordField.setPreferredSize(new Dimension(100,50));
            passwordField.setFont(font);

            buttonEnter.addActionListener(new EnterButtonListener());
            passwordField.addKeyListener(new MyKeyListener());

            add(labelPassword, gridBagConstraints);
            add(passwordField, gridBagConstraints);
            add(buttonEnter, gridBagConstraints);

            getContentPane().add(buttonEnter, gridBagConstraints);

            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(500,500);
            setResizable(false);
            setLocationRelativeTo(null);
        }



        public class EnterButtonListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                char[] password = passwordField.getPassword();
                if(isPasswordCorrect(password)){
                    setVisible(false);
                    new MainMenu("Главное меню", true);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Неверный пароль");
                    passwordField.setText(null);
                }
            }

            public boolean isPasswordCorrect(char[] password){
                boolean isCorrect = true;
                char[] origPassword = {'r', 'o', 'o', 't'};
                if(password.length != origPassword.length) return false;
                else{
                    isCorrect = Arrays.equals(password, origPassword);
                }
                Arrays.fill(origPassword, '0');
                return isCorrect;
            }

        }

        public class MyKeyListener extends KeyAdapter{
            @Override
            public void keyReleased(KeyEvent e) {
                EnterButtonListener enterButtonListener = new EnterButtonListener();
                char[] password = passwordField.getPassword();
                if(enterButtonListener.isPasswordCorrect(password) && e.getKeyCode() == KeyEvent.VK_ENTER){
                    setVisible(false);
                    new MainMenu("Главное меню", true);
                }
            }
        }


    }
}
