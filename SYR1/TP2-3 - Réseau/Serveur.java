import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Serveur {

	private static final int PORT = 1025;

	public static void main(String[] args) throws Exception {

		//Open the socket
		DatagramSocket socket = new DatagramSocket(PORT);

		//Listening
		while (true) {
			//Get the paquet
			DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
			socket.receive(request);

			//Get informations about the client
			InetAddress clientHost = request.getAddress();
			int clientPort = request.getPort();

			//Get the datas
			byte[] buf = request.getData();

			//Print on the server
			printOnServer(buf);

			//Then create and send the response
			DatagramPacket reply = new DatagramPacket(buf, buf.length, clientHost, clientPort);
			socket.send(reply);
		}
	}

	private static void printOnServer(byte[] buf) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(buf);
		InputStreamReader isr = new InputStreamReader(bais);
		BufferedReader br = new BufferedReader(isr);
		String line = br.readLine();
		System.out.println("Re�u sur le serveur: " + new String(line));
	}
}
