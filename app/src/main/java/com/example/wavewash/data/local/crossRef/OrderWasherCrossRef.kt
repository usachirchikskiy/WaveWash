package com.example.wavewash.data.local.crossRef

import androidx.room.Entity

@Entity(primaryKeys = ["orderId","washerId"])
data class OrderWasherCrossRef(
    val orderId:Long,
    val washerId:Long
)

//@Entity
//data class Playlist(
//    @PrimaryKey val playlistId: Long,
//    val playlistName: String
//)
//
//@Entity
//data class Song(
//    @PrimaryKey val songId: Long,
//    val songName: String,
//    val artist: String
//)
//
//@Entity(primaryKeys = ["playlistId", "songId"])
//data class PlaylistSongCrossRef(
//    val playlistId: Long,
//    val songId: Long
//)