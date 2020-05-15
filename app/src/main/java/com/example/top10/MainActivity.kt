package com.example.top10

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(tag, "onCreate: called")
        val downloadData = DownloadData()
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
        Log.d(tag, "onCreate: done!")
    }

    companion object {
        private class DownloadData: AsyncTask<String, Void, String>() {
            private val tag = "DownloadData"

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
//                Log.d(tag, "onPostExecute: parameter is $result")
                val parseApplications = ParseApplications()
                parseApplications.parse((result))
            }

            override fun doInBackground(vararg url: String?): String {
                Log.d(tag, "doInBackground: starts with ${url[0]}")
                val rssFeed = downloadXML(url[0])
                if(rssFeed.isEmpty()) {
                    Log.e(tag, "doInBackground: Error downloading")
                }
                return rssFeed
            }

            private fun downloadXML(urlPath: String?): String {
                return URL(urlPath).readText()
            }
        }
    }


}
