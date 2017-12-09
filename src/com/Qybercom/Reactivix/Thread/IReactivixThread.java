package com.Qybercom.Reactivix.Thread;

/**
 * Interface IReactivixThread
 */
public interface IReactivixThread {
	/**
	 * @param context The internal thread context
	 */
	void ReactivixThreadStart(ReactivixThread context);

	/**
	 * @param context The internal thread context
	 */
	void ReactivixThreadPipe(ReactivixThread context);

	/**
	 * @param context The internal thread context
	 */
	void ReactivixThreadStop(ReactivixThread context);
}