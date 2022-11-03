package com.example.chess

import com.example.chess.model.Move
import com.example.chess.viewModel.Game
import org.junit.Test

import org.junit.Assert.*
import kotlin.system.measureTimeMillis

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun gameWorks(){
        val game = Game()

        //this little test shows that min prefers moves that actually put them ahead in point
        game.resolveMove(Move(listOf(1,0), listOf(3,0), false, null,"double" ))
        game.resolveMove(Move(listOf(3,0), listOf(4,0), false, null,null ))
        game.resolveMove(Move(listOf(4,0), listOf(5,0), false, null,null ))

        val move = game.min(-10000000,1000000, 3)
         print(move)
    }


    @Test
    fun enPassant(){
        val game = Game()

        //this little test shows that min prefers moves that actually put them ahead in point
        game.resolveMove(Move(listOf(1,0), listOf(3,0), false, null,"double" ))
        game.resolveMove(Move(listOf(3,0), listOf(4,0), false, null,null ))
        game.resolveMove(Move(listOf(6,1), listOf(4,1), false, null,"double"))

        val boardBeforeResolveUndo = game.board
        val whiteMoves = game.getPossibleMoves("white", game.board)
        for (i in whiteMoves.indices){
            if (whiteMoves[i].specialMove == "en passant"){
                val move =  whiteMoves[i]
                game.resolveMove(move)
                break
            }
        }

        val blackMoves = game.getPossibleMoves("black", game.board)
        for (i in blackMoves.indices){
            if (blackMoves[i].specialMove == "en passant"){
                val move =  blackMoves[i]
                game.resolveMove(move)
                break
            }
        }
        game.undoMove()
        assertEquals(boardBeforeResolveUndo, game.board)
    }

    @Test
    fun time(){
        val game = Game()
        //this little test shows that min prefers moves that actually put them ahead in point
        val elapsed = measureTimeMillis {
            game.min(-10000000,1000000, 6)
        }
        print(elapsed)
    }
}
