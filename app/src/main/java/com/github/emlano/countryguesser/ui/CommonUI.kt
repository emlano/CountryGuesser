package com.github.emlano.countryguesser.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.emlano.countryguesser.R
import com.github.emlano.countryguesser.Result
import kotlinx.coroutines.delay

@Composable
fun FlagHero(
    resource: Int,
    modifier: Modifier = Modifier,
    clickable: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(30.dp)
    ) {
        Image(
            painter = painterResource(id = resource),
            contentDescription = stringResource(id = R.string.flag_caption),
            contentScale = ContentScale.Fit,
            modifier = modifier
                .clip(RoundedCornerShape(15.dp))
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(15.dp)
                )
                .clickable(
                    enabled = clickable,
                    onClick = onClick
                )
        )
    }
}

@Composable
fun HeaderText(text: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = text),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 26.sp,
        softWrap = true,
        modifier = modifier.padding(10.dp)
    )
}

@Composable
fun ResultText(result: Result, modifier: Modifier = Modifier, answer: String = "") {
    val resultString = when (result) {
        Result.Correct -> stringResource(id = R.string.correct)
        Result.Wrong -> stringResource(id = R.string.wrong)
        Result.Ongoing -> ""
    }

    val formattedAnswer = when (result) {
        Result.Wrong -> stringResource(id = R.string.answer_was).replace("\$answer", answer)
        else -> ""
    }

    val color = when (result) {
        Result.Correct -> MaterialTheme.colorScheme.onError
        Result.Wrong -> MaterialTheme.colorScheme.error
        Result.Ongoing -> MaterialTheme.colorScheme.surface
    }

    if (result != Result.Ongoing) {
        Column(
            modifier.padding(top = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = resultString,
                color = color,
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )

            if (result == Result.Wrong && answer.isNotEmpty()) {
                Text(
                    text = formattedAnswer,
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                )
            }
        }
    }
}

@Composable
fun SubmitNextButton(
    result: Result,
    onClickSubmit: () -> Unit,
    onClickNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonString = when (result) {
        Result.Ongoing -> stringResource(id = R.string.submit)
        else -> stringResource(id = R.string.next)
    }

    val buttonAction = when (result) {
        Result.Ongoing -> onClickSubmit
        else -> onClickNext
    }

    Button(
        onClick = buttonAction,
        contentPadding = PaddingValues(horizontal = 35.dp, vertical = 10.dp),
        modifier = modifier.padding(20.dp)

    ) {
        Text(
            text = buttonString,
            fontSize = 16.sp
        )
    }
}

@Composable
fun CountDownTimer(result: Result, modifier: Modifier = Modifier, onEnd: () -> Unit) {
    var timeNow by rememberSaveable { mutableIntStateOf(10) }
    var isPaused by rememberSaveable { mutableStateOf(false) }

    if (result != Result.Ongoing) isPaused = true

    if (isPaused && result == Result.Ongoing) {
        timeNow = 10
        isPaused = false
    }

    LaunchedEffect(key1 = timeNow, key2 = result) {
        if (timeNow > 0 && !isPaused) {
            delay(1000L)
            timeNow--
        } else {
            onEnd()
        }
    }

    val timerColor = if (isPaused && timeNow == 0) {
        MaterialTheme.colorScheme.error
    } else MaterialTheme.colorScheme.primary
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Box(
            modifier = modifier
                .border(
                    width = 3.dp,
                    shape = RoundedCornerShape(10.dp),
                    color = timerColor
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = modifier.padding(10.dp),
                text = stringResource(id = R.string.timer_label)
                    .replace("\$timeLeft", timeNow.toString()),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = timerColor
            )
        }
    }
}