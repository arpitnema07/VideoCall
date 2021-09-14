package com.example.arpitnema.videocall

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    lateinit var join:Button
    lateinit var share:Button
    lateinit var code:EditText
    override fun onStart() {
        if(auth.currentUser==null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        super.onStart()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        join = findViewById(R.id.join)
        share = findViewById(R.id.share)
        code = findViewById(R.id.codeEditText)


        val options: JitsiMeetConferenceOptions = JitsiMeetConferenceOptions.Builder()
            .setServerURL(URL("https://meet.jit.si"))
            .setAudioMuted(false)
            .setVideoMuted(true)
            .setAudioOnly(false)
            .setWelcomePageEnabled(false)
            .build()
        JitsiMeet.setDefaultConferenceOptions(options)
        join.setOnClickListener {
            val op: JitsiMeetConferenceOptions = JitsiMeetConferenceOptions.Builder()
                .setRoom(code.text.toString().trim())
                .build()
            JitsiMeetActivity.launch(this,op)
        }

        share.setOnClickListener {

        }
    }
}