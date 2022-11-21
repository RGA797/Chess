package com.example.chess.model.UImodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.chess.model.Move

class UiValues {
    //is true if the player has clicked on a button before
    var firstClick = mutableStateOf(true)
    //the position of the last click
    var lastClickPosition = mutableStateListOf<Int>()
    //the list of possible moves
    var possibleMoves = mutableStateListOf<Move>()
    //the current recommended move
    var recommendedMove = mutableStateOf<String>("")
}