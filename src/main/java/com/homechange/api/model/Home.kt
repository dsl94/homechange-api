package com.homechange.api.model

import javax.persistence.*

/**
 * Created by Nemanja on 5/15/17.

 * Class that represents home of User
 *
 */
@Entity
data class Home (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(nullable = false)
    var bedroomNumber: Int? = null,
    @Column(nullable = false)
    var bathroomNumber: Int? = null,
    @Column(nullable = false)
    var propertyType: PropertyType? = null,
    @Column(nullable = false)
    var description: String? = null,
    var tv: Boolean = true,
    var internet: Boolean = true,
    var airConditioning: Boolean = true,
    var heating: Boolean = true,
    var petsAllowed: Boolean = false,
    //TODO ADD PICTURES

    @OneToOne(mappedBy = "home")
    var user: User? = null
)
