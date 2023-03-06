package com.amlavati.artbook.view

import android.os.Binder
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.amlavati.artbook.R
import com.amlavati.artbook.databinding.FragmentArtDetailsBinding

class ArtDetailFragment : Fragment(R.layout.fragment_art_details) {

    private lateinit var binding: FragmentArtDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArtDetailsBinding.bind(view)

        binding.artImageView.setOnClickListener {
            findNavController().navigate(R.id.action_artDetailFragment_to_imageApiFragment)
        }

//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                findNavController().popBackStack()
//            }
//        }
//
//        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}