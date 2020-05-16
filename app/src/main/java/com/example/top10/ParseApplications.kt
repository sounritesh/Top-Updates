package com.example.top10
import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.lang.Exception

class ParseApplications {
    private val tag = "ParseApplications"
    val applications = ArrayList<FeedEntry>()

    fun parse(xmlData: String): Boolean {
        Log.d(tag, "parse: called with $xmlData")
        var status = true
        var inEntry = false
        var textValue = ""

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType = xpp.eventType
            var currentRecord = FeedEntry()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = xpp.name?.toLowerCase()
                 when (eventType) {
                     XmlPullParser.START_TAG -> {
//                         Log.d(tag, "parse: Starting tag for $tagName")
                         if (tagName == "entry") {
                             inEntry = true
                         }
                     }

                     XmlPullParser.TEXT -> textValue = xpp.text

                     XmlPullParser.END_TAG -> {
//                         Log.d(tag, "parse: Ending tag for $tagName")
                         if (inEntry) {
                             when(tagName) {
                                 "entry" -> {
                                     applications.add(currentRecord)
                                     inEntry = false
                                     currentRecord = FeedEntry()
                                 }
                                 "name" -> currentRecord.name = textValue
                                 "artist" -> currentRecord.artist = textValue
                                 "releasedate" -> currentRecord.releaseDate = textValue
                                 "summary" -> currentRecord.summary = textValue
                                 "image" -> currentRecord.imageURL = textValue
                             }
                         }
                     }
                 }
                //nothing else to do
                 eventType = xpp.next()
            }

//          Temporary function to test the app without interface
//            for (app in applications) {
//                Log.d(tag, "------------------------------")
//                Log.d(tag, app.toString())
//            }
        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }
        return status
    }
}