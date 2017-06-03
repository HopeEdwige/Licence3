import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ClientPing {

	private static final int NB_PAQUETS = 10;

	public static void main(String[] args) throws Exception {

		//Open the socket
		DatagramSocket socket = new DatagramSocket();

		//For the statistics
		long max = 0;
		long min = 0;
		long[] calculAverage = new long[NB_PAQUETS];
		int nb_receive = 0;

		//Read the message to send
		for (int i = 0; i < NB_PAQUETS; i++) {
			//Create the message
			//Date d = new Date();
			long d = System.currentTimeMillis();
			String text = "PING " + i + " " + d + " \n";

			//Parse it into a byte table
			byte[] buf = new byte[1024];
			buf = text.getBytes();

			//The host
			InetAddress serverHost = InetAddress.getByName("localhost");

			//Display the first line
			if (i == 0)
				System.out.println("Paquet re�u de " + serverHost.getHostAddress() + ", pour la requ�te num�ro :");

			//Display each line
			System.out.print(i + ", RTT : ");

			//Try to send it but with a timeout of 1sec
			socket.setSoTimeout(1000);
			try {
				//Then create and send the response
				DatagramPacket message = new DatagramPacket(buf, buf.length, serverHost, 1035);
				socket.send(message);

				//And receive the response
				DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
				socket.receive(response);

				//Split the response
				ByteArrayInputStream bais = new ByteArrayInputStream(response.getData());
				InputStreamReader isr = new InputStreamReader(bais);
				BufferedReader br = new BufferedReader(isr);
				String line = br.readLine();
				String[] splitted = line.split(" ");

				//Calcul the dates
				/*@SuppressWarnings("deprecation")
				Date respDate = new Date(splitted[2]);
				Date now = new Date();
				int rtt = now.compareTo(respDate);*/

				long respTime = Long.parseLong(splitted[2]);
				long result = System.currentTimeMillis() - respTime;

				//Some calculs
				if (result > max)
					max = result;

				if (result < min)
					min = result;

				calculAverage[nb_receive] = result;
				nb_receive++;

				System.out.println(result + " ms");
			}
			catch (Exception e) {
				System.out.println("Timeout atteint.");
			}
		}

		//Display the stats
		long average = 0;
		for (int i = 0; i < nb_receive; i++)
			average += calculAverage[i];

		average = average / nb_receive;
		System.out.println("Statistiques: Max = " + max + "ms, Min = " + min + "ms, Moyenne = " + average + "ms, Paquets re�us = " + nb_receive + " paquets, Paquets perdus = " + (NB_PAQUETS-nb_receive) + " paquets.");

		//Then close the socket
		socket.close();
	}
}
