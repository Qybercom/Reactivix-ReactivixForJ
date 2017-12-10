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

	public QuarkNetworkPacket () {
		_serializer = new Gson();
	}

	public QuarkNetworkPacket (String url, Object data) {
		_serializer = new Gson();

		this.url = url;
		this.data = data;
	}

	public Gson Serializer () { return _serializer; }
	public Type TypeData () { return _typeData; }
	public String URL () { return url; }
	public String Response () { return response == null ? "" : response; }
	public String Event () { return event == null ? "" : event; }
	public Object Data () { return data; }

	public QuarkNetworkPacket Trigger (Type typeData) {
		_typeData = typeData;
		data = _serializer.fromJson(Payload(), _typeData);

		return this;
	}

	public String Payload () {
		return _serializer.toJson(data);
	}
}