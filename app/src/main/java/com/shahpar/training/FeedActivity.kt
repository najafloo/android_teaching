package com.shahpar.training

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class FeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
         menuInflater.inflate(R.menu.post_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add_post) {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}