package com.github.emlano.countryguesser.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.emlano.countryguesser.R
import com.github.emlano.countryguesser.Result

@Composable
fun GuessCountry(switch: Boolean, modifier: Modifier = Modifier) {
    var selected by remember { mutableIntStateOf(0) }
    var isAnswerCorrect by remember { mutableStateOf(Result.Correct) }
    var countryName by remember { mutableStateOf("United States of America") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.guess_which_country),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 26.sp,
            softWrap = true
        )
        FlagHero(R.drawable.us)
        Box(
            modifier = modifier
                .height(250.dp)
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                items(250) {
                    Row(
                        modifier = modifier.padding(start = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val ol = listOf("United States of America", "Sri Lanka", "Canada", "Tuvalu", "China")
                        RadioButton(selected = it == selected, onClick = { selected = it })
                        Text(text = ol[it % 5])
                    }
                }
            }
        }
        ResultText(result = isAnswerCorrect, answer = countryName)
        SubmitNextButton(result = isAnswerCorrect, onClickSubmit = {  }, onClickNext = {  })
    }
}