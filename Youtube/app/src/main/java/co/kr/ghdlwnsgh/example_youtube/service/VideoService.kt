package co.kr.ghdlwnsgh.example_youtube.service

import co.kr.ghdlwnsgh.example_youtube.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET

interface VideoService {

    @GET("/v3/89f9ce64-29ce-4642-9079-0f06dee94aa4")
    fun listVideos(): Call<VideoDto>
}