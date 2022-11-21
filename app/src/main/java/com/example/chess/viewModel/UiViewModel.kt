package com.example.chess.viewModel

import com.example.chess.model.Move
import com.example.chess.model.UImodel.UiValues

class UiViewModel {
    val uiModel = UiValues()
    fun changeFirstClick(bool: Boolean){
        uiModel.firstClick.value = bool
    }
    fun changeLastClickPosition(list: List<Int>){
        uiModel.lastClickPosition.clear()
        for (i in list.indices){
            uiModel.lastClickPosition.add(list[i])
        }
    }

    fun changepossibleMoves(list: List<Move>){
        uiModel.possibleMoves.clear()
        for (i in list.indices){
            uiModel.possibleMoves.add(list[i])
        }
    }

    fun changeRecommendedMove(string: String){
        uiModel.recommendedMove.value = string
    }
}