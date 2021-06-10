package co.kr.ghdlwnsgh.example_youtube.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.kr.ghdlwnsgh.example_youtube.R
import co.kr.ghdlwnsgh.example_youtube.model.VideoModel
import com.bumptech.glide.Glide

class VideoAdapter(val callback: (String, String) -> Unit): ListAdapter<VideoModel, VideoAdapter.ViewHolder>(diffUtil) {

    inner class  ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: VideoModel) {
            val titleTextView = view.findViewById<TextView>(R.id.title_tv)
            val subTitleTextView = view.findViewById<TextView>(R.id.sub_title_tv)
            val thumbnailImageview = view.findViewById<ImageView>(R.id.thumb_imageView)
            titleTextView.text = item.title
            subTitleTextView.text = item.subtitle
            Glide.with(thumbnailImageview.context)
                .load(item.thumb)
                .into(thumbnailImageview)
            view.setOnClickListener {
                callback(item.sources, item.title)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<VideoModel>() {
            override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(currentList[position])
    }
}