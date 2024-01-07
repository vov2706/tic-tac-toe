package com.example.tictactoe.model

import android.util.Log
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.BoardCellValueEnum
import com.example.tictactoe.R

class BoardListViewModel() : ViewModel() {
    val boardList: MutableLiveData<MutableList<Button>> = MutableLiveData<MutableList<Button>>()
    var currentSymbol = MutableLiveData<BoardCellValueEnum>()
    var tappedSymbol = BoardCellValueEnum.ZERO
    var playerOneScore = 0
    var playerTwoScore = 0
    var isWin = false

    init {
        boardList.value = mutableListOf()
        currentSymbol.value = BoardCellValueEnum.ZERO
    }

    fun add(button: Button?) {
        if (button == null) {
            return
        }

        if(button.text != "") {
            return
        }

        if(currentSymbol.value == BoardCellValueEnum.ZERO) {
            button.text = ZERO
            currentSymbol.value = BoardCellValueEnum.CROSS
            tappedSymbol = BoardCellValueEnum.ZERO;
        } else {
            button.text = CROSS
            currentSymbol.value = BoardCellValueEnum.ZERO
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
        for(button in boardList.value!!) {
            when (button.id) {
                R.id.a1 -> Log.d("view model a1", button.text.toString())
                R.id.a2 -> Log.d("view model a2", button.text.toString())
                R.id.a3 -> Log.d("view model a3", button.text.toString())
                R.id.b1 -> Log.d("view model b1", button.text.toString())
                R.id.b2 -> Log.d("view model b2", button.text.toString())
                R.id.b3 -> Log.d("view model b3", button.text.toString())
                R.id.c1 -> Log.d("view model c1", button.text.toString())
                R.id.c2 -> Log.d("view model c2", button.text.toString())
                R.id.c3 -> Log.d("view model c3", button.text.toString())
            }
            if (button.text == "") {
                return false
            }
        }

        return true
    }

    fun resetBoard() {
        for(button in boardList.value!!) {
            button.text = ""
        }

        currentSymbol.value = when (currentSymbol.value == BoardCellValueEnum.ZERO) {
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