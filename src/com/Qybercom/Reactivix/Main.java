package com.Qybercom.Reactivix;

import java.io.DataInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.Qybercom.Reactivix.Quark.Events.QuarkNetworkClientEventClose;
import com.Qybercom.Reactivix.Quark.Events.QuarkNetworkClientEventConnect;
import com.Qybercom.Reactivix.Quark.Events.QuarkNetworkClientEventError;
import com.Qybercom.Reactivix.Quark.IQuarkNetworkPacketData;
import com.Qybercom.Reactivix.Quark.QuarkNetworkClient;
import com.Qybercom.Reactivix.Quark.QuarkNetworkPacket;
import com.Qybercom.Reactivix.Thread.IReactivixThread;
import com.Qybercom.Reactivix.Thread.ReactivixThread;

import com.Qybercom.Reactivix.Network.ReactivixNetworkSocket;
import com.Qybercom.Reactivix.Network.Transports.ReactivixNetworkTransportTCP;
import com.Qybercom.Reactivix.Network.Events.ReactivixNetworkSocketEventConnect;
import com.Qybercom.Reactivix.Network.Events.ReactivixNetworkSocketEventData;
import com.Qybercom.Reactivix.Network.Events.ReactivixNetworkSocketEventClose;
import com.Qybercom.Reactivix.Network.Events.ReactivixNetworkSocketEventError;
import com.google.gson.annotations.Expose;

public class Main {
	public static ReactivixThread Thread;
	public static boolean run = true;

	public static void main1 (String[] args) {
		try {
			Socket socket = new Socket();

			socket.connect(new InetSocketAddress("127.0.0.1", 8500));
			//DataInputStream streamInput =

			boolean run = true;
			while (run) {

			}
		}
		catch (Exception e) {
			Log(e.getMessage());
		}
	}

    public static void main2 (String[] args) {
		Log("Init.Main");

		Thread = new ReactivixThread(new ClientThread());
		Thread.Start();
		Thread.Internal((IReactivixThread thread) -> {
			Log("internal");
			return null;
		});

		while (run) {
			Thread.Pipe();

			try {
				java.lang.Thread.sleep(10);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main (String[] args) {
		Log("Init.Main");

		Thread = new ReactivixThread(new ClientThread());
		Thread.Start();
		Thread.Internal((IReactivixThread thread) -> {
			Log("internal");
			return null;
		});

		while (run) {
			Thread.Pipe();

			try {
				java.lang.Thread.sleep(10);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void Log (String message) {
    	System.out.println("[" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(Calendar.getInstance().getTime()) + "] [thread: " + java.lang.Thread.currentThread().getId() + "] " + message);
	}
}

class MessageWelcome implements IQuarkNetworkPacketData {
	@Expose
	public int status;
	@Expose
	public String message;

	public MessageWelcome () { }
	public MessageWelcome (String message) {
		this.message = message;
	}
}

class TestResponseDTO implements IQuarkNetworkPacketData {
	public int status;
}

class TestEventDTO implements IQuarkNetworkPacketData {
	public String message;
}

class ClientThread implements IReactivixThread {
	public QuarkNetworkClient Client;

	public ClientThread () {
		Client = new QuarkNetworkClient(new ReactivixNetworkTransportTCP(), "127.0.0.1", 8500);
	}

	/**
	 * @param context The internal thread context
	 */
	public void ReactivixThreadStart (ReactivixThread context) {
		Main.Log("ClientThread.Start");

		Client.EventConnect().On((QuarkNetworkClientEventConnect e) -> {
			context.External((Object main) -> {
				Main.Log("Main.Client.Connect");

				return null;
			});

			return null;
		});

		Client.EventClose().On((QuarkNetworkClientEventClose e) -> {
			context.External((Object main) -> {
				Main.Log("Main.Client.Close");

				return null;
			});

			return null;
		});

		Client.EventError().On((QuarkNetworkClientEventError e) -> {
			context.External((Object main) -> {
				Main.Log("Main.Client.Error: " + e.Exception().getMessage());

				return null;
			});

			return null;
		});

		Client.Response(new MessageWelcome(), "/", (QuarkNetworkPacket packet) -> {
			MessageWelcome dto = (MessageWelcome)packet.data;

			context.External((Object main) -> {
				Main.Log("Main.Client.Response./: " + dto.message);

				return null;
			});

			Client.Service("/test", new MessageWelcome("Hello from ReactivixForJ"));

			return null;
		});

		Client.Response(new TestResponseDTO(), "/test", (QuarkNetworkPacket packet) -> {
			TestResponseDTO dto = (TestResponseDTO)packet.data;

			context.External((Object main) -> {
				Main.Log("Main.Client.Response./test: " + dto.status);

				return null;
			});

			return null;
		});

		Client.Event(new TestEventDTO(), "/test", (QuarkNetworkPacket packet) -> {
			context.External((Object main) -> {
				TestEventDTO dto = (TestEventDTO)packet.data;

				Main.Log("Main.Client.Event./test: " + dto.message);

				return null;
			});

			return null;
		});

		Client.Connect();
	}

	/**
	 * @param context The internal thread context
	 */
	public void ReactivixThreadPipe (ReactivixThread context) {
		Client.Pipe();
	}

	/**
	 * @param context The internal thread context
	 */
	public void ReactivixThreadStop (ReactivixThread context) {
		Main.Log("ClientThread.Stop");

		//Main.run = false;
		Client.Close();
	}
}

class ClientThread1 implements IReactivixThread {
	public ReactivixNetworkSocket Client;

	public ClientThread1 () {
		Client = new ReactivixNetworkSocket(new ReactivixNetworkTransportTCP(), "127.0.0.1", 8500);
	}

	/**
	 * @param context The internal thread context
	 */
	public void ReactivixThreadStart (ReactivixThread context) {
		Main.Log("ClientThread.Start");

		Client.EventConnect().On((ReactivixNetworkSocketEventConnect e) -> {
			context.External((Object main) -> {
				Main.Log("Main.Client.Connect");

				return null;
			});

			return null;
		});

		Client.EventData().On((ReactivixNetworkSocketEventData e) -> {
			context.External((Object main) -> {
				Main.Log("Main.Client.Data: " + e.Data());

				return null;
			});

			return null;
		});

		Client.EventClose().On((ReactivixNetworkSocketEventClose e) -> {
			context.External((Object main) -> {
				Main.Log("Main.Client.Close");

				return null;
			});

			return null;
		});

		Client.EventError().On((ReactivixNetworkSocketEventError e) -> {
			context.External((Object main) -> {
				Main.Log("Main.Client.Error: " + e.Exception().getMessage());

				return null;
			});

			return null;
		});

		Client.Connect();
	}

	/**
	 * @param context The internal thread context
	 */
	public void ReactivixThreadPipe (ReactivixThread context) {
		//Main.Log("ClientThread.Pipe");

		Client.Pipe();
		/*context.External((Object main) -> {
			Main.Log("Main.Stop");

			Main.Thread.Stop();

			return null;
		});*/
	}

	/**
	 * @param context The internal thread context
	 */
	public void ReactivixThreadStop (ReactivixThread context) {
		Main.Log("ClientThread.Stop");

		//Main.run = false;
		Client.Close();
	}
}