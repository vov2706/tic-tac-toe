package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.databinding.FragmentButtonListBinding
import com.example.tictactoe.model.BoardListViewModel
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import kotlin.system.exitProcess

class BoardListFragment : Fragment(), OnClickListener {
    private lateinit var binding: FragmentButtonListBinding

    private val boardListViewModel: BoardListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        boardListViewModel.boardList.observe(this, Observer {
            for (button in it) {
                when (button.id) {
                    binding.a1.id -> binding.a1.text = button.text
                    binding.a2.id -> binding.a2.text = button.text
                    binding.a3.id -> binding.a3.text = button.text
                    binding.b1.id -> binding.b1.text = button.text
                    binding.b2.id -> binding.b2.text = button.text
                    binding.b3.id -> binding.b3.text = button.text
                    binding.c1.id -> binding.c1.text = button.text
                    binding.c2.id -> binding.c2.text = button.text
                    binding.c3.id -> binding.c3.text = button.text
                }
            }
        })

        boardListViewModel.currentSymbol.observe(this, Observer {
            changeLabel(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentButtonListBinding.inflate(inflater, container, false)
        
        binding = fragmentBinding

        initBoard()
        changeLabel()
        initScores()

        if (boardListViewModel.isWin) {
            result()
        }

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
        val str = when (boardListViewModel.tappedSymbol) {
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
        var title = when (boardListViewModel.tappedSymbol) {
            BoardCellValueEnum.ZERO -> "Player 1 won!"
            BoardCellValueEnum.CROSS -> "Player 2 won!"
        }

        if (isFullBoard) {
            title = "Draw"
        }

        val alertDialog = AlertDialog.Builder(requireContext(), R.style.AlertDialog).create()
        val alertDialogView = layoutInflater.inflate(R.layout.alert_layout,null)

        val textTitle = alertDialogView.findViewById<TextView>(R.id.alertTitle)
        textTitle.text = title

        val exitButton = alertDialogView.findViewById<Button>(R.id.alertExitButton)

        alertDialog.setView(alertDialogView)

        exitButton.setOnClickListener {
            exitProcess(0)
        }

        val playAgainButton = alertDialogView.findViewById<Button>(R.id.alertPlayAgainButton)

        playAgainButton.setOnClickListener {
            boardListViewModel.resetBoard()
            alertDialog.hide()
        }

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun changeLabel(currentSymbol: BoardCellValueEnum? = null) {

        var current = currentSymbol

        if (current === null) {
            current = boardListViewModel.currentSymbol.value
        }

        binding.label.text = when (current == BoardCellValueEnum.ZERO) {
            true -> getString(R.string.tap_O)
            false -> getString(R.string.tap_x)
        }
    }

    private fun initScores() {
        binding.player1Score.text = boardListViewModel.playerOneScore.toString()
        binding.player2Score.text = boardListViewModel.playerTwoScore.toString()
    }

    private fun initBoard() {
        Log.d("a1", binding.a1.id.toString())
        Log.d("a2", binding.a2.id.toString())
        Log.d("a3", binding.a3.id.toString())
        Log.d("b1", binding.b1.id.toString())
        Log.d("b2", binding.b2.id.toString())
        Log.d("b3", binding.b3.id.toString())
        Log.d("c1", binding.c1.id.toString())
        Log.d("c2", binding.c2.id.toString())
        Log.d("c3", binding.c3.id.toString())
        binding.a1.let { boardListViewModel.boardList.value?.add(it) }
        binding.a2.let { boardListViewModel.boardList.value?.add(it) }
        binding.a3.let { boardListViewModel.boardList.value?.add(it) }
        binding.b1.let { boardListViewModel.boardList.value?.add(it) }
        binding.b2.let { boardListViewModel.boardList.value?.add(it) }
        binding.b3.let { boardListViewModel.boardList.value?.add(it) }
        binding.c1.let { boardListViewModel.boardList.value?.add(it) }
        binding.c2.let { boardListViewModel.boardList.value?.add(it) }
        binding.c3.let { boardListViewModel.boardList.value?.add(it) }
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
    }
}
