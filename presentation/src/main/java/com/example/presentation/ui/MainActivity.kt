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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.DayModel
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.ui.adapter.DayAdapter
import com.example.presentation.ui.helper.SwipeHelper
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

        viewModel.getAllDay()
        initListener()
        initAdapter()
        lifecycleScope()
    }

    private fun lifecycleScope() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            launch {
                viewModel.days.collect {
                    it?.let {
                        if (it.isEmpty()) {
                            binding.topImg.visibility = View.GONE
                            binding.dDayRv.visibility = View.GONE
                            binding.emptyTxt.visibility = View.VISIBLE
                        } else {
                            days.clear()
                            days.addAll(it)
                            dayAdapter.notifyItemRangeChanged(0, it.size)
                            binding.emptyTxt.visibility = View.GONE
                            binding.topImg.visibility = View.VISIBLE
                            binding.dDayRv.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun initAdapter() = with(binding) {
        dDayRv.apply {
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = dayAdapter

            val test = SwipeHelper(dayAdapter)
            ItemTouchHelper(test).attachToRecyclerView(dDayRv)
        }
    }

    private fun initListener() = with(binding) {
        addBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, DayActivity::class.java))
        }
    }
}