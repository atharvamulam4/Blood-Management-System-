package BBMS;

import java.awt.Color;
import java.awt.*;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class getblood extends JFrame implements ActionListener {
    public static final long serialVersionUID = 1L;
    public JPanel contentPane;
    public JTextField typetext;
    JButton btnNewButton;



    public getblood() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 90, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        typetext = new JTextField();
        typetext.setFont(new Font("Tahoma", Font.PLAIN, 32));
        typetext.setBounds(400, 200, 228, 50);
        contentPane.add(typetext);
        typetext.setColumns(10);
        btnNewButton = new JButton("get blood");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnNewButton.setBounds(400, 300, 228, 74);
        contentPane.add(btnNewButton);
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 39));
        btnNewButton.setBounds(300, 92, 362, 73);
        btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        String type = typetext.getText().toString();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bbms", "root", "shashank");


            Statement sta = connection.createStatement();

            ResultSet rs = sta.executeQuery("select * from available_blood where type='" + type + "'");
            rs.next();
            int no=0;
            if(rs.getInt(3)>0) {
                no = rs.getInt(3) - 1;
                JOptionPane.showMessageDialog(btnNewButton, "donation successful");
            }
            else {
                JOptionPane.showMessageDialog(btnNewButton, "blood not available");
            }

            PreparedStatement stm = connection.prepareStatement("update available_blood set quantity='" + no + "' where type='" + type + "'");
            stm.executeUpdate();


            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }



    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    getblood frame = new getblood();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}


