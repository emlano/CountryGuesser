package com.github.emlano.countryguesser.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.emlano.countryguesser.R
import com.github.emlano.countryguesser.Result
import com.github.emlano.countryguesser.getFlagIdFromCountryCode
import com.github.emlano.countryguesser.readJsonAsset
import com.github.emlano.countryguesser.ui.theme.CountryGuesserTheme

class GuessCountryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryGuesserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GuessCountry(switch = intent.getBooleanExtra("switch", false))
                }
            }
        }
    }
}

@Composable
fun GuessCountry(switch: Boolean, modifier: Modifier = Modifier) {
    val state = rememberScrollState()

    val countries = readJsonAsset(LocalContext.current)
    val countryList = countries.values.toList()

    var randomCountryCode by rememberSaveable { mutableStateOf(countries.keys.random()) }
    val randomCountryName = countries.getValue(randomCountryCode)
    val randomCountryFlag = getFlagIdFromCountryCode(randomCountryCode)

    var selectedCountry by rememberSaveable { mutableIntStateOf(0) }
    var isAnswerCorrect by rememberSaveable { mutableStateOf(Result.Ongoing) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (switch) {
            CountDownTimer(
                result = isAnswerCorrect,
                modifier = modifier,
                onEnd = { isAnswerCorrect = submit(countryList, selectedCountry, randomCountryName) }
            )
        }
        HeaderText(text = R.string.guess_which_country)
        FlagHero(randomCountryFlag)
        Box(
            modifier = modifier
                .height(250.dp)
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(start = 75.dp, end = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                items(countryList.size) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .clickable { selectedCountry = it },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            modifier = modifier.padding(start = 10.dp),
                            selected = it == selectedCountry,
                            onClick = { selectedCountry = it }
                        )

                        Text(text = countryList[it])
                    }
                }
            }
        }
        ResultText(result = isAnswerCorrect, answer = randomCountryName)
        SubmitNextButton(result = isAnswerCorrect, onClickSubmit = {
            isAnswerCorrect = submit(countryList, selectedCountry, randomCountryName)
        }, onClickNext = {
            randomCountryCode = countries.keys.random()
            isAnswerCorrect = Result.Ongoing
            selectedCountry = 0
        })
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CountryGuesserTheme {
        GuessCountry(switch = false)
    }
}

fun submit(countryList: List<String>, selectedCountry: Int, randomCountryName: String): Result {
    return if (countryList[selectedCountry] == randomCountryName) {
        Result.Correct
    } else {
        Result.Wrong
    }
}