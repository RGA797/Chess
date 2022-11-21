package com.example.chess.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.chess.model.pieces.*
import com.example.chess.viewModel.Game
import com.example.chess.viewModel.UiViewModel

@Composable
fun SandboxMode(game: Game, uiViewModel: UiViewModel){
    Column() {
        SandboxBoard(game, uiViewModel)
        UndoButton(gameObject = game)
        RecommendButton(gameObject = game, uiViewModel = uiViewModel )
        RecommendDisplay(uiViewModel = uiViewModel)
    }
}
@Composable
fun SandboxBlock(isBlack: Boolean, gameObject: Game, position: List<Int>, uiViewModel: UiViewModel){
    val block = remember { gameObject.board[position[0]][position[1]] }
    val firstClick = remember {uiViewModel.uiModel.firstClick}
    val lastClickPosition = remember {uiViewModel.uiModel.lastClickPosition}
    val possibleMoves = remember {uiViewModel.uiModel.possibleMoves}
    val blockColor = remember{ mutableStateOf(Color.White) }
    if (isBlack){
        blockColor.value = Color.Gray
    }
    else{ blockColor.value = Color.Yellow}
    Button(onClick = {
        if (firstClick.value){
            if (block.piece.value != null) {
                uiViewModel.changeLastClickPosition(position)
                uiViewModel.changeFirstClick(!firstClick.value)
                uiViewModel.changepossibleMoves(gameObject.getValidMoves(gameObject.board,block.piece.value!!.team))
            }
        }
        else{
            if (lastClickPosition[0] != position[0] || lastClickPosition[1] != position[1]) {
                for (i in possibleMoves.indices) {
                    if (possibleMoves[i].newPosition[0] == position[0] && possibleMoves[i].newPosition[1] == position[1]) {
                        if (possibleMoves[i].oldPosition[0] == lastClickPosition[0] && possibleMoves[i].oldPosition[1] == lastClickPosition[1]){
                            gameObject.resolveMove(possibleMoves[i])
                            uiViewModel.changeFirstClick(!firstClick.value)
                            break
                        }
                    }
                }
                if (block.piece.value == null){
                    uiViewModel.changeFirstClick(!firstClick.value)
                }
            }
            else{
                uiViewModel.changeFirstClick(!firstClick.value)
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
fun SandboxBoard(game: Game, uiViewModel: UiViewModel) {
    Box() {
        Column() {
            var x = 1
            for (i in 7 downTo 0) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (j in 0..7) {
                        if (x %2 == 0) {
                            SandboxBlock(true, game, listOf(i, j),uiViewModel)
                        }
                        else{
                            SandboxBlock(false, game, listOf(i, j),uiViewModel)
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
