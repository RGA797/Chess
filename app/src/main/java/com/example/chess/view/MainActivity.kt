package com.example.chess.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap.Companion.Square
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chess.model.*
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
fun HelloContedddntt() {
    Column(modifier = Modifier.padding(16.dp)) {
        var name by remember { mutableStateOf("") }
        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h5
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
    }
}
@Composable
fun Board() {
    val game = Game()
    Box() {
    Column() {
        var x = 0
        for (i in 0..7) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (j in 0..7) {
                    if (x %2 == 0) {
                        Block(true, game, listOf(i, j))
                    }
                    else{
                        Block(false, game, listOf(i, j))
                    }
                    x++
                    if (j == 7){
                        x++
                    }
                }

            }
        }
    }
    }
}

@Composable
fun Block(isBlack: Boolean, gameObject: Game, position: List<Int>){
    var block = remember { gameObject.board[position[0]][position[1]] }
    var blockColor: Color
    if (isBlack){
        blockColor = Color.Gray
    }
    else{ blockColor = Color.Yellow}
        Button(onClick = {
            //your onclick code here
            gameObject.resolveMove(Move(position, listOf(0, 5), false, null, null))
        } ,modifier = Modifier
            .height(50.dp)
            .width(50.dp),
    colors = ButtonDefaults.buttonColors(backgroundColor = blockColor)
        )

    {
            if (block.piece.value == null) {
                Text(
                    text = "",
                    modifier = Modifier.padding(bottom = 1.dp),
                    style = MaterialTheme.typography.h5
                )
            }
            if (block.piece.value is Rook) {
                if ((block.piece.value as Rook).team == "white") {
                    Text(
                        text = "♖",
                        modifier = Modifier.padding(bottom = 1.dp),
                        style = MaterialTheme.typography.h5
                    )
                }
                if ((block.piece.value as Rook).team == "black") {
                    Text(
                        text = "♜",
                        modifier = Modifier.padding(bottom = 1.dp),
                        style = MaterialTheme.typography.h5
                    )
                }
            }
            if (block.piece.value is Queen) {
                if ((block.piece.value as Queen).team == "white") {
                    Text(
                        text = "♕",
                        modifier = Modifier.padding(bottom = 1.dp),
                        style = MaterialTheme.typography.h5
                    )
                }
                if ((block.piece.value as Queen).team == "black") {
                    Text(
                        text = "♛",
                        modifier = Modifier.padding(bottom = 1.dp),
                        style = MaterialTheme.typography.h5
                    )
                }
            }
            if (block.piece.value is King) {
                if ((block.piece.value as King).team == "white") {
                    Text(
                        text = "♔",
                        modifier = Modifier.padding(bottom = 1.dp),
                        style = MaterialTheme.typography.h5
                    )
                }
                if ((block.piece.value as King).team == "black") {
                    Text(
                        text = "♚",
                        modifier = Modifier.padding(bottom = 1.dp),
                        style = MaterialTheme.typography.h5
                    )
                }
            }
            if (block.piece.value is Knight) {
                if ((block.piece.value as Knight).team == "white") {
                    Text(
                        text = "♘",
                        modifier = Modifier.padding(bottom = 1.dp),
                        style = MaterialTheme.typography.h5
                    )
                }
                if ((block.piece.value as Knight).team == "black") {
                    Text(
                        text = "♞",
                        modifier = Modifier.padding(bottom = 1.dp),
                        style = MaterialTheme.typography.h5
                    )
                }
            }
            if (block.piece.value is Pawn) {
                if ((block.piece.value as Pawn).team == "white") {
                    Text(
                        text = "♙",
                        modifier = Modifier.padding(bottom = 1.dp),
                        style = MaterialTheme.typography.h5
                    )
                }
                if ((block.piece.value as Pawn).team == "black") {
                    Text(
                        text = "♟︎",
                        modifier = Modifier.padding(bottom = 1.dp),
                        style = MaterialTheme.typography.h5
                    )
                }
            }
            if (block.piece.value is Bishop) {
                if ((block.piece.value as Bishop).team == "white") {
                    Text(
                        text = "♗",
                        modifier = Modifier.padding(bottom = 1.dp),
                        style = MaterialTheme.typography.h5
                    )
                }
                if ((block.piece.value as Bishop).team == "black") {
                    Text(
                        text = "♝",
                        modifier = Modifier.padding(bottom = 1.dp),
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        Board()
    }
}