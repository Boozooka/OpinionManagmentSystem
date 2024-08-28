import dataConnect.Connector;
import dataConnect.Opinion;
import dataConnect.User;
import interfaceConnect.Frame;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connector base = new Connector("jdbc:mysql://localhost:3306/opinion", "root", "Tchepigo1!");

        new Frame(base);

        /*Scanner scanner = new Scanner(System.in);

        Connector base = new Connector("jdbc:mysql://localhost:3306/opinion", "root", "Tchepigo1!");

        System.out.print("Name -> ");
        String name = scanner.nextLine();

        System.out.print("Password -> ");
        String password = scanner.nextLine();

        //connecting user test
        System.out.println("dataConnect.User connecting: " + base.connectUser(name, password));
        User user = base.getUserByName(name);

        //show opinions of current user test
        showOpinionsOnConsole(user, base);*/

        //put opinion test
        /*System.out.print("Your opinion -> ");
        String opinion = scanner.nextLine();

        base.putOpinion(user, opinion);*/

        /* register user test
        base.registerUser(name, password);*/

        //show all users test
        //showUsersOnConsole(base);
    }
    public static void showUsersOnConsole(Connector base){
        List<User> users = base.getUsers();

        for (int i = 0; i < users.size(); i++){
            User currentUser = users.get(i);
            System.out.println("Id: " + currentUser.getId() + ", Username: " + currentUser.getName() +
                    ", Password: " + currentUser.getPassword());
        }
    }
    public static void showOpinionsOnConsole (User user, Connector base){
        List<Opinion> opinions = base.getOpinionsOfUser(user);

        System.out.println("Opinions of '" + user.getName() + "' :");
        for (int i = 0; i < opinions.size(); i++){
            Opinion currentOpinion = opinions.get(i);
            System.out.println((i + 1) + ". " + currentOpinion.getValue());
        }
    }
}
