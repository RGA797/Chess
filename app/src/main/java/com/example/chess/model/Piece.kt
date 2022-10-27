package com.example.chess.model

    abstract class Piece(
        val team: String
    ) {
        var moveCounter = 0
        fun incrementMoveCounter(){
            moveCounter++
        }
        fun decrementMoveCounter(){
            moveCounter--
        }
        fun setCounter(number: Int){
            moveCounter = number
        }
        //returns possible moves given game state
        abstract fun possibleMoves(gameState: List<List<Block>>, piecePosition: List<Int>, lastMove: Move?): MutableList<Move>
    }