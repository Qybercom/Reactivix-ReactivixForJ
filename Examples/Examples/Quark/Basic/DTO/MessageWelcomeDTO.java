package Examples.Quark.Basic.DTO;

import com.google.gson.annotations.Expose;

import com.Qybercom.Reactivix.Quark.IQuarkNetworkPacketData;

/**
 * Class MessageWelcomeDTO
 */
public class MessageWelcomeDTO implements IQuarkNetworkPacketData {
	@Expose(serialize = false)
	public int status;

	@Expose
	public String message;

	/**
	 * Default constructor of MessageWelcomeDTO
	 */
	public MessageWelcomeDTO () { }

	/**
	 * @param message Transmitted message
	 */
	public MessageWelcomeDTO (String message) {
		this.message = message;
	}
}