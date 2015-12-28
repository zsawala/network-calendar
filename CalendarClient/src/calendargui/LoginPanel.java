
package calendargui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class LoginPanel extends JPanel{
    JLabel title;
    JLabel nick;
    JLabel password;
    JTextField nickField;
    JTextField passField;
    JButton acceptButton;
    static int bitsCount;
    static byte[] message = new byte[4];
    public LoginPanel(DataOutputStream out,DataInputStream in,CardLayout cardLayout, JPanel cardPanel){
        setLayout(null);
        title = new JLabel("CALENDAR");
        nick = new JLabel("NICK:");
        password = new JLabel("PASSWORD:");
        nickField = new JTextField();
        passField = new JTextField();
        acceptButton = new JButton("Accept");

        title.setBounds(100,100,100,10);
        nick.setBounds(100,150,100,10);
        password.setBounds(100,200,100,10);
        nickField.setBounds(100,170,100,20);
        passField.setBounds(100,220,100,20);
        acceptButton.setBounds(100,250,100,20);
        
        add(title);
        add(nick);
        add(password);
        add(nickField);
        add(passField);
        add(acceptButton);
        
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    out.write("login".getBytes());  
                    out.write((nickField.getText()+"\n").getBytes());
                    out.write((passField.getText()+"\n").getBytes());
                    bitsCount = in.read(message);
                    String mess = new String(message);
                    System.out.print(mess);
                    System.out.print(mess);
                    if (mess.equals("true")){
                        System.out.println("weszlo");
                        cardLayout.show(cardPanel,"2");

                    }
                } catch (IOException ex) {
                    Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    
    
}
