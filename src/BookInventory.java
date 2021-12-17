import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                majDb();
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

    private void majDb(){
        try{
            //Open a connection
            System.out.println("Connexion à la base de données...");
            Connection connection = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Connecté avec succès...");
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT*FROM mytable");


            String selectedBook = comboBox2.getSelectedItem().toString();
            String selectedId = comboBox1.getSelectedItem().toString();
            String date1Before = textField1.getText();
            Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(date1Before);
            java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
            String date2Before = "17/12/2021";
            Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(date2Before);
            java.sql.Date aujour = new java.sql.Date(date2.getTime());

            //Db Requests
            PreparedStatement pstmt = connection.prepareStatement("UPDATE mytable SET ID_UTILISATEUR=? WHERE NOM_LIVRE =?");
            pstmt.setString(1, selectedId);
            pstmt.setString(2, selectedBook);
            pstmt.executeUpdate();

            PreparedStatement pstmt2 = connection.prepareStatement("UPDATE mytable SET DATE_RETOUR=? WHERE NOM_LIVRE =?");
            pstmt2.setDate(1, sqlDate1);
            pstmt2.setString(2, comboBox2.getSelectedItem().toString());
            pstmt2.executeUpdate();

            PreparedStatement pstmt3 = connection.prepareStatement("UPDATE mytable SET DATE_EMPRUNT=?WHERE NOM_LIVRE=?");
            pstmt3.setDate(1, aujour);
            pstmt3.setString(2, selectedBook);
            pstmt3.executeUpdate();

            connection.close();

        } catch (SQLException | ParseException e) {
            System.out.println("Une erreur s'est produite...");
            e.printStackTrace();
        }
    }
}
