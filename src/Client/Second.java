package Client;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import gnu.io.*;
public class Second extends JFrame implements ChangeListener {
	private		JTabbedPane tabbedPane;
	private		JPanel		panel1;
	private		JPanel		panel2;
	String	list[] ={"Stage 1 Checkin","Stage 1 Checkout","Stage 2 Checkin","Stage 2 Checkout","Stage 3 Checkin","Stage 3 Checkout"};
	JDBC connect_db= new JDBC();

	public Second()
	{
		// NOTE: to reduce the amount of code in this example, it uses
		// panels with a NULL layout.  This is NOT suitable for
		// production code since it may not display correctly for
		// a look-and-feel.

		super("NFC reader");

		//setBackground( Color.gray );

		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

		// Create the tab pages
		createPage1();
		createPage2();
		//createPage3();

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab( "¥´¥dÃ±¨ì", panel1 );
		tabbedPane.addTab( "¦åÀ£¬ö¿ý", panel2 );
		//tabbedPane.addTab( "Page 3", panel3 );
		topPanel.add( tabbedPane, BorderLayout.CENTER );
	}

	public void createPage1()
	{
		panel1 = new JPanel();
		panel1.setLayout( null );

		JLabel label1 = new JLabel( "Username:" );
		label1.setBounds( 10, 15, 150, 20 );
		panel1.add( label1 );

		JTextField field = new JTextField();
		field.setBounds( 10, 35, 150, 20 );
		panel1.add( field );

		JLabel label2 = new JLabel( "Password:" );
		label2.setBounds( 10, 60, 150, 20 );
		panel1.add( label2 );

		JPasswordField fieldPass = new JPasswordField();
		fieldPass.setBounds( 10, 80, 150, 20 );
		panel1.add( fieldPass );
	}

	public void createPage2()
	{
		panel2 = new JPanel();
		panel2.setLayout( new BorderLayout() );

		panel2.add( new JButton( "North" ), BorderLayout.NORTH );
		panel2.add( new JButton( "South" ), BorderLayout.SOUTH );
		panel2.add( new JButton( "East" ), BorderLayout.EAST );
		panel2.add( new JButton( "West" ), BorderLayout.WEST );
		panel2.add( new JButton( "Center" ), BorderLayout.CENTER );
	}

	/*public void createPage3()
	{
		panel3 = new JPanel();
		panel3.setLayout( new GridLayout( 3, 2 ) );

		panel3.add( new JLabel( "Field 1:" ) );
		panel3.add( new TextArea() );
		panel3.add( new JLabel( "Field 2:" ) );
		panel3.add( new TextArea() );
		panel3.add( new JLabel( "Field 3:" ) );
		panel3.add( new TextArea() );
	}*/

    // Main method to get things started
	public static void main( String args[] )
	{
		// Create an instance of the test application
		Second mainFrame	= new Second();
		mainFrame.setVisible( true );
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub

	}
}