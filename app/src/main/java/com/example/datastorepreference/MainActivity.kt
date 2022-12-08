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
        loadWalkMode()

        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                val str = binding.edtText.text.toString().trim()
                viewModel.setText(str)
                binding.edtText.text?.clear()
            }
        }
        binding.swMode.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                when(isChecked){
                    true -> viewModel.setWalkMode(true)
                    false -> viewModel.setWalkMode(false)
                }
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

    private fun loadWalkMode(){
        binding.apply {
            viewModel.getWalkMode.observe(this@MainActivity){ isWalk ->
                when(isWalk){
                    true -> {
                        swMode.isChecked = true
                        swMode.text = "WALK..."
                        imgStatus.setAnimationFromUrl("https://assets2.lottiefiles.com/packages/lf20_h4mjsyjz.json")
//                        imgStatus.setAnimation(R.raw.walking)
                        imgStatus.playAnimation()
                    }
                    false -> {
                        swMode.isChecked = false
                        swMode.text = "RUN !!"
                        imgStatus.setAnimationFromUrl("https://assets8.lottiefiles.com/packages/lf20_mzbdc0qk.json")
//                        imgStatus.setAnimation(R.raw.running)
                        imgStatus.playAnimation()
                    }
                }
            }
        }
    }
}