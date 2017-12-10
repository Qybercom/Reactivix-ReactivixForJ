package Examples.Quark.Stream;

import com.Qybercom.Reactivix.Thread.IReactivixThread;
import com.Qybercom.Reactivix.Thread.ReactivixThread;

import Examples.ExampleBehavior;

/**
 * Class ExampleQuarkStream
 */
public class ExampleQuarkStream extends ExampleBehavior {
	public static ReactivixThread Thread;

	/**
	 * @param args Terminal args
	 */
	public static void main (String[] args) {
		Log("ExampleQuarkStream.Init");

		Thread = new ReactivixThread(new ClientThread());
		Thread.Start();
		Thread.Internal((IReactivixThread thread) -> {
			Log("ExampleQuarkStream.Internal");

			return null;
		});

		while (run) {
			Thread.Pipe();
			_sleep();
		}
	}
}