package com.sathvik.fetchtestapp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sathvik.fetchtestapp.theme.FetchTestAppTheme
import com.sathvik.fetchtestapp.ui.viewmodel.ListContent
import com.sathvik.fetchtestapp.ui.viewmodel.MainUIState
import com.sathvik.fetchtestapp.ui.viewmodel.MainViewModel

@Composable
fun MainContent(
    viewModel: MainViewModel,
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val state = uiState.value

    Box {
        when (state) {
            is MainUIState.Data -> MainDataContent(listItems = state.list)
            is MainUIState.Error -> ErrorView(state.message)
            MainUIState.Init -> Spacer(modifier = Modifier.height(2.dp))
            MainUIState.Loading -> LoadingView()
        }
    }

}

@Composable
fun ErrorView(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(message)
    }
}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun MainDataContent(
    listItems: List<ListContent>,
) {
    LazyColumn {
        items(listItems, key = { item -> item.id }) { item ->
            when (item) {
                is ListContent.ListHeader -> ItemHeaderView(item)
                is ListContent.ListItem -> ItemContentView(item)
            }
        }
    }
}

@Composable
private fun ItemHeaderView(item: ListContent.ListHeader) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Gray)
    ) {
        Text(
            text = item.listId,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun ItemContentView(item: ListContent.ListItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = item.name,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun ListPreview() {
    FetchTestAppTheme {
        Scaffold { ip ->
            Box(modifier = Modifier.padding(ip)) {
                MainDataContent(
                    listItems = listOf(
                        ListContent.ListHeader(headerId = 1, listId = "Header"),
                        ListContent.ListItem(itemId = 2, name = "Item1"),
                        ListContent.ListItem(itemId = 3, name = "Item2"),
                        ListContent.ListItem(itemId = 4, name = "Item3"),
                    )
                )
            }
        }

    }
}







