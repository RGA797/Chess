package com.example.chess.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.chess.model.pieces.*

class Block (typeString: String?) {
    var piece: MutableState<Piece?> = mutableStateOf(null)
    init {
        when (typeString) {
            "white king" -> {
                piece = mutableStateOf(King("white"))
            }
            "white queen" -> {
                piece = mutableStateOf(Queen("white"))
            }
            "white pawn" -> {
                piece = mutableStateOf(Pawn("white"))
            }
            "white rook" -> {
                piece = mutableStateOf(Rook("white"))
            }
            "white knight" -> {
                piece = mutableStateOf(Knight("white"))
            }
            "white bishop" -> {
                piece = mutableStateOf(Bishop("white"))
            }
            "black king" -> {
                piece = mutableStateOf(King("black"))
            }
            "black queen" -> {
                piece = mutableStateOf(Queen("black"))
            }
            "black pawn" -> {
                piece = mutableStateOf(Pawn("black"))
            }
            "black rook" -> {
                piece = mutableStateOf(Rook("black"))
            }
            "black knight" -> {
                piece = mutableStateOf(Knight("black"))
            }
            "black bishop" -> {
                piece = mutableStateOf(Bishop("black"))
            }
        }
    }

    fun changePiece(newPiece: Piece?){
        piece.value = newPiece
    }
}