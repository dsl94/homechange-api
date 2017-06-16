package com.homechange.api.model

import javax.persistence.*

/**
 * Created by Nemanja on 6/15/17.
 *
 * Model class for review
 */
@Entity
data class Review(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @Column(nullable = false)
        var numberOfStars: Int? = null,

        var comment: String? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "reviewer_id")
        var reviewer: User? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "reviewed_id")
        var reviewedUser: User? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "offer_id")
        var offer: Offer? = null
)