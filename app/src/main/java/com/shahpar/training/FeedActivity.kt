package com.shahpar.training

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shahpar.training.const.Const.FIREBASE_POSTS_COMMENT
import com.shahpar.training.const.Const.FIREBASE_POSTS_EMAIL
import com.shahpar.training.const.Const.FIREBASE_POSTS_IMAGE
import com.shahpar.training.const.Const.FIREBASE_POSTS_TABLE
import com.shahpar.training.const.Const.TAG
import com.shahpar.training.databinding.ActivityFeedBinding
import org.json.JSONObject

class FeedActivity : AppCompatActivity() {

    lateinit var binding: ActivityFeedBinding

    var postAdapter: PostAdapter? = null
    var firebaseDatabase: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        postAdapter = PostAdapter(ArrayList<PostModel>(), this)
        binding.listviewPost.adapter = postAdapter

        getDataFromServer()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.post_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add_post) {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDataFromServer() {

        val dbRef = firebaseDatabase!!.getReference(FIREBASE_POSTS_TABLE)

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                postAdapter!!.clear()

                for (item in snapshot.children) {
                    val hashMap = item.value as HashMap<String, String>

                    Log.d(TAG, "onDataChange: $hashMap")

                    if(hashMap.size > 0) {
                        val email = hashMap[FIREBASE_POSTS_EMAIL] ?: ""
                        val comment = hashMap[FIREBASE_POSTS_COMMENT] ?: ""
                        val image = hashMap[FIREBASE_POSTS_IMAGE] ?: ""

                        val postModel = PostModel(email, image, comment)
                        postAdapter!!.addItem(postModel)
                    }
                }
                postAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Do nothing
            }
        })
    }
}