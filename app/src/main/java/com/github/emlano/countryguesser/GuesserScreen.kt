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

const val TIMER_SWITCH = "switch"

sealed class Screen(val route: String) {
    object Home: Screen(route = "home")
    object Country: Screen(route = "country/{$TIMER_SWITCH}")
    object Hints: Screen(route = "hints/{$TIMER_SWITCH}")
    object Flag: Screen(route = "flag/{$TIMER_SWITCH}")
    object Advanced: Screen(route = "advanced/{$TIMER_SWITCH}")

    fun passSwitch(sw: Boolean): String {
        return this.route.replace(oldValue = "{$TIMER_SWITCH}", newValue = sw.toString())
    }
}

enum class Result() {
    Correct,
    Wrong,
    Ongoing,
}

@Composable
fun GuesserApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            Home(navController = navController)
        }

        composable(
            route = Screen.Country.route,
            arguments = listOf(
                navArgument("switch") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { entry ->
            GuessCountry(switch = entry.arguments?.getBoolean("switch") ?: false)
        }

        composable(
            route = Screen.Hints.route,
            arguments = listOf(
                navArgument("switch") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { entry ->
            GuessHints(switch = entry.arguments?.getBoolean("switch") ?: false)
        }

        composable(
            route = Screen.Flag.route,
            arguments = listOf(
                navArgument("switch") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { entry ->
            GuessFlag(switch = entry.arguments?.getBoolean("switch") ?: false)
        }

        composable(
            route = Screen.Advanced.route,
            arguments = listOf(
                navArgument("switch") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { entry ->
            GuessAdvanced(switch = entry.arguments?.getBoolean("switch") ?: false)
        }
    }
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