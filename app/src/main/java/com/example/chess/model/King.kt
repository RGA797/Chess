package com.example.chess.model

class King(team: String) : Piece(team) {
    private var moveList: MutableList<Move?> = mutableListOf()

    //adds moves to movelist, dependent on x,y offsets
    private fun checkMoves(xOffset: Int, yOffset:Int, gameState: List<List<Block>>, piecePosition: List<Int>){
        val currentBlock = gameState[piecePosition[0]][piecePosition[1]]
        if (currentBlock.piece.value!!.team == "white") {
            if (currentBlock.piece.value!!.moveCounter == 0) {
                if (gameState[0][5].piece.value == null && gameState[0][6].piece.value == null) {
                    if (gameState[0][7].piece.value != null) {
                        if (gameState[0][7].piece.value!!.moveCounter == 0) {
                            moveList.add(Move(piecePosition, listOf(0, 6), false, null, "castling"))
                        }
                    }
                }
                if (gameState[0][1].piece.value == null && gameState[0][2].piece.value == null && gameState[0][3].piece.value == null) {
                    if (gameState[0][0].piece.value != null) {
                        if (gameState[0][0].piece.value!!.moveCounter == 0) {
                            moveList.add(Move(piecePosition, listOf(0, 1), false, null, "castling"))
                        }
                    }
                }
            }
        }
        if (currentBlock.piece.value!!.team == "black") {
            if (currentBlock.piece.value!!.moveCounter == 0) {
                if (gameState[7][5].piece.value == null && gameState[7][6].piece.value == null) {
                    if (gameState[7][7].piece.value!!.moveCounter == 0) {
                        moveList.add(Move(piecePosition, listOf(7, 6), false, null, "castling"))
                    }
                }
                if (gameState[7][1].piece.value == null && gameState[7][2].piece.value == null && gameState[7][3].piece.value == null) {
                    if (gameState[7][0].piece.value != null){
                        if (gameState[7][0].piece.value!!.moveCounter == 0) {
                            moveList.add(Move(piecePosition, listOf(7, 1), false, null, "castling"))
                        }
                    }
                }
            }
        }

        try {
            val newPiecePosition = listOf(piecePosition[0]+xOffset, piecePosition[1]+yOffset)
            val newBlock = gameState[newPiecePosition[0]][newPiecePosition[1]]
            if (newBlock.piece.value == null ){
                moveList.add(Move(piecePosition, newPiecePosition, false, null, null))
            }
            else if (newBlock.piece.value != null){
                if (newBlock.piece.value!!.team != currentBlock.piece.value!!.team){
                    moveList.add(Move(piecePosition, newPiecePosition, true, newPiecePosition, null))
                }
            }
        }
        catch (e: IndexOutOfBoundsException){
        }
    }

    //returns movelist for the current king
    override fun possibleMoves(
        gameState: List<List<Block>>,
        piecePosition: List<Int>,
        lastMove: Move?
    ): MutableList<Move> {
        moveList.clear()

        //diagonal moves
        checkMoves(1,1, gameState, piecePosition)
        checkMoves(1,-1, gameState, piecePosition)
        checkMoves(-1,1, gameState, piecePosition)
        checkMoves(-1,-1, gameState, piecePosition)

        //lined moves
        checkMoves(1,0, gameState, piecePosition)
        checkMoves(-1,0, gameState, piecePosition)
        checkMoves(0,1, gameState, piecePosition)
        checkMoves(0,-1, gameState, piecePosition)

        //now we have to filter off the illegal moves that put the king in check
        //this current way might be slow, as we ask for all enemy moves
        for (i in gameState.indices){
            for (j in gameState[i].indices) {
                if (gameState[i][j].piece.value != null){
                    if (gameState[i][j].piece.value!!.team != gameState[piecePosition[0]][piecePosition[1]].piece.value!!.team){
                        //because kings would ask eachother for their position otherwise, i make some custom code to check if king is around
                        if(gameState[i][j].piece.value!! is King){
                            for (i in moveList.indices){
                                if (moveList[i] != null) {
                                    //basically asking if the move ends up next to the enemy king
                                    if (moveList[i]!!.newPosition == listOf(i - 1, j) ||
                                        moveList[i]!!.newPosition == listOf(i - 1, j - 1) ||
                                        moveList[i]!!.newPosition == listOf(i - 1, j + 1) ||
                                        moveList[i]!!.newPosition == listOf(i + 1, j) ||
                                        moveList[i]!!.newPosition == listOf(i + 1, j + 1) ||
                                        moveList[i]!!.newPosition == listOf(i + 1, j - 1) ||
                                        moveList[i]!!.newPosition == listOf(i, j + 1) ||
                                        moveList[i]!!.newPosition == listOf(i, j - 1)
                                    ) {
                                        continue
                                    } else {
                                        moveList[i] = null
                                    }
                                }
                            }
                            continue
                        }
                        val enemyPieceMoves = gameState[i][j].piece.value!!.possibleMoves(gameState, listOf(i,j),null)
                        for (x in moveList.indices) {
                            for (y in enemyPieceMoves.indices) {
                                if (moveList[x] != null) {
                                    if (moveList[x]!!.newPosition == enemyPieceMoves[y].newPosition) {
                                        moveList[x] = null
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //removing null values
        var cleanMoveList: MutableList<Move> = mutableListOf()
        for (i in moveList.indices){
            if (moveList[i] != null){
                cleanMoveList.add(moveList[i]!!)
            }
        }

        return cleanMoveList
    }

}