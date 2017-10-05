

//The UDP Sender
import java.net.*;
import java.io.*;
import java.util.*;


public class UDPSender extends Thread{
    
    //variables for sending a specific message to a specific IP address
    public String hostname="localhost";    
    public String message = "";
    
    //empty constructor used to create a sender object
    public UDPSender() {
        
    }
	
	 //override the run method with the given UDPSender code
    @Override
    public void run () {
		try {
		   // Create a datagram socket, look for the first available port
		   DatagramSocket socket = new DatagramSocket();

		   System.out.println ("Using local port: " + socket.getLocalPort());
                           ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                           PrintStream pOut = new PrintStream(bOut);
                           pOut.print(message);
                           //convert printstream to byte array
                           byte [ ] bArray = bOut.toByteArray();
		   // Create a datagram packet, containing a maximum buffer of 256 bytes
		   DatagramPacket packet=new DatagramPacket( bArray, bArray.length );

                        System.out.println("Looking for hostname " + hostname);
		//get the InetAddress object
                        InetAddress remote_addr = InetAddress.getByName(hostname);
                        //check its IP number
                        System.out.println("Hostname has IP address = " + 
remote_addr.getHostAddress());
                        //configure the DataGramPacket
                        packet.setAddress(remote_addr);
                        packet.setPort(2000);
                        //send the packet
                        socket.send(packet);
		System.out.println ("Packet sent at!" + new Date());

		// Display packet information
		System.out.println ("Sent by  : " + remote_addr.getHostAddress() );
        		System.out.println ("Send from: " + packet.getPort());

		}
                catch (UnknownHostException ue){
                        System.out.println("Unknown host "+hostname);
                }
		catch (IOException e)	{
			System.out.println ("Error - " + e);
		}
		
		//puts message in file
		GUIBox.writeSentToFile(message);

	}//end of main
}//end of class definition