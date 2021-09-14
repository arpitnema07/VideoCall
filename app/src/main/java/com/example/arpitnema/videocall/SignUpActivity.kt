package com.example.arpitnema.videocall

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.arpitnema.videocall.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class SignUpActivity : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    lateinit var db:FirebaseFirestore
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var username:EditText
    lateinit var signUp:Button
    lateinit var toLogin:TextView

    override fun onStart() {
        if(auth.currentUser!=null){
           moveToMain()
        }
        super.onStart()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        email = findViewById(R.id.emailInputSignUp)
        password = findViewById(R.id.passwordInputSignUp)
        username = findViewById(R.id.usernameInputSignUp)
        signUp = findViewById(R.id.signUp)
        toLogin = findViewById(R.id.signUpToLogin)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        toLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        signUp.setOnClickListener {
            val name = username.text.toString()
            val emailT = email.text.toString().trim()
            val passwordT = password.text.toString()
            auth.createUserWithEmailAndPassword(emailT,passwordT).addOnCompleteListener {
                if (it.isSuccessful){
                    val user = User(name,emailT)
                    db.collection("users")
                        .add(user)
                        .addOnCompleteListener { d->
                            if (d.isSuccessful){
                                if(auth.currentUser==null){
                                    auth.signInWithEmailAndPassword(emailT,passwordT).addOnCompleteListener { a->
                                        if (a.isSuccessful){
                                            //success
                                            moveToMain()
                                        } else{
                                            Toast.makeText(this,"Auth Error "+it.exception?.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } else moveToMain()
                            } else {
                                Toast.makeText(this,"Data Base Error "+it.exception?.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this,it.exception?.localizedMessage.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun moveToMain(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}