package co.kr.ghdlwnsgh.example_youtube.service

import co.kr.ghdlwnsgh.example_youtube.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface VideoService {
    @Headers("Content-Type: text/html")
    @GET("/v3/f6fcaa08-0469-4e02-940c-17392f0586da")
    fun listVideos(): Call<VideoDto>
}