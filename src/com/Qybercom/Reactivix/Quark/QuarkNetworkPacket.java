package com.Qybercom.Reactivix.Quark;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Class QuarkNetworkPacket
 */
public class QuarkNetworkPacket {
	@Expose(serialize = true, deserialize = false)
	public String url;

	@Expose(serialize = false, deserialize = true)
	public String response;

	@Expose(serialize = false, deserialize = true)
	public String event;

	@Expose(serialize = true, deserialize = true)
	public Object data;

	@Expose(serialize = true, deserialize = true)
	public HashMap<String, String> session;

	@Expose(serialize = false, deserialize = false)
	private Gson _serializer;

	@Expose(serialize = false, deserialize = false)
	private Type _typeData;

	/**
	 * @return String
	 */
	public String URL () { return url; }

	/**
	 * @return String
	 */
	public String Response () { return response == null ? "" : response; }

	/**
	 * @return String
	 */
	public String Event () { return event == null ? "" : event; }

	/**
	 * @return Object
	 */
	public Object Data () { return data; }

	/**
	 * @return Type
	 */
	public Type TypeData () { return _typeData; }

	/**
	 * @return Gson
	 */
	public Gson Serializer () { return _serializer; }

	/**
	 * Default constructor of QuarkNetworkPacket
	 */
	public QuarkNetworkPacket () {
		_serializer = new Gson();
	}

	/**
	 * @param url Target endpoint
	 * @param data Payload DTO
	 */
	public QuarkNetworkPacket (String url, Object data) {
		_serializer = new Gson();

		this.url = url;
		this.data = data;
	}

	/**
	 * @param typeData Serialization notes
	 *
	 * @return QuarkNetworkPacket
	 */
	public QuarkNetworkPacket Trigger (Type typeData) {
		_typeData = typeData;

		if (_typeData != null)
			data = _serializer.fromJson(Payload(), _typeData);

		return this;
	}

	/**
	 * @return String
	 */
	public String Payload () {
		return _serializer.toJson(data);
	}
}