package com.github.emlano.countryguesser

import android.content.Context
import org.json.JSONObject


enum class Result() {
    Correct,
    Wrong,
    Ongoing,
}

fun readJsonAsset(context: Context): Map<String, String> {
    val jsonString = context.assets
        .open("countries.json")
        .bufferedReader()
        .use { it.readText() }

    val jsonObj = JSONObject(jsonString)
    return jsonObj.keys().asSequence().associateWith { jsonObj.getString(it) }
}

fun getFlagIdFromCountryCode(code: String): Int {
    val countryCode = code.lowercase()
    val resourceIdField = R.drawable::class.java.getField(countryCode)
    return resourceIdField.getInt(null)
}