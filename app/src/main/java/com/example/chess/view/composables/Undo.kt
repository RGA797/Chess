package com.example.chess.view

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chess.viewModel.Game

@Composable
fun UndoButton(gameObject: Game){
    Button(onClick = {gameObject.undoMove()}
        , modifier = Modifier
            .height(70.dp)
            .width(100.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
    ){
        Text(
        text = "Undo",
        modifier = Modifier.padding(bottom = 1.dp),
        style = MaterialTheme.typography.h5
    )
    }
}
