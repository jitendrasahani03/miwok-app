package com.example.miwok

class GeneralClass(englishWord: String, miwokWord: String,imageId:Int,audioId:Int) {
    private var englishWord:String =""
    private var miwokWord:String = ""
    private var NO_IMAGE = -1
    private var imageId = NO_IMAGE
    private var audioId = 0
    init {
        this.englishWord = englishWord
        this.miwokWord   = miwokWord
        this.imageId = imageId
        this.audioId = audioId
    }
    fun getMiwok():String{
        return miwokWord
    }
    fun getEnglish():String{
        return englishWord
    }
    fun getImageId():Int{
        return imageId
    }
    fun hasImage():Boolean{
        return imageId!=NO_IMAGE
    }
    fun getaudioId():Int{
        return audioId
    }
}