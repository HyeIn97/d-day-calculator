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
    private var isSingleBtn = true
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
        fun setIsSingleBtn(isSingleBtn: Boolean): Builder {
            this@CustomDialog.isSingleBtn = isSingleBtn
            return this
        }

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
        if (title.isNotBlank()) {
            titleTxt.visibility = View.VISIBLE
            titleTxt.text = title
        } else {
            titleTxt.visibility = View.GONE
        }

        if (content.isNotBlank()) {
            contentTxt.visibility = View.VISIBLE
            contentTxt.text = content
        } else {
            contentTxt.visibility = View.GONE
        }

        positiveBtn.text = positive
        if (isSingleBtn) {
            negativeBtn.visibility = View.GONE
        } else {
            negativeBtn.visibility = View.VISIBLE
            negativeBtn.text = negative
        }
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