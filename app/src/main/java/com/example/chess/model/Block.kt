package com.example.chess.model

import androidx.compose.runtime.MutableState
import com.example.chess.model.pieces.Piece

//a block when initialized takes a piece object.
//note that this is my atempt at a dependency injection
class Block (givenPiece: MutableState<Piece?>) {
    val piece = givenPiece
    fun changePiece(newPiece: Piece?){
        piece.value = newPiece
    }
}