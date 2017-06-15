package com.homechange.api.model

import javax.persistence.*
import java.util.Date

/**
 * Created by Nemanja on 5/14/17.

 * User entity class
 */
@Entity
@Table(name = "users")
data class User (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var username: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var password: String? = null,
    var email: String? = null,
    var country: String? = null,
    var city: String? = null,
    var address: String? = null,
    var dateOfBirth: Date? = null,
    var phoneNumber: String? = null,

    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "home_id")
    var home: Home? = null,

    @OneToMany(mappedBy = "user")
    var offers: List<Offer>? = null,

    @OneToMany(mappedBy = "sender")
    var sentMessages: List<Message>? = null,

    @OneToMany(mappedBy = "recipient")
    var receivedMessages: List<Message>? = null
)
