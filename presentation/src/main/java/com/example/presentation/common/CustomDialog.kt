package com.example.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.presentation.R
import com.example.presentation.databinding.UiDialogBinding

class CustomDialog : DialogFragment() {
    private lateinit var binding: UiDialogBinding
    private var title = ""
    private var content = ""
    private var positive = ""
    private var negative = ""
    private var negativeEvent: ((View) -> Unit)? = null
    private var positiveEvent: ((View) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.custom_dialog)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = UiDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
    }

    inner class Builder {
        fun setTitleTxt(title: String): Builder {
            this@CustomDialog.title = title
            return this
        }

        fun setContentTxt(content: String): Builder {
            this@CustomDialog.content = content
            return this
        }

        fun setPositiveTxt(positive: String): Builder {
            this@CustomDialog.positive = positive
            return this
        }

        fun setNegativeTxt(negative: String): Builder {
            this@CustomDialog.negative = negative
            return this
        }

        fun setNegativeListener(onNegativeListener: (View) -> Unit): Builder {
            this@CustomDialog.negativeEvent = onNegativeListener
            return this
        }

        fun setPositiveListener(onPositiveListener: (View) -> Unit): Builder {
            this@CustomDialog.positiveEvent = onPositiveListener
            return this
        }

        fun show(fragmentManager: FragmentManager, tag: String) {
            this@CustomDialog.show(fragmentManager, tag)
        }
    }

    private fun initView() = with(binding) {
        titleTxt.text = title
        contentTxt.text = content
        positiveBtn.text = positive
        negativeBtn.text = negative
    }

    private fun initListener() = with(binding) {
        negativeBtn.setOnClickListener {
            negativeEvent?.invoke(it)
            this@CustomDialog.dismiss()
        }

        positiveBtn.setOnClickListener {
            positiveEvent?.invoke(it)
            this@CustomDialog.dismiss()
        }
    }
}