package com.example.miwok

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class PhraseAdapter(mContext: Context, englishToMiwokPhrase: ArrayList<PhraseClass>) :
    ArrayAdapter<PhraseClass>(mContext, 0, englishToMiwokPhrase) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var mConvertView: View? = convertView
        if (mConvertView == null) {
            mConvertView = LayoutInflater.from(context).inflate(R.layout.phrase_list, parent, false)
        }
        try {
            var currentObject = getItem(position)
            var phraseEnglishTextView =
                mConvertView?.findViewById<TextView>(R.id.phrase_english_text)
            phraseEnglishTextView?.text = currentObject?.getEnglishPhrase()

            var phraseMiwokTextView = mConvertView?.findViewById<TextView>(R.id.phrase_miwok_text)
            phraseMiwokTextView?.text = currentObject?.getMiwokPhrase()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mConvertView!!
    }
}