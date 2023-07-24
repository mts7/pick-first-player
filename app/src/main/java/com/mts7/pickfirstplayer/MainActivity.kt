package com.mts7.pickfirstplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mts7.pickfirstplayer.ui.theme.PickFirstPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PickFirstPlayerTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val numberOfPlayers = remember { mutableStateOf(0) }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar() },
    ) { contentPadding ->
        // unused variable/expression
        contentPadding
        if (numberOfPlayers.value > 0) {
            DisplayRandomPlayer(
                maxValue = numberOfPlayers.value,
                player = generateRandomNumber(numberOfPlayers.value),
                onResetClick = { numberOfPlayers.value = 0 }
            )
        } else {
            MainLayout(onNumberClick = { numberOfPlayers.value = it })
        }
    }
}

@Composable
fun TopBar() {
    Surface(
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Pick First Player",
            fontSize = 18.sp,
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 10.dp
            )
        )
    }
}

@Preview
@Composable
fun PreviewTopBar() {
    TopBar()
}

@Composable
fun BottomBar() {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(
                top = 12.dp,
                bottom = 8.dp,
            ),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(id = R.drawable.mts7_logo_black_192),
                contentDescription = "mts7 logo",
                modifier = Modifier
                    .size(48.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomBar() {
    BottomBar()
}

@Composable
fun MainLayout(onNumberClick: (Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Tap the number of players.",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 48.sp,
            lineHeight = 50.sp,
            modifier = Modifier.padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))
        ButtonGrid(numbers = listOf(2, 3, 4, 5, 6, 7), onNumberClick)
    }
}

@Preview
@Composable
fun PreviewMainLayout() {
    MainLayout(onNumberClick = {})
}

@Composable
fun ButtonGrid(numbers: List<Int>, onNumberClick: (Int) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            NumberButton(value = numbers[0], onNumberClick)
            NumberButton(value = numbers[1], onNumberClick)
            NumberButton(value = numbers[2], onNumberClick)
        }
        Spacer(
            modifier = Modifier.padding(
                vertical = 8.dp,
            )
        )
        Row {
            NumberButton(value = numbers[3], onNumberClick)
            NumberButton(value = numbers[4], onNumberClick)
            NumberButton(value = numbers[5], onNumberClick)
        }
    }
}

@Preview
@Composable
fun PreviewButtonGrid() {
    ButtonGrid(listOf(2, 3, 4, 5, 6, 7)) {}
}

@Composable
fun NumberButton(value: Int, onNumberClick: (Int) -> Unit) {
    Spacer(modifier = Modifier.padding(horizontal = 8.dp))
    Surface {
        ElevatedButton(
            onClick = { onNumberClick(value) }
        ) {
            Text(
                text = value.toString(),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(
                        horizontal = 4.dp,
                        vertical = 4.dp,
                    )
                    .width(56.dp),
                fontSize = 128.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun PreviewNumberButton() {
    NumberButton(value = 7, onNumberClick = {})
}

fun generateRandomNumber(maxValue: Int): Int {
    return (1..maxValue).random()
}

fun getRelationalWording(maxCount: Int, player: Int): String {
    if (player == 1) {
        return "You go first."
    }

    val half = kotlin.math.ceil(maxCount.toDouble() / 2)
    val direction: String
    val places: Int

    if (player > half) {
        direction = "right"
        places = maxCount + 1 - player
    } else {
        direction = "left"
        places = player - 1
    }

    return "The player $places to your $direction goes first."
}

@Composable
fun DisplayRandomPlayer(maxValue: Int, player: Int, onResetClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Surface(
            color = MaterialTheme.colorScheme.surface,
        ) {
            Text(
                text = "Number of players: $maxValue",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 32.sp,
                modifier = Modifier.padding(horizontal = 48.dp),
            )
        }
        Spacer(
            modifier = Modifier.height(96.dp)
        )
        Text(
            text = getRelationalWording(maxValue, player),
            modifier = Modifier.padding(24.dp),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 64.sp,
            lineHeight = 64.sp,
            textAlign = TextAlign.Center
        )
        ElevatedButton(onClick = onResetClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Reset",
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                text = "Reset",
                fontSize = 32.sp
            )
        }
    }
}

@Preview
@Composable
fun PreviewDisplayRandomPlayer() {
    DisplayRandomPlayer(maxValue = 6, player = 3, onResetClick = {})
}