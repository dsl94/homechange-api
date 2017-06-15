package com.homechange.api.security

import com.homechange.api.model.User
import com.homechange.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Created by Nemanja on 5/15/17.
 */
@Service
class MyUserDetailsService : UserDetailsService {

    @Autowired
    private val userRepository: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails {
        val user = userRepository!!.findByUsernameIgnoreCase(s) ?: throw UsernameNotFoundException(s)
        return MyUserPrincipal(user)
    }
}
