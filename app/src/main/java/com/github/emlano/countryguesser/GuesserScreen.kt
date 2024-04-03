package com.github.emlano.countryguesser

import android.content.Context
import org.json.JSONObject

// Used to track game state of each activity
enum class Result {
    Correct,
    Wrong,
    Ongoing,
}

// Reads the json country codes and names from file
fun readJsonAsset(context: Context): Map<String, String> {
    val jsonString = context.assets
        .open("countries.json")
        .bufferedReader()
        .use { it.readText() }

    val jsonObj = JSONObject(jsonString)
    return jsonObj.keys().asSequence().associateWith { jsonObj.getString(it) }
}

// Dynamically fetches the drawable resource by its country code
fun getFlagIdFromCountryCode(code: String): Int {
    val countryCode = code.lowercase()
    val resourceIdField = R.drawable::class.java.getField(countryCode)
    return resourceIdField.getInt(null)
}