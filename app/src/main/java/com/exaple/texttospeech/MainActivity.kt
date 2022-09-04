package com.exaple.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.exaple.texttospeech.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(),TextToSpeech.OnInitListener{
    private var tts: TextToSpeech? = null // Variable for TextToSpeech
    //Todo 3: create a binding variable
    private var binding:ActivityMainBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        // Initialize the Text To Speech
        tts = TextToSpeech(this, this)

        //function called when user start the app
        waitForWhile()

        binding?.btnSpeak?.setOnClickListener {

            if (binding?.eText?.text!!.isEmpty()) {
                Toast.makeText(this@MainActivity, "Enter a text to speak.", Toast.LENGTH_SHORT).show()
            } else {
                //let,s speak
                speakOut(binding?.eText?.text.toString())
            }
        }
    }

    //method for show toast after 3 seconds
    private fun waitForWhile() {
        Handler(Looper.getMainLooper()).postDelayed({
            speakOut(binding?.textView?.text.toString())
        }, 1000)

    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.UK)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }

        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    override fun onDestroy() {
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
        super.onDestroy()
    }
    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}