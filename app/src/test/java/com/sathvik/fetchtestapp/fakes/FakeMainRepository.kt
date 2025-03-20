package com.sathvik.fetchtestapp.fakes

import com.sathvik.fetchtestapp.netowork.repository.MainRepository
import com.sathvik.fetchtestapp.netowork.repository.Result
import com.sathvik.fetchtestapp.ui.viewmodel.ListContent
import java.lang.Exception

class FakeMainRepository: MainRepository {

    var shouldResultSucceed: Boolean = true
    override suspend fun getData(): Result<List<ListContent>> {
        if(shouldResultSucceed) {
            val res = listOf(
                ListContent.ListHeader(headerId = 1, listId = "Header"),
                ListContent.ListItem(itemId = 2, name = "Item1"),
                ListContent.ListItem(itemId = 3, name = "Item2"),
                ListContent.ListItem(itemId = 4, name = "Item3"),
            )
            return Result.Success(res)
        } else {
            return Result.Error(Exception(""))
        }
    }
}