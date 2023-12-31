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

class BoardListFragment : Fragment(), OnClickListener {
    private var binding: FragmentButtonListBinding? = null

    private val boardListViewModel: BoardListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentButtonListBinding.inflate(inflater, container, false)
        
        binding = fragmentBinding

        initBoard()
        changeLabel()
        
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonsEventListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(view: View?) {
        if (view !is Button) return

        tap(view)
    }

    private fun tap(button: Button) {
        boardListViewModel.add(button)
        changeLabel()
    }

    private fun changeLabel() {
        binding?.label?.text = when (boardListViewModel.currentSymbol == BoardCellValueEnum.ZERO) {
            true -> getString(R.string.tap_O)
            false -> getString(R.string.tap_x)
        }
    }

    private fun initBoard() {
        binding?.a1?.let { boardListViewModel.boardList.add(it) }
        binding?.a2?.let { boardListViewModel.boardList.add(it) }
        binding?.a3?.let { boardListViewModel.boardList.add(it) }
        binding?.b1?.let { boardListViewModel.boardList.add(it) }
        binding?.b2?.let { boardListViewModel.boardList.add(it) }
        binding?.b3?.let { boardListViewModel.boardList.add(it) }
        binding?.c1?.let { boardListViewModel.boardList.add(it) }
        binding?.c2?.let { boardListViewModel.boardList.add(it) }
        binding?.c3?.let { boardListViewModel.boardList.add(it) }
    }

    private fun setButtonsEventListeners() {
        binding?.a1?.setOnClickListener(this)
        binding?.a2?.setOnClickListener(this)
        binding?.a3?.setOnClickListener(this)
        binding?.b1?.setOnClickListener(this)
        binding?.b2?.setOnClickListener(this)
        binding?.b3?.setOnClickListener(this)
        binding?.c1?.setOnClickListener(this)
        binding?.c2?.setOnClickListener(this)
        binding?.c3?.setOnClickListener(this)
    }
}