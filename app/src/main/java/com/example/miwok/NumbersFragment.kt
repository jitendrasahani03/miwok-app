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

class NumbersFragment : Fragment() {
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
        word.add(GeneralClass("One", "lutti", R.drawable.number_one, R.raw.number_one))
        word.add(GeneralClass("Two", "Otiiko", R.drawable.number_two, R.raw.number_two))
        word.add(GeneralClass("Three", "tolookosu", R.drawable.number_three, R.raw.number_three))
        word.add(GeneralClass("Four", "oyyisa", R.drawable.number_four, R.raw.number_four))
        word.add(GeneralClass("Five", "maokka", R.drawable.number_five, R.raw.number_five))
        word.add(GeneralClass("Six", "temmokka", R.drawable.number_six, R.raw.number_six))
        word.add(GeneralClass("Seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven))
        word.add(GeneralClass("Eight", "kawinta", R.drawable.number_eight, R.raw.number_eight))
        word.add(GeneralClass("Nine", "wo\'e", R.drawable.number_nine, R.raw.number_nine))
        word.add(GeneralClass("Ten", "na\'aacha", R.drawable.number_ten, R.raw.number_ten))

        val numberAdapter = GeneralAdapter(requireActivity(), word, R.color.category_numbers)

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