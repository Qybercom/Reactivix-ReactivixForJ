package com.Qybercom.Reactivix.Network.Events;

import com.Qybercom.Reactivix.Utils.IReactivixEvent;

import com.Qybercom.Reactivix.Network.ReactivixNetworkSocket;

/**
 * Class ReactivixNetworkSocketEventError
 */
public class ReactivixNetworkSocketEventError implements IReactivixEvent {
	private ReactivixNetworkSocket _socket;
	private Exception _e;

	/**
	 * @param socket Transmitted ReactivixNetworkSocket actor
	 * @param e Occurred exception
	 */
	public ReactivixNetworkSocketEventError (ReactivixNetworkSocket socket, Exception e) {
		_socket = socket;
		_e = e;
	}

	/**
	 * @return ReactivixNetworkSocket
	 */
	public ReactivixNetworkSocket Socket () {
		return _socket;
	}

	/**
	 * @return Exception
	 */
	public Exception Exception () {
		return _e;
	}
}