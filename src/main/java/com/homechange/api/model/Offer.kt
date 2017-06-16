package com.homechange.api.model

import javax.persistence.*
import java.util.Date

/**
 * Created by Nemanja on 5/16/17.

 * Offer model
 */
@Entity
data class Offer (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false)
    var startDate: Date? = null,

    @Column(nullable = false)
    var endDate: Date? = null,

    var country: String? = null,
    var city: String? = null,
    var details: String? = null,
    var status: OfferStatus? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @OneToMany(mappedBy = "offer")
    var messages: List<Message>? = null,

    @OneToMany(mappedBy = "offer")
    var reviews: List<Review>? = null
)
