package com.sathvik.fetchtestapp.ui.viewmodel

sealed class MainUIState {
    data object Init : MainUIState()
    data object Loading : MainUIState()
    data class Data(val list: List<ListContent>) : MainUIState()
    data class Error(val message: String) : MainUIState()
}

sealed class ListContent(val id: Int) {
    data class ListHeader(
        val headerId: Int,
        val listId: String
    ) : ListContent(id = headerId)

    data class ListItem(
        val itemId: Int,
        val name: String
    ) : ListContent(id = itemId)
}