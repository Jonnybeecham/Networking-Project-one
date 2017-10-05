/*
 * This class creates the GUI for sending and receiving messges.
 * The writeSenderToFile method saves the incoming message to a text file.
 * The writeReceiverToFile method saves the outgoing message to a text file.
 */


 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import java.awt.*;

public class GUIBox extends javax.swing.JFrame {
    
    //constructor for the GUI that initializes the necessary components
    public GUIBox() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        IPAddressBox = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageTextBox = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        incomingTextBox = new javax.swing.JTextArea();
        senderTextBox = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Message to be sent:");

        jLabel2.setText("Send to IP:");

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Message received: ");

        messageTextBox.setColumns(20);
        messageTextBox.setRows(5);
        jScrollPane1.setViewportView(messageTextBox);
        incomingTextBox.setColumns(20);
        incomingTextBox.setRows(5);
        jScrollPane2.setViewportView(incomingTextBox);
		//
		getContentPane().setBackground(Color.GREEN);
		
        jLabel4.setText("Sent from IP:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
		//HORIZONTAL
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(IPAddressBox, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
							//
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)) //changes panel
                        .addComponent(jScrollPane1)
                        .addComponent(jScrollPane2))
                    .addComponent(senderTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(150, Short.MAX_VALUE)) //right edge
        );
		//VERTICAL
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
						//
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE) //SendBox
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IPAddressBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				//
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE) //ReceiveBox
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(59, 59, 59)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(senderTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    //when the send button is clicked, create sender and receiver threads
    //and sends or receives a message
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        
        //create a sender and receiver object
        UDPSender sender = new UDPSender();
        UDPReceiver receiver = new UDPReceiver();
        
        //set the message and destination for the sent message
        sender.message = messageTextBox.getText();
        sender.hostname = IPAddressBox.getText();
        
        //start the sender and receiver threads
        //this reciever is used to update the messages received after sending
        sender.start();
        receiver.start();

    }
    
    //writes the incoming message to a file
    public static void writeRecievedToFile(String message){
        try {
            String temp = message;
            File file = new File("received.txt");
            if (!file.exists()){
                file.createNewFile();
            }
            
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(temp);
            bw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
    }
    
    //writes the outgoing message to a file
    public static void writeSentToFile(String message) {
          try {
            String temp = message;
            File file = new File("sent.txt");
            if (!file.exists()){
                file.createNewFile();
            }
            
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(temp);
            bw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        
    }   
    
    //this is the main of the program that creates a GUI and base receiver thread
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        //initialize the GUI
        GUIBox temp = new GUIBox();
        temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        temp.setVisible(true);
        
        //create a receiver thread so you can always recieve messages
        //without having to first send a message
        UDPReceiver receiver = new UDPReceiver();
        receiver.start();
		
		
		
    }

    private javax.swing.JTextField IPAddressBox;
    public static javax.swing.JTextArea incomingTextBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea messageTextBox;
    public static javax.swing.JTextField senderTextBox;
}