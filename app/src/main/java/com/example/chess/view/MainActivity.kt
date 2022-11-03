package com.example.chess.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.chess.R
import com.example.chess.model.*
import com.example.chess.ui.theme.ChessTheme
import com.example.chess.viewModel.Game
import com.example.chess.viewModel.UiRemembrance

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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val game = Game()
    val uiRemembrance = UiRemembrance()
    MyApp {
        Navigation()
        //Menu(game,uiRemembrance)
        //AiMode(game = game, uiRemembrance = uiRemembrance)
        //SandboxMode(game = game, uiRemembrance = uiRemembrance)
    }
}