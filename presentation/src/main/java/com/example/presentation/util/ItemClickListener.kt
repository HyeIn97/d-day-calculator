package com.example.presentation.util

interface ItemClickListener<T> {
    fun itemSettingClick(position: Int, data: T) {}
    fun itemDeleteClick(data: T) {}
}