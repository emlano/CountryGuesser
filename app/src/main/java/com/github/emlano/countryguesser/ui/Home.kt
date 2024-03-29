package com.github.emlano.countryguesser.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.emlano.countryguesser.R

@Composable
fun Home(
    modifier: Modifier = Modifier
) {
    var timerEnabled by rememberSaveable { mutableStateOf(false) }
    val state = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(40.dp)
            .verticalScroll(state),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(id = R.string.home_title),
            textAlign = TextAlign.Center,
            fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                val intent = Intent(context, GuessCountryActivity::class.java)
                intent.putExtra("switch", timerEnabled)
                context.startActivity(intent)
            }) {
                Text(text = stringResource(id = R.string.guess_country))
            }
            Spacer(modifier = modifier.height(14.dp))
            Button(onClick = {
                val intent = Intent(context, GuessHintsActivity::class.java)
                intent.putExtra("switch", timerEnabled)
                context.startActivity(intent)
            }) {
                Text(text = stringResource(id = R.string.guess_hints))
            }
            Spacer(modifier = modifier.height(14.dp))
            Button(onClick = {
                val intent = Intent(context, GuessFlagActivity::class.java)
                intent.putExtra("switch", timerEnabled)
                context.startActivity(intent)
            }) {
                Text(text = stringResource(id = R.string.guess_flag))
            }
            Spacer(modifier = modifier.height(14.dp))
            Button(onClick = {
                val intent = Intent(context, GuessAdvancedActivity::class.java)
                intent.putExtra("switch", timerEnabled)
                context.startActivity(intent)
            }) {
                Text(text = stringResource(id = R.string.advanced_level))
            }
            Spacer(modifier = modifier.height(14.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.timer),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = modifier.width(14.dp))
                Switch(checked = timerEnabled, onCheckedChange = {
                    timerEnabled = !timerEnabled
                })
            }
        }
    }
}