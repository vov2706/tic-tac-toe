package com.example.tictactoe.model

import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.BoardCellValueEnum

class BoardListViewModel() : ViewModel() {
//    val boardList: MutableLiveData<List<Button>> by lazy {
//        MutableLiveData<List<Button>>()
//    }
    val boardList = mutableListOf<Button>()
    var currentSymbol = BoardCellValueEnum.ZERO
    var tappedSymbol = BoardCellValueEnum.ZERO
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
            tappedSymbol = BoardCellValueEnum.ZERO;
        } else {
            button.text = CROSS
            currentSymbol = BoardCellValueEnum.ZERO
            tappedSymbol = BoardCellValueEnum.CROSS;
        }
    }

    fun incrementScore() {
        if(tappedSymbol == BoardCellValueEnum.ZERO) {
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