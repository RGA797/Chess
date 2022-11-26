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
    fun time(){
        //this little test shows that min prefers moves that actually put them ahead in point
        // depth 6 time: 942239 milis - 4.8 minutes!
        //depth  4 time: 18822 milis - 0.3 minutes!
        val elapsed = measureTimeMillis {
            EvalFun.minVal(-10000000, 1000000, 6, System.currentTimeMillis(), 60*5)
        }
        print(elapsed)
    }

}
