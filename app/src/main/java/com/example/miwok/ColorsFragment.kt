package com.example.miwok

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.annotation.RequiresApi

class ColorsFragment : Fragment() {
    private  var mediaPlayer: MediaPlayer?= null
    private  var audioManager : AudioManager? = null
    private var focusRequest : AudioFocusRequest? = null

    private val mediaPlayerListener: MediaPlayer.OnCompletionListener =
        MediaPlayer.OnCompletionListener {
            releaseMediaPlayer()
        }

    private var audioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                mediaPlayer?.start()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK -> {
                mediaPlayer?.pause()
                mediaPlayer?.seekTo(0)
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                releaseMediaPlayer()
            }
        }
    }
    private val TAG = "NumbersFragment"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        Log.d(TAG,"onCreateView : called")
        val rootView = inflater.inflate(R.layout.word_list, container, false)

        audioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val audioAttributes  = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        //step-2
        focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(audioAttributes)                    //initiate the audio playback attributes
            .setAcceptsDelayedFocusGain(true)
            .setOnAudioFocusChangeListener(audioFocusChangeListener) //change in the focus which Android system grants for
            .build()

        val word = ArrayList<GeneralClass>()
        word.add(GeneralClass("red","wetetti",R.drawable.color_red,R.raw.color_red))
        word.add(GeneralClass("mustard yellow","chiwiita",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow))
        word.add(GeneralClass("dusty yellow","topiisa",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow))
        word.add(GeneralClass("green","chokokki",R.drawable.color_green,R.raw.color_green))
        word.add(GeneralClass("brown","takaakki",R.drawable.color_brown,R.raw.color_brown))
        word.add(GeneralClass("gray","topoppi",R.drawable.color_gray,R.raw.color_gray))
        word.add(GeneralClass("black","kululli",R.drawable.color_black,R.raw.color_black))
        word.add(GeneralClass("white","kelelli",R.drawable.color_white,R.raw.color_white))

        val numberAdapter = GeneralAdapter(requireActivity(), word, R.color.category_colors)

        val listView = rootView.findViewById<ListView>(R.id.word_list_view)
        listView.adapter = numberAdapter

        listView.setOnItemClickListener { adapterView, view, position, l ->
            Log.d("NumberActivity", "number activity clicked")
            val currentObject = word.get(position).getaudioId()
            releaseMediaPlayer()
            mediaPlayer = MediaPlayer.create(activity, currentObject)
            try {
                mediaPlayer!!.start()
                mediaPlayer!!.setOnCompletionListener(mediaPlayerListener)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return rootView
    }

    private fun releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                audioManager?.abandonAudioFocusRequest(focusRequest!!)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        releaseMediaPlayer()

    }
}