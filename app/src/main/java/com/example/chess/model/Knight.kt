package com.example.chess.model

class Knight(team: String) : Piece(team) {
    var moveList: MutableList<Move> = mutableListOf()
    //returns a list of all possible moves
    private fun checkMoves(xOffset: Int, yOffset:Int, gameState: List<List<Block>>, piecePosition: List<Int>){
            val currentBlock = gameState[piecePosition[0]][piecePosition[1]]
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

    override fun possibleMoves(
        gameState: List<List<Block>>,
        piecePosition: List<Int>,
        lastMove: Move?
    ): MutableList<Move> {
        moveList.clear()

        checkMoves(2,1, gameState, piecePosition)
        checkMoves(2,-1, gameState, piecePosition)

        checkMoves(1,2, gameState, piecePosition)
        checkMoves(1,-2, gameState, piecePosition)

        checkMoves(-2,1, gameState, piecePosition)
        checkMoves(-2,-1, gameState, piecePosition)

        checkMoves(-1,2, gameState, piecePosition)
        checkMoves(-1,-2, gameState, piecePosition)

        return moveList
    }

}