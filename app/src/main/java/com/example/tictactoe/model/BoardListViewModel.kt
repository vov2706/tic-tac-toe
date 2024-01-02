package com.example.tictactoe.model

import android.widget.Button
import androidx.lifecycle.ViewModel
import com.example.tictactoe.BoardCellValueEnum

class BoardListViewModel() : ViewModel() {
    val boardList = mutableListOf<Button>()
    var currentSymbol = BoardCellValueEnum.ZERO
    var playerOneScore = 0
    var playerTwoScore = 0
    var isWin = false

    fun add(button: Button?) {
        if (button == null) {
            return
        }

        if(button.text != "") {
            return
        }

        if(currentSymbol == BoardCellValueEnum.ZERO) {
            button.text = ZERO
            currentSymbol = BoardCellValueEnum.CROSS
        } else if(currentSymbol == BoardCellValueEnum.CROSS) {
            button.text = CROSS
            currentSymbol = BoardCellValueEnum.ZERO
        }
    }

    fun incrementScore() {
        if(currentSymbol == BoardCellValueEnum.ZERO) {
            playerOneScore++
        } else {
            playerTwoScore++
        }
    }

    fun isFullBoard(): Boolean {
        for(button in boardList) {
            if (button.text == "") {
                return false
            }
        }

        return true
    }

    fun resetBoard() {
        for(button in boardList) {
            button.text = ""
        }

        currentSymbol = when (currentSymbol == BoardCellValueEnum.ZERO) {
            true -> BoardCellValueEnum.CROSS
            false -> BoardCellValueEnum.ZERO
        }

        isWin = false
    }

    companion object {
        const val ZERO = "O"
        const val CROSS = "X"
    }
}