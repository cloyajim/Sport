package com.example.sports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sports.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar

class DetailFragment: Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : SportAdapter
    private lateinit var sport : Sport

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            sport = Sport(0,
                it.getString("Name", ""),
                it.getString("Description", ""),
                it.getString("Image", ""))

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvName.text = sport.name
        binding.tvDescription.text = sport.description
        Glide.with(this)
            .load(sport.imgUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivCard)

        setupRecyclerView()
        getAllSport()

    }

    private fun setupRecyclerView() {
        adapter = SportAdapter()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = this@DetailFragment.adapter
        }

    }

    private fun getAllSport() {
        val sportData = FakeDatabase.sports()
        sportData.forEach { sport ->
            adapter.add(sport)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}