package com.example.presentation.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.DayModel
import com.example.presentation.R
import com.example.presentation.common.CustomDialog
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.ui.adapter.DayAdapter
import com.example.presentation.ui.helper.SwipeHelper
import com.example.presentation.util.ItemClickListener
import com.example.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val days = arrayListOf<DayModel>()
    private val swipeHelper = SwipeHelper()

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val insertDay = result.data?.getSerializableExtra("insertDay", DayModel::class.java)
            val updateDay = result.data?.getSerializableExtra("updateDay", DayModel::class.java)
            val position = result.data?.getIntExtra("position", -100)

            insertDay?.let {
                days.add(0, it)
                dayAdapter.notifyItemRangeInserted(0, 1)
            }

            updateDay?.let { day ->
                position?.let { position ->
                    if (position != -100) {
                        days[position] = day
                        dayAdapter.notifyItemChanged(position)
                    }
                }
            }
        }
    }

    private val dayAdapter by lazy {
        DayAdapter(
            days, object : ItemClickListener<DayModel> {
                override fun itemSettingClick(position: Int, data: DayModel) {
                    super.itemSettingClick(position, data)

                    goUpdate(position, data)
                }

                override fun itemDeleteClick(position: Int, data: DayModel) {
                    super.itemDeleteClick(position, data)

                    CustomDialog().Builder().apply {
                        setIsSingleBtn(false)
                        setTitleTxt(getString(R.string.dialog_title))
                        setContentTxt(getString(R.string.dialog_content))
                        setPositiveTxt(getString(R.string.positive))
                        setNegativeTxt(getString(R.string.negative))
                        setPositiveListener {
                            viewModel.deleteDay(data.key, position)
                        }
                    }.show(supportFragmentManager, "deleteDialog")
                }
            })
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
                        days.clear()

                        if (it.isEmpty()) {
                            binding.topImg.visibility = View.GONE
                            binding.dDayRv.visibility = View.GONE
                            binding.emptyTxt.visibility = View.VISIBLE
                        } else {
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

            ItemTouchHelper(swipeHelper).attachToRecyclerView(dDayRv)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() = with(binding) {
        addBtn.setOnClickListener {
            launcher.launch(Intent(this@MainActivity, InsertDayActivity::class.java))
        }

        dDayRv.setOnTouchListener { view, motionEvent ->
            swipeHelper.removePreviousClamp(dDayRv)
            false
        }
    }

    private fun goUpdate(position: Int, data: DayModel) {
        val intent = Intent(this@MainActivity, UpdateDayActivity::class.java).apply {
            putExtra("position", position)
            putExtra("data", data)
        }

        launcher.launch(intent)
    }
}