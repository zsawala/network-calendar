
package calendarclient;

import calendargui.LoginPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CalendarClient {
    static InetAddress addr;
     byte[] ipAddr;
    static int port = Integer.parseInt("1234");
    static byte[] message = new byte[15];
    static int bitsCount; //liczba zwroconych bitow
    static DataOutputStream out;
    static DataInputStream in;
    public static void main(String[] args) {
        
        //POŁĄCZENIE
          try
      {
         InetAddress addr = InetAddress.getByName("192.168.0.12");
         System.out.println("Connecting to " + addr + " on port "+  port);
         Socket client = new Socket(addr, port);
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         out = new DataOutputStream(outToServer);
         InputStream inFromServer = client.getInputStream();
         in = new DataInputStream(inFromServer);
//         System.out.println("Odbieranie");
//         System.out.println("Server says " + in.readUTF());
//         bitsCount = in.read(message);
////         while(!end){
////             
////         }
//         wynik.setText(new String(message));
//         while(true){
//            out.write("blabla".getBytes()); 
//         } 
      }catch(IOException c)
      {
         c.printStackTrace();
      }
        
          //GUI
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        LoginPanel panel = new LoginPanel(out,in);
        frame.add(panel);
        frame.setVisible(true);
//        JPanel panel = new JPanel();
//        frame.add(panel);
//        JLabel label = new JLabel("Wpisz indeks");
//        panel.add(label);
//        JTextField text = new JTextField("",10);
//        panel.add(text);
//        JButton button = new JButton("Button");
//        panel.add(button);
//        frame.setVisible(true);
//        panel.setVisible(true);
//        JLabel wynik = new JLabel();
//        panel.add(wynik);
//        button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//              wynik.setText("hahahahaah");
//            }
//        });
       
    }

    public CalendarClient() throws UnknownHostException {
        
        
    }
    
}
