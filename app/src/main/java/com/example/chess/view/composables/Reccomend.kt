package com.example.chess.view

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chess.model.Move
import com.example.chess.viewModel.EvalFun
import com.example.chess.viewModel.Game
import com.example.chess.viewModel.UiRemembrance

@Composable
fun RecommendButtonWhite(uiRemembrance: UiRemembrance){
    Button(onClick = {
        val min = EvalFun.minVal(-10000000,1000000, 2, System.currentTimeMillis(), 15)
        val move: Move? = min[1] as Move?
        print(EvalFun.nodeCounter)
        EvalFun.nodeCounter = 0
        if (move != null){
            uiRemembrance.changeRecommendedMove("" + move.oldPosition[0]+","+move.oldPosition[1] + " ->" + move.newPosition[0]+","+move.newPosition[1])
        }
        else{
            uiRemembrance.changeRecommendedMove("No possible moves!")
        }
                     }
        , modifier = Modifier
            .height(80.dp)
            .width(200.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
    ){
        Text(
        text = "Recommend move for white",
        modifier = Modifier.padding(bottom = 1.dp),
        style = MaterialTheme.typography.h5
    )
    }
}


@Composable
fun RecommendButtonBlack(uiRemembrance: UiRemembrance){
    Button(onClick = {
        val move: Move? = EvalFun.maxVal(-10000000,10000000, 2, System.currentTimeMillis(), 15)[1] as Move?
        print(EvalFun.nodeCounter)
        EvalFun.nodeCounter = 0
        if (move != null){
            uiRemembrance.changeRecommendedMove("" + move.oldPosition[0]+","+move.oldPosition[1] + " ->" + move.newPosition[0]+","+move.newPosition[1])
        }
        else{
            uiRemembrance.changeRecommendedMove("No possible moves!")
        }
    }
        , modifier = Modifier
            .height(80.dp)
            .width(200.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
    ){
        Text(
            text = "Recommend move for black",
            modifier = Modifier.padding(bottom = 1.dp),
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun RecommendDisplay(uiRemembrance: UiRemembrance){
    val textToShow = remember {uiRemembrance.recommendedMove}
    Text(
        text = textToShow.value,
        modifier = Modifier.padding(bottom = 1.dp),
        style = MaterialTheme.typography.h5,
    )
}
