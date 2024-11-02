package com.dicoding.dicodingevent.ui.detailEvent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.dicodingevent.data.EventsRepository
import com.dicoding.dicodingevent.databinding.ActivityDetailEventBinding

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailEventBinding
    private val detailEventViewModel by viewModels<DetailEventViewModel>()

    companion object {
         const val ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idParam = intent.getStringExtra(ID)

        if (idParam != null){
            Log.d("ID", idParam)
            detailEventViewModel.getDetailEvent(idParam)
        }

        detailEventViewModel.isLoading.observe(this@DetailEventActivity) {
            showLoading(it)
        }

        detailEventViewModel.detailEvent.observe(this@DetailEventActivity) {detailEvent ->

            val sisaQuota : Int = detailEvent.event.quota - detailEvent.event.registrants

            binding.tvTitle.text = detailEvent.event.name
            binding.tvOwnerTitle.text = detailEvent.event.ownerName
            binding.tvMoreDesc.text = "Waktu : ${detailEvent.event.beginTime} | Sisa Kuota ($sisaQuota)"
            Glide.with(this).load(detailEvent.event.mediaCover).into(binding.ivPicture)
            binding.tvDesc.movementMethod = LinkMovementMethod.getInstance()
            binding.tvDesc.text = Html.fromHtml(detailEvent.event.description, Html.FROM_HTML_MODE_COMPACT)
            binding.btnRegist.setOnClickListener{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(detailEvent.event.link))
                    startActivity(intent)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.tvTitle.visibility = View.INVISIBLE
            binding.tvOwnerTitle.visibility = View.INVISIBLE
            binding.tvMoreDesc.visibility = View.INVISIBLE
            binding.ivPicture.visibility = View.INVISIBLE
            binding.tvDesc.visibility = View.INVISIBLE
            binding.btnRegist.visibility = View.INVISIBLE
            binding.tvDescTitle.visibility = View.INVISIBLE

        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.tvTitle.visibility = View.VISIBLE
            binding.tvOwnerTitle.visibility = View.VISIBLE
            binding.tvMoreDesc.visibility = View.VISIBLE
            binding.ivPicture.visibility = View.VISIBLE
            binding.tvDesc.visibility = View.VISIBLE
            binding.btnRegist.visibility = View.VISIBLE
            binding.tvDescTitle.visibility = View.VISIBLE

        }
    }
}