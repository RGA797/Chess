package com.example.chess.model

class Pawn(team: String) : Piece(team) {
    private var moveList: MutableList<Move> = mutableListOf()
    private fun checkMoves(gameState: List<List<Block>>, piecePosition: List<Int>,lastMove: Move?){
        val currentBlock = gameState[piecePosition[0]][piecePosition[1]]
        var moveIncrement: Int = 0
        //as pawn moves differ depending on teams we need to depend on that
        if (currentBlock.piece!!.team == "white"){
            moveIncrement = 1
        }
        if (currentBlock.piece!!.team == "black"){
            moveIncrement = -1
        }
        //right for white, left for black
        val crossOnePosition = listOf(piecePosition[0] + moveIncrement, piecePosition[1] + moveIncrement)
        //left for white, right for black
        val crossTwoPosition = listOf(piecePosition[0] + moveIncrement, piecePosition[1] - moveIncrement)
        //normal move check
        try {
            val newPiecePosition = listOf(piecePosition[0]+ moveIncrement, piecePosition[1])
            val newBlock = gameState[newPiecePosition[0]][newPiecePosition[1]]
            if (newBlock.piece == null) {
                if (newPiecePosition[0] == 0 || newPiecePosition[0] == 7 ){
                    moveList.add(Move(piecePosition, newPiecePosition, false, null, "promotion"))
                }
                else {
                    moveList.add(Move(piecePosition, newPiecePosition, false, null, null))
                }
            }
        }
        catch (e: IndexOutOfBoundsException){
        }
        //double move check
        try {
            if (currentBlock.piece!!.moveCounter == 0) {
                val blockOnePosition = listOf(piecePosition[0] + moveIncrement, piecePosition[1])
                val stepOneBlock = gameState[blockOnePosition[0]][blockOnePosition[1]]

                val blockTwoPosition = listOf(piecePosition[0]+ moveIncrement*2, piecePosition[1] )
                val stepTwoBlock = gameState[blockTwoPosition[0]][blockTwoPosition[1]]
                if (stepOneBlock.piece == null && stepTwoBlock.piece == null) {
                    moveList.add(Move(piecePosition, blockTwoPosition, false, null, "double"))
                }
            }
        }
        catch (e: IndexOutOfBoundsException){
        }

        //first cross take
        try {
            val firstCrossBlock = gameState[crossOnePosition[0]][crossOnePosition[1]]
            if (firstCrossBlock.piece != null ){
                if (firstCrossBlock.piece!!.team != currentBlock.piece!!.team){
                    if (crossOnePosition[0] == 0 || crossOnePosition[0] == 7 ){
                        moveList.add(Move(piecePosition, crossOnePosition, true, crossOnePosition, "promotion"))
                    }
                    else {
                        moveList.add(Move(piecePosition, crossOnePosition, true, crossOnePosition, null))
                    }
                }
            }
        }
        catch (e: IndexOutOfBoundsException){
        }

        //second cross take check
        try {
            val secondCrossBlock = gameState[crossTwoPosition[0]][crossTwoPosition[1]]
            if (secondCrossBlock.piece != null ){
                if (secondCrossBlock.piece!!.team != currentBlock.piece!!.team){
                    if (crossTwoPosition[0] == 0 || crossTwoPosition[0] == 7 ){
                        moveList.add(Move(piecePosition, crossTwoPosition, true, crossTwoPosition, "promotion"))
                    }
                    else {
                        moveList.add(Move(piecePosition, crossTwoPosition, true, crossTwoPosition, null))
                    }
                }
            }
        }
        catch (e: IndexOutOfBoundsException){
        }

        //en passant check
        try {
            if (lastMove != null) {
                if (lastMove.specialMove == "double") {
                    //if they doubled left to piece
                    if (lastMove.newPosition[0] == piecePosition[0] && piecePosition[1] == (lastMove.newPosition[1]+1)) {
                            //take to the left (differs for teams)
                            if (currentBlock.piece!!.team == "black" && gameState[lastMove.newPosition[0]][lastMove.newPosition[1]].piece!!.team == "white") {
                                moveList.add(
                                    Move(
                                        piecePosition,
                                        crossOnePosition,
                                        true,
                                        lastMove.newPosition,
                                        "en passant"
                                    )
                                )
                            }
                            if (currentBlock.piece!!.team == "white" && gameState[lastMove.newPosition[0]][lastMove.newPosition[1]].piece!!.team == "black") {
                                moveList.add(
                                    Move(
                                        piecePosition,
                                        crossTwoPosition,
                                        true,
                                        lastMove.newPosition,
                                        "en passant"
                                    )
                                )
                            }
                        }
                    }
                }
            }
        catch (e: IndexOutOfBoundsException){
        }


        //en passant check
        try {
            if (lastMove != null) {
                if (lastMove.specialMove == "double") {
                    //enemy in same row, right to
                    if (lastMove.newPosition[0] == piecePosition[0] && piecePosition[1] == (lastMove.newPosition[1]-1))  {
                            //take to the right (location is a bit wonky due to team differences)
                            if (currentBlock.piece!!.team == "black" && gameState[lastMove.newPosition[0]][lastMove.newPosition[1]].piece!!.team == "white") {
                                moveList.add(
                                    Move(
                                        piecePosition,
                                        crossTwoPosition,
                                        true,
                                        lastMove.newPosition,
                                        "en passant"
                                    )
                                )
                            }
                            if (currentBlock.piece!!.team == "white" && gameState[lastMove.newPosition[0]][lastMove.newPosition[1]].piece!!.team == "black") {
                                moveList.add(
                                    Move(
                                        piecePosition,
                                        crossOnePosition,
                                        true,
                                        lastMove.newPosition,
                                        "en passant"
                                    )
                                )
                            }
                        }
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
        checkMoves(gameState, piecePosition, lastMove)
        return moveList
    }
}