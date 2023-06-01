package com.example.zipcodefounder

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.zipcodefounder.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {
    private lateinit var zipViewModel: ZipViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        zipViewModel = ViewModelProvider(this).get(ZipViewModel::class.java)


        binding.searchButton.setOnClickListener {
            val zipCode = binding.zipCodeEditText.text.toString().trim()
            zipViewModel.searchZip(zipCode)
        }

        zipViewModel.observeState().observe(this, Observer { state ->
            when (state) {
                is ZipViewModel.State.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val region = state.region
                    val message = "Country: ${region.country}\nState: ${region.state}\nCity: ${region.city}"
                    showAlertDialog("Region Found", message)
                }
                ZipViewModel.State.Failure -> {
                    showAlertDialog("Error", "Region not found")
                }
                ZipViewModel.State.Loading -> {
                    binding.progressBar.visibility = VISIBLE
                }
                ZipViewModel.State.Empty -> {
                    // Empty state, do nothing
                }
            }
        })
    }

    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }
}
