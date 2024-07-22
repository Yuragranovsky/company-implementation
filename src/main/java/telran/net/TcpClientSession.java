package telran.net;
import java.net.*;

import lombok.RequiredArgsConstructor;

import java.io.*;
@RequiredArgsConstructor
public class TcpClientSession {
final Socket socket;
final Protocol protocol;

public void run() {
	try(BufferedReader reader =
			new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream writer = new PrintStream(socket.getOutputStream())) {
		String line = null;
		while((line = reader.readLine()) != null) {
			String response = protocol.getResponseWithJSON(line);
			writer.println(response);
		}
		
	}catch(EOFException e) {
		System.out.println("client closed connection");
	}
	catch (IOException e) {
		System.out.println(e);
		
	}
}

}
