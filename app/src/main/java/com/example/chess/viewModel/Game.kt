package com.example.chess.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chess.model.Block
import com.example.chess.model.Move
import com.example.chess.model.pieces.*

class Game: ViewModel() {

    var board: MutableList<MutableList<Block>> = mutableStateListOf()
    var movesPerformed: MutableList<Move> = mutableStateListOf()
    private var destroyedQueue: MutableList<Piece> = mutableStateListOf()
    init {
        //upon initialization of a game object, we inject pieces into lists of blocks with manual dependency injection
        for (i in 0..7)
            if (i == 0 ){
                board.add(mutableListOf<Block>(Block(mutableStateOf(Rook("white"))),Block(mutableStateOf(Knight("white"))),Block(mutableStateOf(Bishop("white"))),Block(mutableStateOf(Queen("white"))),Block(mutableStateOf(King("white"))),Block(mutableStateOf(Bishop("white"))),Block(mutableStateOf(Knight("white"))),Block(mutableStateOf(Rook("white"))) ))
            }
            else if (i == 1){
                board.add(mutableListOf<Block>(Block(mutableStateOf(Pawn("white"))),Block(mutableStateOf(Pawn("white"))),Block(mutableStateOf(Pawn("white"))),Block(mutableStateOf(Pawn("white"))),Block(mutableStateOf(Pawn("white"))),Block(mutableStateOf(Pawn("white"))),Block(mutableStateOf(Pawn("white"))),Block(mutableStateOf(Pawn("white"))) ))
            }
            else if (i == 6){
                board.add(mutableListOf<Block>(Block(mutableStateOf(Pawn("black"))),Block(mutableStateOf(Pawn("black"))),Block(mutableStateOf(Pawn("black"))),Block(mutableStateOf(Pawn("black"))),Block(mutableStateOf(Pawn("black"))),Block(mutableStateOf(Pawn("black"))),Block(mutableStateOf(Pawn("black"))),Block(mutableStateOf(Pawn("black"))) ))
            }
            else if (i == 7){
                board.add(mutableListOf<Block>(Block(mutableStateOf(Rook("black"))),Block(mutableStateOf(Knight("black"))),Block(mutableStateOf(Bishop("black"))),Block(mutableStateOf(Queen("black"))),Block(mutableStateOf(King("black"))),Block(mutableStateOf(Bishop("black"))),Block(mutableStateOf(Knight("black"))),Block(mutableStateOf(Rook("black"))) ))
            }
            else{
                board.add(mutableListOf<Block>(Block(mutableStateOf(null)),Block(mutableStateOf(null)),Block(mutableStateOf(null)),Block(mutableStateOf(null)),Block(mutableStateOf(null)),Block(mutableStateOf(null)),Block(mutableStateOf(null)),Block(mutableStateOf(null)) ))
            }


    }

    fun updatedLastMove(): Move?{
        if (movesPerformed.isEmpty()) {
            return  null
        }
        else {
            return movesPerformed[movesPerformed.size-1]
        }
    }

    //returns every valid move for current gameState
    fun getValidMoves(gameState: MutableList<MutableList<Block>>, team: String): List<Move> {

        val validMoves = mutableListOf<Move>()
        val teamMoves = getPossibleMoves(team,gameState,updatedLastMove())
        var enemyMoves: List<Move> = listOf()
        if (team == "white"){
            enemyMoves = getPossibleMoves("black",gameState,updatedLastMove())
        }
        if (team == "black"){
            enemyMoves = getPossibleMoves("white",gameState,updatedLastMove())
        }

        if(kingIsMate(enemyMoves, getKingPosition(team, gameState)) ){
            return validMoves
        }

        if (kingIsCheck(enemyMoves, getKingPosition(team, gameState))) {
            for (i in teamMoves.indices) {
                    resolveMove(teamMoves[i])
                    if (team == "white"){
                        enemyMoves = getPossibleMoves("black",gameState,updatedLastMove())
                    }
                    if (team == "black"){
                        enemyMoves = getPossibleMoves("white",gameState,updatedLastMove())
                    }
                    if (kingIsCheck(enemyMoves, getKingPosition(team, gameState))) {
                        undoMove()
                        continue
                    } else {
                        validMoves.add(teamMoves[i])
                        undoMove()
                    }
            }
            return validMoves
        }
        else{
            for (i in teamMoves.indices){
                    resolveMove(teamMoves[i])
                    if (team == "white"){
                        enemyMoves = getPossibleMoves("black",gameState,updatedLastMove())
                    }
                    if (team == "black"){
                        enemyMoves = getPossibleMoves("white",gameState,updatedLastMove())
                    }
                    if (kingIsCheck(enemyMoves, getKingPosition(team, gameState))){
                        undoMove()
                        continue
                    }
                    else {
                        validMoves.add(teamMoves[i])
                        undoMove()
                    }
            }
            return validMoves
        }
    }

    fun getKingPosition(team: String, gameState: MutableList<MutableList<Block>>): List<Int> {
        var kingPosition = listOf<Int>()
        if (team == "white"){
            for (i in gameState.indices){
                for (j in gameState[i].indices){
                    if (gameState[i][j].piece.value != null) {
                        if (gameState[i][j].piece.value is King && gameState[i][j].piece.value!!.team == team){
                            kingPosition = listOf(i,j)
                        }
                    }
                }
            }
        }

        else if (team == "black"){
            for (i in gameState.indices){
                for (j in gameState[i].indices){
                    if (gameState[i][j].piece.value != null) {
                        if (gameState[i][j].piece.value is King && gameState[i][j].piece.value!!.team == team){
                            kingPosition = listOf(i,j)
                        }
                    }
                }
            }
        }
        return kingPosition
    }

    fun kingIsCheck(enemyMoves: List<Move>, kingPosition: List<Int>): Boolean{
        for (i in enemyMoves.indices){
            if (enemyMoves[i].newPosition == kingPosition){
                return true
            }
        }
        return false
    }
    fun getValue(): MutableList<MutableList<Block>> {
        return board
    }

    fun kingIsStuck(teamMoves:List<Move>, kingPosition: List<Int>): Boolean{
        var kingHasMove = false
        for (i in teamMoves.indices){
            if (teamMoves[i].oldPosition == kingPosition){
                kingHasMove = true
                break
            }
        }
        return kingHasMove
    }

    fun kingIsMate(enemyMoves: List<Move>, kingPosition: List<Int>): Boolean{
        if (kingIsCheck(enemyMoves,kingPosition) && kingIsStuck(enemyMoves,kingPosition)){
            return true
        }
        return false
    }

    //returns where any piece can go, regardless of special rules such as check.
    fun getPossibleMoves(team:String, gameState:MutableList<MutableList<Block>>, lastMove: Move?): MutableList<Move> {
        val allPossibleMoves = mutableListOf<Move>()
        for (i in board.indices) {
            for (j in board[i].indices) {
                val piece = board[i][j].piece.value
                if (piece != null) {
                        if (board[i][j].piece.value!!.team == team) {
                            val moves = piece.possibleMoves(gameState, listOf(i, j), lastMove )
                            for (x in moves.indices) {
                                allPossibleMoves.add(moves[x])
                            }
                        }
                }
            }
        }
        return allPossibleMoves
    }

    //moves a piece if possible
    private fun movePiece(move: Move){
        //piece moved
        val pieceMoved = board[move.oldPosition[0]][move.oldPosition[1]].piece.value
        board[move.oldPosition[0]][move.oldPosition[1]].changePiece(null)

        //removeDestroyedPiece
        if (move.enemyDestroyed){
            destroyedQueue.add(board[move.enemyDestroyedPosition!![0]][move.enemyDestroyedPosition[1]].piece.value!!)
            board[move.enemyDestroyedPosition[0]][move.enemyDestroyedPosition[1]].changePiece(null)
        }
        //move piece
        board[move.newPosition[0]][move.newPosition[1]].changePiece(pieceMoved)

        //increment counter
        pieceMoved!!.incrementMoveCounter()
    }

    //moves a piece if possible, sets last move, and resolves special moves logic
    fun resolveMove(move: Move){

        movePiece(move)
        if (move.specialMove == "en passant"  ){
            //nothing new happens
        }
        else if (move.specialMove == "promotion"){
            var movedPiece = board[move.newPosition[0]][move.newPosition[1]].piece.value!!
            val pawnMoveCounter = movedPiece.moveCounter

            //make queen object and replace pawn
            val newQueen = Queen(movedPiece.team)
            for (i in 0..pawnMoveCounter){
                newQueen.incrementMoveCounter()
            }
            movedPiece  = newQueen
            board[move.newPosition[0]][move.newPosition[1]].changePiece(movedPiece)
        }

        else if (move.specialMove == "castling"){
            //move the rook beside the castle
            //king side castling
            if (move.newPosition[0] == 0 && move.newPosition[1] == 6){
                movePiece(Move(listOf(0,7),listOf(0,5), false,null,null))
            }
            //queen side castling
            if (move.newPosition[0] == 0 && move.newPosition[1] == 1){
                movePiece(Move(listOf(0,0),listOf(0,2), false,null,null))
            }
        }
        movesPerformed.add(move)
    }

    fun undoMove(){
        if (movesPerformed.size != 0) {
            val reverseMove = Move(
                movesPerformed[movesPerformed.size - 1].newPosition,
                movesPerformed[movesPerformed.size - 1].oldPosition,
                false,
                null,
                null
            )
            movePiece(reverseMove)
            //reset move counter to what it was before
            board[reverseMove.newPosition[0]][reverseMove.newPosition[1]].piece.value!!.decrementMoveCounter()
            board[reverseMove.newPosition[0]][reverseMove.newPosition[1]].piece.value!!.decrementMoveCounter()

            if (movesPerformed[movesPerformed.size - 1].specialMove == "en passant" || movesPerformed[movesPerformed.size - 1].specialMove == "double") {
                //nothing new happens
            } else if (movesPerformed[movesPerformed.size - 1].specialMove == "promotion") {
                var movedPiece =
                    board[reverseMove.newPosition[0]][reverseMove.newPosition[1]].piece.value!!
                //make pawn and replace queen
                val newPawn = Pawn(movedPiece.team)
                newPawn.setCounter(movedPiece.moveCounter)
                movedPiece = newPawn
                board[reverseMove.newPosition[0]][reverseMove.newPosition[1]].changePiece(movedPiece)
            } else if (movesPerformed[movesPerformed.size - 1].specialMove == "castling") {
                //king side castling
                if (movesPerformed[movesPerformed.size - 1].newPosition[0] == 0 && movesPerformed[movesPerformed.size - 1].newPosition[1] == 6) {
                    movePiece(Move(listOf(0, 5), listOf(0, 7), false, null, null))
                    board[0][7].piece.value!!.decrementMoveCounter()
                    board[0][7].piece.value!!.decrementMoveCounter()
                }
                //queen side castling
                if (movesPerformed[movesPerformed.size - 1].newPosition[0] == 0 && movesPerformed[movesPerformed.size - 1].newPosition[1] == 1) {
                    movePiece(Move(listOf(0, 2), listOf(0, 0), false, null, null))
                    board[0][0].piece.value!!.decrementMoveCounter()
                    board[0][0].piece.value!!.decrementMoveCounter()
                }
            }

            if (movesPerformed[movesPerformed.size - 1].enemyDestroyed) {

                board[movesPerformed[movesPerformed.size - 1].enemyDestroyedPosition!![0]][movesPerformed[movesPerformed.size - 1].enemyDestroyedPosition!![1]].changePiece(
                    destroyedQueue[destroyedQueue.size - 1]
                )
                destroyedQueue.removeLast()
            }
            movesPerformed.removeLast()
        }
    }

    //returns the value of a game (this is the heuristics for our algorithm)
    fun evalGame(gameState: MutableList<MutableList<Block>>): Int{
        var value = 0
        for(i in gameState.indices){
            for (j in gameState[i].indices){
                if (gameState[i][j].piece.value != null){
                    if (gameState[i][j].piece.value!!.team == "black"){
                        if (gameState[i][j].piece.value is King){
                            val enemyMoves = getValidMoves(board, "white")
                            if (kingIsMate(enemyMoves, listOf(i,j))){
                                value -= 100000
                            }
                            else if (kingIsCheck(enemyMoves, listOf(i,j)) ){
                                value -= 500000
                            }
                            else {
                                value += 10000
                            }
                        }
                        if (gameState[i][j].piece.value is Queen){
                            value += 900
                        }
                        if (gameState[i][j].piece.value is Rook){
                            value += 500
                        }
                        if (gameState[i][j].piece.value is Bishop){
                            value += 300
                        }
                        if (gameState[i][j].piece.value is Knight){
                            value += 300
                        }
                        if (gameState[i][j].piece.value is Pawn){
                            value += 300
                        }
                    }

                    if (gameState[i][j].piece.value!!.team == "white"){
                        if (gameState[i][j].piece.value is King){
                            val enemyMoves = getValidMoves(board, "black")
                            if (kingIsMate(enemyMoves, listOf(i,j))){
                                value += 100000
                            }
                            else if (kingIsCheck(enemyMoves, listOf(i,j)) ){
                                value += 500000
                            }
                            else {
                                value -= 10000
                            }
                        }
                        if (gameState[i][j].piece.value is Queen){
                            value -= 900
                        }
                        if (gameState[i][j].piece.value is Rook){
                            value -= 500
                        }
                        if (gameState[i][j].piece.value is Bishop){
                            value -= 300
                        }
                        if (gameState[i][j].piece.value is Knight){
                            value -= 300
                        }
                        if (gameState[i][j].piece.value is Pawn){
                            value -= 300
                        }
                    }
                }
            }
        }
        return value
    }

    fun max(alpha: Int, beta: Int, depth: Int,startTime: Long): List<Any?> {
        var maxValue = -1000000
        var bestMove: Move? = null
        //reached depth
        var duration = (System.currentTimeMillis() - startTime)/1000.0
        if (depth == 0 || duration >= 30){
            return listOf(evalGame(board), null)
        }

        //we go through every possible branch from the current node, then find the min,value
        //we then set a new minValue if needed
        val moveList = getValidMoves(board,"black")
        var alphaTemp = alpha

        for (i in moveList.indices){
            duration = (System.currentTimeMillis() - startTime)/1000.0
            if (duration < 30){
                resolveMove(moveList[i])
                val nodeResult = min(alphaTemp, beta, depth-1, startTime)
                val nodeValue = nodeResult[0] as Int

                if (nodeValue > maxValue) {
                    maxValue = nodeValue
                    bestMove = moveList[i]
                }

                if (maxValue >alphaTemp){
                    alphaTemp = maxValue
                }

                //reset board
                undoMove()

                // Alpha Beta Pruning
                duration = (System.currentTimeMillis() - startTime)/1000.0
                if (beta <= alphaTemp || duration >= 30) {
                    break
                }
            }
        }
        return listOf(maxValue, bestMove)
    }

    fun min(alpha: Int, beta: Int, depth: Int, startTime: Long): List<Any?> {
        var minValue = 1000000
        var bestMove: Move? = null

        var duration = (System.currentTimeMillis() - startTime)/1000.0
        //reached depth

        if (depth == 0 || duration  >= 30){
            return listOf(evalGame(board), null)
        }

        //we go through every possible branch from the current node, then find the min,value
        //we then set a new minValue if needed
        val moveList = getValidMoves(board,"white")
        var betaTemp = beta


        for (i in moveList.indices){
            duration = (System.currentTimeMillis() - startTime)/1000.0
            if (duration < 30){
                resolveMove(moveList[i])
                val nodeResult = max(alpha, betaTemp, depth-1, startTime)
                val nodeValue = nodeResult[0] as Int

                if (nodeValue < minValue) {
                    minValue = nodeValue
                    bestMove = moveList[i]
                }

                if (betaTemp > minValue){
                    betaTemp = minValue
                }

                //reset board
                undoMove()

                // Alpha Beta Pruning
                duration = (System.currentTimeMillis() - startTime)/1000.0
                if (betaTemp <= alpha || duration >= 30) {
                    break
                }
            }
        }
        return listOf(minValue, bestMove)
    }
}