package com.github.emlano.countryguesser.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavHostController
import com.github.emlano.countryguesser.R
import com.github.emlano.countryguesser.Screen

@Composable
fun Home(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var timerEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.home_title),
            textAlign = TextAlign.Center,
            fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            navController.navigate(
                route = Screen.Country.passSwitch(timerEnabled)
            )
        }) {
            Text(text = stringResource(id = R.string.guess_country))
        }
        Spacer(modifier = modifier.height(14.dp))
        Button(onClick = {
            navController.navigate(
                route = Screen.Hints.passSwitch(timerEnabled),
            )
        }) {
            Text(text = stringResource(id = R.string.guess_hints))
        }
        Spacer(modifier = modifier.height(14.dp))
        Button(onClick = {
            navController.navigate(
                route = Screen.Flag.passSwitch(timerEnabled)
            )
        }) {
            Text(text = stringResource(id = R.string.guess_flag))
        }
        Spacer(modifier = modifier.height(14.dp))
        Button(onClick = {
            navController.navigate(
                route = Screen.Advanced.passSwitch(timerEnabled)
            )
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