import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class ServeurTCP {

	private static final int PORT = 1032;
	
	public static void main(String[] args) throws Exception {

		//Open the server's socket
		ServerSocket ownSocket = new ServerSocket(PORT);

		//Listening
		while (true) {
			
			//Create the socket which will receive messages from the client
			Socket socket = ownSocket.accept();

			//Buffer to receive the client's messages
			BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//OS to respond the client
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			
			//Get and display the line read
			String lineRead = inputFromClient.readLine();
			System.out.println("Message reçu: " + lineRead);

			//Read the client's message, put it in upper case and send it back
			output.writeBytes(lineRead.toUpperCase() + '\n');
		}
	}
}
