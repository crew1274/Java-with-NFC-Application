package Client;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import gnu.io.*;
public class MAIN extends JFrame  implements ChangeListener    {
	Writer writer = new Writer();JDBC db= new JDBC();
	String	list[] ={"Stage 1 Checkin","Stage 1 Checkout","Stage 2 Checkin","Stage 2 Checkout","Stage 3 Checkin","Stage 3 Checkout"};
	String NFC="NFC";
	int status =0;
	private		JPanel		panel1;
	private		JPanel		panel2;
	private		JPanel		panel3;
	private    JTabbedPane tp = new JTabbedPane();
	private static Calendar calendar = new GregorianCalendar();  //建立一個 Calendar
	private ImageIcon not = new ImageIcon("not.png");
	private ImageIcon ok = new ImageIcon("ok.png");
	private ImageIcon disconnect = new ImageIcon("disconnect.png");
	private ImageIcon connect = new ImageIcon("connect.png");
	private JLabel dbhint = new JLabel(not);
	private JLabel readerhint = new JLabel(disconnect);
	private PrintStream standardOut;
	private JTextArea log;
	private JTextArea record;
	private JButton connent_db = new JButton("連接資料庫");
	private JButton disconnent_db = new JButton("登出資料庫");
	private JLabel db_ip =new JLabel ("資料庫位址:");private JTextField db_ip_tf =new JTextField ("140.116.39.225");
	private JLabel db_account =new JLabel ("帳號:");private JTextField db_account_tf =new JTextField ("nfc_user");
	private JLabel db_password =new JLabel ("密碼:");private JPasswordField db_password_tf =new JPasswordField ("50150");
    private JButton clear = new JButton("清除");
    private JButton next = new JButton("讀取下一個Tag");
    private JButton clear_2 = new JButton("清除");
    private JButton yes = new JButton("讀取Tag資料");
    private JButton write_tag = new JButton("寫入Tag");
    private JButton sys = new JButton("雲端資料庫資料同步到Tag");
    private JButton no = new JButton("讀取下一個Tag");
    private JButton check = new JButton("打卡");
    private JButton check_2 = new JButton("紀錄雲端資料庫");
    private JButton select = new JButton("查詢");
    private JButton select_2 = new JButton("查詢雲端資料庫");
    private JLabel id =new JLabel ("讀取的卡號:");
    private JTextField idtf =new JTextField ("");
    private JLabel id_2 =new JLabel ("讀取的卡號:");
    private JTextField idtf_2 =new JTextField ("");
    private JLabel time =new JLabel ("卡號                 關卡                  時間");
    private JLabel blood_print =new JLabel ("卡號                 血壓(舒張/收縮)               時間");
    private JLabel f =new JLabel ("選擇關卡:");
    private JList listbox = new JList(list) ;
    private JLabel g =new JLabel ("血壓(舒張/收縮):"); private JTextField gtf =new JTextField ("");
    private JLabel h =new JLabel ("/"); private JTextField htf =new JTextField ("");
    private JLabel i =new JLabel ("Tag裡的資料");
    private JLabel j =new JLabel ("時間1:");private JTextField jtf =new JTextField ("");
    private JLabel j_b =new JLabel ("血壓1:");private JTextField j_btf =new JTextField ("");
    private JLabel k =new JLabel ("時間2:");private JTextField ktf =new JTextField ("");
    private JLabel k_b =new JLabel ("血壓2:");private JTextField k_btf =new JTextField ("");
    private JLabel l =new JLabel ("時間3:");private JTextField ltf =new JTextField ("");
    private JLabel l_b =new JLabel ("血壓3:");private JTextField l_btf =new JTextField ("");
    public MAIN() {
        super("NFC reader");
        printLog();
        setLayout (null);
        createPage1();
		createPage2();
		createPage3();
        add(tp);tp.setBounds(460,10,520,580);

        tp.addTab("資料庫登入",panel1);
        tp.addTab("打卡簽到",panel2);
        tp.addTab("血壓紀錄",panel3);
        log=new JTextArea(10,50);
        log.setEditable(true);
        PrintStream printStream = new PrintStream(new OutStream(log));
        standardOut = System.out;
        System.setOut(printStream);
        record=new JTextArea();
        record.setEditable(true);
        connent_db.setFont(new Font("Serief",Font.BOLD,20));disconnent_db.setFont(new Font("Serief",Font.BOLD,20));
        db_ip.setFont(new Font("Serief",Font.BOLD,20));db_ip_tf.setFont(new Font("Serief",Font.BOLD,20));
        db_account.setFont(new Font("Serief",Font.BOLD,20));db_account_tf.setFont(new Font("Serief",Font.BOLD,20));
        db_password.setFont(new Font("Serief",Font.BOLD,20));db_password_tf.setFont(new Font("Serief",Font.BOLD,20));
        check.setFont(new Font("Serief",Font.BOLD,20));
        check_2.setFont(new Font("Serief",Font.BOLD,20));
        yes.setFont(new Font("Serief",Font.BOLD,14));
        no.setFont(new Font("Serief",Font.BOLD,14));
        next.setFont(new Font("Serief",Font.BOLD,20));
        clear.setFont(new Font("Serief",Font.BOLD,20));
        write_tag.setFont(new Font("Serief",Font.BOLD,20));
        sys.setFont(new Font("Serief",Font.BOLD,20));
        clear_2.setFont(new Font("Serief",Font.BOLD,20));
        select.setFont(new Font("Serief",Font.BOLD,20));
        select_2.setFont(new Font("Serief",Font.BOLD,20));
        log.setFont(new Font("Times",Font.BOLD,20));
        record.setFont(new Font("Times",Font.BOLD,20));
        id.setFont(new Font("",Font.BOLD,20));
        idtf.setFont(new Font("Times New Roman",Font.BOLD,20));
        id_2.setFont(new Font("",Font.BOLD,20));
        idtf_2.setFont(new Font("Times New Roman",Font.BOLD,20));
        time.setFont(new Font("Serief",Font.BOLD,20));
        blood_print.setFont(new Font("Serief",Font.BOLD,20));
        f.setFont(new Font("Serief",Font.BOLD,20));
        listbox.setFont(new Font("Serief",Font.BOLD,16));
        g.setFont(new Font("Serief",Font.BOLD,20));
        gtf.setFont(new Font("Serief",Font.BOLD,16));
        h.setFont(new Font("Serief",Font.BOLD,20));
        htf.setFont(new Font("Serief",Font.BOLD,16));
        i.setFont(new Font("Serief",Font.BOLD,20));
        j.setFont(new Font("Serief",Font.BOLD,20));
        jtf.setFont(new Font("Serief",Font.BOLD,16));
        j_b.setFont(new Font("Serief",Font.BOLD,20));
        j_btf.setFont(new Font("Serief",Font.BOLD,16));
        k.setFont(new Font("Serief",Font.BOLD,20));
        ktf.setFont(new Font("Serief",Font.BOLD,16));
        k_b.setFont(new Font("Serief",Font.BOLD,20));
        k_btf.setFont(new Font("Serief",Font.BOLD,16));
        l.setFont(new Font("Serief",Font.BOLD,20));
        ltf.setFont(new Font("Serief",Font.BOLD,16));
        l_b.setFont(new Font("Serief",Font.BOLD,20));
        l_btf.setFont(new Font("Serief",Font.BOLD,16));
        add(new JScrollPane(log)).setBounds(10,10,440,780);
        add(new JScrollPane(record)).setBounds(460,600,510,190);
        connent_db.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                	if(db.connect(db_ip_tf.getText(),NFC,db_account_tf.getText(),db_password_tf.getText()))
               	 {
                 System.out.println("success connect database!");
                 db_ip_tf.setEnabled(false);
                 db_account_tf.setEnabled(false);
                 db_password_tf.setEnabled(false);
               	 }
                }});
        disconnent_db.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	db.close();
            	System.out.println("disconnect database!");
            	db_ip_tf.setEnabled(true);
                db_account_tf.setEnabled(true);
                db_password_tf.setEnabled(true);
             }});

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                	record.getDocument().remove(0,record.getDocument().getLength());
                	System.out.println("Record cleared!");
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }}});
        clear_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                	record.getDocument().remove(0,record.getDocument().getLength());
                	System.out.println("Record cleared!");
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }}});

        select.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){

		    	if(db.SelectTable(idtf.getText()).length()<5)
		    		System.out.println("No record!");
		    	else
		    		record.setText(db.SelectTable(idtf.getText()));
		    	}});
        select_2.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){

		    	if(db.SelectTable_2(idtf_2.getText()).length()<5)
		    		System.out.println("No record!");
		    	else
		    		record.setText(db.SelectTable_2(idtf_2.getText().toString()));
		    	}});
    	check.addActionListener(new  ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	if(listbox.getSelectedValue()==null)
		    	{   System.out.println("Do not choose state!");
		    		return;
		    	}
		    	setTime();
		    	db.insertTable(idtf.getText().toString(),listbox.getSelectedValue().toString(),calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+
		      			 calendar.get(Calendar.DATE)+" "+ calendar.get(Calendar.HOUR_OF_DAY)+":"+
		       			 calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND));
		    	}});
    	check_2.addActionListener(new  ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	setTime();
		    	db.insertTable_2(idtf_2.getText().toString(),
		    			gtf.getText().toString(),htf.getText().toString(),
		    			               calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE)+" "+ calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND));
		    	}});
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,850);
        setLocationRelativeTo(null);
    }

    private void printLog() {

    	next.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){writer.sendData("q");}
		    });
    	yes.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	writer.sendData("w");
		    	jtf.setText(writer.time1);
            	j_btf.setText(writer.blood1);
            	ktf.setText(writer.time2);
            	k_btf.setText(writer.blood2);
            	ltf.setText(writer.time3);
            	l_btf.setText(writer.blood3);
                write_tag.setVisible(true);
                sys.setVisible(true);
		    }
		    });
    	no.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	writer.sendData("q");
		    	write_tag.setVisible(false);
		    	sys.setVisible(false);
		    	}
		    });
    	write_tag.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	String fix;
		    	fix="@"+jtf.getText()+"|"+j_btf.getText()+"|"+ktf.getText()+"|"+k_btf.getText()+"|"+ltf.getText()+"|"+l_btf.getText();
		    	writer.sendData(fix);
		    	write_tag.setVisible(false);
		    	}
		    });
    	sys.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	if(db.SelectTable_2(idtf_2.getText()).length()<5)
		    		{System.out.println("No record!");}
		    	else
		    		{record.setText(db.SelectTable_2(idtf_2.getText().toString()));
		    	jtf.setText(db.the_new[0][0]);j_btf.setText(db.the_new[0][1]);
		    	ktf.setText(db.the_new[1][0]);k_btf.setText(db.the_new[1][1]);
		    	ltf.setText(db.the_new[2][0]);l_btf.setText(db.the_new[2][1]);
		    	sys.setVisible(false);
		    	}}
		    });
        Thread thread = new Thread(new Runnable() {
            public void run() {
            	 if ( writer.initialize() )
            	 {
                while (true) {
                	readerhint.setIcon(connect);
                	status = tp.getSelectedIndex();
                	if(status==1)
                	{
                	if(idtf.getText()!=writer.uid)
                	{idtf.setText(writer.uid);
                	writer.sendData("q");}}
                	idtf_2.setText(writer.uid);
                    try {Thread.sleep(2000);}
                    catch (InterruptedException e){e.printStackTrace();}
                    }
            }}});
        thread.start();
            };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new MAIN().setVisible(true);
            	}});
        /*while(true){setTime();
   	    time.setText(calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+
   			 calendar.get(Calendar.DATE)+"日"+ calendar.get(Calendar.HOUR_OF_DAY)+"時"+
   			 calendar.get(Calendar.MINUTE)+"分"+calendar.get(Calendar.SECOND)+"秒");}*/
    }
    private static void setTime()  //設定系統時間
	 {
	     calendar.setTimeInMillis( System.currentTimeMillis() );
	 }
    public void createPage1()
	{status=1;
	System.out.println(status);
		panel1 = new JPanel();
		panel1.setLayout( null );
		panel1.add(connent_db);
		connent_db.setBounds( 10, 35, 150, 30 );
		panel1.add(disconnent_db);
		disconnent_db.setBounds( 200,35, 150, 30 );
		panel1.add(db_ip);
		db_ip.setBounds( 10, 100, 150, 30 );
		panel1.add(db_ip_tf);
		db_ip_tf.setBounds( 170, 100, 180, 30 );
		panel1.add(db_account);
		db_account.setBounds( 10, 140, 150, 30 );
		panel1.add(db_account_tf);
		db_account_tf.setBounds( 170, 140, 180, 30 );
		panel1.add(db_password);
		db_password.setBounds( 10, 180, 150, 30 );
		panel1.add(db_password_tf);
		db_password_tf.setBounds( 170, 180, 180, 30 );
	}

    public void createPage2()
	{
    	status=2;
    	System.out.println(status);
		panel2 = new JPanel();
		panel2.setLayout( null );
		panel2.add(id);
		id.setBounds( 10, 35, 150, 20 );
		panel2.add(idtf);
        idtf.setBounds( 10, 60, 170, 30 );
        panel2.add(next);
        next.setBounds(200 , 60, 200, 30 );
		panel2.add(listbox);
		panel2.add(new JScrollPane(listbox)).setBounds( 10, 100, 170, 200);
		panel2.add(check);
		check.setBounds( 10, 360, 120, 30 );
		panel2.add(select);
		select.setBounds( 140, 360, 120, 30 );
		panel2.add(clear);
		clear.setBounds( 400, 500, 100, 30 );
		panel2.add(time);
		time.setBounds( 10, 500,400, 30 );
	}
    public void createPage3()
	{
    	status=3;
    	System.out.println(status);
		panel3 = new JPanel();
		panel3.setLayout( null );
		panel3.add(id_2);
		id_2.setBounds( 10, 35, 150, 20 );
		panel3.add(idtf_2);
        idtf_2.setBounds( 10, 60, 170, 30 );
        panel3.add(no);
        no.setBounds( 200, 60, 140, 30 );
        panel3.add(yes);
        yes.setBounds( 350, 60, 140, 30 );
        panel3.add(g);
        g.setBounds( 10, 100, 170, 30 );
        panel3.add(gtf);
        gtf.setBounds( 10, 140, 100, 30 );
        panel3.add(h);
        h.setBounds( 120, 140, 30, 30 );
        panel3.add(htf);
        htf.setBounds( 140, 140, 100, 30 );
        panel3.add(blood_print);
        blood_print.setBounds( 10, 500,500, 30 );
        panel3.add(check_2);
        check_2.setBounds( 10, 200,200,30 );
        panel3.add(select_2);
        select_2.setBounds( 10, 250,200, 30 );
        panel3.add(clear_2);
        clear_2.setBounds( 10, 450, 120, 30  );

        panel3.add(i);
        i.setBounds( 300, 100, 140, 30  );  //tag資料顯示
        panel3.add(j);
        j.setBounds( 270, 150, 100, 30  );  //時間1
        panel3.add(jtf);
        jtf.setBounds( 380, 150, 120, 30  );
        panel3.add(j_b);
        j_b.setBounds( 270, 190, 120, 30  ); //血壓1
        panel3.add(j_btf);
        j_btf.setBounds( 380, 190, 120, 30  );
        panel3.add(k);
        k.setBounds( 270, 230, 120, 30  );  //時間2
        panel3.add(ktf);
        ktf.setBounds( 380, 230, 120, 30  );
        panel3.add(k_b);
        k_b.setBounds( 270, 270, 120, 30  );//血壓2
        panel3.add(k_btf);
        k_btf.setBounds( 380, 270, 120, 30  );
        panel3.add(l);
        l.setBounds( 270, 310, 120, 30  ); //時間3
        panel3.add(ltf);
        ltf.setBounds( 380, 310, 120, 30  );
        panel3.add(l_b);
        l_b.setBounds( 270, 350, 120, 30  );//血壓3
        panel3.add(l_btf);
        l_btf.setBounds( 380, 350, 120, 30  );
        write_tag.setVisible(false);
        panel3.add(write_tag);
        write_tag.setBounds( 380, 450, 120, 30  );
        sys.setVisible(false);
        panel3.add(sys);
        sys.setBounds( 170, 450, 200, 30  );

	}

	public void stateChanged(ChangeEvent e) {
		tp=(JTabbedPane)e.getSource();
		int index=tp.getSelectedIndex();
	    String msg="目前頁籤 : [" + index + "]" + tp.getTitleAt(index);
	    System.out.println(msg);
	}

}
