package com.amlavati.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.amlavati.artbook.R
import com.amlavati.artbook.databinding.FragmentArtDetailsBinding
import com.amlavati.artbook.util.Status
import com.amlavati.artbook.viewmodel.ArtViewModel
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtDetailFragment @Inject constructor(private var glide: RequestManager) :
    Fragment(R.layout.fragment_art_details) {

    private lateinit var binding: FragmentArtDetailsBinding
    lateinit var viewModel : ArtViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        binding = FragmentArtDetailsBinding.bind(view)

        binding.artImageView.setOnClickListener {
            findNavController().navigate(R.id.action_artDetailFragment_to_imageApiFragment)
        }

        subscribeToObservers()
        binding.submit.setOnClickListener {
            viewModel.makeArt(binding.editTextArtName.text.toString(),
                binding.editTextArtistName.text.toString(),
                binding.editTextYear.text.toString())

        }
//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                findNavController().popBackStack()
//            }
//        }
//
//        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }


    private fun subscribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            println(url)
            binding.let {
                glide.load(url).into(it.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }
            }
        })
    }

}