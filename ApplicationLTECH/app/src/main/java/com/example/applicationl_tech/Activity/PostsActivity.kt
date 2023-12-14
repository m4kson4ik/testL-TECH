package com.example.applicationl_tech.Activity

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavHostController
import com.example.applicationl_tech.R
import com.example.applicationl_tech.ViewModel.PostsViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedPost(viewModel: PostsViewModel, navController: NavHostController)
{
    val selectedItem by viewModel.selectedPost.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title =
            {
                IconButton(onClick = { navController.navigate("listPosts") }) {
                    Icon(imageVector =  Icons.Filled.ArrowBack, contentDescription = "" )
                }
                Box(modifier = Modifier.fillMaxWidth())
                {
                    Text(text = selectedItem.title, Modifier.align(Alignment.Center))
                }
            }, Modifier.shadow(4.dp))
        }
    ) { innerPadding ->
        val bitmap = BitmapFactory.decodeByteArray(selectedItem.imageSource, 0, selectedItem.imageSource!!.size)
        Column(
            Modifier
                .padding(innerPadding)
                .padding(20.dp)) {
            Box(modifier = Modifier.fillMaxWidth())
            {
                Image(
                    bitmap = bitmap.asImageBitmap(), contentDescription = "",
                    Modifier
                        .size(200.dp, 200.dp)
                )
            }
            Text(selectedItem.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(selectedItem.text)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition",
    "CoroutineCreationDuringComposition"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPosts(
    lifecycleScope: LifecycleCoroutineScope,
    navController: NavHostController,
    viewModel: PostsViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    {
                        Text(stringResource(id = R.string.NewsFeed), Modifier.align(Alignment.Center))
                        IconButton(
                            onClick = {
                                viewModel.getPosts()
                                Toast.makeText(navController.context, "Updating", Toast.LENGTH_LONG).show()
                            },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Image(imageVector = Icons.Filled.Refresh, contentDescription = "")
                        }
                    }
                }, Modifier.shadow(5.dp))
        }
    )
    { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            var selectedOption by remember { mutableStateOf(false) }
            Row {
                Text(
                    text = stringResource(id = R.string.ServerSorted),
                    modifier = Modifier
                        .clickable {
                            selectedOption = false
                            viewModel.sortedPostsServer()
                        }
                        .padding(16.dp)
                        .background(if (!selectedOption) Color.LightGray else Color.Transparent)
                )
                Text(
                    text = stringResource(id = R.string.SortedDate),
                    modifier = Modifier
                        .clickable {
                            selectedOption = true
                            viewModel.sortedPostsDate()
                        }
                        .padding(16.dp)
                        .background(if (selectedOption) Color.LightGray else Color.Transparent)
                )
            }
            val list by viewModel.postsFlow.collectAsState(emptyList())
            LazyColumn()
            {
                items(list)
                {
                    val bitmap =
                        BitmapFactory.decodeByteArray(it.imageSource, 0, it.imageSource!!.size)
                    Box(
                        Modifier
                            .padding(20.dp)
                            .clickable {
                                navController.navigate("selectedPosts")
                                lifecycleScope.launch {
                                    viewModel.selectedPost.emit(it)
                                }
                            })
                    {
                        Row {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "Photo",
                                Modifier.size(95.dp, 95.dp)
                            )
                            Column(Modifier.padding(20.dp, 0.dp)) {
                                Text(it.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                                Text(it.text)
                                val formatter =
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                                val date = LocalDateTime.parse(it.date, formatter)
                                Text(
                                    "${date.dayOfMonth} ${date.month}, ${date.hour}:${date.minute}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                    Divider(color = Color.LightGray, thickness = 1.dp)
                }
            }
        }
    }
}