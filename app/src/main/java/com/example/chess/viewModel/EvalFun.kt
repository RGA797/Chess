package com.example.chess.viewModel

import com.example.chess.model.Block
import com.example.chess.model.Move
import com.example.chess.model.pieces.*
import com.example.chess.viewModel.Game.board
import com.example.chess.viewModel.Game.getValidMoves
import com.example.chess.viewModel.Game.kingIsCheck
import com.example.chess.viewModel.Game.kingIsMate
import com.example.chess.viewModel.Game.resolveMove
import com.example.chess.viewModel.Game.undoMove
import kotlin.system.measureTimeMillis

object EvalFun {
    var nodeCounter = 0

    /**
     * Center:
     * gameState[3][3]
     * gameState[3][4]
     * gameState[4][3]
     * gameState[4][4]
     */


    //returns the value of a game (this is the eval score for our algorithm)
    fun heuristics(gameState: MutableList<MutableList<Block>>): Int {
        var value = 0
        for (i in gameState.indices) {
            for (j in gameState[i].indices) {
                if (gameState[i][j].piece.value != null) {
                    if (gameState[i][j].piece.value!!.team == "black") {
                        if (gameState[i][j].piece.value is King) {
                            val enemyMoves = getValidMoves(board, "white")
                            if (kingIsMate(enemyMoves, listOf(i, j))) {
                                value -= 100000
                            } else if (kingIsCheck(enemyMoves, listOf(i, j))) {
                                value -= 500000
                            } else {
                                value += 10000
                            }

                            //if king has made a castle and hasnt moved from it (little less valuable than losing a pawn)
                            if (gameState[i][j].piece.value!!.moveCounter == 1 ){
                                if (i == 0 && j == 6){
                                    value += 10
                                }
                                else if (i == 0 && j == 2){
                                    value += 10
                                }
                            }

                        }
                        if (gameState[i][j].piece.value is Queen) {
                            value += 900
                            //queen outside of back is valued
                            if (i in 1..6){
                                value +=9
                            }
                        }
                        if (gameState[i][j].piece.value is Rook) {
                            value += 500
                            //rook in center
                            if (i in 2..5 && j in 2..5){
                                value += 5
                            }
                        }
                        if (gameState[i][j].piece.value is Bishop) {
                            value += 300
                            //bishop outside of back is valued
                            if (i in 1..6){
                                value += 3
                            }
                        }
                        if (gameState[i][j].piece.value is Knight) {
                            value += 300
                            //centered knight
                            if (i in 2..5 && j in 2..5){
                                value += 3
                            }
                        }
                        if (gameState[i][j].piece.value is Pawn) {
                            value += 100
                            //center piece pawns valued
                            if (i in 2..5 && j in 2..5){
                                value += 1
                            }
                        }
                    }
                    if (gameState[i][j].piece.value!!.team == "white") {
                        if (gameState[i][j].piece.value is King) {
                            val enemyMoves = getValidMoves(board, "black")

                            if (kingIsMate(enemyMoves, listOf(i, j))) {
                                value += 100000
                            } else if (kingIsCheck(enemyMoves, listOf(i, j))) {
                                value += 500000
                            } else {
                                value -= 10000
                            }

                            //if king has made a castle and hasnt moved from it (little less valuable than losing a pawn)
                            if (gameState[i][j].piece.value!!.moveCounter == 1 ){
                                if (i == 0 && j == 6){
                                    value -= 10
                                }
                                else if (i == 0 && j == 2){
                                    value -= 10
                                }
                            }

                        }
                        if (gameState[i][j].piece.value is Queen) {
                            value -= 900
                            //queen outside of back is valued
                            if (i in 1..6){
                                value -=9
                            }
                        }
                        if (gameState[i][j].piece.value is Rook) {
                            value -= 500
                            //rook in center
                            if (i in 2..5 && j in 2..5){
                                value -= 5
                            }
                        }
                        if (gameState[i][j].piece.value is Bishop) {
                            value -= 300
                            //bishop outside of back is valued
                            if (i in 1..6){
                                value -= 3
                            }
                        }
                        if (gameState[i][j].piece.value is Knight) {
                            value -= 300
                            //centered knight
                            if (i in 2..5 && j in 2..5){
                                value -= 3
                            }
                        }
                        if (gameState[i][j].piece.value is Pawn) {
                            value -= 100
                            //center piece pawns valued
                            if (i in 2..5 && j in 2..5){
                                value -= 1
                            }
                        }

                    }
                }
            }
        }
        return value
    }


    fun maxVal(alpha: Int, beta: Int, depth: Int, startTime: Long, timeLimit: Int): List<Any?> {
        nodeCounter++
        var maxValue = -1000000
        var bestMove: Move? = null

        //reached depth
        var duration: Double
        if (depth == 0) {
            val value = heuristics(board)
            return listOf(value, null)
        }

        //we go through every possible branch from the current node, then find the min,value
        //we then set a new minValue if needed
        val moveList = getValidMoves(board, "black")
        if (moveList.isEmpty()){
            print("error")
        }

        var alphaTemp = alpha

        //sorting movelist check order
        val weightedMovelist = getSortedWeightedMovelist(moveList)

        for (i in weightedMovelist.indices) {
            duration = (System.currentTimeMillis() - startTime) / 1000.0
            if (duration < timeLimit) {
                resolveMove(weightedMovelist[i][0] as Move)
                val nodeResult = minVal(alphaTemp, beta, depth - 1, startTime, timeLimit)
                val nodeValue = nodeResult[0] as Int

                if (nodeValue > maxValue) {
                    maxValue = nodeValue
                    bestMove = weightedMovelist[i][0] as Move
                }

                if (maxValue > alphaTemp) {
                    alphaTemp = maxValue
                }

                //reset board
                undoMove()

                // Alpha Beta Pruning
                if (beta <= alphaTemp) {
                    break
                }
            }
        }

        return listOf(maxValue, bestMove)
    }


    fun minVal(alpha: Int, beta: Int, depth: Int, startTime: Long, timeLimit: Int): List<Any?> {
        nodeCounter++
        var minValue = 1000000
        var bestMove: Move? = null

        var duration: Double
        //reached depth

        if (depth == 0) {
            val value = heuristics(board)
            return listOf(value, null)
        }

        //we go through every possible branch from the current node, then find the min,value
        //we then set a new minValue if needed
        val moveList = getValidMoves(board, "white")
        if (moveList.isEmpty()){
            print("error")
        }

        var betaTemp = beta

        //sort moves from highest score to lowest
        val weightedMovelist = getSortedWeightedMovelist(moveList)




        for (i in weightedMovelist.indices) {
            duration = (System.currentTimeMillis() - startTime) / 1000.0
            if (duration < timeLimit) {
                resolveMove(weightedMovelist[i][0] as Move)
                val nodeResult = maxVal(alpha, betaTemp, depth - 1, startTime, timeLimit)
                val nodeValue = nodeResult[0] as Int

                if (nodeValue < minValue) {
                    minValue = nodeValue
                    bestMove = weightedMovelist[i][0] as Move
                }

                if (betaTemp > minValue) {
                    betaTemp = minValue
                }

                //reset board
                undoMove()

                // Alpha Beta Pruning
                if (betaTemp <= alpha) {
                    break
                }
            }
        }

        return listOf(minValue, bestMove)
    }


    //takes list of moves, and returns sorted weighted list of prioritized moves to check
    fun getSortedWeightedMovelist(moveList: List<Move>): MutableList<List<Any>> {
        var movescoreList = mutableListOf<Int>()
        //move ordering. following MVV-LVA
        for (i in moveList.indices){
            var movescore = 0
            // prioritize capture moves in accordance with MVV-LVA
            //movescore following MVV-LVA score. following advice for scoring from forum: https://www.talkchess.com/forum3/viewtopic.php?t=61021
            if (moveList[i].enemyDestroyed){
                //MVV score
                when (board[moveList[i].newPosition[0]][moveList[i].newPosition[1]].piece.value) {
                    is Queen -> {
                        movescore += 900
                    }
                    is Rook -> {
                        movescore += 500
                    }
                    is Bishop -> {
                        movescore += 300
                    }
                    is Knight -> {
                        movescore += 300
                    }
                    is Pawn -> {
                        movescore += 100
                    }
                }

                //LVA score
                if (moveList[i].enemyDestroyed) {
                    when (board[moveList[i].newPosition[0]][moveList[i].newPosition[1]].piece.value) {
                        is Queen -> {
                            movescore -= 5
                        }
                        is Rook -> {
                            movescore -= 4
                        }
                        is Bishop -> {
                            movescore -= 3
                        }
                        is Knight -> {
                            movescore -= 2
                        }
                        is Pawn -> {
                            movescore -= 1
                        }
                    }
                }
            }
            else{
                //if we arent evaluating capture moves, we do some heuristics specific for piece types.
                //we keep the value under the minimum capture move values, which is 99, unless some obvious thing like promotion can happen

                //moving pawns
                if(board[moveList[i].oldPosition[0]][moveList[i].oldPosition[1]].piece.value is Pawn){
                    //prioritize moving pawns from the middle
                    if (moveList[i].oldPosition[1] in 2..5){
                        movescore += 1
                    }
                    //promotion moves should be prioritized
                    if (moveList[i].specialMove == "promotion"){
                        movescore += 1000
                    }
                }

                //moving knights
                else if(board[moveList[i].oldPosition[0]][moveList[i].oldPosition[1]].piece.value is Knight){
                    //prioritize checking moves that move knights out on center of the board
                    if (moveList[i].oldPosition[0] !in 2..5 && moveList[i].newPosition[0] in 2..5 && moveList[i].oldPosition[1] !in 2..5 && moveList[i].newPosition[1] in 2..5  ){
                        movescore += 3
                    }
                }

                //moving bishops
                else if(board[moveList[i].oldPosition[0]][moveList[i].oldPosition[1]].piece.value is Bishop){
                    //prioritize checking moves that move bishops out on the board
                    if (moveList[i].newPosition[0] in 2..5  && moveList[i].oldPosition[0] !in 2..5){
                        movescore += 3
                    }
                }

                //moving rooks
                else if(board[moveList[i].oldPosition[0]][moveList[i].oldPosition[1]].piece.value is Rook){
                    //prioritize checking moves that move rooks into the board
                    if (moveList[i].newPosition[0] in 2..5  && moveList[i].oldPosition[0] !in 2..5){
                        movescore += 5
                    }
                }

                //moving queens
                else if(board[moveList[i].oldPosition[0]][moveList[i].oldPosition[1]].piece.value is Queen){
                    //prioritize checking moves that move rooks into the middle of the board (row 2-4)
                    //this should be pretty valuable
                    if (moveList[i].newPosition[0] in 2..5 && moveList[i].oldPosition[0] !in 2..5){
                        movescore += 9
                    }
                }

                //moving kings into the middle should be low priority untill lategame (unless castling).
                //we therefore prioritize checking for moving the king into the middle (center,really), if we have less that 10 move options
                //this is experimental. might be totally wrong
                else if (board[moveList[i].oldPosition[0]][moveList[i].oldPosition[1]].piece.value is King){
                    if (moveList.size < 10){
                        if (moveList[i].newPosition[0] in 2..5 && moveList[i].newPosition[1] in 2..5 && moveList[i].oldPosition[0] !in 2..5 && moveList[i].oldPosition[1] !in 2..5){
                            movescore += 5
                        }
                    }
                    //castle structure should be valued
                    if (moveList[i].specialMove == "castling"){
                        movescore += 50
                    }
                }
            }
            movescoreList.add(movescore)
        }

        //combine movelist with the scores for respective moves
        val weightedMovelist = mutableListOf<List<Any>>()
        for (i in moveList.indices){
            weightedMovelist.add(listOf(moveList[i], movescoreList[i]))
        }

        //sort weighted movelist by score
        weightedMovelist.sortByDescending { it[1] as Int}

        return weightedMovelist
    }

    fun pawnPoints() {
        val boardArr_black = arrayOf(
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(5.0, 1.0, -2.0, -2.0, -2.0, 0.0, 3.0, 4.0),
            doubleArrayOf(6.5, 0.5, -4.0, -4.0, -4.0, -1.0, 3.5, 5.0),
            doubleArrayOf(10.0, 2.0, -4.0, -4.0, -4.0, 0.0, 6.0, 8.0),
            doubleArrayOf(14.5, 4.5, -3.0, -3.0, -3.0, 2.0, 9.5, 12.0),
            doubleArrayOf(29.0, 17.0, 8.0, 8.0, 8.0, 14.0, 23.0, 26.0),
            doubleArrayOf(47.5, 33.5, 23.0, 23.0, 23.0, 30.0, 41.5, 44.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0))

        val boardArr_white = arrayOf(
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(23.0, 30.0, 41.5, 44.0, 47.5, 33.5, 23.0, 23.0),
            doubleArrayOf(8.0, 14.0, 23.0, 26.0, 29.0, 17.0, 8.0, 8.0),
            doubleArrayOf(-3.0, 2.0, 9.5, 12.0, 14.5, 4.5, -3.0, -3.0),
            doubleArrayOf(-4.0, 0.0, 6.0, 8.0, 10.0, 2.0, -4.0, -4.0),
            doubleArrayOf(-4.0, -1.0, 3.5, 5.0, 6.5, 0.5, -4.0, -4.0),
            doubleArrayOf(23.0, 30.0, 41.5, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0))



        //println(Arrays.deepToString(boardArr_black)) //works as well
        println(boardArr_black.contentDeepToString())

    }




}