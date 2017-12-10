package com.Qybercom.Reactivix.Network;

import java.io.IOException;

/**
 * Interface IReactivixNetworkTransport
 */
public interface IReactivixNetworkTransport {
	/**
	 * @return boolean
	 */
	boolean Connected();

	/**
	 * @param host Server hostname or IP address
	 * @param port Server port
	 *
	 * @return boolean
	 */
	boolean Connect(String host, int port) throws IOException;

	/**
	 * @param data Data for transmitting
	 *
	 * @return boolean
	 */
	boolean Send(String data) throws IOException;

	/**
	 * @return String
	 */
	String Receive() throws IOException;

	/**
	 * @return boolean
	 */
	boolean Close() throws IOException;
}