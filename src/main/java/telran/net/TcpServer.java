package telran.net;
import java.net.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TcpServer {
	final int port;
	final Protocol protocol;
	public void run() {
		try(ServerSocket serverSocket = new ServerSocket(port) ;) {
			
			System.out.println("Server listening on the port " + port);
			while(true) {
				Socket socket = serverSocket.accept();
				TcpClientSession session = new TcpClientSession(socket, protocol);
				session.run();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
