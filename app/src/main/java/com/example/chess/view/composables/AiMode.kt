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
import com.example.chess.model.*
import com.example.chess.model.pieces.*
import com.example.chess.viewModel.Game
import com.example.chess.viewModel.UiRemembrance

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
fun DoubleUndoButton(gameObject: Game){
    Button(onClick = {
        gameObject.undoMove()
        gameObject.undoMove()
    }
        , modifier = Modifier
            .height(70.dp)
            .width(100.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
    ){
        Text(
        text = "Undo",
        modifier = Modifier.padding(bottom = 1.dp),
        style = MaterialTheme.typography.h5
    )
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
fun AiBlock(isBlack: Boolean, gameObject: Game, position: List<Int>, uiRemembrance: UiRemembrance){
    val block = remember { gameObject.board[position[0]][position[1]] }
    val firstClick = remember {uiRemembrance.firstClick}
    val lastClickPosition = remember {uiRemembrance.lastClickPosition}
    val possibleMoves = remember {uiRemembrance.possibleMoves}
    val blockColor = remember{ mutableStateOf(Color.White) }
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