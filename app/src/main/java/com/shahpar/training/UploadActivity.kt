package com.shahpar.training

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.core.Context
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shahpar.training.const.Const
import com.shahpar.training.const.Const.TAG
import com.shahpar.training.databinding.ActivityUploadBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files.copy
import java.util.*

class UploadActivity : AppCompatActivity() {

    lateinit var binding: ActivityUploadBinding

    private var mSelectUri: Uri? = null
    var filePath: String? = null

    private var mStorageRef: StorageReference? = null

    private fun upload() {

        val uuid = UUID.randomUUID()
        val imageName = Const.FIREBASE_IMAGE_FOLDER + uuid + ".jpg"

        Log.d(TAG, "upload: $imageName")

        val imageStorageRef = mStorageRef!!.child(imageName)

        if (mSelectUri == null) return

        val ref = imageStorageRef.putFile(mSelectUri!!)

        ref.addOnSuccessListener { taskSnapshot ->
            val downloadURL = taskSnapshot.storage.downloadUrl

            Log.d(TAG, "upload finished: $downloadURL")
        }

        ref.addOnFailureListener { exception ->
            Log.d(TAG, "upload: falied : ${exception.message}")
            Toast.makeText(application, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }

        ref.addOnCompleteListener { taskSnapshot ->
            if (taskSnapshot.isSuccessful) {
                Toast.makeText(application, "Post is shared", Toast.LENGTH_LONG).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mStorageRef = FirebaseStorage.getInstance().reference

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
                binding.textView.text = mSelectUri.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}