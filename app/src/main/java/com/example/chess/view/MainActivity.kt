package com.example.chess.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chess.ui.theme.ChessTheme

// this ui takes inspiration from : https://medium.com/bumble-tech/checkmate-on-compose-part-1-5a435419c51f
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                Navigation()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
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
    MyApp {
        Navigation()
        //Menu(game,uiRemembrance)
        //AiMode(game = game, uiRemembrance = uiRemembrance)
        //SandboxMode(game = game, uiRemembrance = uiRemembrance)
    }
}