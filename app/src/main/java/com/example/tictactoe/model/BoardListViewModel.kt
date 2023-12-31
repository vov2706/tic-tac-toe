package com.example.tictactoe.model

import android.widget.Button
import androidx.lifecycle.ViewModel
import com.example.tictactoe.BoardCellValueEnum

class BoardListViewModel() : ViewModel() {
     val boardList = mutableListOf<Button>()
    private var currentSymbol = BoardCellValueEnum.ZERO

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

        if (isFullBoard()) {
            resetBoard()
        }
    }

    private fun isFullBoard(): Boolean {
        for(button in boardList) {
            if (button.text == "") {
                return false
            }
        }

        return true
    }

    private fun resetBoard() {
        for(button in boardList) {
            button.text = ""
        }
    }

    companion object {
        const val ZERO = "O"
        const val CROSS = "X"
    }
}