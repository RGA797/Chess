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
import com.example.chess.viewModel.Game
import com.example.chess.viewModel.UiViewModel

@Composable
fun RecommendButton(gameObject: Game, uiViewModel: UiViewModel){
    Button(onClick = {
        val move: Move? = gameObject.min(-10000000,1000000, 2, System.currentTimeMillis())[1] as Move?
        if (move != null){
            uiViewModel.changeRecommendedMove("" + move.oldPosition[0]+","+move.oldPosition[1] + " ->" + move.newPosition[0]+","+move.newPosition[1])
        }
        else{
            uiViewModel.changeRecommendedMove("No possible moves!")
        }
                     }
        , modifier = Modifier
            .height(80.dp)
            .width(200.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
    ){
        Text(
        text = "Recommend move",
        modifier = Modifier.padding(bottom = 1.dp),
        style = MaterialTheme.typography.h5
    )
    }
}

@Composable
fun RecommendDisplay(uiViewModel: UiViewModel){
    val textToShow = remember {uiViewModel.uiModel.recommendedMove}
    Text(
        text = textToShow.value,
        modifier = Modifier.padding(bottom = 1.dp),
        style = MaterialTheme.typography.h5,
    )
}
