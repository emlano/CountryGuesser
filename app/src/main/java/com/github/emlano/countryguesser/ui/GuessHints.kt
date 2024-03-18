package com.github.emlano.countryguesser.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.emlano.countryguesser.R

@Composable
fun GuessHints(switch: Boolean, modifier: Modifier = Modifier) {
    HeaderText(text = R.string.guess_which_country)
}