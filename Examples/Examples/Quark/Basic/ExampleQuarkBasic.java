package Examples.Quark.Basic;

import com.Qybercom.Reactivix.Thread.IReactivixThread;
import com.Qybercom.Reactivix.Thread.ReactivixThread;

import Examples.ExampleBehavior;

/**
 * Class ExampleQuarkBasic
 */
public class ExampleQuarkBasic extends ExampleBehavior {
	public static ReactivixThread Thread;

	/**
	 * @param args Terminal args
	 */
	public static void main (String[] args) {
		Log("ExampleQuarkBasic.Init");

		Thread = new ReactivixThread(new ClientThread());
		Thread.Start();
		Thread.Internal((IReactivixThread thread) -> {
			Log("ExampleQuarkBasic.Internal");

			return null;
		});

		while (run) {
			Thread.Pipe();
			_sleep();
		}
	}
}