package com.example.chess.model

class Move(
    val oldPosition: List<Int>,
    val newPosition: List<Int>,
    val enemyDestroyed: Boolean,
    val enemyDestroyedPosition: List<Int>?,
    val specialMove: String?
)