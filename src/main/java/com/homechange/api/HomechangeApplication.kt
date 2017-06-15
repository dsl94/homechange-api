package com.homechange.api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class HomechangeApplication {


}
fun main(args: Array<String>) {
    SpringApplication.run(HomechangeApplication::class.java, *args)
}

