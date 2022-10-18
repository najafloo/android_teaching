package com.shahpar.training

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shahpar.training.const.Const
import com.shahpar.training.const.Const.FIREBASE_POSTS_COMMENT
import com.shahpar.training.const.Const.FIREBASE_POSTS_EMAIL
import com.shahpar.training.const.Const.FIREBASE_POSTS_IMAGE
import com.shahpar.training.const.Const.FIREBASE_POSTS_TABLE
import com.shahpar.training.const.Const.TAG
import com.shahpar.training.databinding.ActivityUploadBinding
import java.util.*

class UploadActivity : AppCompatActivity() {

    lateinit var binding: ActivityUploadBinding

    private var mSelectUri: Uri? = null

    private var mStorageRef: StorageReference? = null
    private var mAuth: FirebaseAuth? = null
    private var firebaseDatebase : FirebaseDatabase? = null
    private var databaseRef : DatabaseReference? = null

    private fun upload() {

        val uuid = UUID.randomUUID()
        val imageName = Const.FIREBASE_IMAGE_FOLDER + uuid + ".jpg"

        val imageStorageRef = mStorageRef!!.child(imageName)

        Log.d(TAG, "upload: $imageName")

        if (mSelectUri == null) return

        Log.d(TAG, "upload: step 1")

        imageStorageRef.putFile(mSelectUri!!)
            .addOnSuccessListener { taskSnapshot ->

                imageStorageRef.downloadUrl.addOnSuccessListener {
                    val postUrl = it.toString()
                    Log.d(TAG, "upload succeed upload: $postUrl")


                    val user = mAuth!!.currentUser
                    val email = user!!.email
                    val comment = binding.txtMessage.text.toString()

                    Log.d(TAG, "upload succeed: user : $user")
                    Log.d(TAG, "upload succeed: email : $email")
                    Log.d(TAG, "upload succeed: comment : $comment")

                    val uuid = UUID.randomUUID().toString()

                    databaseRef!!.child(FIREBASE_POSTS_TABLE).child(uuid).child(FIREBASE_POSTS_EMAIL).setValue(email)
                    databaseRef!!.child(FIREBASE_POSTS_TABLE).child(uuid).child(FIREBASE_POSTS_COMMENT).setValue(comment)
                    databaseRef!!.child(FIREBASE_POSTS_TABLE).child(uuid).child(FIREBASE_POSTS_IMAGE).setValue(postUrl)

                    Log.d(TAG, "upload: upload succeed FINISHed")

                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "upload: failed : ${exception.message}")
                Toast.makeText(application, exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
            .addOnCompleteListener { taskSnapshot ->
                if (taskSnapshot.isSuccessful) {
                    Toast.makeText(application, "Post is shared", Toast.LENGTH_LONG).show()
                }
            }
            .addOnProgressListener{
                val progress = (100.0 * it.bytesTransferred) / it.totalByteCount
                binding.progressBar.progress = progress.toInt()
                val per = "${progress.toInt()} %"
                binding.textView2.text = per
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        mStorageRef = FirebaseStorage.getInstance().reference
        firebaseDatebase = FirebaseDatabase.getInstance()
        databaseRef = firebaseDatebase!!.reference

        binding.imgPostImage.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                selectImage()
            }
        }

        binding.btnPost.setOnClickListener {
            upload()
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 2)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            mSelectUri = data.data

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, mSelectUri)
                binding.imgPostImage.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}