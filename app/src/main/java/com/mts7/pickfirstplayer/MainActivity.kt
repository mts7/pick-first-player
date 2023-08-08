package com.mts7.pickfirstplayer

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
            val numberOfPlayers = remember { mutableStateOf(0) }
            val player = remember { mutableStateOf(0) }

            PickFirstPlayerTheme {
                MainScreen(
                    numberOfPlayers.value,
                    player.value,
                    updateNumber = {
                        numberOfPlayers.value = it
                        if (it > 0) {
                            setRandomPlayer(player, numberOfPlayers.value)
                        }
                    },
                    onExit = { exitApplication() },
                    onRefresh = {
                        setRandomPlayer(player, numberOfPlayers.value)
                    },
                )
            }

            this.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (numberOfPlayers.value > 0) {
                        numberOfPlayers.value = 0
                    } else {
                        exitApplication()
                    }
                }
            })
        }
    }

    private fun exitApplication() {
        this@MainActivity.finishAndRemoveTask()
        exitProcess(0)
    }

    private fun setRandomPlayer(player: MutableState<Int>, maxPlayers: Int) {
        player.value = (1..maxPlayers).random()
    }
}

//@Composable
//fun ClickDonate() {
//    val context = LocalContext.current
//    val urlIntent = Intent(
//        Intent.ACTION_VIEW,
//        Uri.parse("https://www.paypal.com/donate/?hosted_button_id=ZNB83KNCMLDCA")
//    )
//    context.startActivity(urlIntent)
//}

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

@Composable
fun MainScreen(
    numberOfPlayers: Int,
    player: Int,
    updateNumber: (Int) -> Unit,
    onExit: () -> Unit,
    onRefresh: () -> Unit,
) {
    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomBar(
                onExit = onExit,
                displayReset = numberOfPlayers > 0,
                onResetClick = { updateNumber(0) },
            )
        },
    ) { contentPadding ->
        // unsure of what to do with the unused variable
        contentPadding
        val configuration = LocalConfiguration.current
        val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        if (numberOfPlayers > 0) {
            val (direction, places) = getRelationalValues(
                numberOfPlayers,
                player
            )
            ResultScreen(
                maxValue = numberOfPlayers,
                direction = direction,
                places = places,
                refreshSelection = onRefresh,
                isPortrait = isPortrait,
            )
        } else {
            MainLayout(onNumberClick = { updateNumber(it) }, isPortrait = isPortrait)
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    PickFirstPlayerTheme {
        MainScreen(0, player = 2, updateNumber = {}, onExit = {}, onRefresh = {})
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
                painter = painterResource(id = R.drawable.logo_light),
                contentDescription = "Pick First Player",
                modifier = Modifier
                    .size(72.dp)
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
fun BottomBar(
    onExit: () -> Unit,
    displayReset: Boolean,
    onResetClick: () -> Unit,
) {
    Surface(
        //color = MaterialTheme.colorScheme.tertiary,
        color = if (isSystemInDarkTheme()) Blue80 else Blue60,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
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
            } else {
                Spacer(
                    modifier = Modifier.width(146.dp)
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
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.mts7_logo_black_192),
                contentDescription = "mts7 logo",
                modifier = Modifier
                    .size(48.dp),
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomBarWithReset() {
    PickFirstPlayerTheme {
        BottomBar(onExit = {}, true, onResetClick = {})
    }
}

@Preview
@Composable
fun PreviewBottomBarWithoutReset() {
    PickFirstPlayerTheme {
        BottomBar(onExit = {}, false, onResetClick = {})
    }
}

@Composable
fun MainLayout(onNumberClick: (Int) -> Unit, isPortrait: Boolean) {
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(15))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Tap the number of players.",
                //color = MaterialTheme.colorScheme.secondary,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                textAlign = TextAlign.Center,
                //style = MaterialTheme.typography.bodyMedium,
                fontFamily = Lato,
                fontSize = 28.sp,
                lineHeight = 28.sp,
            )
            if (isPortrait) {
                Spacer(modifier = Modifier.height(16.dp))
            }
            ButtonGrid(onNumberClick)
        }
    }
}

@Preview
@Composable
fun PreviewMainLayoutPortrait() {
    PickFirstPlayerTheme {
        MainLayout(onNumberClick = {}, isPortrait = true)
    }
}

@Preview
@Composable
fun PreviewMainLayoutLandscape() {
    PickFirstPlayerTheme {
        MainLayout(onNumberClick = {}, isPortrait = false)
    }
}

@Composable
fun ButtonGrid(onNumberClick: (Int) -> Unit) {
    val numbers = (2..7).map { it.toString() }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(138.dp),
//        contentPadding = PaddingValues(
//            horizontal = 24.dp
//        ),
        ) {
            items(numbers.size) { index ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    NumberButton(value = numbers[index].toInt(), onNumberClick)
                }
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
fun PlayerDirection(direction: String, places: Int, refreshSelection: () -> Unit) {
    val rotation = when (direction) {
        "left" -> 0.0F
        "self" -> 270.0F
        "other" -> 90.0F
        else -> 180.0F
    }

    val isVertical = places == 0 || direction == "other"

    Button(
        modifier = Modifier
            .padding(
                horizontal = 24.dp,
                vertical = if (isVertical) 8.dp else 0.dp
            )
            .clickable { refreshSelection() },
        shape = RoundedCornerShape(15),
        colors = ButtonDefaults.buttonColors(
            //containerColor = MaterialTheme.colorScheme.primary,
            containerColor = if (isSystemInDarkTheme()) Blue80 else Blue20,
        ),
        onClick = refreshSelection,
    ) {
        Image(
            painter = painterResource(id = if (isSystemInDarkTheme()) R.drawable.white_arrow else R.drawable.black_arrow),
            contentDescription = "Back arrow from Nikita Gohel",
            modifier = Modifier
                .size(64.dp)
                .rotate(rotation)
        )
        if (!isVertical) {
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
            .padding(horizontal = 64.dp)
            .height(72.dp),
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
            PlayerDirection("left", 2, refreshSelection = {})
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
            PlayerDirection("right", 3, refreshSelection = {})
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
            PlayerDirection("other", 1, refreshSelection = {})
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
            PlayerDirection("self", 0, refreshSelection = {})
        }
    }
}

@Composable
fun ResultScreen(
    maxValue: Int,
    direction: String,
    places: Int,
    refreshSelection: () -> Unit,
    isPortrait: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        if (!isPortrait) {
            Spacer(modifier = Modifier.height(100.dp))
        }
        ChosenValue(maxValue)
        PlayerDirection(direction, places, refreshSelection)
        if (!isPortrait) {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Preview
@Composable
fun PreviewResultScreenPortrait() {
    PickFirstPlayerTheme {
        ResultScreen(
            maxValue = 6,
            direction = "left",
            places = 3,
            refreshSelection = {},
            isPortrait = true
        )
    }
}

@Preview
@Composable
fun PreviewResultScreenLandscape() {
    PickFirstPlayerTheme {
        ResultScreen(
            maxValue = 6,
            direction = "left",
            places = 3,
            refreshSelection = {},
            isPortrait = false
        )
    }
}
