package com.Qybercom.Reactivix.Network;

import com.Qybercom.Reactivix.Utils.ReactivixEvent;
import com.Qybercom.Reactivix.Network.Events.*;

/**
 * Class ReactivixNetworkSocket
 */
public class ReactivixNetworkSocket {
	private IReactivixNetworkTransport _transport;
	public IReactivixNetworkTransport getTransport () { return _transport; }
	public void setTransport (IReactivixNetworkTransport transport) { _transport = transport; }

	private String _host;
	public String getHost () { return _host; }
	public void setHost (String host) { _host = host; }

	private int _port;
	public int getPort () { return _port; }
	public void setPort (int port) { _port = port; }


	/**
	 * @return ReactivixEvent<ReactivixNetworkSocketEventConnect>
	 */
	public ReactivixEvent<ReactivixNetworkSocketEventConnect> EventConnect () { return _eventConnect; }
	private ReactivixEvent<ReactivixNetworkSocketEventConnect> _eventConnect;

	/**
	 * @return ReactivixEvent<ReactivixNetworkSocketEventData>
	 */
	public ReactivixEvent<ReactivixNetworkSocketEventData> EventData () { return _eventData; }
	private ReactivixEvent<ReactivixNetworkSocketEventData> _eventData;

	/**
	 * @return ReactivixEvent<ReactivixNetworkSocketEventClose>
	 */
	public ReactivixEvent<ReactivixNetworkSocketEventClose> EventClose () { return _eventClose; }
	private ReactivixEvent<ReactivixNetworkSocketEventClose> _eventClose;

	/**
	 * @return ReactivixEvent<ReactivixNetworkSocketEventError>
	 */
	public ReactivixEvent<ReactivixNetworkSocketEventError> EventError () { return _eventError; }
	private ReactivixEvent<ReactivixNetworkSocketEventError> _eventError;

	/**
	 * @param transport Selected low-level protocol
	 * @param host Server hostname or IP address
	 * @param port Server port
	 */
	public ReactivixNetworkSocket (IReactivixNetworkTransport transport, String host, int port) {
		setTransport(transport);

		setHost(host);
		setPort(port);

		_eventConnect = new ReactivixEvent<>();
		_eventData = new ReactivixEvent<>();
		_eventClose = new ReactivixEvent<>();
		_eventError = new ReactivixEvent<>();
	}

	/**
	 * Performing listening for incoming data
	 */
	public void Pipe () {
		try {
			if (_transport.Connected()) {
				String data = _transport.Receive();

				if (data != null)
					_eventData.Trigger(new ReactivixNetworkSocketEventData(this, data));
			}
		}
		catch (Exception e) {
			_eventError.Trigger(new ReactivixNetworkSocketEventError(this, e));
		}
	}

	/**
	 * @return boolean
	 */
	public boolean Connect () {
		try {
			boolean connect = _transport.Connect(_host, _port);

			if (connect)
				_eventConnect.Trigger(new ReactivixNetworkSocketEventConnect(this));

			return connect;
		}
		catch (Exception e) {
			_eventError.Trigger(new ReactivixNetworkSocketEventError(this, e));
			return false;
		}
	}

	/**
	 * @param data Data string
	 *
	 * @return boolean
	 */
	public boolean Send (String data) {
		try {
			return _transport.Send(data);
		}
		catch (Exception e) {
			_eventError.Trigger(new ReactivixNetworkSocketEventError(this, e));

			return false;
		}
	}

	/**
	 * @return boolean
	 */
	public boolean Close () {
		try {
			boolean close = _transport.Close();

			if (close)
				_eventClose.Trigger(new ReactivixNetworkSocketEventClose(this));

			return close;
		}
		catch (Exception e) {
			_eventError.Trigger(new ReactivixNetworkSocketEventError(this, e));

			return false;
		}
	}
}