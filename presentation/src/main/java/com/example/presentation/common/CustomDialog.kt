package com.example.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.presentation.R
import com.example.presentation.databinding.UiDialogBinding

class CustomDialog : DialogFragment() {
    private lateinit var binding: UiDialogBinding
    private var title = ""
    private var content = ""
    private var noneEvent: ((View) -> Unit)? = null
    private var checkEvent: ((View) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = UiDialogBinding.inflate(inflater, container, false)
        setStyle(STYLE_NO_TITLE, R.style.custom_dialog)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    inner class Builder {
        fun setTitle(title: String): Builder {
            this@CustomDialog.title = title
            return this
        }

        fun setContent(content: String): Builder {
            this@CustomDialog.content = content
            return this
        }

        fun setNoneListener(onNoneListener: (View) -> Unit): Builder {
            this@CustomDialog.noneEvent = onNoneListener
            return this
        }

        fun setCheckListener(onCheckListener: (View) -> Unit): Builder {
            this@CustomDialog.checkEvent = onCheckListener
            return this
        }
    }
}