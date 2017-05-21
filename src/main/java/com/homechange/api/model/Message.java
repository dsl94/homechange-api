package com.homechange.api.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Nemanja on 5/21/17.
 *
 * Model class for a single message on system
 */
@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipient_user_id")
	private User recipient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_user_id")
	private User sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "offer_id")
	private Offer offer;

	private Date sentDateAndTime;

	String message;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Date getSentDateAndTime() {
		return sentDateAndTime;
	}

	public void setSentDateAndTime(Date sentDateAndTime) {
		this.sentDateAndTime = sentDateAndTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
