package com.Qybercom.Reactivix.Network.Transports;

import com.Qybercom.Reactivix.Network.IReactivixNetworkTransport;

import java.io.*;
import java.net.Socket;
import java.net.InetSocketAddress;

/**
 * Class ReactivixNetworkTransportTCP
 *
 * https://habrahabr.ru/post/330676/
 * http://www.quizful.net/post/java-socket-programming
 *
 * http://www.codenet.ru/webmast/java/sockets.php
 * http://www.dokwork.ru/2012/02/http-java-tcp.html
 * https://systembash.com/a-simple-java-udp-server-and-udp-client/
 * https://docs.oracle.com/javase/7/docs/api/java/net/InetSocketAddress.html
 * https://stackoverflow.com/questions/3895461/java-non-blocking-socket
 * http://www.java2s.com/Tutorials/Java/Java_Network/0070__Java_Network_Non-Blocking_Socket.htm
 * https://stackoverflow.com/questions/3551337/java-sockets-nonblocking-read
 * https://stackoverflow.com/questions/1698654/how-to-make-an-accepted-socket-non-blocking-in-java
 * http://www.studytrails.com/java-io/non-blocking-io-multiplexing/
 * http://www.cyberforum.ru/java-networks/thread500470.html
 * https://stackoverflow.com/questions/38939111/android-socket-getchannel-method-always-returns-null-in-javamail
 * https://coderanch.com/t/444042/java/Null-Pointer-Exception-SocketChannel
 * https://coderanch.com/t/443992/java/tranfering-big-files-network
 * https://stackoverflow.com/questions/5984432/understanding-cause-of-nullpointerexception-in-java-nio-socket-channel
 *
 * https://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html
 * https://stackoverflow.com/questions/5680259/using-sockets-to-send-and-receive-data
 *
 * https://stackoverflow.com/questions/30395281/is-there-a-equivalent-to-json-net-in-java
 * http://blog.harrix.org/article/7348
 * http://blog.harrix.org/article/7231
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
		if (_socket == null) return false;

		_writer.write(data);
		_writer.flush();

		return true;
	}

	/**
	 * @return String
	 */
	public String Receive () throws IOException {
		if (_socket == null) return null;

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
		if (_socket == null) return false;

		_socket.close();

		return true;
	}
}