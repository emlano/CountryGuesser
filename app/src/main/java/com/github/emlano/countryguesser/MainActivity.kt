package com.github.emlano.countryguesser

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.emlano.countryguesser.ui.theme.CountryGuesserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryGuesserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home(modifier: Modifier = Modifier) {
    val timerEnabled = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Country Guesser",
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

        }) {
            Text(text = "Guess the Country")
        }
        Spacer(modifier = modifier.height(14.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Guess-Hints")
        }
        Spacer(modifier = modifier.height(14.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Guess the Flag")
        }
        Spacer(modifier = modifier.height(14.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Advanced Level")
        }
        Spacer(modifier = modifier.height(14.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Timer",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = modifier.width(14.dp))
            Switch(checked = timerEnabled.value, onCheckedChange = {
                timerEnabled.value = !timerEnabled.value
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CountryGuesserTheme {
        Home()
    }
}