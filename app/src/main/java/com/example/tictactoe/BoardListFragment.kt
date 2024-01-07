package com.example.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.tictactoe.databinding.FragmentButtonListBinding
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.model.BoardListViewModel
import kotlin.system.exitProcess

class BoardListFragment : Fragment(), OnClickListener {
    private lateinit var binding: FragmentButtonListBinding

    private lateinit var viewModel: BoardListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentButtonListBinding.inflate(inflater, container, false)
        
        binding = fragmentBinding

        viewModel = ViewModelProvider(this)[BoardListViewModel::class.java]

        setObservers()

        initScores()

        setButtonsEventListeners()

        if (viewModel.isWin) {
            result()
        } else if (viewModel.isFullBoard()) {
            result(true)
        }

        return fragmentBinding.root
    }

    override fun onClick(view: View?) {
        if (view !is Button) return

        tap(view)
    }

    private fun tap(button: Button) {
        if (!viewModel.isWin) {

            if(button.text != "") {
                return
            }

            viewModel.add(button)

            // switch header text after every click
            changeLabel()

            if (checkForVictory()) {
                // set winning status
                viewModel.isWin = true

                // add +1 win in player score
                viewModel.incrementScore()

                // Output alert dialog
                result()

                // render scores in footer
                initScores()

                return;
            }

            // Draw
            if (viewModel.isFullBoard()) {
                result(true)
            }
        }
    }

    private fun checkForVictory(): Boolean {
        val str = when (viewModel.tappedSymbol) {
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
        var title = when (viewModel.tappedSymbol) {
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
            viewModel.resetBoard()
            alertDialog.hide()
        }

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun changeLabel(currentSymbol: BoardCellValueEnum? = null) {

        var current = currentSymbol

        if (current === null) {
            current = viewModel.currentSymbol.value
        }

        binding.label.text = when (current == BoardCellValueEnum.ZERO) {
            true -> getString(R.string.tap_O)
            false -> getString(R.string.tap_x)
        }
    }

    private fun initScores() {
        binding.player1Score.text = viewModel.playerOneScore.toString()
        binding.player2Score.text = viewModel.playerTwoScore.toString()
    }

    private fun initBoard() {
        viewModel.boardList.value?.add(binding.a1)
        viewModel.boardList.value?.add(binding.a2)
        viewModel.boardList.value?.add(binding.a3)
        viewModel.boardList.value?.add(binding.b1)
        viewModel.boardList.value?.add(binding.b2)
        viewModel.boardList.value?.add(binding.b3)
        viewModel.boardList.value?.add(binding.c1)
        viewModel.boardList.value?.add(binding.c2)
        viewModel.boardList.value?.add(binding.c3)
    }

    private fun setObservers() {
        viewModel.boardList.observe(viewLifecycleOwner, Observer {
            for (button in it) {
                when (button.id) {
                    binding.a1.id -> binding.a1.text = button.text.toString()
                    binding.a2.id -> binding.a2.text = button.text.toString()
                    binding.a3.id -> binding.a3.text = button.text.toString()
                    binding.b1.id -> binding.b1.text = button.text.toString()
                    binding.b2.id -> binding.b2.text = button.text.toString()
                    binding.b3.id -> binding.b3.text = button.text.toString()
                    binding.c1.id -> binding.c1.text = button.text.toString()
                    binding.c2.id -> binding.c2.text = button.text.toString()
                    binding.c3.id -> binding.c3.text = button.text.toString()
                }
            }

            initBoard()
        })

        viewModel.currentSymbol.observe(viewLifecycleOwner, Observer {
            changeLabel(it)
        })
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
