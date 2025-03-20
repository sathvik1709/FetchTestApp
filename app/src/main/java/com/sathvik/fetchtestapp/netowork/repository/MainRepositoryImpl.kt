package com.sathvik.fetchtestapp.netowork.repository

import com.sathvik.fetchtestapp.netowork.ApiInterface
import com.sathvik.fetchtestapp.netowork.models.DataItem
import com.sathvik.fetchtestapp.ui.viewmodel.ListContent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

class MainRepositoryImpl(
    private val api: ApiInterface,
    private val defaultDispatcher: CoroutineDispatcher,
) : MainRepository {

    override suspend fun getData(): Result<List<ListContent>> {
        return try {
            val response = api.getData().body()
            response?.let {
                Result.Success(mapListContent(response))
            } ?: throw IOException("Something went wrong!")
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /*
    * Here we are doing some heavy operation such as filtering,
    * grouping and sorting of the sublist on a large list, so its better to move
    * this work out of main thread so it doesn't block the UI thread.
    * */
    private suspend fun mapListContent(response: List<DataItem>): List<ListContent> =
        withContext(defaultDispatcher) {
            val listContent: MutableList<ListContent> = mutableListOf()

            // This filters out any null or empty strings
            val filtered = response.filter { item ->
                !item.name.isNullOrEmpty()
            }

            // This groups the filtered items by listId
            val groups = filtered.groupBy {
                it.listId
            }

            // This will sort the listId (keys) in the map
            val sortedGroups = groups.toSortedMap()

            // This adds the items to result list, after sorting the sublist
            for ((groupId, groupItems) in sortedGroups) {
                listContent.add(
                    ListContent.ListHeader(
                        headerId = groupId,
                        listId = groupId.toString()
                    )
                )
                val sortedSubGroup = groupItems.sortedWith { a, b ->
                    a.name!!.compareTo(b.name!!)
                }
                listContent.addAll(sortedSubGroup.map { item ->
                    ListContent.ListItem(itemId = item.id, name = item.name ?: "")
                })
            }

            listContent
        }

}