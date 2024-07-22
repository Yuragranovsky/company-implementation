package telran.net;

import java.net.*;
import java.time.Duration;
import java.time.Instant;
import java.io.*;
import java.util.*;

import org.json.JSONObject;

public class TcpClient implements Closeable {
	Socket socket;
	PrintStream sender;
	BufferedReader receiver;
	String host;
	int port;
	int nAttempts;
	int interval;

	public TcpClient(String host, int port, int nAttempts, int interval) {
		this.host = host;
		this.port = port;
		this.nAttempts = nAttempts;
		this.interval = interval;
		connect();
	}

	private void connect() {
		int countAttempts = nAttempts;
		do {
			try {
				socket = null;
				socket = new Socket(host, port);
				sender = new PrintStream(socket.getOutputStream());
				receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				countAttempts = 0;
			} catch (IOException e) {
				waitForInterval();
				countAttempts--;
			}
		} while (countAttempts != 0);

	}

	private void waitForInterval() {
		Instant endInterval = Instant.now().plusMillis(interval);
		while (Instant.now().isBefore(endInterval)) {

		}

	}

	@Override
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public String sendAndGet(String requestType, String requestData) {
		try {
			if (socket == null) {
				throw new IllegalStateException(String.format("server  %s on port %d ia unavailable", host, port));
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("requestType", requestType);
			jsonObject.put("requestData", requestData);

			sender.println(jsonObject.toString());
			String responseJSON = receiver.readLine();
			JSONObject responseJSONObj = new JSONObject(responseJSON);
			ResponseCode responseCode = responseJSONObj.getEnum(ResponseCode.class, "responseCode");
			String responseData = responseJSONObj.getString("responseData");
			if(responseCode != ResponseCode.OK) {
				throw new RuntimeException(responseData);
			}
			return responseData;
		} catch (IOException e) {
			throw new IllegalStateException(String.format("server  %s on port %d ia unavailable", host, port));
		}

	}

}
