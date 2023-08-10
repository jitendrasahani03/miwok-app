package com.example.miwok

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

class FamilyFragment : Fragment() {
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
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
        word.add(GeneralClass("father","upu",R.drawable.family_father,R.raw.family_father))
        word.add(GeneralClass("mother","uta",R.drawable.family_mother,R.raw.family_mother))
        word.add(GeneralClass("son","angsi",R.drawable.family_son,R.raw.family_son))
        word.add(GeneralClass("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter))
        word.add(GeneralClass("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother))
        word.add(GeneralClass("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother))
        word.add(GeneralClass("older sister","tete",R.drawable.family_older_sister,R.raw.family_older_sister))
        word.add(GeneralClass("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister))
        word.add(GeneralClass("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother))
        word.add(GeneralClass("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather))
        val numberAdapter = GeneralAdapter(requireActivity(), word, R.color.category_family)

        val listView = rootView.findViewById<ListView>(R.id.word_list_view)
        listView.adapter = numberAdapter

        listView.setOnItemClickListener { adapterView, view, position, l ->
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