package com.example.miwok

class PhraseClass(englishPhrase:String,miwokPhrase:String,audioResourceId:Int) {
    private var englishPhrase = ""
    private var miwokPhrase = ""
    private var audioResourceId = -1
    init {
            this.englishPhrase = englishPhrase
            this.miwokPhrase = miwokPhrase
            this.audioResourceId = audioResourceId
    }
    public fun getEnglishPhrase():String{
        return englishPhrase
    }
    fun getMiwokPhrase():String{
        return miwokPhrase
    }
    fun getAudioResourceId():Int{
        return audioResourceId
    }

    override fun toString(): String {
        return """$englishPhrase
                  $miwokPhrase""".trimIndent()

    }
}