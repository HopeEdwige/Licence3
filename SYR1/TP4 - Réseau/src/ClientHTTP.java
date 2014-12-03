import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;


public class ClientHTTP {
	
	//HTTP port
	private static final int PORT = 1042;
	
	public static void main(String[] args) throws Exception {

		//Read the message so send
		System.out.println("Veuillez entrer une url:");
		Scanner scan = new Scanner(System.in);
		String text = scan.nextLine();
		//System.out.println("URL entré: " + text);
		
		//Get the host and the file requested
		String[] splitted = text.split("/");
		String host = splitted[0];
		String requested = "";
		for (int i = 1; i < splitted.length; i++) {
			requested += "/" + splitted[i];
		}

		//Open the socket (create the connection at the same time)
		Socket socket = new Socket(host, PORT);

		//The output stream send to the server
		DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

		//The input stream read from the server
		BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		//Create the request
		String request = "GET ";
		if (requested == "")
			requested = "/";

		request += requested + " HTTP/1.0 \n\n";

		//Send the request to the server
		outputStream.writeBytes(request);

		//Display what is received from the server
		String line = inputBuffer.readLine();
		while (line != null) {
			System.out.println(line);
			line = inputBuffer.readLine();
		}

		//Then close the socket
		socket.close();
	}
}
