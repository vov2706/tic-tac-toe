package com.example.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.tictactoe.databinding.FragmentButtonListBinding
import com.example.tictactoe.model.BoardListViewModel

class BoardListFragment : Fragment() {
    private var binding: FragmentButtonListBinding? = null

    private val boardListViewModel: BoardListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBoard()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentButtonListBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

//      binding?.boardListFragment = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

//    fun tap(button: Button)
//    {
//        boardListViewModel.add(button)
//    }

    private fun initBoard() {
        binding?.let { boardListViewModel.add(it.a1) }
        binding?.let { boardListViewModel.add(it.a2) }
        binding?.let { boardListViewModel.add(it.a3) }
        binding?.let { boardListViewModel.add(it.b1) }
        binding?.let { boardListViewModel.add(it.b2) }
        binding?.let { boardListViewModel.add(it.b3) }
        binding?.let { boardListViewModel.add(it.c1) }
        binding?.let { boardListViewModel.add(it.c2) }
        binding?.let { boardListViewModel.add(it.c3) }
    }

    companion object
    {
        const val ZERO = "O"
        const val CROSS = "X"
    }
}