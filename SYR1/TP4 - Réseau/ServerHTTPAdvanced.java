import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerHTTPAdvanced {

	//The path to the directory
	private static final String DIR = "/var/www";  //Without / at the end please

	//The port
	private static final int PORT = 1042;


	public static void main(String[] args) throws Exception {

		//Open the server's socket
		ServerSocket ownSocket = new ServerSocket(PORT);

		while (true) {
			//Create the socket which will receive messages from the client
			Socket socket = ownSocket.accept();

			//Buffer to receive the client's messages
			BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			//Get the response from the request
			String response = getResponse(inputFromClient.readLine());

			//OS to respond the client
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());

			//Read the client's message, put it in upper case and send it back
			output.writeBytes(response);

			//Close the socket
			socket.close();
		}
	}


	/**
	 * Get the response to send to the client
	 * @param String request The request sent by the client
	 * @return String The message to respond to the client
	 */
	private static String getResponse(String request) {
		System.out.println("Request received: " + request);

		//Only if there's a request
		if (request != null) {
			String[] splitted = request.split(" ");

			//If correct beginning "GET HTTP/1.0" or "GET HTTP/1.1"
			if (splitted[0].equals("GET") && ((splitted[2].equals("HTTP/1.0") || (splitted[2].equals("HTTP/1.1"))))) {

				//Now try to read the file
				try {
					//Get the stream, read it and store it into a buffer
					InputStream ips = new FileInputStream(DIR + splitted[1]);
					InputStreamReader ipsr = new InputStreamReader(ips);
					BufferedReader br = new BufferedReader(ipsr);
					String line = br.readLine();
					String response = "";

					//While we can read it
					while (line != null) {
						response += line + "\n";
						line = br.readLine();
					}

					//Close the buffer after all is done
					br.close();

					//And in the end, return the response
					return response;
				}

				//If Exception during the reading
				catch (Exception e) {
					return "HTTP/1.0 404 NOT FOUND \n\n" + e.getMessage();
				}
			}
		}

		//If error during the process
		return "HTTP/1.0 404 NOT FOUND \n\n";
	}
}
