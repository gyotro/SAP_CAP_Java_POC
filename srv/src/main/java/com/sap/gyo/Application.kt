package com.sap.gyo;
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
open class Application
private val logger = LoggerFactory.getLogger(Application::class.java)

fun main(args: Array<String>) 
{
    SpringApplication.run(Application::class.java, *args)
    logger.info("Starting Application in Kotlin...")
}



