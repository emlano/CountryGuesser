package com.github.emlano.countryguesser

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.emlano.countryguesser.ui.GuessAdvanced
import com.github.emlano.countryguesser.ui.GuessCountry
import com.github.emlano.countryguesser.ui.GuessFlag
import com.github.emlano.countryguesser.ui.GuessHints
import com.github.emlano.countryguesser.ui.Home
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