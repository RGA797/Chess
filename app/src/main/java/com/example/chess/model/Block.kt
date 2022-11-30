package com.example.chess.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.chess.model.pieces.*

class Block (typeString: String?) {
    var piece: MutableState<Piece?> = mutableStateOf(null)
    init {
        if (typeString == "white king") {
            piece = mutableStateOf(King("white"))
        }
        else if (typeString == "white queen") {
            piece = mutableStateOf(Queen("white"))
        }
        else if (typeString == "white pawn") {
            piece = mutableStateOf(Pawn("white"))
        }
        else if (typeString == "white rook") {
            piece = mutableStateOf(Rook("white"))
        }
        else if  (typeString == "white knight") {
            piece = mutableStateOf(Knight("white"))
        }
        else if  (typeString == "white bishop") {
            piece = mutableStateOf(Bishop("white"))
        }
        else if (typeString == "black king") {
            piece = mutableStateOf(King("black"))
        }
        else if (typeString == "black queen") {
            piece = mutableStateOf(Queen("black"))
        }
        else if (typeString == "black queen") {
            piece = mutableStateOf(Queen("black"))
        }
        else if (typeString == "black pawn") {
            piece = mutableStateOf(Pawn("black"))
        }
        else if (typeString == "black rook") {
            piece = mutableStateOf(Rook("black"))
        }
        else if (typeString == "black knight") {
            piece = mutableStateOf(Knight("black"))
        }
        else if (typeString == "black bishop") {
            piece = mutableStateOf(Bishop("black"))
        }
    }

    fun changePiece(newPiece: Piece?){
        piece.value = newPiece
    }
}