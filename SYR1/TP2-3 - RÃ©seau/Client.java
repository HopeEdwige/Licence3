import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class Client {
	
	public static void main(String[] args) throws Exception {

		//Open the socket
		DatagramSocket socket = new DatagramSocket();

		//Read the message so send
		System.out.println("Veuillez entrer un message:");
		Scanner scan = new Scanner(System.in);
		String text = scan.next();
		byte[] buf = new byte[1024];
		buf = text.getBytes();

		//The host
		InetAddress serverHost = InetAddress.getByName("localhost");

		//Then create and send the response
		DatagramPacket message = new DatagramPacket(buf, buf.length, serverHost, 1025);
		socket.send(message);

		//And receive the response
		DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
		socket.receive(response);

		//And display it
		printOnClient(response.getData());
		
		//Then close the socket
		socket.close();
	}

	private static void printOnClient(byte[] buf) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(buf);
		InputStreamReader isr = new InputStreamReader(bais);
		BufferedReader br = new BufferedReader(isr);
		String line = br.readLine();
		System.out.println("Reçu sur le client: " + new String(line));
	}
}
