package com.homechange.api.model

import javax.persistence.*
import java.util.Date

/**
 * Created by Nemanja on 5/21/17.

 * Model class for a single message on system
 */
@Entity
data class Message (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_user_id")
    var recipient: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_user_id")
    var sender: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
    var offer: Offer? = null,

    var sentDateAndTime: Date? = null,

    var message: String? = null
)
