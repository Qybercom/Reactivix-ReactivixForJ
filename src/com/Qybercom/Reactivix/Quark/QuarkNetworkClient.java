package com.Qybercom.Reactivix.Quark;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.Qybercom.Reactivix.Network.IReactivixNetworkTransport;
import com.Qybercom.Reactivix.Network.ReactivixNetworkSocket;
import com.Qybercom.Reactivix.Network.Events.*;

import com.Qybercom.Reactivix.Utils.*;

import com.Qybercom.Reactivix.Quark.Stream.*;
import com.Qybercom.Reactivix.Quark.Events.*;

/**
 * Class QuarkNetworkClient
 */
public class QuarkNetworkClient {
	/**
	 * @return ReactivixNetworkSocket
	 */
	public ReactivixNetworkSocket Socket () { return _socket; }
	private ReactivixNetworkSocket _socket;


	/**
	 * @return ReactivixEvent<QuarkNetworkClientEventConnect>
	 */
	public ReactivixEvent<QuarkNetworkClientEventConnect> EventConnect () { return _eventConnect; }
	private ReactivixEvent<QuarkNetworkClientEventConnect> _eventConnect;

	/**
	 * @return ReactivixEvent<QuarkNetworkClientEventClose>
	 */
	public ReactivixEvent<QuarkNetworkClientEventClose> EventClose () { return _eventClose; }
	private ReactivixEvent<QuarkNetworkClientEventClose> _eventClose;

	/**
	 * @return ReactivixEvent<QuarkNetworkClientEventError>
	 */
	public ReactivixEvent<QuarkNetworkClientEventError> EventError () { return _eventError; }
	private ReactivixEvent<QuarkNetworkClientEventError> _eventError;


	/**
	 * Class Callback
	 */
	private class Callback {
		private String _url;
		private Action<QuarkNetworkPacket> _worker;
		private Type _typeData;

		/**
		 * @param url Endpoint
		 * @param worker Callback for incoming packet
		 * @param typeData Serialization notes
		 */
		private Callback (String url, Action<QuarkNetworkPacket> worker, Type typeData) {
			_url = url;
			_worker = worker;
			_typeData = typeData;
		}

		/**
		 * @return String
		 */
		private String URL () { return _url; }

		/**
		 * @return Action<QuarkNetworkPacket>
		 */
		private Action<QuarkNetworkPacket> Worker () { return _worker; }

		/**
		 * @return Type
		 */
		private Type TypeData () { return _typeData; }
	}

	private List<Callback> _responses;
	private List<Callback> _events;

	/**
	 * @return Gson
	 */
	public Gson Serializer () { return _serializer; }
	private Gson _serializer;

	/**
	 * @param transport Selected low-level protocol
	 * @param host Server hostname or IP address
	 * @param port Server port
	 */
	public QuarkNetworkClient (IReactivixNetworkTransport transport, String host, int port) {
		_responses = new ArrayList<>();
		_events = new ArrayList<>();

		_socket = new ReactivixNetworkSocket(transport, host, port);

		_eventConnect = new ReactivixEvent<>();
		_eventClose = new ReactivixEvent<>();
		_eventError = new ReactivixEvent<>();

		_socket.EventConnect().On((ReactivixNetworkSocketEventConnect e) -> {
			_eventConnect.Trigger(new QuarkNetworkClientEventConnect(this));
			return null;
		});

		_socket.EventData().On((ReactivixNetworkSocketEventData e) -> {
			String data = e.Data();

			if (!data.isEmpty()) {
				String[] queue = data.replaceAll("}\\{", "}}{{").split("}\\{");
				int i = 0;

				while (i < queue.length) {
					QuarkNetworkPacket packet = _serializer.fromJson(queue[i].trim(), QuarkNetworkPacket.class);

					if (packet != null) {
						if (!packet.Response().isEmpty()) _trigger(_responses, packet, packet.Response());
						if (!packet.Event().isEmpty()) _trigger(_events, packet, packet.Event());
					}

					i++;
				}
			}

			return null;
		});

		_socket.EventClose().On((ReactivixNetworkSocketEventClose e) -> {
			_eventClose.Trigger(new QuarkNetworkClientEventClose(this));
			return null;
		});

		_socket.EventError().On((ReactivixNetworkSocketEventError e) -> {
			_eventError.Trigger(new QuarkNetworkClientEventError(this, e.Exception()));
			return null;
		});

		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		_serializer = builder.create();
	}


	/**
	 * @return boolean
	 */
	public boolean Connect () {
		return _socket.Connect();
	}

	/**
	 * Performing listening for incoming data
	 */
	public void Pipe () {
		_socket.Pipe();
	}

	/**
	 * @return boolean
	 */
	public boolean Close () {
		return _socket.Close();
	}


	/**
	 * @param data Sample of DTO
	 * @param callbacks Collection of target callbacks
	 * @param url Target endpoint
	 * @param callback Subscribed callback
	 */
	private void _subscribe (IQuarkNetworkPacketData data, List<Callback> callbacks, String url, Action<QuarkNetworkPacket> callback) {
		callbacks.add(new Callback(url, callback, data == null ? null : data.getClass()));
	}

	/**
	 * @param callbacks Collection of target callbacks
	 * @param packet Ready for using DTO
	 * @param url Target endpoint
	 */
	private void _trigger (List<Callback> callbacks, QuarkNetworkPacket packet, String url) {
		int i = 0;
		int size = callbacks.size();

		while (i < size) {
			Callback callback = callbacks.get(i);

			if (callback.URL().equals(url))
				callback.Worker().apply(packet.Trigger(callback.TypeData()));

			i++;
		}
	}

	/**
	 * @param url Target endpoint
	 * @param data Payload DTO
	 *
	 * @return boolean
	 */
	public boolean Service (String url, IQuarkNetworkPacketData data) {
		return _socket.Send(_serializer.toJson(new QuarkNetworkPacket(url, data)));
	}

	/**
	 * @param data Sample of DTO
	 * @param url Target endpoint
	 * @param callback Subscribed callback
	 */
	public void Response (IQuarkNetworkPacketData data, String url, Action<QuarkNetworkPacket> callback) {
		_subscribe(data, _responses, url, callback);
	}

	/**
	 * @param data Sample of DTO
	 * @param url Target endpoint
	 * @param callback Subscribed callback
	 */
	public void Event (IQuarkNetworkPacketData data, String url, Action<QuarkNetworkPacket> callback) {
		_subscribe(data, _events, url, callback);
	}


	/**
	 * @param stream Dedicated handler for stream
	 */
	public void Stream (IQuarkNetworkStreamGeneric stream) {
		if (stream instanceof IQuarkNetworkStreamResponse) {
			IQuarkNetworkStreamResponse _stream = (IQuarkNetworkStreamResponse)stream;
			Response(_stream.StreamResponseDTO(), stream.URL(), (QuarkNetworkPacket packet) -> {
				_stream.StreamResponse(this, packet);
				return null;
			});
		}

		if (stream instanceof IQuarkNetworkStreamEvent) {
			IQuarkNetworkStreamEvent _stream = (IQuarkNetworkStreamEvent)stream;
			Event(_stream.StreamEventDTO(), stream.URL(), (QuarkNetworkPacket packet) -> {
				_stream.StreamEvent(this, packet);
				return null;
			});
		}
	}
}