package Client;

import gnu.io.*;
import java.io.*;
import java.util.Enumeration;
public class Writer implements SerialPortEventListener {
    public SerialPort serialPort = null;
    public  static final String PORT_NAMES[] = {null};
    public  String appName;
    public BufferedReader input;
    public OutputStream output;
    public String uid="";
    public String uid_length;
    public String[] uid_data;
    public String time1="";
    public String blood1="";
    public String time2="";
    public String blood2="";
    public String time3="";
    public String blood3="";
    public String date="";
    public String imgpath="";
    private static final int TIME_OUT = 1000; // Port open timeout
    private static final int DATA_RATE = 9600; // Arduino serial port
    public boolean initialize() {
        try {
            CommPortIdentifier portId = null;
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
            System.out.println( "Trying connect NFC reader...");
            while (portId == null && portEnum.hasMoreElements()) {
                // Iterate through your host computer's serial port IDs
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                System.out.println( "Connect:" + currPortId.getName() );
                PORT_NAMES[0]=currPortId.getName() ;
                for (String portName : PORT_NAMES) {
                    if ( currPortId.getName().equals(portName)|| currPortId.getName().startsWith(portName))
                    {   // Try to connect to this port
                        //
                        // Open serial port
                        serialPort = (SerialPort)currPortId.open(appName, TIME_OUT);
                        portId = currPortId;
                        System.out.println( "Successful connected on port " + currPortId.getName()+"!" );
                        break;
                    }
                }
            }
            if (portId == null || serialPort == null) {
                System.out.println("Oops... Could not connect to NFC reader!");
                return false;
            }
            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            // Give the Arduino some time
            try { Thread.sleep(4000); } catch (InterruptedException ie) {}
            return true;}
        catch ( Exception e ) {e.printStackTrace();}
        return false;
    }
    public void sendData(String data) {
        try {
           // System.out.println("Sending data: '" + data +"'");
            output = serialPort.getOutputStream();
            output.write( data.getBytes() );
            System.out.println(data);
        }
        catch (Exception e) {
            System.err.println(e.toString());
            System.exit(0);
        }
    }
    public synchronized void close() {
            serialPort.removeEventListener();
            serialPort.close();
    }
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        //System.out.println("Event received: " + oEvent.toString());
        try {
            switch (oEvent.getEventType() ) {
                case SerialPortEvent.DATA_AVAILABLE:
                    if ( input == null ) {
                        input = new BufferedReader(new InputStreamReader( serialPort.getInputStream()));
                         }
                    String inputLine = input.readLine();
                    if (inputLine.startsWith("#")) //tag uid的長度
                    {
                    	StringBuilder builder_uid_length = new StringBuilder();
                    	builder_uid_length.append(inputLine);
                    	builder_uid_length.delete(0,1);
                    	inputLine=builder_uid_length.toString();
                    	uid_length=inputLine;
                    	System.out.println("Get Tag uid length:"+uid_length);
                    }
                    else if (inputLine.startsWith("&")) //tag的uid
                    {
                    	StringBuilder builder_uid = new StringBuilder();
                    	builder_uid.append(inputLine);
                    	builder_uid.delete(0,1);
                    	inputLine=builder_uid.toString();
                    	uid=inputLine;
                    	System.out.println("Get theTag uid:"+uid);
                    }
                    else if (inputLine.startsWith("@")) //tag的資料
                    {
                    	StringBuilder builder_uid_data = new StringBuilder(128);
                    	builder_uid_data.append(inputLine);
                    	builder_uid_data.delete(0,1);
                    	/*inputLine=builder_uid_data.toString();
                    	inputLine.replaceAll("\\s","");*/
                    	System.out.println(inputLine);
                    	uid_data=inputLine.split("[|\\s]+");
                    	time1=uid_data[0];
                    	blood1=uid_data[1];
                    	time2=uid_data[2];
                    	blood2=uid_data[3];
                    	time3=uid_data[4];
                    	blood3=uid_data[5];
                    	//for (int i = 0; i<uid_data.length; i++)
                    		//System.out.println(uid_data[i]);
                    	System.out.println("Get the Mifare Ultralight Tag data!");
                    }
                    else
                    	{System.out.println(inputLine);}
                    break;
                default:
                    break;
            }
        }
        catch (Exception e) {System.err.println(e.toString());}
    }

    public Writer() {appName = getClass().getName();}
    public static void main(String[] args) throws Exception {
        Writer test = new Writer();
        if ( test.initialize() ) {
        	while(true)
        	{

        	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s = br.readLine();
            test.sendData(s);
            try { Thread.sleep(2000); } catch (InterruptedException ie) {}
        }}
        try { Thread.sleep(2000); } catch (InterruptedException ie) {}
    }
}
