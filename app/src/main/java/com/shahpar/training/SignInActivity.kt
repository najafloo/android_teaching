package com.shahpar.training

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.shahpar.training.const.Const.TAG
import com.shahpar.training.databinding.ActivitySignInBinding
import java.time.Duration

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding

    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        mAuth = FirebaseAuth.getInstance()

        setContentView(binding.root)

        binding.btnSignin.setOnClickListener {
            val email = binding.txtEmail.text
            val pass = binding.txtPassword.text

            mAuth!!.signInWithEmailAndPassword(email.toString(), pass.toString())
                .addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Sign in successful!",
                            Toast.LENGTH_LONG
                        ).show()

                        val intent = Intent(this, FeedActivity::class.java)
                        startActivity(intent)
                    }
                }).addOnFailureListener(this, OnFailureListener { exception ->
                    Toast.makeText(
                        applicationContext,
                        exception.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                })
        }

        binding.btnSignup.setOnClickListener {
            val email = binding.txtEmail.text
            val pass = binding.txtPassword.text
            mAuth!!.createUserWithEmailAndPassword(email.toString(), pass.toString())
                .addOnCompleteListener(this, OnCompleteListener { task ->
                    Log.d(TAG, "onCreate: $task")
                    if (task.isSuccessful) {
                        Log.d(TAG, "onCreate: SUCESSFUL")
                        Toast.makeText(
                            applicationContext,
                            "User $email \nhas been added",
                            Toast.LENGTH_LONG
                        )
                        .show()

                        val intent = Intent (this, FeedActivity::class.java)
                        startActivity(intent)
                    }
                }).addOnFailureListener(this, OnFailureListener { exception ->
                    Toast.makeText(
                        applicationContext, exception.message,
                        Toast.LENGTH_LONG
                    ).show()
                })
        }
    }
}