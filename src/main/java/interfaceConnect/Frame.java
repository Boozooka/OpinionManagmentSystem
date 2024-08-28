package interfaceConnect;

import dataConnect.Connector;
import dataConnect.Opinion;
import dataConnect.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Frame extends JFrame {

    private JFrame instance;

    private SpringLayout layout;

    private User currentUser;

    private Connector base;

    private LoginFrame loginFrame;

    private RegisterFrame registerFrame;

    private JLabel userLabel;

    private JButton unLoginButton;

    private JButton registerButton;

    private JTextField opinionTextField;

    private JButton addButton;

    private JLabel correctLabel;

    private JLabel yourOpinionLabel;

    private JScrollPane userOpinionList;

    private JLabel yourOpinionsLabel;

    private JScrollPane strangerOpinionList;

    private JList<String> strangerOpinionContent;

    private JLabel strangeOpinionLabel;

    private JButton updateButton;

    public Frame(Connector base){
        super("Opinions");

        instance = this;

        this.base = base;

        layout = new SpringLayout();

        this.getContentPane().setLayout(layout);

        this.setSize(800, 500);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loginFrame = new LoginFrame(base, this);
        loginFrame.setVisible(false);

        registerFrame = new RegisterFrame(base, this);
        registerFrame.setVisible(false);

        String userName;
        if (currentUser == null){
            userName = "Невойдённый";
        } else {
            userName = currentUser.getName();
        }

        createUserLabel(userName);
        createLoginButton();
        createUnLoginButton();
        createRegisterButton();
        createOpinionTextField();
        createAddButton();
        createCorrectLabel();
        createYourOpinionLabel();
        createYourOpinionsLabel();
        createOpinionScrollPane();
        createStrangerOpinionList();
        createStrangerOpinionLabel();
        createUpdateButton();

        this.setVisible(true);
    }

    private void createUserLabel (String userName){
        userLabel = new JLabel(" Ты: " + userName);

        userLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        add(userLabel);

        setCoordinate(userLabel, 20, 20);
        setSize(userLabel, 230, 50);
    }

    private void createLoginButton (){
        Icon icon = new ImageIcon("src/main/resources/images/loginButton.png");

        JButton button = new JButton(icon);

        add(button);

        setCoordinate(button, 20, 90);
        setSize(button, 70, 70);

        button.addActionListener(ae -> {
            showLPFrame();
        });
    }

    private void createUnLoginButton(){
        Icon icon = new ImageIcon("src/main/resources/images/unLoginButton.png");

        unLoginButton = new JButton(icon);

        add(unLoginButton);

        setCoordinate(unLoginButton, 100, 90);
        setSize(unLoginButton, 70, 70);

        unLoginButton.addActionListener(ae -> {
            unLogin();
        });
    }

    private void createRegisterButton(){
        Icon icon = new ImageIcon("src/main/resources/images/registerButton.png");

        registerButton = new JButton(icon);

        add(registerButton);

        setSize(registerButton, 70, 70);
        setCoordinate(registerButton, 180, 90);

        registerButton.addActionListener(ae -> {
            showRegFrame();
        });
    }

    private void createOpinionTextField (){
        opinionTextField = new JTextField();

        setCoordinate(opinionTextField, 20, 200);
        setSize(opinionTextField, 190, 30);

        add(opinionTextField);
    }

    private void createAddButton (){
        Icon icon = new ImageIcon("src/main/resources/images/addButton.png");

        addButton = new JButton(icon);

        setCoordinate(addButton, 220, 200);
        setSize(addButton, 30, 30);

        add(addButton);

        addButton.addActionListener(ae -> {
            String opinion = opinionTextField.getText();

            if (currentUser == null) {
                correctLabel.setText("Для начала авторизируйтесь");
            } else if (opinion.isEmpty()){
                correctLabel.setText("Мнение не может быть пустым");
            } else {
                base.putOpinion(currentUser, opinion);
                correctLabel.setText("Мнение успешно выложено");
            }

            //TODO: синхронизовать с отчётной JLabel
        });
    }

    private void createCorrectLabel (){
        correctLabel = new JLabel();

        setCoordinate(correctLabel, 20, 250);
        setSize(correctLabel, 230, 40);

        correctLabel.setFont(new Font("Arial", Font.BOLD, 15));

        add(correctLabel);
    }

    private void createYourOpinionLabel (){
        yourOpinionLabel = new JLabel("Введи своё мнение: ");

        setCoordinate(yourOpinionLabel, 20, 180);
        setSize(yourOpinionLabel, 150, 20);

        add(yourOpinionLabel);
    }

    private void createOpinionScrollPane (){
        if (userOpinionList != null) {
            remove(userOpinionList);
        }

        userOpinionList = new JScrollPane();

        setCoordinate(userOpinionList, 280, 40);
        setSize(userOpinionList, 400, 180);

        add(userOpinionList);
    }

    private void createUserOpinionList (User user){
        if (userOpinionList != null){
            remove(userOpinionList);
        }

        List<Opinion> opinions = base.getOpinionsOfUser(user);

        String[] opinionsStringArray;

        if (opinions != null){
            opinionsStringArray = opinionsToStringArray(opinions);
        } else {
            opinionsStringArray = null;
        }

        userOpinionList = new JScrollPane(new JList<>(opinionsStringArray));

        userOpinionList.setFont(new Font("Arial", Font.BOLD, 14));

        setCoordinate(userOpinionList, 280, 40);
        setSize(userOpinionList, 400, 180);

        add(userOpinionList);
    }

    private void createYourOpinionsLabel (){
        yourOpinionsLabel = new JLabel("Твои мнения: ");

        setCoordinate(yourOpinionsLabel, 280, 20);
        setSize(yourOpinionsLabel, 150, 20);

        add(yourOpinionsLabel);
    }

    private void createStrangerOpinionList (){
        List<Opinion> randomOpinions = getRandomOpinions();

        String[] randomOpinionsArray = opinionsToStringArray(randomOpinions);

        strangerOpinionContent = new JList<>(randomOpinionsArray);

        strangerOpinionList = new JScrollPane(strangerOpinionContent);

        setCoordinate(strangerOpinionList, 280, 250);
        setSize(strangerOpinionList, 400, 180);

        add(strangerOpinionList);
    }

    private void createStrangerOpinionLabel (){
        strangeOpinionLabel = new JLabel("Случайные мнения: ");

        setCoordinate(strangeOpinionLabel, 280, 230);
        setSize(strangeOpinionLabel, 150, 20);

        add(strangeOpinionLabel);
    }

    private void createUpdateButton (){
        Icon icon = new ImageIcon("src/main/resources/images/updateButton.png");

        updateButton = new JButton(icon);

        setCoordinate(updateButton, 200, 300);
        setSize(updateButton, 60, 60);

        add(updateButton);

        updateButton.addActionListener(ae -> {
            remove(strangerOpinionList);
            createStrangerOpinionList();

            if (userOpinionList != null){
                remove(userOpinionList);
            }
            createUserOpinionList(currentUser);

            setVisible(false);
            setVisible(true);
        });
    }

    private String[] opinionsToStringArray (List<Opinion> list){
        String[] result = new String[list.size()];

        for (int i = 0; i < list.size(); i++){
            Opinion currentOpinion = list.get(i);
            result[i] = currentOpinion.getId() + ". " + currentOpinion.getValue();
        }

        return result;
    }

    private List<Opinion> getRandomOpinions (){
        long lastId = base.getLastOpinionId();

        List<Long> randomId = new ArrayList<>();
        Random random = new Random();

        long bound;

        if (lastId > 10) {
            bound = 10;
        } else {
            bound = lastId + 1;
        }

        for (int i = 1; i <= bound; i++){
            randomId.add(random.nextLong(lastId - 1) + 1);
        }

        List<Opinion> result = new ArrayList<>();

        for (int i = 0; i < randomId.size(); i++){
            Opinion currentOpinion = base.getOpinionById(randomId.get(i));

            result.add(currentOpinion);
        }


        return result;
    }

    private void unLogin(){
        currentUser = null;
        userLabel.setText(" Ты: Невойдённый");
        opinionTextField.setText("");
        createOpinionScrollPane();
    }

    private void showLPFrame(){
        loginFrame.setVisible(true);
    }

    private void showRegFrame (){
        registerFrame.setVisible(true);
    }

    public void setCurrentUser(User user){
        this.currentUser = user;

        userLabel.setText(" Ты: " + user.getName());

        correctLabel.setText("");

        createUserOpinionList(currentUser);
    }

    private void setCoordinate (Component component, int x, int y){
        layout.putConstraint(SpringLayout.WEST, component, x, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, component, y, SpringLayout.NORTH, this);
    }
    private void setSize (Component component, int x, int y){
          layout.putConstraint(SpringLayout.EAST, component, x, SpringLayout.WEST, component);
          layout.putConstraint(SpringLayout.SOUTH, component, y, SpringLayout.NORTH, component);
    }
}
