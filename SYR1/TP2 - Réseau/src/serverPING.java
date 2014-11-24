import java.io.*;
import java.net.*;
import java.util.*;
/*
 * Le code du serveur répondant aux ping sur UDP.
 */
public class serverPING
{
	private static final double LOSS_RATE = 0.3;
	public static void main(String[] args) throws Exception
	{
		if (args.length != 1) {
			System.out.println("Required arguments: port");
			return;
		}
		int port = Integer.parseInt(args[0]);
		//	int port = 7775;
		// récuperer un nombre aléatoire pour simuler une perte de paquets 

		Random random = new Random();
		DatagramSocket socket = new DatagramSocket(port);
		// La boucle d'ecoute
		while (true) {
			DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
			socket.receive(request);
			printData(request);
			if (random.nextDouble() < LOSS_RATE) {
				System.out.println(" Reply not sent.");
				continue;
			}
			InetAddress clientHost = request.getAddress();
			int clientPort = request.getPort();
			byte[] buf = request.getData();
			DatagramPacket reply = new DatagramPacket(buf, buf.length,
					clientHost, clientPort);
			socket.send(reply);
			System.out.println(" Reply sent.");
		}
	}
	private static void printData(DatagramPacket request) throws Exception
	{
		byte[] buf = request.getData();
		ByteArrayInputStream bais = new ByteArrayInputStream(buf);
		InputStreamReader isr = new InputStreamReader(bais);
		BufferedReader br = new BufferedReader(isr);
		String line = br.readLine();
		System.out.println(
				"Received from " +
						request.getAddress().getHostAddress() +
						": " +
						new String(line) );
	}
}