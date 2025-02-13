package com.example.presentation.ui.helper

import android.graphics.Canvas
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.presentation.R
import com.example.presentation.ui.adapter.DayAdapter
import kotlin.math.max
import kotlin.math.min

class SwipeHelper(private val adapter: DayAdapter) : ItemTouchHelper.Callback() {
    private var currentPosition: Int? = null
    private var previousPosition: Int? = null
    private var currentDx = 0f
    private var clamp = 0f

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        return makeMovementFlags(0, LEFT)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
//        adapter.notifyItemRemoved(viewHolder.adapterPosition)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
        currentDx = 0f
        previousPosition = viewHolder.adapterPosition
        getDefaultUIUtil().clearView(getView(viewHolder))
    }

    override fun onSelectedChanged(viewHolder: ViewHolder?, actionState: Int) {
        viewHolder?.let {
            currentPosition = viewHolder.adapterPosition
            getDefaultUIUtil().onSelected(getView(it))
        }
    }

    override fun getSwipeThreshold(viewHolder: ViewHolder): Float {
        val isClamped = getTag(viewHolder)
        setTag(viewHolder, !isClamped && currentDx <= -clamp)

        return 2f
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return defaultValue * 10
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (actionState == ACTION_STATE_SWIPE) {
            val view = getView(viewHolder)
            val isClamped = getTag(viewHolder)
            val x = clampViewPositionHorizontal(view, dX, isClamped, isCurrentlyActive)
            currentDx = x

            getDefaultUIUtil().onDraw(
                c, recyclerView, view, x, dY, actionState, isCurrentlyActive
            )
        }
    }

    private fun clampViewPositionHorizontal(view: View, dX: Float, isClamped: Boolean, isCurrentlyActive: Boolean): Float {
        val min = -view.width.toFloat() / 2
        val max = 0f
        val x = if (isClamped) {
            if (isCurrentlyActive) dX - clamp else -clamp
        } else {
            dX
        }

        return min(max(min, x), max)
    }

    private fun setTag(viewHolder: ViewHolder, isClamped: Boolean) {
        viewHolder.itemView.tag = isClamped
    }

    private fun getTag(viewHolder: ViewHolder): Boolean {
        return viewHolder.itemView.tag as? Boolean ?: false
    }

    private fun setClamp(clamp: Float) {
        this.clamp = clamp
    }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    fun removePreviousClamp(recyclerView: RecyclerView) {
        if (currentPosition == previousPosition) return

        previousPosition?.let {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(it) ?: return
            getView(viewHolder).translationX = 0f
            setTag(viewHolder, false)
            previousPosition = null
        }
    }

    private fun getView(viewHolder: ViewHolder): View {
        return viewHolder.itemView.findViewById(R.id.day_layout)
    }
}