package com.app.meditar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.meditar.databinding.FragmentInicioBinding


class InicioFragment : Fragment() {
    private lateinit var binding: FragmentInicioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioBinding.inflate(layoutInflater).apply {
            viewLifecycleOwner
        }
        initView()
        return binding.root
    }

    private fun initView() {
        binding.img1.setOnClickListener {
            findNavController().navigate(
                R.id.action_inicioFragment_to_oneFragment
            )
        }
        binding.img2.setOnClickListener {

            findNavController().navigate(
                R.id.action_inicioFragment_to_twoFragment
            )

        }

        binding.img3.setOnClickListener {
            findNavController().navigate(
                R.id.action_inicioFragment_to_threeFragment
            )

        }

        binding.img4.setOnClickListener {
            findNavController().navigate(
                R.id.action_inicioFragment_to_forFragment
            )

        }

        binding.img5.setOnClickListener {
            findNavController().navigate(
                R.id.action_inicioFragment_to_fiveFragment
            )

        }

        binding.img6.setOnClickListener {
            findNavController().navigate(
                R.id.action_inicioFragment_to_sixFragment
            )

        }
    }

}