package com.mts7.pickfirstplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mts7.pickfirstplayer.ui.theme.PickFirstPlayerTheme
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PickFirstPlayerTheme {
                MainScreen(onExit = { exitApplication() })
            }
        }
    }

    private fun exitApplication() {
        this@MainActivity.finishAndRemoveTask()
        exitProcess(0)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onExit: () -> Unit) {
    val numberOfPlayers = remember { mutableStateOf(0) }

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomBar(
                onExit = onExit,
                displayReset = numberOfPlayers.value > 0,
                onResetClick = { numberOfPlayers.value = 0 })
        },
    ) { contentPadding ->
        // unused variable/expression
        contentPadding
        if (numberOfPlayers.value > 0) {
            val (direction, places) = getRelationalValues(
                numberOfPlayers.value,
                generateRandomNumber(numberOfPlayers.value)
            )
            DisplayRandomPlayer(
                maxValue = numberOfPlayers.value,
                direction = direction,
                places = places
            )
        } else {
            MainLayout(onNumberClick = { numberOfPlayers.value = it })
        }
    }
}

@Composable
fun TopBar() {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_meeples),
                contentDescription = "Pick First Player",
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(15)),
            )
        }
    }
}

@Preview
@Composable
fun PreviewTopBar() {
    TopBar()
}

@Composable
fun BottomBar(onExit: () -> Unit, displayReset: Boolean, onResetClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            if (displayReset) {
                ElevatedButton(onClick = onResetClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Reset",
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = "Reset",
                        fontSize = 24.sp
                    )
                }
                Spacer(
                    modifier = Modifier.width(64.dp)
                )
            }
            ElevatedButton(
                onClick = onExit,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Exit",
                    fontSize = 24.sp,
                )
            }
            Image(
                painter = painterResource(id = R.drawable.mts7_logo_black_192),
                contentDescription = "mts7 logo",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomBar() {
    BottomBar(onExit = {}, true, onResetClick = {})
}

@Composable
fun MainLayout(onNumberClick: (Int) -> Unit) {
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(15))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(128.dp))
            Text(
                text = "Tap the number of players.",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 32.sp,
                lineHeight = 50.sp,
                modifier = Modifier.padding(horizontal = 8.dp),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(24.dp))
            ButtonGrid(numbers = listOf(2, 3, 4, 5, 6, 7), onNumberClick)
        }
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
            Spacer(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                )
            )
            NumberButton(value = numbers[1], onNumberClick)
        }
        Spacer(
            modifier = Modifier.padding(
                vertical = 8.dp,
            )
        )
        Row {
            NumberButton(value = numbers[2], onNumberClick)
            Spacer(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                )
            )
            NumberButton(value = numbers[3], onNumberClick)
        }
        Spacer(
            modifier = Modifier.padding(
                vertical = 8.dp,
            )
        )
        Row {
            NumberButton(value = numbers[4], onNumberClick)
            Spacer(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                )
            )
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
    Button(
        onClick = { onNumberClick(value) },
        shape = RoundedCornerShape(15),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = value.toString(),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .padding(
                    horizontal = 24.dp,
                    vertical = 4.dp,
                ),
            fontSize = 96.sp,
            textAlign = TextAlign.Center,
        )
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

fun getRelationalValues(maxCount: Int, player: Int): Pair<String, Int> {
    if (player == 1) {
        return Pair("self", 0)
    }

    val half = kotlin.math.ceil(maxCount.toDouble() / 2)

    if (player > half) {
        return Pair("right", maxCount + 1 - player)
    }
    return Pair("left", player - 1)
}

fun getRelationalWording(direction: String, places: Int): String {
    if (places == 0) {
        return "You go first."
    }

    if (places == 1) {
        return "The player on your $direction goes first."
    }

    return "The player $places to your $direction goes first."
}

@Composable
fun DisplayRandomPlayer(maxValue: Int, direction: String, places: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(128.dp))
        Surface {
            Text(
                text = "Number of players",
                fontSize = 32.sp,
                modifier = Modifier.padding(horizontal = 48.dp),
            )
        }
        Spacer(modifier = Modifier.height(48.dp))
        Surface(
            shape = RoundedCornerShape(15)
        ) {
            Text(
                text = "$maxValue",
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.secondary)
                    .padding(
                        horizontal = 24.dp,
                        vertical = 4.dp,
                    )
                    .width(96.dp),
                fontSize = 96.sp,
                textAlign = TextAlign.Center,
            )
        }
        Spacer(
            modifier = Modifier.height(64.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.right_arrow_44621),
                contentDescription = "<a href=\"https://www.freepik.com/icon/right-arrow_44621\">Icon by Freepik</a>",
                modifier = Modifier
                    .size(64.dp)
                    .rotate(
                        when (direction) {
                            "left" -> 180.0F
                            "self" -> 90.0F
                            else -> 0.0F
                        }
                    ),
            )
            Spacer(
                modifier = Modifier.width(24.dp)
            )
            Text(
                text = if (places > 0) "$places" else "",
                fontSize = 96.sp,
            )
        }
        Text(
            text = getRelationalWording(direction, places),
            modifier = Modifier.padding(24.dp),
            fontSize = 32.sp,
            lineHeight = 32.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewDisplayRandomPlayer() {
    DisplayRandomPlayer(maxValue = 6, direction = "left", places = 3)
}
