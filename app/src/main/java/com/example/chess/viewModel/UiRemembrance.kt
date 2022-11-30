package com.example.chess.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.chess.model.Move

class UiRemembrance {
    var firstClick = mutableStateOf(true)
    var lastClickPosition = mutableStateListOf<Int>()
    var possibleMoves = mutableStateListOf<Move>()
    var recommendedMove = mutableStateOf<String>("")

    fun changeFirstClick(bool: Boolean){
        firstClick.value = bool
    }
    fun changeLastClickPosition(list: List<Int>){
        lastClickPosition.clear()
        for (i in list.indices){
            lastClickPosition.add(list[i])
        }
    }

    fun changepossibleMoves(list: List<Move>){
        possibleMoves.clear()
        for (i in list.indices){
            possibleMoves.add(list[i])
        }
    }

    fun changeRecommendedMove(string: String){
        recommendedMove.value = string
    }
}