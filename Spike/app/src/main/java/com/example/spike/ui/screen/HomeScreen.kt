package com.example.spike.ui.screen

import android.media.MediaPlayer
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spike.R
import kotlinx.coroutines.launch

data class Song(val title: String, val imageRes: Int, val audioRes: Int)

@Composable
fun HomeScreen() {
    val songs = listOf(
        Song("Trúc Xinh", R.drawable.song1, R.raw.song1),
        Song("Đừng làm trái tim anh đau", R.drawable.song2, R.raw.song2)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopBar()
        SearchBar()
        SuggestionTitle()
        PlaylistSuggestions()
        SongList(songs = songs)
        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationBar()
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(40.dp)
        )
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Thông báo",
            tint = Color(0xFF212121)
        )
    }
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Bạn muốn nghe gì?", color = Color(0xFF757575)) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search", tint = Color(0xFF757575))
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF5F5F5),
            unfocusedContainerColor = Color(0xFFF5F5F5),
            disabledContainerColor = Color(0xFFF5F5F5),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun SuggestionTitle() {
    Text(
        text = "Gợi ý cho bạn",
        color = Color(0xFF212121),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun PlaylistSuggestions() {
    val playlists = listOf("Top Hits", "Chill Vibes", "Workout", "Ballads", "EDM", "Remix")
    // Danh sách màu tươi, tương ứng từng playlist
    val colors = listOf(
        Color(0xFFFF6F61), // đỏ cam tươi
        Color(0xFF6BCB77), // xanh lá mạ tươi
        Color(0xFF4D96FF), // xanh dương tươi
        Color(0xFFF9DC5C), // vàng tươi
        Color(0xFFFF9F1C), // cam sáng
        Color(0xFF9B5DE5)  // tím sáng
    )
    val scope = rememberCoroutineScope()

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(playlists) { index, name ->
            var clicked by remember { mutableStateOf(false) }
            val bgColor by animateColorAsState(
                if (clicked) colors[index].copy(alpha = 0.7f)
                else colors[index]
            )
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(bgColor, MaterialTheme.shapes.medium)
                    .clickable {
                        clicked = true
                        scope.launch {
                            kotlinx.coroutines.delay(300)
                            clicked = false
                        }
                    }
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun SongList(songs: List<Song>) {
    val ctx = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var playingIdx by remember { mutableStateOf<Int?>(null) }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Bài hát nổi bật",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF212121),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // ✅ Bọc LazyRow trong Box để căn giữa
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 0.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(songs) { index, song ->
                    val isPlaying = (playingIdx == index)
                    Box(
                        modifier = Modifier
                            .width(160.dp)
                            .height(250.dp)
                            .background(Color(0xFFF0F0F0), MaterialTheme.shapes.medium)
                            .padding(8.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(song.imageRes),
                                contentDescription = song.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = song.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF212121),
                                maxLines = 2,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                            IconButton(onClick = {
                                if (isPlaying) {
                                    mediaPlayer?.pause()
                                    playingIdx = null
                                } else {
                                    mediaPlayer?.release()
                                    mediaPlayer = MediaPlayer.create(ctx, song.audioRes).apply {
                                        setOnCompletionListener { playingIdx = null }
                                        start()
                                    }
                                    playingIdx = index
                                }
                            }) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    tint = Color(0xFF6200EE),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    var sel by remember { mutableStateOf(0) }
    NavigationBar(containerColor = Color.White) {
        listOf(
            Icons.Default.Home to "Trang chủ",
            Icons.Default.Search to "Tìm kiếm",
            Icons.Default.LibraryMusic to "Thư viện",
            Icons.Default.Settings to "Cài đặt"
        ).forEachIndexed { i, (icon, label) ->
            val selColor = if (sel == i) Color(0xFF6200EE) else Color(0xFF9E9E9E)
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = label, tint = selColor) },
                label = { Text(label, color = selColor) },
                selected = (sel == i),
                onClick = { sel = i }
            )
        }
    }
}
