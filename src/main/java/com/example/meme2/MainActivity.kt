package com.example.meme2

import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var curl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()

        var next = findViewById<ImageView>(R.id.next)
        var share = findViewById<ImageView>(R.id.share)

        next.setOnClickListener(View.OnClickListener {
            loadData()
        })
        share.setOnClickListener(View.OnClickListener {
            var intent = Intent(Intent.ACTION_SEND)
            intent.type = "plan/text"
//            intent.setPackage("com.whatsapp");
            intent.putExtra(Intent.EXTRA_TEXT, "I have cool meme look at this $curl")
            var chooser = Intent.createChooser(Intent(intent), "Share with")
            startActivity(chooser)
        })
    }

    fun loadData() {
        val meme = findViewById<ImageView>(R.id.imageView)
        var progress = findViewById<ProgressBar>(R.id.progress)
        progress.visibility = View.VISIBLE
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        var url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null, { response ->

                curl = response.getString("url")
                Glide.with(this).load(curl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }
                }).into(meme)
            }, { Toast.makeText(this, "There is something wrong!!", Toast.LENGTH_LONG).show() })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }
}