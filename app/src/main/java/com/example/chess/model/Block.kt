package com.example.chess.model

class Block (typeString: String?) {
    var piece: Piece? = null
    init {
        if (typeString == "white king") {
            piece = King("white")
        }
        else if (typeString == "white queen") {
            piece = Queen("white")
        }
        else if (typeString == "white pawn") {
            piece = Pawn("white")
        }
        else if (typeString == "white rook") {
            piece = Rook("white")
        }
        else if  (typeString == "white knight") {
            piece = Knight("white")
        }
        else if  (typeString == "white bishop") {
            piece = Bishop("white")
        }
        else if (typeString == "black king") {
            piece = King("black")
        }
        else if (typeString == "black queen") {
            piece = Queen("black")
        }
        else if (typeString == "black queen") {
            piece = Queen("black")
        }
        else if (typeString == "black pawn") {
            piece = Pawn("black")
        }
        else if (typeString == "black rook") {
            piece = Rook("black")
        }
        else if (typeString == "black knight") {
            piece = Knight("black")
        }
        else if (typeString == "black bishop") {
            piece = Bishop("black")
        }
        val test = 0
    }
    fun changePiece(newPiece: Piece?){
        piece = newPiece
    }
}