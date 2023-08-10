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

class PhrasesFragment : Fragment() {
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

        val enlgishToMiwoPhrase = arrayListOf<PhraseClass>()
        enlgishToMiwoPhrase.add(PhraseClass("where are you going?", "minto wuksus", R.raw.testing2))
        enlgishToMiwoPhrase.add(PhraseClass("what is your name?", "tinna oyaasana", R.raw.phrase_what_is_your_name))
        enlgishToMiwoPhrase.add(PhraseClass("My name is..", "oyaset", R.raw.phrase_my_name_is))
        enlgishToMiwoPhrase.add(PhraseClass("How are you feeling?", "michaksas?", R.raw.phrase_how_are_you_feeling))
        enlgishToMiwoPhrase.add(PhraseClass("I'm feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good))
        enlgishToMiwoPhrase.add(PhraseClass("Are you coming?", "aanas'aa", R.raw.phrase_are_you_coming))
        enlgishToMiwoPhrase.add(PhraseClass("Yes,I'm coming", "haa'aanam", R.raw.phrase_yes_im_coming))
        enlgishToMiwoPhrase.add(PhraseClass("I'm coming", "aanam", R.raw.phrase_im_coming))
        enlgishToMiwoPhrase.add(PhraseClass("Let's go", "yoowutis", R.raw.phrase_lets_go))
        enlgishToMiwoPhrase.add(PhraseClass("Come here", "anninem", R.raw.phrase_come_here))

        val phraseAdapter = PhraseAdapter(requireActivity(), enlgishToMiwoPhrase)
        val listView = rootView.findViewById<ListView>(R.id.word_list_view)

        listView.adapter = phraseAdapter
        listView.setOnItemClickListener { _, _, i, _ ->
            val currentObject = enlgishToMiwoPhrase[i].getAudioResourceId()
            releaseMediaPlayer()
            //step-3
            val result : Int? = audioManager?.requestAudioFocus(focusRequest!!)
            //step-4
            try {
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(activity, currentObject)
                    mediaPlayer!!.start()
                    mediaPlayer!!.setOnCompletionListener(mediaPlayerListener)
                }
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