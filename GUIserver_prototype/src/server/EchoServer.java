// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.net.InetAddress;
import java.net.UnknownHostException;

import common.ServerMessage;
import gui.ClientConnectedController;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  public static EchoServer instance;
  private static ClientConnectedController ccc;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) {
    super(port);
	InstanceManager.setInstance(this);
	InstanceManager.setClientConnectedController(ccc);
  }
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
	
	
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("Message received: " + msg + " from " + client);
    // ccc = InstanceManager.getClientConnectedController();
    
		  if (msg instanceof ServerMessage) {
        ServerMessage serverMessage = (ServerMessage) msg;
        String type = serverMessage.getType();
        Object data = serverMessage.getData();
	
        try {
          switch (type) {
            case "updateSubscriber": {
              System.out.println("updateSubscriber");
              Logic.updateSubscriberDetails(client);
              break;
            } case "showSubscribersTable": {
              System.out.println("showSubscribersTable");
              Logic.showSubscribersTable(client);
              break;
            } case "sendSubscriber": {
              System.out.println("sendSubscriber");
              Logic.specificSubscriber(Integer.parseInt(data.toString()), client);
              break;
            } case "sendSearchedBooks": {
              String searchCriteria = (String) data;
              String[] parts = searchCriteria.split(":", 2);
              String searchType = parts[0];
              String searchText = parts[1];
              System.out.println("sendSearchedBooks");
              Logic.sendSearchedBooks(searchType, searchText, client);
              break;
            } case "newBorrow": {
              System.out.println("newBorrow");
              Logic.addBorrow(client);
              break;
            } case "connect": {
              Logic.connect(client);
              break;
            } case "disconnect": {
              Logic.disconnect(client);
              break;
            } default: {
              System.out.println("Invalid command");
              MessageUtils.sendResponseToClient("Error", "Invalid command", client);
              break;
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("Unknown message type: " + msg.getClass().getName());
      }
	}



  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
	    try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ipAddress = inetAddress.getHostAddress();
			System.out.println("Server IP address: " + ipAddress);
            System.out.println("Server listening for connections on port " + getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println("Server has stopped listening for connections.");
  }
}
//End of EchoServer class
