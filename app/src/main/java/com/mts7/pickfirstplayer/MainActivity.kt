package com.mts7.pickfirstplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mts7.pickfirstplayer.ui.theme.Blue20
import com.mts7.pickfirstplayer.ui.theme.Blue60
import com.mts7.pickfirstplayer.ui.theme.Blue80
import com.mts7.pickfirstplayer.ui.theme.Lato
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

fun generateRandomNumber(maxValue: Int): Int {
    return (1..maxValue).random()
}

fun getRelationalValues(maxCount: Int, player: Int): Pair<String, Int> {
    if (player == 1) {
        return Pair("self", 0)
    }

    if (maxCount == 2 && player == 2) {
        return Pair("other", 1)
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
        if (direction == "other") {
            return "The other player goes first."
        }

        return "The player on your $direction goes first."
    }

    return "The player $places to your $direction goes first."
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
            ResultScreen(
                maxValue = numberOfPlayers.value,
                direction = direction,
                places = places
            )
        } else {
            MainLayout(onNumberClick = { numberOfPlayers.value = it })
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    PickFirstPlayerTheme {
        MainScreen(onExit = {})
    }
}

@Composable
fun TopBar() {
    Surface(
        //color = MaterialTheme.colorScheme.tertiary,
        color = if (isSystemInDarkTheme()) Blue80 else Blue60,
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
    PickFirstPlayerTheme {
        TopBar()
    }
}

@Composable
fun BottomBar(onExit: () -> Unit, displayReset: Boolean, onResetClick: () -> Unit) {
    Surface(
        //color = MaterialTheme.colorScheme.tertiary,
        color = if (isSystemInDarkTheme()) Blue80 else Blue60,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 30.dp),

            ) {
            if (displayReset) {
                ElevatedButton(
                    onClick = onResetClick,
                    //colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isSystemInDarkTheme()) Blue60 else Blue80),
                    shape = RoundedCornerShape(15),
                    modifier = Modifier
                        .width(128.dp)
                        .height(46.dp),
                ) {
                    Text(
                        text = "Reset",
                        //color = MaterialTheme.colorScheme.onPrimary,
                        color = if (isSystemInDarkTheme()) Color.Black else Color.White,
                        //style = MaterialTheme.typography.labelMedium,
                        fontFamily = Lato,
                        fontSize = 18.sp,
                    )
                }
                Spacer(
                    modifier = Modifier.width(16.dp)
                )
            }
            ElevatedButton(
                onClick = onExit,
                modifier = Modifier
                    .width(128.dp)
                    .height(46.dp),
                shape = RoundedCornerShape(15),
                //colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                colors = ButtonDefaults.buttonColors(containerColor = if (isSystemInDarkTheme()) Blue60 else Blue80),
            ) {
                Text(
                    text = "Exit",
                    style = MaterialTheme.typography.labelMedium,
                    //color = MaterialTheme.colorScheme.onPrimary,
                    color = if (isSystemInDarkTheme()) Color.Black else Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .wrapContentHeight(),
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
    PickFirstPlayerTheme {
        BottomBar(onExit = {}, true, onResetClick = {})
    }
}

@Composable
fun MainLayout(onNumberClick: (Int) -> Unit) {
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(15))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(144.dp))
            Text(
                text = "Tap the number of players.",
                //color = MaterialTheme.colorScheme.secondary,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                textAlign = TextAlign.Center,
                //style = MaterialTheme.typography.bodyMedium,
                fontFamily = Lato,
                fontSize = 28.sp,
                lineHeight = 28.sp,
                //modifier = Modifier.padding(horizontal = 12.dp),
            )
            Spacer(modifier = Modifier.height(24.dp))
            ButtonGrid(onNumberClick)
        }
    }
}

@Preview
@Composable
fun PreviewMainLayout() {
    PickFirstPlayerTheme {
        MainLayout(onNumberClick = {})
    }
}

@Composable
fun ButtonGrid(onNumberClick: (Int) -> Unit) {
    val numbers = (2..7).map { it.toString() }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
    ) {
        items(numbers.size) { index ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                NumberButton(value = numbers[index].toInt(), onNumberClick)
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewButtonGrid() {
    PickFirstPlayerTheme {
        ButtonGrid(onNumberClick = {})
    }
}

@Composable
fun NumberButton(value: Int, onNumberClick: (Int) -> Unit) {
    Button(
        onClick = { onNumberClick(value) },
        shape = RoundedCornerShape(15),
        colors = ButtonDefaults.buttonColors(
            //containerColor = MaterialTheme.colorScheme.primary,
            containerColor = if (isSystemInDarkTheme()) Blue80 else Blue20,
        ),
    ) {
        Text(
            text = value.toString(),
            //color = MaterialTheme.colorScheme.secondary,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            modifier = Modifier
                .height(96.dp)
                .width(72.dp)
                .wrapContentHeight(),
            //style = MaterialTheme.typography.displayLarge,
            fontFamily = Lato,
            fontSize = 62.sp,
            lineHeight = 66.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun PreviewNumberButton() {
    PickFirstPlayerTheme {
        NumberButton(value = 7, onNumberClick = {})
    }
}

@Composable
fun ChosenValue(maxValue: Int) {
    Surface {
        Text(
            text = "Number of players: $maxValue",
            //color = MaterialTheme.colorScheme.secondary,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
            style = MaterialTheme.typography.bodyMedium,
            //modifier = Modifier.padding(horizontal = 48.dp),
        )
    }
    Spacer(modifier = Modifier.height(48.dp))
}

@Preview
@Composable
fun PreviewChosenValue() {
    PickFirstPlayerTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            ChosenValue(maxValue = 5)
        }
    }
}

@Composable
fun PlayerDirection(direction: String, places: Int) {
    val rotation = when (direction) {
        "left" -> 0.0F
        "self" -> 270.0F
        "other" -> 90.0F
        else -> 180.0F
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(
            horizontal = 24.dp,
            vertical = if (places == 0 || direction == "other") 24.dp else 0.dp
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = "<a href=\"https://www.freepik.com/icon/right-arrow_44621\">Icon by Freepik</a>",
            modifier = Modifier
                .size(64.dp)
                .rotate(rotation)
        )
        if (places > 0 && direction != "other") {
            Spacer(
                modifier = Modifier.width(24.dp)
            )
            Text(
                text = "$places",
                //color = MaterialTheme.colorScheme.secondary,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                style = MaterialTheme.typography.displayLarge,
            )
        }
    }
    Text(
        text = getRelationalWording(direction, places),
        modifier = Modifier
            .padding(24.dp)
            .width(248.dp),
        //style = MaterialTheme.typography.bodyMedium,
        fontFamily = Lato,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        //color = MaterialTheme.colorScheme.secondary,
        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
fun PreviewPlayerDirectionLeft() {
    PickFirstPlayerTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            PlayerDirection("left", 2)
        }
    }
}

@Preview
@Composable
fun PreviewPlayerDirectionRight() {
    PickFirstPlayerTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            PlayerDirection("right", 3)
        }
    }
}

@Preview
@Composable
fun PreviewPlayerDirectionOther() {
    PickFirstPlayerTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            PlayerDirection("other", 1)
        }
    }
}

@Preview
@Composable
fun PreviewPlayerDirectionSelf() {
    PickFirstPlayerTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            PlayerDirection("self", 0)
        }
    }
}

@Composable
fun ResultScreen(maxValue: Int, direction: String, places: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(144.dp))
        ChosenValue(maxValue)
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        PlayerDirection(direction, places)
    }
}

@Preview
@Composable
fun PreviewResultScreen() {
    PickFirstPlayerTheme {
        ResultScreen(maxValue = 6, direction = "left", places = 3)
    }
}
