package com.mts7.pickfirstplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mts7.pickfirstplayer.ui.theme.PickFirstPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PickFirstPlayerTheme {
                MainLayout()
            }
        }
    }
}

@Composable
fun MainLayout() {
    Column {
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
        Text(
            text = "Tap the number of players.",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 48.sp,
            lineHeight = 50.sp,
            textAlign = TextAlign.Center,
        )
        ButtonGrid(numbers = listOf(2, 3, 4, 5, 6, 7))
    }
}

@Composable
fun ButtonGrid(numbers: List<Int>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            NumberButton(value = numbers[0])
            NumberButton(value = numbers[1])
            NumberButton(value = numbers[2])
        }
        Spacer(
            modifier = Modifier.padding(
                vertical = 8.dp,
            )
        )
        Row {
            NumberButton(value = numbers[3])
            NumberButton(value = numbers[4])
            NumberButton(value = numbers[5])
        }
    }
}

@Composable
fun NumberButton(value: Int) {
    Spacer(modifier = Modifier.padding(horizontal = 8.dp))
    Surface() {
        Text(
            text = value.toString(),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp,
                )
                .width(96.dp),
            fontSize = 128.sp,
            textAlign = TextAlign.Center,
        )
    }
}
