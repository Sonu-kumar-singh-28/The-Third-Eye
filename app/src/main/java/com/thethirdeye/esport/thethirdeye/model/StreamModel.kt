package com.thethirdeye.esport.thethirdeye.model

data class StreamModel(
    val title: String,
    val videoUrl: String
)


val streamList = listOf(

    StreamModel(
        "BGMI Live Match 1",
        "https://www.youtube.com/watch?v=5qap5aO4i9A"
    ),

    StreamModel(
        "Free Fire Live Tournament",
        "https://www.youtube.com/watch?v=jfKfPfyJRdk"
    ),

    StreamModel(
        "Esports Live Battle",
        "https://www.youtube.com/watch?v=ysz5S6PUM-U"
    ),

    StreamModel(
        "PUBG Global Championship",
        "https://www.youtube.com/watch?v=ScMzIvxBSi4"
    ),

    StreamModel(
        "BGMI Finals Live",
        "https://www.youtube.com/watch?v=aqz-KE-bpKQ"
    )
)