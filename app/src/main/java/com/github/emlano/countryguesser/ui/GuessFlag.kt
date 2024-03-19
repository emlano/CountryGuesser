package com.github.emlano.countryguesser.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.emlano.countryguesser.R
import com.github.emlano.countryguesser.Result
import com.github.emlano.countryguesser.getFlagIdFromCountryCode
import com.github.emlano.countryguesser.readJsonAsset

@Composable
fun GuessFlag(switch: Boolean, modifier: Modifier = Modifier) {
    val state = rememberScrollState()

    val countries = readJsonAsset(LocalContext.current)
    var countryList by rememberSaveable { mutableStateOf(List(3) { _ -> countries.keys.random() }) }

    var randomCountryCode by rememberSaveable { mutableStateOf(countryList.random()) }
    var randomCountryName = countries.getValue(randomCountryCode)
    var randomCountryFlag = getFlagIdFromCountryCode(randomCountryCode)

    var correctCountryIndex = countryList.indexOf(randomCountryCode)
    var result by rememberSaveable { mutableStateOf(Result.Ongoing) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HeaderText(text = R.string.guess_which_flag)
        Text(
            text = stringResource(id = R.string.of),
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
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
                .height(200.dp)
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
                    FlagHero(
                        resource = getFlagIdFromCountryCode(countryList[0]),
                        clickable = true,
                        onClick = {
                            if (correctCountryIndex == 0 && result == Result.Ongoing) {
                                result = Result.Correct
                            } else result = Result.Wrong
                        },
                        modifier = modifier,
                    )
                    FlagHero(
                        resource = getFlagIdFromCountryCode(countryList[1]),
                        clickable = true,
                        onClick = {
                            if (correctCountryIndex == 1 && result == Result.Ongoing) {
                                result = Result.Correct
                            } else result = Result.Wrong
                        },
                        modifier = modifier,
                    )
                    FlagHero(
                        resource = getFlagIdFromCountryCode(countryList[2]),
                        clickable = true,
                        onClick = {
                            if (correctCountryIndex == 2 && result == Result.Ongoing) {
                                result = Result.Correct
                            } else result = Result.Wrong
                        },
                        modifier = modifier,
                    )
                }
            }
        }
        ResultText(
            result = result,
            modifier = modifier,
            answer = stringResource(id = formatAnswerString(countryList.indexOf(randomCountryCode)))
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

fun formatAnswerString(flagIndex: Int): Int {
    return when (flagIndex) {
        0 -> R.string.first_flag
        1 -> R.string.second_flag
        else -> R.string.third_flag
    }
}
