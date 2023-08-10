package com.example.miwok

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout


class GeneralAdapter(context: Context, dataSource: ArrayList<GeneralClass>, color: Int) :
    ArrayAdapter<GeneralClass>(context, 0, dataSource) {

    private val mcolor:Int
    init{
        mcolor = color
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var mConvertView: View? = convertView
        if (mConvertView == null) {
            mConvertView =LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }
        try {
            var currentObject: GeneralClass? = getItem(position)
            var imageId = mConvertView?.findViewById<ImageView>(R.id.icon_image_view)
            currentObject?.getImageId()?.let { imageId?.setImageResource(it) }
            var textEnglish = mConvertView?.findViewById<TextView>(R.id.english_word_text_view)
            textEnglish?.text = currentObject?.getEnglish()
            var textMiwok = mConvertView?.findViewById<TextView>(R.id.miwok_word_text_view)
            textMiwok?.text = currentObject?.getMiwok()

            var textContainter = mConvertView?.findViewById<LinearLayout>(R.id.text_container)
            textContainter?.setBackgroundColor(context.getColor(mcolor))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mConvertView!!
    }
}