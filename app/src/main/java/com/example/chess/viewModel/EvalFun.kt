package com.example.chess.viewModel

import com.example.chess.model.Block
import com.example.chess.model.Move
import com.example.chess.model.pieces.*
import com.example.chess.viewModel.Game.getValidMoves
import com.example.chess.viewModel.Game.kingIsCheck
import com.example.chess.viewModel.Game.kingIsMate
import com.example.chess.viewModel.Game.resolveMove
import com.example.chess.viewModel.Game.undoMove

class EvalFun() {

    /**
     * gameState[3][3]
     * gameState[3][4]
     * gameState[4][3]
     * gameState[4][4]
     */

    //returns the value of a game (this is the heuristics for our algorithm)
    fun heuristics(gameState: MutableList<MutableList<Block>>): Int {
        var value = 0
        var number = 0
        for (i in gameState.indices) {
            for (j in gameState[i].indices) {
                if (gameState[i][j].piece.value != null) {
                    if (gameState[i][j].piece.value!!.team == "black") {
                        when (gameState[i][j].piece.value) {
                            is King -> {
                                val enemyMoves = getValidMoves(Game.board, "white")
                                if (kingIsMate(enemyMoves, listOf(i, j))) {
                                    value -= 100000
                                } else if (kingIsCheck(enemyMoves, listOf(i, j))) {
                                    value -= 500000
                                } else {
                                    value += 10000
                                }
                            }
                            is Queen -> value += 900
                            is Rook -> value += 500
                            is Bishop -> value += 300
                            is Knight -> value += 300
                            is Pawn -> {
                                value += 300; println("Is pawn lol"); number += 1; println(number); println(
                                    "lmao"
                                )
                            }
                        }
                    }



                    /*
                    when (x) {
                        1 -> print("x == 1")
                        2 -> print("x == 2")

                            else -> {
                            print("x is neither 1 nor 2")
                        }
                    }
                     */

                    if (gameState[i][j].piece.value!!.team == "white") {
                        if (gameState[i][j].piece.value is King) {
                            val enemyMoves = getValidMoves(Game.board, "black")
                            if (kingIsMate(enemyMoves, listOf(i, j))) {
                                value += 100000
                            } else if (kingIsCheck(enemyMoves, listOf(i, j))) {
                                value += 500000
                            } else {
                                value -= 10000
                            }
                        }


                        if (gameState[i][j].piece.value is Queen) {
                            value -= 900
                        }
                        if (gameState[i][j].piece.value is Rook) {
                            value -= 500
                        }
                        if (gameState[i][j].piece.value is Bishop) {
                            value -= 300
                        }
                        if (gameState[i][j].piece.value is Knight) {
                            value -= 300
                        }
                        if (gameState[i][j].piece.value is Pawn) {
                            value -= 300
                        }
                    }
                }
            }
        }
        return value
    }


    fun maxVal(alpha: Int, beta: Int, depth: Int, startTime: Long): List<Any?> {
        var maxValue = -1000000
        var bestMove: Move? = null
        //reached depth
        var duration = (System.currentTimeMillis() - startTime) / 1000.0
        if (depth == 0 || duration >= 15) {
            return listOf(heuristics(Game.board), bestMove)
        }

        //we go through every possible branch from the current node, then find the min,value
        //we then set a new minValue if needed
        val moveList = getValidMoves(Game.board, "black")
        var alphaTemp = alpha

        for (i in moveList.indices) {
            duration = (System.currentTimeMillis() - startTime) / 1000.0
            if (duration < 15) {
                resolveMove(moveList[i])
                val nodeResult = minVal(alphaTemp, beta, depth - 1, startTime)
                val nodeValue = nodeResult[0] as Int

                if (nodeValue > maxValue) {
                    maxValue = nodeValue
                    bestMove = moveList[i]
                }

                if (maxValue > alphaTemp) {
                    alphaTemp = maxValue
                }

                //reset board
                undoMove()

                // Alpha Beta Pruning
                duration = (System.currentTimeMillis() - startTime) / 1000.0
                if (beta <= alphaTemp || duration >= 15) {
                    break
                }
            }
        }
        return listOf(maxValue, bestMove)
    }

    fun minVal(alpha: Int, beta: Int, depth: Int, startTime: Long): List<Any?> {
        var minValue = 1000000
        var bestMove: Move? = null

        var duration = (System.currentTimeMillis() - startTime) / 1000.0
        //reached depth

        if (depth == 0 || duration >= 15) {
            return listOf(heuristics(Game.board), bestMove)
        }

        //we go through every possible branch from the current node, then find the min,value
        //we then set a new minValue if needed
        val moveList = getValidMoves(Game.board, "white")
        var betaTemp = beta


        for (i in moveList.indices) {
            duration = (System.currentTimeMillis() - startTime) / 1000.0
            if (duration < 15) {
                resolveMove(moveList[i])
                val nodeResult = maxVal(alpha, betaTemp, depth - 1, startTime)
                val nodeValue = nodeResult[0] as Int

                if (nodeValue < minValue) {
                    minValue = nodeValue
                    bestMove = moveList[i]
                }

                if (betaTemp > minValue) {
                    betaTemp = minValue
                }

                //reset board
                undoMove()

                // Alpha Beta Pruning
                duration = (System.currentTimeMillis() - startTime) / 1000.0
                if (betaTemp <= alpha || duration >= 15) {
                    break
                }
            }
        }
        return listOf(minValue, bestMove)
    }


}