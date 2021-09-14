package com.example.arpitnema.videocall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    private lateinit var email:EditText
    lateinit var password:EditText
    lateinit var login:Button
    lateinit var toSignUp:TextView
    override fun onStart() {
        if(auth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        super.onStart()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.emailInput)
        password = findViewById(R.id.passwordInput)
        login = findViewById(R.id.login)
        toSignUp = findViewById(R.id.loginToSignUp)
        auth = FirebaseAuth.getInstance()
        toSignUp.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
        login.setOnClickListener {
            val emailT = email.text.toString().trim()
            val passwordT = password.text.toString()
            auth.signInWithEmailAndPassword(emailT,passwordT).addOnCompleteListener {
                if (it.isSuccessful){
                    //success
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                } else{
                    Toast.makeText(this,it.exception?.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}