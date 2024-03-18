package com.github.emlano.countryguesser.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.emlano.countryguesser.R
import com.github.emlano.countryguesser.Result
import com.github.emlano.countryguesser.getFlagIdFromCountryCode
import com.github.emlano.countryguesser.readJsonAsset

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
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                items(countryList.size) {
                    Row(
                        modifier = modifier.padding(start = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = it == selectedCountry, onClick = { selectedCountry = it })
                        Text(text = countryList[it])
                    }
                }
            }
        }
        ResultText(result = isAnswerCorrect, answer = randomCountryName)
        SubmitNextButton(result = isAnswerCorrect, onClickSubmit = {
            isAnswerCorrect = if (countryList[selectedCountry] == randomCountryName) {
                Result.Correct
            } else {
                Result.Wrong
            }
        }, onClickNext = {
            randomCountryCode = countries.keys.random()
            isAnswerCorrect = Result.Ongoing
            selectedCountry = 0
        })
    }
}