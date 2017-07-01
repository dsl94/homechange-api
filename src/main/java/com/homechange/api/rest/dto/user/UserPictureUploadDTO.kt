package com.homechange.api.rest.dto.user

import javax.persistence.Lob

/**
 * Created by nemanja on 7/1/17.
 */
class UserPictureUploadDTO {

    var username: String? = null

    @Lob
    var cvFile: ByteArray? = null

    constructor(){}
}