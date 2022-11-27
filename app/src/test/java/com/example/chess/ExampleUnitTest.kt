package com.example.chess

import com.example.chess.model.Move
import com.example.chess.viewModel.EvalFun
import com.example.chess.viewModel.Game
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDateTime
import kotlin.system.measureTimeMillis

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun gameWorks(){

        //this little test shows that min prefers moves that actually put them ahead in point
        Game.resolveMove(Move(listOf(1,0), listOf(3,0), false, null,"double" ))
        Game.resolveMove(Move(listOf(3,0), listOf(4,0), false, null,null ))
        Game.resolveMove(Move(listOf(4,0), listOf(5,0), false, null,null ))

        val move = EvalFun.minVal(-10000000,1000000, 3, System.currentTimeMillis(), 15)
         print(move)
    }


    @Test
    fun enPassant(){

        //this little test shows that min prefers moves that actually put them ahead in point
        Game.resolveMove(Move(listOf(1,0), listOf(3,0), false, null,"double" ))
        Game.resolveMove(Move(listOf(3,0), listOf(4,0), false, null,null ))
        Game.resolveMove(Move(listOf(6,1), listOf(4,1), false, null,"double"))

        val boardBeforeResolveUndo = Game.board
        val whiteMoves = Game.getPossibleMoves("white", Game.board, Game.movesPerformed[Game.movesPerformed.size-1])
        for (i in whiteMoves.indices){
            if (whiteMoves[i].specialMove == "en passant"){
                val move =  whiteMoves[i]
                Game.resolveMove(move)
                break
            }
        }

        val blackMoves = Game.getPossibleMoves("black", Game.board, Game.movesPerformed[Game.movesPerformed.size-1])
        for (i in blackMoves.indices){
            if (blackMoves[i].specialMove == "en passant"){
                val move =  blackMoves[i]
                Game.resolveMove(move)
                break
            }
        }
        Game.undoMove()
        assertEquals(boardBeforeResolveUndo, Game.board)
    }

    @Test
    fun algorithmTime(){
        //this little test shows that min prefers moves that actually put them ahead in point

        Game.resolveMove(Move(listOf(0,1), listOf(2,2), false, null,null ))
        Game.resolveMove(Move(listOf(0,6), listOf(2,5), false, null,null ))
        Game.resolveMove(Move(listOf(1,3), listOf(2,3), false, null,null))
        Game.resolveMove(Move(listOf(0,2), listOf(2,4), false, null,null))

        val move = EvalFun.maxVal(-10000000, 1000000, 3, System.currentTimeMillis(), 15)[1] as Move


    }

    @Test
    fun moveListGenerationTime(){
        Game.resolveMove(Move(listOf(0,1), listOf(2,2), false, null,null ))
        Game.resolveMove(Move(listOf(0,6), listOf(2,5), false, null,null ))
        Game.resolveMove(Move(listOf(1,3), listOf(2,3), false, null,null))
        Game.resolveMove(Move(listOf(0,2), listOf(2,4), false, null,null))

        val elapsed = measureTimeMillis {
            val moveList = Game.getValidMoves(Game.board, "white")
        }
        print(elapsed)
    }

    @Test
    fun heuristics(){

        val value = EvalFun.heuristics(Game.board)
        print("fff")
    }

}
