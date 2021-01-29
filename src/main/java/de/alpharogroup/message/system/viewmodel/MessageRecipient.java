package de.alpharogroup.message.system.viewmodel;

import de.alpharogroup.user.auth.dto.Contactmethod;
import de.alpharogroup.user.auth.dto.User;

public class MessageRecipient
{
	/**
	 * The message attribute that references to the Entity class {@link Message}.
	 */
	private Message message;
	/**
	 * The recipient attribute that references to the Entity class {@link User}.
	 */
	private User recipient;
	/**
	 * The recipient email as a String object that can be indicate that the recipient is a user
	 * outside from the system. For instance a user from the system makes a recommendation to a
	 * friend.
	 */
	private Contactmethod recipientEmail;
}
