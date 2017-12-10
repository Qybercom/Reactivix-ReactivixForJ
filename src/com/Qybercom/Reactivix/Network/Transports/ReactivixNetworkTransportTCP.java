package com.Qybercom.Reactivix.Network.Transports;

import com.Qybercom.Reactivix.Network.IReactivixNetworkTransport;

import java.io.*;
import java.net.Socket;
import java.net.InetSocketAddress;

/**
 * Class ReactivixNetworkTransportTCP
 */
public class ReactivixNetworkTransportTCP implements IReactivixNetworkTransport {
	private Socket _socket;
	private BufferedReader _reader;
	private BufferedWriter _writer;

	/**
	 * Default constructor of ReactivixNetworkTransportTCP
	 */
	public ReactivixNetworkTransportTCP () {
		_socket = new Socket();
	}

	/**
	 * @return boolean
	 */
	public boolean Connected () {
		return _socket != null && _socket.isConnected();
	}

	/**
	 * @param host Server hostname or IP address
	 * @param port Server port
	 *
	 * @return boolean
	 */
	public boolean Connect (String host, int port) throws IOException {
		_socket.connect(new InetSocketAddress(host, port));

		_reader = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		_writer = new BufferedWriter(new OutputStreamWriter(_socket.getOutputStream()));

		return true;
	}

	/**
	 * @param data Data for transmitting
	 *
	 * @return boolean
	 */
	public boolean Send (String data) throws IOException {
		_writer.write(data);
		_writer.flush();

		return true;
	}

	/**
	 * @return String
	 */
	public String Receive () throws IOException {
		int available = _socket.getInputStream().available();

		if (available == 0) return null;

		char[] buffer = new char[available];
		//noinspection ResultOfMethodCallIgnored
		_reader.read(buffer, 0, available);

		return new String(buffer);
	}

	/**
	 * @return boolean
	 */
	public boolean Close () throws IOException {
		_socket.close();

		return true;
	}
}