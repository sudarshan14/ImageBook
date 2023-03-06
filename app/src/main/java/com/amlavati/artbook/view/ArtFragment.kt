package com.amlavati.artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amlavati.artbook.R
import com.amlavati.artbook.databinding.FragmentArtsBinding

class ArtFragment : Fragment(R.layout.fragment_arts) {
    lateinit var binding: FragmentArtsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArtsBinding.bind(view)


        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_artFragment_to_artDetailFragment)
        }

    }



}