package com.example.tictactoe.model

import android.widget.Button
import androidx.lifecycle.ViewModel
import com.example.tictactoe.BoardCellValueEnum
import com.example.tictactoe.BoardListFragment

class BoardListViewModel : ViewModel() {
    private val boardList = mutableListOf<Button>()
    private var currentSymbol = BoardCellValueEnum.ZERO

    fun add(button: Button) {
        boardList.add(button)

        if(button.text != "") return

        if(currentSymbol == BoardCellValueEnum.ZERO) {
            button.text = BoardListFragment.ZERO
            currentSymbol = BoardCellValueEnum.CROSS
        } else if(currentSymbol == BoardCellValueEnum.CROSS) {
            button.text = BoardListFragment.CROSS
            currentSymbol = BoardCellValueEnum.ZERO
        }
    }

    fun isFullBoard(): Boolean
    {
        for(button in boardList) {
            if(button.text == "") return false
        }

        return true
    }
}