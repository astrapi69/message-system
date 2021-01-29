package de.alpharogroup.message.system.jpa.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import de.alpharogroup.resource.system.jpa.entities.Resources;

import de.alpharogroup.db.entity.uniqueable.UUIDEntity;
import de.alpharogroup.message.system.enums.MessageState;
import de.alpharogroup.message.system.enums.MessageType;
import de.alpharogroup.user.auth.jpa.entities.Contactmethods;
import de.alpharogroup.user.auth.jpa.entities.Users;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Entity class {@link Messages } is keeping the information for the messages or notes from
 * users.
 */
@Entity
@Table(name = "messages")
@TypeDefs({
		@TypeDef(name = "messagetypeConverter", typeClass = de.alpharogroup.db.postgres.usertype.PGEnumUserType.class, parameters = {
				@Parameter(name = "enumClassName", value = "de.alpharogroup.message.system.enums.MessageType") }),
		@TypeDef(name = "stateConverter", typeClass = de.alpharogroup.db.postgres.usertype.PGEnumUserType.class, parameters = {
				@Parameter(name = "enumClassName", value = "de.alpharogroup.message.system.enums.MessageState") }) })
@Getter
@Setter
@NoArgsConstructor
public class Messages extends UUIDEntity implements Cloneable
{

	/** The serial Version UID */
	private static final long serialVersionUID = 920286633675636537L;
	/**
	 * The parent of this message can be null if its the root message.
	 **/
	@OneToOne
	@JoinColumn(name = "parent", foreignKey = @ForeignKey(name = "FK_PARENT_MESSAGE_ID"))
	private Messages parent;
	/**
	 * A flag that indicates that the message is deleted from the sender but will not really deleted
	 * because of references to other messages.
	 */
	@Column(name = "sender_deleted_flag")
	private boolean senderDeletedFlag;
	/**
	 * A flag that indicates that the message is deleted from the recipient but will not really
	 * deleted because of references to other messages.
	 */
	@Column(name = "recipient_deleted_flag")
	private boolean recipientDeletedFlag;
	/** A flag that indicates that the message could not be sent. */
	@Column(name = "failed2sentemail")
	private boolean failed2sentemail;
	/** The folder of the message. */
	@Column(name = "folder", length = 64)
	private String folder;
	/** The content of the message. */
	@Column(name = "messageContent", length = 21845)
	private String messageContent;
	/** An enum for the message type. */
	@Enumerated(EnumType.STRING)
	@Column(name = "messagetype")
	@Type(type = "messagetypeConverter")
	private MessageType messagetype;
	/**
	 * A flag that indicates if the message is readed(at least opened) from the recipient(s).
	 */
	@Column(name = "read_flag")
	private Boolean readFlag;
	/** The sender of the message. */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sender", nullable = true, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MESSAGES_SENDER"))
	private Users sender;
	/** The email address from the sender of this message. */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sender_email", nullable = true, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_MESSAGES_SENDER_EMAIL"))
	private Contactmethods senderEmail;
	/** The sent date of the message. */
	@Column(name = "sent_date")
	private Date sentDate;
	/** A flag that indicates if the message is a spam message. */
	@Column(name = "spam_flag")
	private boolean spamFlag;
	/** An enum for the state from the message. */
	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	@Type(type = "stateConverter")
	private MessageState state;
	/** The subject of the message. */
	@Column(name = "subject", length = 1000)
	private String subject;
	/** The attachments of the message. */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "message_attachments", joinColumns = {
			@JoinColumn(name = "message_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "resource_id", referencedColumnName = "id") })
	private Set<Resources> attachments = new HashSet<Resources>();

}
