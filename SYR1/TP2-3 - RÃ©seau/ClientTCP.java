import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;


public class ClientTCP {
	
	private static final int PORT = 1032;
	
	public static void main(String[] args) throws Exception {	
		//Open the socket (create the connection at the same time)
		Socket socket = new Socket("localhost", PORT);

		//Read the message so send
		System.out.println("Veuillez entrer un message:");
		Scanner scan = new Scanner(System.in);
		String text = scan.nextLine();
		System.out.println("Texte entré: " + text);

		//The output stream send to the server
		DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

		//The input stream read from the server
		BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		//Send the message to the server
		outputStream.writeBytes(text + '\n');

		//Display what is received from the server
		System.out.println("FROM SERVER: " + inputBuffer.readLine());

		//Then close the socket
		socket.close();
	}
}
