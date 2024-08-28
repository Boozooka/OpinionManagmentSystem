package interfaceConnect;

import dataConnect.Connector;
import dataConnect.User;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private SpringLayout layout;
    private Connector base;

    private JTextField loginField;
    private JTextField passwordField;

    private JLabel correctLabel;
    private JButton returnButton;

    private Frame parent;

    public RegisterFrame(Connector base, Frame parent) {
        super("Register");

        this.base = base;

        this.parent = parent;

        this.setSize(500, 200);

        layout = new SpringLayout();

        setLayout(layout);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createLoginTextField();

        createPasswordTextField();

        createAcceptButton();

        createCorrectLabel();

        createReturnButton();

        setVisible(true);
    }

    private void createLoginTextField() {
        JLabel loginLabel = new JLabel("Логин");

        add(loginLabel);

        setCoordinateToComponent(loginLabel, 20, 20);

        loginField = new JTextField(30);

        add(loginField);

        setCoordinateToComponent(loginField, 75, 20);
        setSizeToComponent(loginField, 380, 25);

    }

    private void createPasswordTextField() {
        JLabel passwordLabel = new JLabel("Пароль");

        setCoordinateToComponent(passwordLabel, 20, 60);

        add(passwordLabel);

        passwordField = new JTextField(30);

        add(passwordField);

        setCoordinateToComponent(passwordField, 75, 60);

        setSizeToComponent(passwordField, 380, 25);
    }

    private void createAcceptButton (){
        Icon icon = new ImageIcon("src/main/resources/images/acceptButton.png");

        JButton acceptButton = new JButton(icon);

        setSizeToComponent(acceptButton, 50, 50);
        setCoordinateToComponent(acceptButton, 345, 100);

        add(acceptButton);

        acceptButton.addActionListener(ae -> {
            String login = loginField.getText();
            String password = passwordField.getText();

            if (login.isEmpty() || password.isEmpty()){
                correctLabel.setText("Ни логин, ни пароль не могут быть пустыми");
                correctLabel.setForeground(new Color(255, 0, 0));
            }

            if(base.registerUser(login, password)){
                //System.out.println(user.getName() + " " + user.getId());

                correctLabel.setText("Добро пожаловать!");
                correctLabel.setForeground(new Color(25, 175, 25));
            } else {
                correctLabel.setText("Имя уже занято :(");
                correctLabel.setForeground(new Color(255, 0, 0));
            }
        });
    }

    private void createReturnButton(){
        Icon icon = new ImageIcon("src/main/resources/images/returnButton.png");

        returnButton = new JButton(icon);

        add(returnButton);

        setCoordinateToComponent(returnButton, 405, 100);
        setSizeToComponent(returnButton, 50, 50);

        returnButton.addActionListener(ae -> {
            loginField.setText("");
            passwordField.setText("");
            correctLabel.setText("");

            dispose();
            parent.setVisible(true);
        });
    }

    private void createCorrectLabel(){
        correctLabel = new JLabel();

        add(correctLabel);

        correctLabel.setFont(new Font("Arial", Font.BOLD, 14));

        setCoordinateToComponent(correctLabel, 30, 90);
        setSizeToComponent(correctLabel, 300, 50);
    }

    private void setCoordinateToComponent(Component component, int x, int y) {
        layout.putConstraint(SpringLayout.WEST, component, x, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, component, y, SpringLayout.NORTH, this);
    }

    private void setSizeToComponent(Component component, int x, int y) {
        layout.putConstraint(SpringLayout.EAST, component, x, SpringLayout.WEST, component);
        layout.putConstraint(SpringLayout.SOUTH, component, y, SpringLayout.NORTH, component);
    }
}
