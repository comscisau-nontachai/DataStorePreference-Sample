package com.example.datastorepreference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.datastorepreference.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        dataStoreManager = DataStoreManager(this)

        loadSavedText()

        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                val str = binding.edtText.text.toString().trim()
                viewModel.setText(str)
            }
        }
    }

    private fun loadSavedText() {
        binding.apply {
            viewModel.getStringText.observe(this@MainActivity){ str ->
                tvShowData.text = str
            }
        }
    }
}