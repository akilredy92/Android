package com.dsg.demo.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dsg.demo.R
import com.dsg.demo.utils.CommonFunctions
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat

class DetailActivity : AppCompatActivity()
{

    lateinit var title: String
    lateinit var image: String
    lateinit var location: String
    lateinit var date: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        title = intent.getStringExtra("title").toString()
        image = intent.getStringExtra("image").toString()
        location = intent.getStringExtra("location").toString()
        date = intent.getStringExtra("date").toString()

        textViewTitle.text = title
        textViewLocation.text = location

        val formattedDate = CommonFunctions().convertStringToLocalDate("yyyy-MM-dd'T'HH:mm:ss", date)
        val dFormat = SimpleDateFormat("EEE, dd MMM yyyy hh:mm a")
        val strDate = dFormat.format(formattedDate)
        textViewDate.text = strDate

        Glide.with(imageViewAvatar.context)
                .load(image)
                .into(imageViewAvatar)

        imgBack.setOnClickListener{
            finish()
        }
    }


}