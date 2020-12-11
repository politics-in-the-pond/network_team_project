package my_package;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;
import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient {
	
	JFrame frame = new JFrame("Chatter");//
    JTextField textField = new JTextField(50);//

    String serverAddress;
    int port;
	  private InputStream is;
	  private DataInputStream in;
    OutputStream os;
    DataOutputStream out;
    Vector<String> names = new Vector<>();
    boolean login_comp = false;

    public ChatClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
        String_crypto crypt = new String_crypto();
        
        textField.setEditable(true);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.pack();
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            try {
            	byte[] temp = crypt.do_encrypt(textField.getText());
            	byte[] output = new byte[temp.length+17];
            	
            	//샘플코드
            	output[0] = 0x02;
            	System.arraycopy(temp, 0, output, 17, temp.length);
            	System.arraycopy(crypt.ltob(crypt.MT19937_long(126321621)), 0, output, 1, 8);
            	System.arraycopy(crypt.ltob(crypt.MT19937_long(12123123)), 0, output, 9, 8);
				out.write(output);
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			} //textField에 입력된 내용을 전송한다.
            textField.setText(""); //textField를 비운다.
            }});
    }

    private void run() throws IOException, ClassNotFoundException {
    	String_crypto crypt = new String_crypto();
        try {
        	Socket socket = new Socket(serverAddress, port);
            is = socket.getInputStream();
            in = new DataInputStream(is);
            os = socket.getOutputStream();
            out = new DataOutputStream(os);
            String id = "thisistest";
            String password = "test1234";
            String line = null;
            byte[] temp = new byte[16777216]; 
			byte type = 0x00;
            while (true) {
            	int how_many = in.read(temp);
            	if(how_many==0) continue;
            	byte[] input = new byte[how_many];
            	System.arraycopy(temp, 0, input, 0, how_many);
            	line = crypt.do_decrypt(input);
            	System.out.println("***"+line);
                if(!login_comp) {
                    if(line.equals("LOGIN")) {
                    	out.write(crypt.do_encrypt(id+ " "+password));
                    	out.flush();
                    }else if(line.equals("LOGINSUC")) {
                    	login_comp = true;
                    }
                }
            }
        }
        finally {
        	
        }
    }

    public static void main(String[] args) throws Exception {
    	
    	int bytelength=0;
    	String serverAddress=null;
        int port=0;
        String_crypto crypt = new String_crypto();
        
        byte[] reads = new byte[50];
        byte[] portb = new byte[4];
        
        try{
	       File file = new File("./src/my_package/config.dat");
	       FileInputStream inb = new FileInputStream(file);
		   bytelength = inb.read(reads);
	       inb.close();
	    }catch(Exception e){
	        e.printStackTrace(System.out);
	    }
        
        byte[] ipaddress = new byte[bytelength-4];
        System.arraycopy(reads, 0, portb, 0, 4);
        System.arraycopy(reads, 4, ipaddress, 0, bytelength-4);
        
        port = crypt.btoi(portb);
        serverAddress = crypt.do_decrypt(ipaddress);
        ChatClient client = new ChatClient(serverAddress,port); //클라이언트 객체 생성
        
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
}
