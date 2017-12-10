package com.Qybercom.Reactivix.Quark;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.Qybercom.Reactivix.Network.IReactivixNetworkTransport;
import com.Qybercom.Reactivix.Network.ReactivixNetworkSocket;
import com.Qybercom.Reactivix.Network.Events.ReactivixNetworkSocketEventClose;
import com.Qybercom.Reactivix.Network.Events.ReactivixNetworkSocketEventConnect;
import com.Qybercom.Reactivix.Network.Events.ReactivixNetworkSocketEventData;
import com.Qybercom.Reactivix.Network.Events.ReactivixNetworkSocketEventError;

import com.Qybercom.Reactivix.Utils.Action;
import com.Qybercom.Reactivix.Utils.ReactivixEvent;

import com.Qybercom.Reactivix.Quark.Stream.IQuarkNetworkStreamEvent;
import com.Qybercom.Reactivix.Quark.Stream.IQuarkNetworkStreamGeneric;
import com.Qybercom.Reactivix.Quark.Stream.IQuarkNetworkStreamResponse;
import com.Qybercom.Reactivix.Quark.Events.QuarkNetworkClientEventClose;
import com.Qybercom.Reactivix.Quark.Events.QuarkNetworkClientEventConnect;
import com.Qybercom.Reactivix.Quark.Events.QuarkNetworkClientEventError;

/**
 * Class QuarkNetworkClient
 */
public class QuarkNetworkClient {
	private ReactivixNetworkSocket _socket;
	public ReactivixNetworkSocket Socket () { return _socket; }


	private ReactivixEvent<QuarkNetworkClientEventConnect> _eventConnect;
	public ReactivixEvent<QuarkNetworkClientEventConnect> EventConnect () { return _eventConnect; }

	private ReactivixEvent<QuarkNetworkClientEventClose> _eventClose;
	public ReactivixEvent<QuarkNetworkClientEventClose> EventClose () { return _eventClose; }

	private ReactivixEvent<QuarkNetworkClientEventError> _eventError;
	public ReactivixEvent<QuarkNetworkClientEventError> EventError () { return _eventError; }


	private class Callback {
		private String _url;
		private Action<QuarkNetworkPacket> _worker;
		private Type _typeData;

		private Callback (String url, Action<QuarkNetworkPacket> worker, Type typeData) {
			_url = url;
			_worker = worker;
			_typeData = typeData;
		}

		private String URL () { return _url; }
		private Action<QuarkNetworkPacket> Worker () { return _worker; }
		private Type TypeData () {return _typeData; }
	}

	private List<Callback> _responses;
	private List<Callback> _events;

	private Gson _serializer;
	public Gson Serializer () { return _serializer; }


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


	public boolean Connect () {
		return _socket.Connect();
	}

	public void Pipe () {
		_socket.Pipe();
	}

	public boolean Close () {
		return _socket.Close();
	}


	private void _subscribe (IQuarkNetworkPacketData data, List<Callback> callbacks, String url, Action<QuarkNetworkPacket> callback) {
		callbacks.add(new Callback(url, callback, data.getClass()));
	}

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
	 * @param url URL of called service
	 * @param data Payload DTO
	 *
	 * @return boolean
	 */
	public boolean Service (String url, IQuarkNetworkPacketData data) {
		return _socket.Send(_serializer.toJson(new QuarkNetworkPacket(url, data)));
	}

	public void Response (IQuarkNetworkPacketData data, String url, Action<QuarkNetworkPacket> callback) {
		_subscribe(data, _responses, url, callback);
	}

	public void Event (IQuarkNetworkPacketData data, String url, Action<QuarkNetworkPacket> callback) {
		_subscribe(data, _events, url, callback);
	}


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