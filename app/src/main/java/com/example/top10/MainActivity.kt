package com.example.top10

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.MalformedURLException
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

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.d(tag, "onPostExecute: parameter is $result")
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
                val xmlResult = StringBuilder()

                try {
                    val url = URL(urlPath)
                    val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                    val response = connection.responseCode
                    Log.d(tag, "The response code was $response")

//            val inputStream: InputStream = connection.inputStream
//            val inputStreamReader: InputStreamReader = InputStreamReader(inputStream)
//            val reader = BufferedReader(inputStreamReader)
//                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
//
//                    val inputBuffer = CharArray(500)
//                    var charsRead = 0
//                    while (charsRead >= 0 ) {
//                        charsRead = reader.read(inputBuffer)
//                        if (charsRead > 0) {
//                            xmlResult.append(String(inputBuffer,0,charsRead))
//                        }
//                    }

//                    OR
//                    val stream = connection.inputStream
//                    stream.buffered().reader().use { reader ->
//                        xmlResult.append(reader.readText())
//                    }
//                    reader.close()

//                    OR
                    connection.inputStream.buffered().reader().use { xmlResult.append(it.readText()) }

                    Log.d(tag, "downloadXML: Received ${xmlResult.length} bytes")
                    return xmlResult.toString()
                } catch (e: Exception) {
                    val errorMessage = when(e) {
                        is MalformedURLException -> "downloadXML: Invalid URL ${e.message}"
                        is IOException -> "downloadXML: IOException reading data: ${e.message}"
                        is SecurityException -> {
                            e.printStackTrace()
                            "downloadXML: Security Exception. Needs Permission? ${e.message}"
                        }
                        else -> "downloadXML: Unknown Error: ${e.message}"
                    }
                    Log.e(tag, errorMessage)
                }
//                catch (e: MalformedURLException) {
//                    Log.e(tag, "downloadXML: Invalid URL ${e.message}")
//                } catch (e: IOException) {
//                    Log.e(tag, "downloadXML: IO Exception reading data: ${e.message}")
//                } catch (e: SecurityException) {
//                    e.printStackTrace()
//                    Log.e(tag, "downloadXML: Security Exception. Needs permission? ${e.message}")
//                } catch (e: Exception) {
//                    Log.e(tag, "Unknown error: ${e.message}")
//                }
                return ""
            }
        }
    }
}
