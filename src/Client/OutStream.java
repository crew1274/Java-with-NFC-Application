package Client;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
public class OutStream extends OutputStream {
	private JTextArea textArea;
	private JTextArea textArea1;
	public OutStream(JTextArea textArea) {
		this.textArea = textArea;
		this.textArea1= textArea;
	}
	public void write(int b) throws IOException {
        textArea.append(String.valueOf((char)b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	public void rec(int a) throws IOException {
        textArea1.append(String.valueOf((char)a));
        textArea1.setCaretPosition(textArea1.getDocument().getLength());
	}
}
