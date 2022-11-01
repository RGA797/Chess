package com.example.chess.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap.Companion.Square
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chess.R
import com.example.chess.model.*
import com.example.chess.ui.theme.ChessTheme
import com.example.chess.viewModel.Game
import com.example.chess.viewModel.Screens
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


@Composable
fun SandboxBoard(game: Game, uiRemembrance: UiRemembrance) {
    Box() {
    Column() {
        var x = 1
        for (i in 7 downTo 0) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (j in 0..7) {
                    if (x %2 == 0) {
                        SandboxBlock(true, game, listOf(i, j),uiRemembrance)
                    }
                    else{
                        SandboxBlock(false, game, listOf(i, j),uiRemembrance)
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
fun AiBoard(game: Game, uiRemembrance: UiRemembrance) {
    Box() {
        Column() {
            var x = 1
            for (i in 7 downTo 0) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (j in 0..7) {
                        if (x %2 == 0) {
                            AiBlock(true, game, listOf(i, j),uiRemembrance)
                        }
                        else{
                            AiBlock(false, game, listOf(i, j),uiRemembrance)
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
fun UndoButton(gameObject: Game){
    Button(onClick = {gameObject.undoMove()}
        , modifier = Modifier
            .height(70.dp)
            .width(100.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
    ){Text(
        text = "Undo",
        modifier = Modifier.padding(bottom = 1.dp),
        style = MaterialTheme.typography.h5
    )}
}

@Composable
fun DoubleUndoButton(gameObject: Game){
    Button(onClick = {
        gameObject.undoMove()
        gameObject.undoMove()
                     }
        , modifier = Modifier
            .height(70.dp)
            .width(100.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
    ){Text(
        text = "Undo",
        modifier = Modifier.padding(bottom = 1.dp),
        style = MaterialTheme.typography.h5
    )}
}
@Composable
fun RecommendButton(gameObject: Game, uiRemembrance: UiRemembrance){
    Button(onClick = {
        val move: Move = gameObject.min(-10000000,1000000, 4)[1] as Move
        uiRemembrance.changeRecommendedMove("" + move.oldPosition[0]+","+move.oldPosition[1] + " ->" + move.newPosition[0]+","+move.newPosition[1])}
        , modifier = Modifier
            .height(80.dp)
            .width(200.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
    ){Text(
        text = "Recommend move",
        modifier = Modifier.padding(bottom = 1.dp),
        style = MaterialTheme.typography.h5
    )}
}

@Composable
fun RecommendDisplay(uiRemembrance: UiRemembrance){
    val textToShow = remember {uiRemembrance.recommendedMove}
        Text(
            text = textToShow.value,
            modifier = Modifier.padding(bottom = 1.dp),
            style = MaterialTheme.typography.h5,
        )
    }


@Composable
fun AiBlock(isBlack: Boolean, gameObject: Game, position: List<Int>, uiRemembrance: UiRemembrance){
    val block = remember { gameObject.board[position[0]][position[1]] }
    var firstClick = remember {uiRemembrance.firstClick}
    var lastClickPosition = remember {uiRemembrance.lastClickPosition}
    var possibleMoves = remember {uiRemembrance.possibleMoves}
    val blockColor = remember{ mutableStateOf(Color.White)}
    if (isBlack){
        blockColor.value = Color.Gray
    }
    else{ blockColor.value = Color.Yellow}
    Button(onClick = {
        if (firstClick.value){
            if (block.piece.value != null) {
                if (block.piece.value!!.team == "white") {
                    uiRemembrance.changeLastClickPosition(position)
                    uiRemembrance.changeFirstClick(!firstClick.value)
                    uiRemembrance.changepossibleMoves(
                        gameObject.getValidMoves(
                            gameObject.board,
                            block.piece.value!!.team
                        )
                    )
                }
            }
        }
        else{
            if (lastClickPosition[0] != position[0] || lastClickPosition[1] != position[1]) {
                for (i in possibleMoves.indices) {
                    if (possibleMoves[i].newPosition[0] == position[0] && possibleMoves[i].newPosition[1] == position[1]) {
                        if (possibleMoves[i].oldPosition[0] == lastClickPosition[0] && possibleMoves[i].oldPosition[1] == lastClickPosition[1]){
                            gameObject.resolveMove(possibleMoves[i])
                            uiRemembrance.changeFirstClick(!firstClick.value)
                            gameObject.resolveMove(gameObject.max(-10000000,1000000, 4)[1] as Move)
                            break
                        }
                    }
                }
                if (block.piece.value == null){
                    uiRemembrance.changeFirstClick(!firstClick.value)
                }
            }
            else{
                uiRemembrance.changeFirstClick(!firstClick.value)
            }
        }
    } ,modifier = Modifier
        .height(50.dp)
        .width(48.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = blockColor.value),
        shape = RectangleShape
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

private val showAiMode = mutableStateOf(false)
private val showSandboxMode = mutableStateOf(false)
@Composable
fun Menu(gameObject: Game, uiRemembrance: UiRemembrance) {
    val navController = rememberNavController()
    Column() {
        Button(onClick = {//todo naviate to the ai mode
        } ,modifier = Modifier
            .height(50.dp)
            .width(48.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor =Color.Cyan),
            shape = RectangleShape
        ){
            Text(text = "Versus AI Mode")
        }
        Button(onClick = {//todo naviate to the sandbox mode
        } ,modifier = Modifier
            .height(50.dp)
            .width(48.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor =Color.Cyan),
            shape = RectangleShape
        ){
            Text(text = "Sandbox Mode")
        }
    }

}


@Composable
fun SandboxBlock(isBlack: Boolean, gameObject: Game, position: List<Int>, uiRemembrance: UiRemembrance){
    val block = remember { gameObject.board[position[0]][position[1]] }
    var firstClick = remember {uiRemembrance.firstClick}
    var lastClickPosition = remember {uiRemembrance.lastClickPosition}
    var possibleMoves = remember {uiRemembrance.possibleMoves}
    val blockColor = remember{ mutableStateOf(Color.White)}
    if (isBlack){
        blockColor.value = Color.Gray
    }
    else{ blockColor.value = Color.Yellow}
        Button(onClick = {
            if (firstClick.value){
                if (block.piece.value != null) {
                    uiRemembrance.changeLastClickPosition(position)
                    uiRemembrance.changeFirstClick(!firstClick.value)
                    uiRemembrance.changepossibleMoves(gameObject.getValidMoves(gameObject.board,block.piece.value!!.team))
                }
            }
            else{
                if (lastClickPosition[0] != position[0] || lastClickPosition[1] != position[1]) {
                    for (i in possibleMoves.indices) {
                        if (possibleMoves[i].newPosition[0] == position[0] && possibleMoves[i].newPosition[1] == position[1]) {
                            if (possibleMoves[i].oldPosition[0] == lastClickPosition[0] && possibleMoves[i].oldPosition[1] == lastClickPosition[1]){
                                gameObject.resolveMove(possibleMoves[i])
                                uiRemembrance.changeFirstClick(!firstClick.value)
                                break
                            }
                        }
                    }
                    if (block.piece.value == null){
                        uiRemembrance.changeFirstClick(!firstClick.value)
                    }
                }
                else{
                    uiRemembrance.changeFirstClick(!firstClick.value)
                }
            }
        } ,modifier = Modifier
            .height(50.dp)
            .width(48.dp),
    colors = ButtonDefaults.buttonColors(backgroundColor = blockColor.value),
            shape = RectangleShape
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

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.teal_700))
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "Home Screen",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )

    }
}


@Composable
fun AiMode(game: Game, uiRemembrance: UiRemembrance){
    Column() {
        AiBoard(game, uiRemembrance)
        DoubleUndoButton(gameObject = game)
        RecommendButton(gameObject = game, uiRemembrance = uiRemembrance )
        RecommendDisplay(uiRemembrance = uiRemembrance)
    }
}

@Composable
fun SandboxMode(game: Game, uiRemembrance: UiRemembrance){
    Column() {
        SandboxBoard(game, uiRemembrance)
        UndoButton(gameObject = game)
        RecommendButton(gameObject = game, uiRemembrance = uiRemembrance )
        RecommendDisplay(uiRemembrance = uiRemembrance)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val game = Game()
    val uiRemembrance = UiRemembrance()
    MyApp {
        //Menu(game,uiRemembrance)
        //AiMode(game = game, uiRemembrance = uiRemembrance)
        SandboxMode(game = game, uiRemembrance = uiRemembrance)
    }
}