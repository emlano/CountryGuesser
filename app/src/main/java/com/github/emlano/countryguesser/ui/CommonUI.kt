package com.github.emlano.countryguesser.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.emlano.countryguesser.R

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
                .border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(15.dp))
        )
    }
}