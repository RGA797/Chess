package com.example.chess.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap.Companion.Square
import androidx.compose.ui.tooling.preview.Preview
import com.example.chess.ui.theme.ChessTheme
import com.example.chess.viewModel.Game

// this ui takes inspiration from : https://medium.com/bumble-tech/checkmate-on-compose-part-1-5a435419c51f
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(){
               // Greeting(name = "Android")
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    val game = Game()
    ChessTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}
//usestate
//setstate

@Composable
fun Board(){
    val game: MutableState<Game> = remember { mutableStateOf(Game())}
}


@Composable
fun Block(isBlack: Boolean, game: Game){
    Canvas(modifier = Modifier.fillMaxSize(1f)) {
        drawRect(color = if (isBlack) Color(0xFF383435) else Color(0xFFE4E4C3))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val game: MutableState<Game> = remember { mutableStateOf(Game())}
    MyApp {

        Board()
    }
}