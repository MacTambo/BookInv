import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BookInventory {
    private JComboBox comboBox1;
    private JButton validerLEmpruntButton;
    private JComboBox comboBox2;
    private JLabel messageErr;
    private JTextField textField1;
    private JPanel panel1;
    private JTextArea textArea1;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/books";

    static final String USER = "root";
    static final String PASS = "";

    String aujour = "16/12/2021";

    public BookInventory() {
            try{
                //Open a connection
                System.out.println("Connexion à la base de données...");
                Connection connection = DriverManager.getConnection(DB_URL,USER,PASS);
                System.out.println("Connecté avec succès...");
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT*FROM mytable");

                textArea1.append("Liste des livres : \n");

                while (result.next()){
                    String nom = result.getString("NOM_LIVRE");
                    textArea1.append("- "+nom+"\n");
                }
                connection.close();

            } catch (SQLException e) {
                System.out.println("Une erreur s'est produite...");
                e.printStackTrace();
            }

        validerLEmpruntButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collecterRep();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Book Inventory");
        frame.setContentPane(new BookInventory().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setVisible(true);
    }

    String idUser, bookTitle, returnDate;

    private void collecterRep(){
        idUser = comboBox1.getSelectedItem().toString();
        bookTitle = comboBox2.getSelectedItem().toString();
        returnDate = textField1.getText();
    }

    private void majDb(){
        try{
            //Open a connection
            System.out.println("Connexion à la base de données...");
            Connection connection = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connecté avec succès...");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT*FROM mytable");

            statement.executeQuery("UPDATE mytable SET ID="+comboBox1.getSelectedItem()+"WHERE NOM_LIVRE ="+comboBox2.getSelectedItem());
//            statement.executeQuery("INSERT INTO mytable (DATE_RETOUR) VALUE "+comboBox2.getSelectedItem());
//            statement.executeQuery("INSERT INTO mytable (DATE_EMPRUNT) VALUE "+aujour);

        } catch (SQLException e) {
            System.out.println("Une erreur s'est produite...");
            e.printStackTrace();
        }
    }





}
