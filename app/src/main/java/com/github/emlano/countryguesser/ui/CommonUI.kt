package com.github.emlano.countryguesser.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun FlagHero(resource: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(50.dp)
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
        )
    }
}

@Composable
fun ResultText(result: Result, modifier: Modifier = Modifier, answer: String = "") {
    val resultString = when(result) {
        Result.Correct -> stringResource(id = R.string.correct)
        Result.Wrong -> stringResource(id = R.string.wrong)
        Result.Ongoing -> ""
    }

    val answer = when(result) {
        Result.Wrong -> stringResource(id = R.string.answer_was).replace("\$answer", answer)
        else -> ""
    }

    val color = when(result) {
        Result.Correct -> MaterialTheme.colorScheme.onError
        Result.Wrong -> MaterialTheme.colorScheme.error
        Result.Ongoing -> MaterialTheme.colorScheme.surface
    }

    Column(
        modifier.padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = resultString,
            color = color,
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
        )

        if (result == Result.Wrong) {
            Text(
                text = answer,
                color = color,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
            )
        }
    }
}

@Composable
fun SubmitNextButton(result: Result, onClickSubmit: () -> Unit, onClickNext: () -> Unit,modifier: Modifier = Modifier) {
    val buttonString = when (result) {
        Result.Ongoing -> stringResource(id = R.string.submit)
        else -> stringResource(id = R.string.next)
    }

    val buttonAction = when(result) {
        Result.Ongoing -> onClickSubmit
        else -> onClickNext
    }
    
    Button(onClick = buttonAction) {
        Text(text = buttonString)
    }
}