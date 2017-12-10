package Examples.Quark.Stream.DTO;

import com.google.gson.annotations.Expose;

import com.Qybercom.Reactivix.Quark.IQuarkNetworkPacketData;

/**
 * Class MessageWelcomeDTO
 */
public class MessageWelcomeDTO implements IQuarkNetworkPacketData {
	@Expose
	public String message;

	/**
	 * @param message Transmitted message
	 */
	public MessageWelcomeDTO (String message) {
		this.message = message;
	}
}