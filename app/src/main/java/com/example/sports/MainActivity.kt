package com.example.sports

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sports.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.example.sports.FakeDatabase

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: SportListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        getAllSport()
    }

    private fun setupRecyclerView() {
        listAdapter = SportListAdapter(this)
        binding.recyclerView.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter

        }
    }

   private fun getAllSport(){
        val sportData = FakeDatabase.sports()
       listAdapter.submitList(sportData)

   }

    override fun OnCLick(sport: Sport) {
        Snackbar.make(binding.root, sport.name, Snackbar.LENGTH_SHORT).show()

        val fragment = DetailFragment()
        val args = Bundle()

        args.putString("Name", sport.name )
        args.putString("Description", sport.description)
        args.putString("Image", sport.imgUrl)
        fragment.arguments = args


        supportFragmentManager
            .beginTransaction()
            .add(R.id.containerMain, fragment)
            .addToBackStack(null)
            .commit()
    }
}