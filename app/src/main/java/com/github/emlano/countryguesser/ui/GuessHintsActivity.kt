package com.github.emlano.countryguesser.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.emlano.countryguesser.R
import com.github.emlano.countryguesser.Result
import com.github.emlano.countryguesser.getFlagIdFromCountryCode
import com.github.emlano.countryguesser.readJsonAsset
import com.github.emlano.countryguesser.ui.theme.CountryGuesserTheme

class GuessHintsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryGuesserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GuessHints(switch = false)
                }
            }
        }
    }
}

@Composable
fun GuessHints(switch: Boolean, modifier: Modifier = Modifier) {
    val state = rememberScrollState()

    val countries = readJsonAsset(LocalContext.current)
    var randomCountryCode by rememberSaveable { mutableStateOf(countries.keys.random()) }
    var randomCountryName = countries.getValue(randomCountryCode)
    val randomCountryFlag = getFlagIdFromCountryCode(randomCountryCode)

    var inputCharacter by rememberSaveable { mutableStateOf("") }
    var guessedName by rememberSaveable { mutableStateOf(makeDisplayString(randomCountryName)) }
    var attempts by rememberSaveable { mutableIntStateOf(3) }

    var isCharacterInName by rememberSaveable { mutableStateOf(Result.Ongoing) }
    var isGuessCorrect by rememberSaveable { mutableStateOf(Result.Ongoing) }

    Column(
        modifier = modifier
            .padding(25.dp)
            .verticalScroll(state),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        HeaderText(text = R.string.guess_which_country, modifier)
        FlagHero(resource = randomCountryFlag, modifier = modifier)
        Box(
            modifier = modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
                .padding(bottom = 20.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                text = formatDisplayString(guessedName),
                textAlign = TextAlign.Center,
                fontSize = 23.sp,
                softWrap = true,
                modifier = modifier.padding(10.dp)
            )
        }
        Box(
            modifier = modifier
                .width(200.dp)
        ) {
            TextField(
                value = inputCharacter,
                onValueChange = {
                    if (inputCharacter.isEmpty() && it.length == 1) {
                        inputCharacter = it
                    } else if (inputCharacter.length == 1 && it.isEmpty()) {
                        inputCharacter = it
                    }

                    isCharacterInName = Result.Ongoing
                },
                label = { Text(text = stringResource(id = R.string.enter_letter)) },
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                ),
                enabled = isGuessCorrect == Result.Ongoing
            )
        }
        ResultText(result = isGuessCorrect, answer = randomCountryName, modifier = modifier)
        SubmitNextButton(
            result = isGuessCorrect,
            onClickSubmit = {
                isCharacterInName = Result.Wrong
                if (randomCountryName.contains(inputCharacter, ignoreCase = true)) {
                    randomCountryName.forEachIndexed { index, c ->
                        if (c.toString().lowercase() == inputCharacter.lowercase()) {
                            val tempString = guessedName.toMutableList()
                            tempString[index] = c
                            guessedName = tempString.joinToString("")
                            isCharacterInName = Result.Correct
                        }
                    }
                } else {
                    attempts--
                }
                inputCharacter = ""

                if (!guessedName.contains("_")) isGuessCorrect = Result.Correct
                else if (attempts == 0) isGuessCorrect = Result.Wrong
            },
            onClickNext = {
                isGuessCorrect = Result.Ongoing
                isCharacterInName = Result.Ongoing
                attempts = 3
                randomCountryCode = countries.keys.random()
                randomCountryName = countries.getValue(randomCountryCode)
                guessedName = makeDisplayString(randomCountryName)
            },
            modifier = modifier
        )
    }
}

fun makeDisplayString(countryName: String): String {
    return countryName
        .toList()
        .map { if (it in 'A'..'Z' || it in 'a'..'z') '_' else it }
        .joinToString("")
}

fun formatDisplayString(string: String): String {
    return string.split("").joinToString(" ")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    CountryGuesserTheme {
        GuessHints(switch = false)
    }
}