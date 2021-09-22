package com.example.movie_app_xml.view.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.movie_app_xml.R
import com.example.movie_app_xml.api.ApiFactory
import com.example.movie_app_xml.databinding.FragmentAddBinding
import com.example.movie_app_xml.view_model.DetailViewModel
import com.example.movie_app_xml.view_model.FormAddViewModel

class AddFragment : Fragment() {


    private lateinit var _binding : FragmentAddBinding
    private val binding get() = _binding
    private lateinit var addViewModel: FormAddViewModel
    private val restApi by lazy { ApiFactory.create() }

    val itemsAdult = listOf("Pilih Status","Yes", "No")
    val itemsType = listOf("Pilih Type Item","Movie", "Tv Show")
    var adultPosition = 0
    var typePosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapterAdult = ArrayAdapter(requireContext(), R.layout.list_item, itemsAdult)
        val adapterType = ArrayAdapter(requireContext(), R.layout.list_item, itemsType)

        binding.apply {
            actvAddAdult.setAdapter(adapterAdult)
            actvAddType.setAdapter(adapterType)
        }
    }

}