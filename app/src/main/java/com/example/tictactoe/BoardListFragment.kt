package com.example.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.databinding.FragmentButtonListBinding
import com.example.tictactoe.model.BoardListViewModel
import androidx.appcompat.app.AlertDialog
import kotlin.system.exitProcess

class BoardListFragment : Fragment(), OnClickListener {
    private lateinit var binding: FragmentButtonListBinding

    private val boardListViewModel: BoardListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentButtonListBinding.inflate(inflater, container, false)
        
        binding = fragmentBinding

        initBoard()
        changeLabel()
        initScores()
        
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonsEventListeners()
    }

    override fun onClick(view: View?) {
        if (view !is Button) return

        tap(view)
    }

    private fun tap(button: Button) {
        if (!boardListViewModel.isWin) {
            boardListViewModel.add(button)

            // switch header text after every click
            changeLabel()

            if (checkForVictory()) {
                // set winning status
                boardListViewModel.isWin = true

                // add +1 win in player score
                boardListViewModel.incrementScore()

                // Output alert dialog
                result()

                // render scores in footer
                initScores()

                return;
            }

            // Draw
            if (boardListViewModel.isFullBoard()) {
                result(true)
            }
        }
    }

    private fun checkForVictory(): Boolean {
        val str = when (boardListViewModel.currentSymbol) {
            BoardCellValueEnum.ZERO -> ZERO
            BoardCellValueEnum.CROSS -> CROSS
        }

        //Horizontal Victory
        if(match(binding.a1,str) && match(binding.a2,str) && match(binding.a3,str)) {
            return true
        }

        if(match(binding.b1,str) && match(binding.b2,str) && match(binding.b3,str)) {
            return true
        }

        if(match(binding.c1,str) && match(binding.c2,str) && match(binding.c3,str)) {
            return true
        }

        //Vertical Victory
        if(match(binding.a1,str) && match(binding.b1,str) && match(binding.c1,str)) {
            return true
        }

        if(match(binding.a2,str) && match(binding.b2,str) && match(binding.c2,str)) {
            return true
        }

        if(match(binding.a3,str) && match(binding.b3,str) && match(binding.c3,str)) {
            return true
        }

        //Diagonal Victory
        if(match(binding.a1,str) && match(binding.b2,str) && match(binding.c3,str)) {
            return true
        }

        return match(binding.a3,str) && match(binding.b2,str) && match(binding.c1,str)
    }

    private fun match(button: Button, symbol : String): Boolean = button.text == symbol

    private fun result(isFullBoard: Boolean = false) {
        var title = when (boardListViewModel.currentSymbol) {
            BoardCellValueEnum.ZERO -> "Player 1 won!"
            BoardCellValueEnum.CROSS -> "Player 2 won!"
        }

        if (isFullBoard) {
            title = "Draw"
        }

        context?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setNegativeButton("Exit the game")
                { _,_ ->
                    exitProcess(0)
                }
                .setPositiveButton("Play again")
                { _,_ ->
                    boardListViewModel.resetBoard()
                }
                .setCancelable(false)
                .show()
        }
    }

    private fun changeLabel() {
        binding.label.text = when (boardListViewModel.currentSymbol == BoardCellValueEnum.ZERO) {
            true -> getString(R.string.tap_O)
            false -> getString(R.string.tap_x)
        }
    }

    private fun initScores() {
        binding.player1Score.text = boardListViewModel.playerOneScore.toString()
        binding.player2Score.text = boardListViewModel.playerTwoScore.toString()
    }

    private fun initBoard() {
        binding.a1.let { boardListViewModel.boardList.add(it) }
        binding.a2.let { boardListViewModel.boardList.add(it) }
        binding.a3.let { boardListViewModel.boardList.add(it) }
        binding.b1.let { boardListViewModel.boardList.add(it) }
        binding.b2.let { boardListViewModel.boardList.add(it) }
        binding.b3.let { boardListViewModel.boardList.add(it) }
        binding.c1.let { boardListViewModel.boardList.add(it) }
        binding.c2.let { boardListViewModel.boardList.add(it) }
        binding.c3.let { boardListViewModel.boardList.add(it) }
    }

    private fun setButtonsEventListeners() {
        binding.a1.setOnClickListener(this)
        binding.a2.setOnClickListener(this)
        binding.a3.setOnClickListener(this)
        binding.b1.setOnClickListener(this)
        binding.b2.setOnClickListener(this)
        binding.b3.setOnClickListener(this)
        binding.c1.setOnClickListener(this)
        binding.c2.setOnClickListener(this)
        binding.c3.setOnClickListener(this)
    }

    companion object {
        const val ZERO = "O"
        const val CROSS = "X"
        const val PLAYER1 = "Player 1 (O)"
        const val PLAYER2 = "Player 2 (X)"
    }
}
