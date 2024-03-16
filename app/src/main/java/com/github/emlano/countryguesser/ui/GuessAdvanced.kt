package com.github.emlano.countryguesser.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GuessAdvanced(switch: Boolean, modifier: Modifier = Modifier) {
    Text(
        text = "Hello from Advanced! switch is $switch",
        modifier = modifier
    )
}