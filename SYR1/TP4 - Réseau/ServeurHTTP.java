import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;


public class ServeurHTTP {

	private static final int PORT = 1042;

	public static void main(String[] args) throws Exception {

		//Open the server's socket
		ServerSocket ownSocket = new ServerSocket(PORT);

		while (true) {
			//Create the socket which will receive messages from the client
			Socket socket = ownSocket.accept();

			//Buffer to receive the client's messages
			BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			//Get the request
			String lineRead = inputFromClient.readLine();
			System.out.println("Request received: " + lineRead);
			String[] splitted = null;
			if (lineRead != null)
				splitted = lineRead.split(" ");

			//OS to respond the client
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());

			//The response
			String response;
			if ((splitted != null) && (splitted[0].equals("GET"))) {
				response = "HTTP/1.0 200 OK \n\n" + "<html>Hello world!";

				//Date
				Date now = new Date(System.currentTimeMillis());
				response += "<p>On est exactement le <strong>" + now.toString() + "</strong></p>";

				//Informations about the server
				response += "<h1>Server</h1><p>Name: " + System.getProperty("java.vm.name") + "</p>";
				response += "<p>Operating System: " + System.getProperty("os.name") + "</p>";
				response += "<p>Java version: " + System.getProperty("java.vm.version") + "</p>";

				//Informations about the client
				InetAddress ipClient = socket.getInetAddress();
				response += "<h1>Client</h1><p>IP: " + ipClient.getHostAddress() + "</p>";
				response += "<p>Requested file: " + splitted[1] + "</p>";
				
				//Close the html
				response += "</html>";
			}
			else {
				response = "HTTP/1.0 404 NOT FOUND \n\n";
			}

			//Read the client's message, put it in upper case and send it back
			output.writeBytes(response);

			//Close the socket
			socket.close();
		}
	}
}