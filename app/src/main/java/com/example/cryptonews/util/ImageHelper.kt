package com.example.cryptonews.util

import android.widget.ImageView
import com.example.cryptonews.R
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import javax.inject.Inject
import javax.inject.Singleton


/**
 * This class is used for encapsulating the picasso and prevent the direct calls
 * to the library in case we changed our mind using picasso
 */
@Singleton
class ImageHelper @Inject constructor(
    private val picasso: Picasso
) {


    fun loadImageOfflineFirst(url: String, imgView: ImageView?, placeHolder: Int=R.drawable.image_ph) {
        try {
            picasso.load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(placeHolder)
                .into(imgView, object : Callback {
                    override fun onSuccess() {


                    }

                    override fun onError() {
                        //load from network if its not available
                        picasso
                            .load(url)
                            .placeholder(placeHolder)
                            .into(imgView)
                    }

                })


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}