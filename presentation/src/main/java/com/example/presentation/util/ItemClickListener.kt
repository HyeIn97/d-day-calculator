package com.example.presentation.util

interface ItemClickListener<T> {
    fun itemSettingClick(data: T) {}
    fun itemDeleteClick(data: T) {}
}