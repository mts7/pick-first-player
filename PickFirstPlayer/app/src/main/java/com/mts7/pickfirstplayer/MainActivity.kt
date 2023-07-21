package com.mts7.pickfirstplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
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
    ) { _ ->
        if (numberOfPlayers.value > 0) {
            DisplayRandomPlayer(
                maxValue = numberOfPlayers.value,
                onResetClick = { numberOfPlayers.value = 0 },
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

@Composable
fun BottomBar() {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = "App by Mike Rodarte",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = painterResource(id = R.drawable.mts7_logo_black_192),
                    contentDescription = "mts7 logo",
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
    }
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

@Composable
fun DisplayRandomPlayer(maxValue: Int, onResetClick: () -> Unit) {
    val player = (1..maxValue).random()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = player.toString(),
            modifier = Modifier.padding(24.dp),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 128.sp,
        )
        ElevatedButton(onClick = onResetClick) {
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
    DisplayRandomPlayer(maxValue = 6, onResetClick = {})
}