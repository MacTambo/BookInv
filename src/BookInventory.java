import javax.swing.*;

public class BookInventory {
    private JComboBox comboBox1;
    private JButton validerLEmpruntButton;
    private JComboBox comboBox2;
    private JLabel messageErr;
    private JTextField textField1;
    private JPanel panel1;
    private JList list1;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Book Inventory");
        frame.setContentPane(new BookInventory().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



    }



}
