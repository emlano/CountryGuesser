package com.github.emlano.countryguesser.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.emlano.countryguesser.R
import com.github.emlano.countryguesser.Result
import com.github.emlano.countryguesser.getFlagIdFromCountryCode
import com.github.emlano.countryguesser.readJsonAsset
import com.github.emlano.countryguesser.ui.theme.CountryGuesserTheme

class GuessFlagActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryGuesserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GuessFlag(switch = intent.getBooleanExtra("switch", false))
                }
            }
        }
    }
}

@Composable
fun GuessFlag(switch: Boolean, modifier: Modifier = Modifier) {
    val state = rememberScrollState()

    val countries = readJsonAsset(LocalContext.current)
    var countryList by rememberSaveable { mutableStateOf(List(3) { _ -> countries.keys.random() }) }

    var randomCountryCode by rememberSaveable { mutableStateOf(countryList.random()) }
    val randomCountryName = countries.getValue(randomCountryCode)

    val correctCountryIndex = countryList.indexOf(randomCountryCode)
    var result by rememberSaveable { mutableStateOf(Result.Ongoing) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (switch) {
            CountDownTimer(
                modifier = modifier,
                result = result,
                onEnd = {
                    result = Result.Wrong
                }
            )
        }
        HeaderText(text = R.string.guess_which_flag)
        Text(
            text = stringResource(id = R.string.of),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(bottom = 20.dp)
        )
        Text(
            text = randomCountryName,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(bottom = 50.dp, start = 15.dp, end = 15.dp)
        )
        Box(
            modifier = modifier
                .height(250.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            LazyRow(
                modifier = modifier
            ) {
                item {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        FlagHero(
                            resource = getFlagIdFromCountryCode(countryList[0]),
                            clickable = true,
                            onClick = {
                                if (result != Result.Ongoing) return@FlagHero
                                result = if (correctCountryIndex == 0) {
                                    Result.Correct
                                } else Result.Wrong
                            },
                            modifier = modifier,
                        )

                        if (result == Result.Wrong && correctCountryIndex == 0) {
                            CorrectFlagIndicator(modifier = modifier)
                        }
                    }
                }

                item {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        FlagHero(
                            resource = getFlagIdFromCountryCode(countryList[1]),
                            clickable = true,
                            onClick = {
                                if (result != Result.Ongoing) return@FlagHero
                                result = if (correctCountryIndex == 1) {
                                    Result.Correct
                                } else Result.Wrong
                            },
                            modifier = modifier,
                        )

                        if (result == Result.Wrong && correctCountryIndex == 1) {
                            CorrectFlagIndicator(modifier = modifier)
                        }
                    }
                }

                item {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        FlagHero(
                            resource = getFlagIdFromCountryCode(countryList[2]),
                            clickable = true,
                            onClick = {
                                if (result != Result.Ongoing) return@FlagHero
                                result = if (correctCountryIndex == 2) {
                                    Result.Correct
                                } else Result.Wrong
                            },
                            modifier = modifier,
                        )

                        if (result == Result.Wrong && correctCountryIndex == 2) {
                            CorrectFlagIndicator(modifier = modifier)
                        }
                    }
                }
            }
        }

        ResultText(
            result = result,
            modifier = modifier
        )
        if (result != Result.Ongoing) {
            SubmitNextButton(
                result = result,
                onClickSubmit = {},
                onClickNext = {
                    countryList = List(3) { _ -> countries.keys.random() }
                    randomCountryCode = countryList.random()
                    result = Result.Ongoing
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    CountryGuesserTheme {
        GuessFlag(switch = false)
    }
}

// Puts a check icon on the correct flag to indicate to the user
@Composable
fun CorrectFlagIndicator(modifier: Modifier = Modifier) {
    Icon(
        imageVector = Icons.Filled.CheckCircle,
        contentDescription = "result indicator",
        tint = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.onError, shape = CircleShape)
            .size(60.dp)
    )
}