package Examples.Networking;

import com.Qybercom.Reactivix.Thread.IReactivixThread;
import com.Qybercom.Reactivix.Thread.ReactivixThread;

import com.Qybercom.Reactivix.Network.Events.*;
import com.Qybercom.Reactivix.Network.ReactivixNetworkSocket;
import com.Qybercom.Reactivix.Network.Transports.ReactivixNetworkTransportTCP;

/**
 * Class ClientThread
 */
class ClientThread implements IReactivixThread {
	public ReactivixNetworkSocket Client;

	/**
	 * Default constructor of ClientThread
	 */
	public ClientThread () {
		Client = new ReactivixNetworkSocket(new ReactivixNetworkTransportTCP(), "127.0.0.1", 8500);
	}

	/**
	 * @param context The internal thread context
	 */
	public void ReactivixThreadStart (ReactivixThread context) {
		ExampleNetworking.Log("ClientThread.Start");

		Client.EventConnect().On((ReactivixNetworkSocketEventConnect e) -> {
			context.External((Object Networking) -> {
				ExampleNetworking.Log("Networking.Client.Connect");

				return null;
			});

			return null;
		});

		Client.EventData().On((ReactivixNetworkSocketEventData e) -> {
			context.External((Object Networking) -> {
				ExampleNetworking.Log("Networking.Client.Data: " + e.Data());

				return null;
			});

			return null;
		});

		Client.EventClose().On((ReactivixNetworkSocketEventClose e) -> {
			context.External((Object Networking) -> {
				ExampleNetworking.Log("Networking.Client.Close");

				return null;
			});

			return null;
		});

		Client.EventError().On((ReactivixNetworkSocketEventError e) -> {
			context.External((Object Networking) -> {
				ExampleNetworking.Log("Networking.Client.Error: " + e.Exception().getMessage());

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
		ExampleNetworking.Log("ClientThread.Stop");

		//ExampleNetworking.run = false;
		Client.Close();
	}
}