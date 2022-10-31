package com.example.chess.model

class Bishop(team: String) : Piece(team) {
    var moveList: MutableList<Move> = mutableListOf()
    //returns a list of all possible moves
    private fun checkMoves(xOffset: Int, yOffset:Int, gameState: List<List<Block>>, piecePosition: List<Int>){
        val currentBlock = gameState[piecePosition[0]][piecePosition[1]]
        for (i in 1..7){
            try {
                val newPiecePosition = listOf(piecePosition[0]+(i*xOffset), piecePosition[1]+(i*yOffset))
                val newBlock = gameState[newPiecePosition[0]][newPiecePosition[1]]
                if (newBlock.piece.value == null ){
                    moveList.add(Move(piecePosition, newPiecePosition, false, null, null))
                }
                else if (newBlock.piece.value != null){
                    if (newBlock.piece.value!!.team != currentBlock.piece.value!!.team){
                        moveList.add(Move(piecePosition, newPiecePosition, true, newPiecePosition, null))
                    }
                    break
                }
            }
            catch (e: IndexOutOfBoundsException){
                //stop the loop at walls
                break
            }
        }
    }

    override fun possibleMoves(
        gameState: List<List<Block>>,
        piecePosition: List<Int>,
        lastMove: Move?
    ): MutableList<Move> {
        moveList.clear()

        checkMoves(1,1, gameState, piecePosition)
        checkMoves(1,-1, gameState, piecePosition)
        checkMoves(-1,1, gameState, piecePosition)
        checkMoves(-1,-1, gameState, piecePosition)
        return moveList
    }


}