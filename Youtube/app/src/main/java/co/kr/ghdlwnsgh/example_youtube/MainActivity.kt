package co.kr.ghdlwnsgh.example_youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.kr.ghdlwnsgh.example_youtube.adapter.VideoAdapter
import co.kr.ghdlwnsgh.example_youtube.dto.VideoDto
import co.kr.ghdlwnsgh.example_youtube.service.VideoService
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var videoAdapter: VideoAdapter
    private var isFirstStart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, PlayerFragment())
            .commit()
        videoAdapter = VideoAdapter(callback = { url, title ->
            supportFragmentManager.fragments.find { it is PlayerFragment }?.let {
                if (!isFirstStart) {
                    (it as PlayerFragment).setVisibleMiniPlayer()
                    isFirstStart = true
                }
                (it as PlayerFragment).play(url, title)
            }
        })
        findViewById<RecyclerView>(R.id.main_recycler).apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
        }

        getVideoList()
    }

    private fun getVideoList() {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofit.create(VideoService::class.java).also {
            it.listVideos()
                .enqueue(object: Callback<VideoDto> {
                    override fun onResponse(call: Call<VideoDto>, response: Response<VideoDto>) {
                        if (response.isSuccessful.not()) {
                            Log.d("MainActivity", "fail")
                            return
                        }
                        response.body()?.let { videoDto ->
                            Log.d("MainActivity", videoDto.toString())
                            videoAdapter.submitList(videoDto.videos)
                        }
                    }

                    override fun onFailure(call: Call<VideoDto>, t: Throwable) {
                       // 예외 처리
                        Log.d("MainActivity", t.message.toString())
                    }

                })
        }
    }
}