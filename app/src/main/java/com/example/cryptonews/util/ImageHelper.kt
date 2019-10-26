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


    fun loadImage(url: String, imgView: ImageView?, placeHolder: Int) {
        try {
            try {
                imgView?.setImageResource(R.drawable.image_ph)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            picasso
                .load(url)
                .placeholder(placeHolder)
                .centerCrop()
                .into(imgView)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadImageOfflineFirst(url: String, imgView: ImageView?, placeHolder: Int) {
        try {
            picasso.load(url)
                .placeholder(placeHolder)
                .networkPolicy(NetworkPolicy.OFFLINE)
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


    fun loadImageWithResize(url: String, imgView: ImageView?, cropSize: Int) {
        try {
            picasso
                .load(url)
                .resize(cropSize, cropSize)
                .centerCrop()
                .into(imgView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadImageWithOutCenterCrop(url: String, imgView: ImageView?, placeHolder: Int) {
        try {
            picasso
                .load(url)
                .placeholder(placeHolder)
                .into(imgView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadImage(url: String, imgView: ImageView?) {
        try {
            picasso
                .load(url)
                // .centerCrop()
                .into(imgView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadImageWithOutPlaceHolder(url: String, imgView: ImageView?) {
        try {
            picasso
                .load(url)
                .into(imgView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}