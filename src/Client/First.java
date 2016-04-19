package Client;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import gnu.io.*;

public class First extends JFrame {
	public String ip="";
	public String name="";
	public String account="";
	public String password="";
	    public JLabel welcome =new JLabel ("welocme!");
	    public  JLabel a =new JLabel ("連接資料庫地址:");private JTextField db_ip =new JTextField ("140.116.39.225");
	    public  JLabel b =new JLabel ("資料庫名稱:"); private JTextField db_name =new JTextField ("NFC");
	    public  JLabel c =new JLabel ("使用者帳號:"); private JTextField db_account =new JTextField ("nfc_user");
	    public  JLabel d =new JLabel ("使用者密碼:"); private JPasswordField db_password =new JPasswordField ("50150");
	    public  JButton connect_db = new JButton("連接資料庫");
	    public  JButton connect_reader = new JButton("連接讀卡機");
	    public  JLabel db_status =new JLabel ("尚未連接資料庫");
	    public  JLabel reader_status =new JLabel ("尚未連接讀卡機");
        // Constructor for a new frame

        public First() {
        	   super("NFC reader");
        	   setLayout(null);
        	   add(welcome);welcome.setFont(new Font( "Serief", Font.BOLD, 40));
        	   add(a);a.setFont(new Font( "", Font.BOLD, 18));
        	   add(b);b.setFont(new Font( "", Font.BOLD, 18));
        	   add(c);c.setFont(new Font( "", Font.BOLD, 18));
        	   add(d);d.setFont(new Font( "", Font.BOLD, 18));
        	   add(db_ip);db_ip.setFont(new Font( "", Font.BOLD, 18));
        	   add(db_name);db_name.setFont(new Font( "", Font.BOLD, 18));
        	   add(db_account);db_account.setFont(new Font( "", Font.BOLD, 18));
        	   add(db_password);db_account.setFont(new Font( "", Font.BOLD, 18));
               add(connect_db);connect_db.setFont(new Font( "", Font.BOLD, 18));
               add(connect_reader);connect_reader.setFont(new Font( "", Font.BOLD, 18));
               add(d);d.setFont(new Font( "", Font.BOLD, 18));
               add(db_status);db_status.setFont(new Font( "", Font.BOLD, 18));
               add(reader_status);reader_status.setFont(new Font( "", Font.BOLD, 18));
               welcome.setBounds(200,10,240,40);
               connect_reader.setBounds(440,220,140,40);connect_reader.setVisible(false);
               connect_db.setBounds(440,100,140,40);
               a.setBounds(50,100,140,40);db_ip.setBounds(200,100,200,40);
               b.setBounds(50,140,140,40);db_name.setBounds(200,140,200,40);
               c.setBounds(50,180,140,40);db_account.setBounds(200,180,200,40);
               d.setBounds(50,220,140,40);db_password.setBounds(200,220,200,40);
               db_status.setBounds(440,150,200,40);



                ButtonHandler handler = new ButtonHandler();    //creation of a new Object
               connect_db.addActionListener(handler);          // Attach/register handler to myFirstButton
               connect_reader.addActionListener(handler);        //Attach/register handler to mySecondButton


                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(600,400);
                setLocationRelativeTo(null);
        }


        public static void main(String [] args) {
        	 SwingUtilities.invokeLater(new Runnable() {
                 public void run() {
                 	new First().setVisible(true);}});
        } // end of main

        // inner class for button event handling
        private class ButtonHandler implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == connect_db) {
                        	JDBC connect_db= new JDBC();
                        	 if(connect_db.connect(db_ip.getText(),db_name.getText(),db_account.getText(),db_password.getText()))
                        	 {
                        		 ip=db_ip.getText();
                        		 name=db_name.getText();
                        		 account=db_account.getText();
                        		 password=db_password.getText();
                        		 db_status.setText("成功連接資料庫!");
                        		 connect_reader.setVisible(true);
                        	 }

                        }
                        if (e.getSource() == connect_reader) {

                        	new MAIN().setVisible(true);
                        	//First().setVisible(false);
                        }
                }
        } // end of inner class
} // end of outer class
