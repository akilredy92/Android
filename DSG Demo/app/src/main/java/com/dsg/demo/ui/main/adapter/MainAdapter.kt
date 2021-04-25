package com.dsg.demo.ui.main.adapter

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.Target
import com.dsg.demo.R
import com.dsg.demo.data.model.Event
import com.dsg.demo.ui.main.adapter.MainAdapter.DataViewHolder
import com.dsg.demo.ui.main.view.DetailActivity
import com.dsg.demo.utils.CommonFunctions
import kotlinx.android.synthetic.main.item_layout.view.*
import java.text.SimpleDateFormat

class MainAdapter(private val users: ArrayList<Event>) : RecyclerView.Adapter<DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: Event) {
            itemView.apply {
                textViewUserName.text = user.title
                textViewUserEmail.text = user.venue.display_location

                val formattedDate = CommonFunctions().convertStringToLocalDate("yyyy-MM-dd'T'HH:mm:ss", user.datetime_utc)
                val dFormat = SimpleDateFormat("EEE, dd MMM yyyy hh:mm a")
                val strDate = dFormat.format(formattedDate)
                textViewDate.text = strDate

                Glide.with(imageViewAvatar.context)
                    .load(user.performers.get(0).image)
                    .listener(object: RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            TODO("Not yet implemented")
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean
                        {
                            var loadedImage: Bitmap = createBitmapFromDrawable(resource)

                            loadedImage = getRoundedCornerBitmap(loadedImage, 15)
                            val view = (target as DrawableImageViewTarget).view
                            view.setImageBitmap(loadedImage)

                            return true
                        }})
                    .into(imageViewAvatar)

                cardEvent.setTag(user)
                cardEvent.setOnClickListener{
                    val selectedEvent: Event = it.getTag() as Event
                    var intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("title", selectedEvent.title)
                    intent.putExtra("image", selectedEvent.performers.get(0).image)
                    intent.putExtra("location", selectedEvent.venue.display_location)
                    intent.putExtra("date", selectedEvent.datetime_utc)
                    context.startActivity(intent)
                }
            }
        }

        public fun createBitmapFromDrawable(drawable: Drawable?): Bitmap { // notice the .asBitmap() on the first load
            var bitmap: Bitmap
            if (drawable is BitmapDrawable) {
                bitmap = drawable.bitmap
            } else {
                bitmap = Bitmap.createBitmap(
                    drawable!!.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
                val canvas = Canvas(bitmap)
                drawable!!.setBounds(0, 0, drawable.intrinsicWidth, drawable!!.intrinsicHeight)
                drawable.draw(canvas)
            }
            return if (bitmap != null) {
                bitmap.copy(bitmap.config, bitmap.isMutable)
            } else {
                //this is just for avoid NPE
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            }
        }

        fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
            val output = Bitmap.createBitmap(
                bitmap.width, bitmap
                    .height, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(output)
            val color = -0xbdbdbe
            val paint = Paint()
            val rect =
                Rect(0, 0, bitmap.width, bitmap.height)
            val rectF = RectF(rect)
            val roundPx = pixels.toFloat()
            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)
            return output
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun addUsers(users: List<Event>) {
        this.users.apply {
            clear()
            addAll(users)
        }

    }





}