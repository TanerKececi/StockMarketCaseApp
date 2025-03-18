package com.example.stockmarketcaseapp.util

import java.io.File
import java.util.Properties

object LocalProperties {
    private val properties = Properties()

    init {
        try {
            val inputStream = File("local.properties").inputStream()
            properties.load(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getProperty(key: String): String? {
        return properties.getProperty(key)
    }
}