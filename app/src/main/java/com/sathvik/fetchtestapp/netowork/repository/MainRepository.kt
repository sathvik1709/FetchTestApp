package com.sathvik.fetchtestapp.netowork.repository

import com.sathvik.fetchtestapp.ui.viewmodel.ListContent

interface MainRepository {
    suspend fun getData(): Result<List<ListContent>>
}