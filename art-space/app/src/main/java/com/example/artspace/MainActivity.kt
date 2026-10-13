package com.example.artspace

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    var currentArtwork by remember { mutableStateOf(1) }

    val imageRes = when (currentArtwork) {
        1 -> R.drawable.art_1
        2 -> R.drawable.art_2
        else -> R.drawable.art_3
    }
    val titleRes = when (currentArtwork) {
        1 -> R.string.art_1_title
        2 -> R.string.art_2_title
        else -> R.string.art_3_title
    }
    val artistRes = when (currentArtwork) {
        1 -> R.string.art_1_artist
        2 -> R.string.art_2_artist
        else -> R.string.art_3_artist
    }
    val yearRes = when (currentArtwork) {
        1 -> R.string.art_1_year
        2 -> R.string.art_2_year
        else -> R.string.art_3_year
    }

    val onPreviousClick = {
        currentArtwork = if (currentArtwork == 1) 3 else currentArtwork - 1
    }
    val onNextClick = {
        currentArtwork = if (currentArtwork == 3) 1 else currentArtwork + 1
    }

    val isLandscape =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .safeContentPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ArtworkWall(
                imageRes = imageRes,
                titleRes = titleRes,
                modifier = Modifier.weight(1f)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                ArtworkDescriptor(
                    titleRes = titleRes,
                    artistRes = artistRes,
                    yearRes = yearRes
                )
                DisplayController(
                    onPreviousClick = onPreviousClick,
                    onNextClick = onNextClick
                )
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .safeContentPadding()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ArtworkWall(
                imageRes = imageRes,
                titleRes = titleRes,
                modifier = Modifier.weight(1f)
            )
            ArtworkDescriptor(
                titleRes = titleRes,
                artistRes = artistRes,
                yearRes = yearRes
            )
            DisplayController(
                onPreviousClick = onPreviousClick,
                onNextClick = onNextClick
            )
        }
    }
}

@Composable
fun ArtworkWall(
    @DrawableRes imageRes: Int,
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Surface(shadowElevation = 8.dp) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = stringResource(titleRes),
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}

@Composable
fun ArtworkDescriptor(
    @StringRes titleRes: Int,
    @StringRes artistRes: Int,
    @StringRes yearRes: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        tonalElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(titleRes),
                style = MaterialTheme.typography.headlineMedium
            )
            Row {
                Text(
                    text = stringResource(artistRes),
                    fontWeight = FontWeight.Bold
                )
                Text(text = " (" + stringResource(yearRes) + ")")
            }
        }
    }
}

@Composable
fun DisplayController(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier

){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = onPreviousClick) {
            Text(text = stringResource(R.string.previous))
        }
        Button(onClick = onNextClick) {
            Text(text = stringResource(R.string.next))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}