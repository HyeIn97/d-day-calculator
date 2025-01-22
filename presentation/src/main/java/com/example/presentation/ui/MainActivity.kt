package com.example.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.DayModel
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.ui.adapter.DayAdapter
import com.example.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val days = arrayListOf<DayModel>()
    private val dayAdapter by lazy {
        DayAdapter(days)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)

        initListener()
        initAdatper()
        viewModel.getAllDay()
        lifecycleScope()
    }

    private fun lifecycleScope() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            launch {
                viewModel.days.collect {
                    it?.let {
                       days.addAll(it)
                        dayAdapter.notifyItemRangeChanged(0, it.size)
                        binding.emptyTxt.visibility = View.GONE
                        binding.dDayRv.visibility = View.VISIBLE
                    }?: run{
                        binding.dDayRv.visibility = View.GONE
                        binding.emptyTxt.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun initAdatper() = with(binding){
        dDayRv.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = dayAdapter
        }
    }

    private fun initListener() = with(binding) {
        addBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, DayActivity::class.java))
        }
    }
}