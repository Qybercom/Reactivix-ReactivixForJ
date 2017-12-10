package com.Qybercom.Reactivix.Network;

import com.Qybercom.Reactivix.Utils.ReactivixEvent;

import com.Qybercom.Reactivix.Network.Events.ReactivixNetworkSocketEventConnect;
import com.Qybercom.Reactivix.Network.Events.ReactivixNetworkSocketEventData;
import com.Qybercom.Reactivix.Network.Events.ReactivixNetworkSocketEventClose;
import com.Qybercom.Reactivix.Network.Events.ReactivixNetworkSocketEventError;

/**
 * Class ReactivixNetworkSocket
 */
public class ReactivixNetworkSocket {
	public IReactivixNetworkTransport Transport;

	public String Host;
	public int Port;


	private ReactivixEvent<ReactivixNetworkSocketEventConnect> _eventConnect;
	public ReactivixEvent<ReactivixNetworkSocketEventConnect> EventConnect () { return _eventConnect; }

	private ReactivixEvent<ReactivixNetworkSocketEventData> _eventData;
	public ReactivixEvent<ReactivixNetworkSocketEventData> EventData () { return _eventData; }

	private ReactivixEvent<ReactivixNetworkSocketEventClose> _eventClose;
	public ReactivixEvent<ReactivixNetworkSocketEventClose> EventClose () { return _eventClose; }

	private ReactivixEvent<ReactivixNetworkSocketEventError> _eventError;
	public ReactivixEvent<ReactivixNetworkSocketEventError> EventError () { return _eventError; }


	public ReactivixNetworkSocket (IReactivixNetworkTransport transport, String host, int port) {
		Transport = transport;

		Host = host;
		Port = port;

		_eventConnect = new ReactivixEvent<>();
		_eventData = new ReactivixEvent<>();
		_eventClose = new ReactivixEvent<>();
		_eventError = new ReactivixEvent<>();
	}

	public void Pipe () {
		try {
			if (Transport.Connected()) {
				String data = Transport.Receive();
				if (data != null)
					_eventData.Trigger(new ReactivixNetworkSocketEventData(this, data));
			}
		}
		catch (Exception e) {
			_eventError.Trigger(new ReactivixNetworkSocketEventError(this, e));
		}
	}

	public boolean Connect () {
		try {
			boolean connect = Transport.Connect(Host, Port);

			if (connect)
				_eventConnect.Trigger(new ReactivixNetworkSocketEventConnect(this));

			return connect;
		}
		catch (Exception e) {
			_eventError.Trigger(new ReactivixNetworkSocketEventError(this, e));
			return false;
		}
	}

	public boolean Send (String data) {
		try {
			return Transport.Send(data);
		}
		catch (Exception e) {
			_eventError.Trigger(new ReactivixNetworkSocketEventError(this, e));

			return false;
		}
	}

	public boolean Close () {
		try {
			boolean close = Transport.Connect(Host, Port);

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