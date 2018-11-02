package com.example.robin.androidlab

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.os.AsyncTask
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import org.w3c.dom.Text
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.URL
import java.net.HttpURLConnection
import android.graphics.BitmapFactory
import android.util.Log
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.net.MalformedURLException


class WeatherForecast : Activity() {

    val ACTIVITYNAME = "WeatherForecast.kt"

    val cityname = "Montreal"

    var windValue : String = ""
    var tempValue : String = ""
    var minValue : String = ""
    var maxValue : String = ""
    var iconNameValue: String = ""

    var imageBits: Bitmap? = null

    lateinit var low:TextView
    lateinit var high:TextView
    lateinit var image:ImageView
    lateinit var wind:TextView
    lateinit var current:TextView
    lateinit var city:TextView
    lateinit var progress:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        image = findViewById<ImageView>(R.id.current_weather)

        current = findViewById<TextView>(R.id.current_temperature)

        low = findViewById<TextView>(R.id.minimum_temperature)

        high = findViewById<TextView>(R.id.maximum_temperature)

        wind = findViewById<TextView>(R.id.wind_speed)

        city = findViewById<TextView>(R.id.city)

        progress = findViewById<ProgressBar>(R.id.progress_bar)

        progress.visibility = View.INVISIBLE

        var query = forecastQuery()
        query.execute()
    }

    inner class forecastQuery:AsyncTask<String,Int,String> () {


        //var image:Bitmap = Bitmap.createBitmap(Bitmap!)

        var temperature:String = ""

        var mintemp:String = ""

        var maxtemp:String = ""

        var windspeed:String = ""

        //var progress = 0


        fun getImage(url: URL): Bitmap? {
            var connection: HttpURLConnection? = null
            try {
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val responseCode = connection.responseCode
                return if (responseCode == 200) {
                    BitmapFactory.decodeStream(connection.inputStream)
                } else
                    null
            } catch (e: Exception) {
                return null
            } finally {
                connection?.disconnect()
            }
        }


        fun getImage(urlString: String): Bitmap? {
            try {
                val url = URL(urlString)
                return getImage(url)
            } catch (e: MalformedURLException) {
                return null
            }

        }







        override fun doInBackground(vararg params: String?): String {

            val city = cityname
            val id = "d99666875e0e51521f0040a3d97d0f6a"
            val mode = "xml"
            val units = "metric"



            fun fileExistance(fname : String):Boolean{
                val file = getBaseContext().getFileStreamPath(fname)
                return file.exists()   }





            try {
                val urlstr = "http://api.openweathermap.org/data/2.5/weather?q=" + city + ",ca&APPID=" + id + "&mode=" + mode + "&units=" + units
                val url = URL(urlstr)

                val urlConnection = url.openConnection() as HttpURLConnection
                val stream = urlConnection.getInputStream()

                val factory = XmlPullParserFactory.newInstance()
                factory.setNamespaceAware(false)
                val xpp = factory.newPullParser()
                xpp.setInput(stream, "UTF-8")
                var gonesofar = 0

                while (xpp.eventType != XmlPullParser.END_DOCUMENT) {

                    when (xpp.eventType) {
                        XmlPullParser.START_TAG -> {
                            if (xpp.name.equals("speed")) {

                                windValue = xpp.getAttributeValue(null, "value")
                                gonesofar+= 20
                                Log.i(ACTIVITYNAME,"windspeed="+windValue)
                            } else
                                if (xpp.name.equals("temperature")) {
                                    tempValue = xpp.getAttributeValue(null, "value")
                                    gonesofar+= 20
                                    minValue = xpp.getAttributeValue(null, "min")
                                    gonesofar+= 20
                                    maxValue = xpp.getAttributeValue(null, "max")
                                    gonesofar+= 20
                                    Log.i(ACTIVITYNAME,"temperature="+tempValue)
                                } else
                                    if (xpp.name.equals("weather")) {
                                        iconNameValue = xpp.getAttributeValue(null, "icon")
                                        gonesofar+= 20
                                        Log.i(ACTIVITYNAME,"weather="+iconNameValue)
                                    }


                            var filename = iconNameValue +".png"
                            var weatherUrl = "http://openweathermap.org/img/w/"+ filename
                            imageBits = getImage(weatherUrl)

                            if (fileExistance( filename ))
                            {
                                Log.i(ACTIVITYNAME, "getting image from local")

                                var fis:FileInputStream? = null
                                try { fis = openFileInput(filename)}
                                    catch (e: FileNotFoundException) { e.printStackTrace()}
                                    imageBits = BitmapFactory.decodeStream(fis)
                            }
                            else {
                                Log.i(ACTIVITYNAME, "getting image from web")
                                val outputStream = openFileOutput(filename, Context.MODE_PRIVATE)

                                imageBits?.compress(Bitmap.CompressFormat.PNG,80,outputStream)

                                outputStream.flush()
                                outputStream.close()
                            }

                            //progress.src = imageBits
                            publishProgress(gonesofar) //async call /android will call onProgressUpdate function below when it is ready
                            Log.i(ACTIVITYNAME, "published..."+gonesofar)

                        }


                        XmlPullParser.TEXT -> {
                        }
                    }


                    xpp.next()
                }
            } catch (e: Exception) {

            }

            return "Done"
        }

        override fun onProgressUpdate(vararg values: Int?) {

            //var bar: ProgressBar? = null

            city.setText(cityname)
            low.setText("Minimum temperature in the region is $minValue C")
            high.setText("Maximum temperature in the region is $maxValue C")
            wind.setText("The current wind speed is $windValue km/h")
            current.setText("The current temperature is $tempValue C")

            if (values[0] != null) {
                Log.i(ACTIVITYNAME,"onupdate="+values[0])
                if (values[0] != null) {
                    var value: Int = values[0]!!
                    Log.i(ACTIVITYNAME,"onupdatex="+value)
                    progress.progress = value
                    progress.setVisibility(View.VISIBLE )

                }
            }




        }

        override fun onPostExecute(result: String?) {
           // super.onPostExecute(result)
           //imageView
            image.setImageBitmap(imageBits)
            progress.setVisibility(View.INVISIBLE )

        }



    }










}







/*

//////
            val entries: List<Entry> = downloadUrl(urlString)?.use { stream ->
                // Instantiate the parser
                StackOverflowXmlParser().parse(stream)
            } ?: emptyList()

            // Uploads XML from stackoverflow.com, parses it, and combines it with
// HTML markup. Returns HTML string.
            @Throws(XmlPullParserException::class, IOException::class)
            private fun loadXmlFromNetwork(urlString: String): String {
                // Checks whether the user set the preference to include summary text
                val pref: Boolean = PreferenceManager.getDefaultSharedPreferences(this)?.run {
                    getBoolean("summaryPref", false)
                } ?: false

                val entries: List<Entry> = downloadUrl(urlString)?.use { stream ->
                    // Instantiate the parser
                    StackOverflowXmlParser().parse(stream)
                } ?: emptyList()

                return StringBuilder().apply {
                    append("<h3>${resources.getString(R.string.page_title)}</h3>")
                    append("<em>${resources.getString(R.string.updated)} ")
                    append("${formatter.format(rightNow.time)}</em>")
                    // StackOverflowXmlParser returns a List (called "entries") of Entry objects.
                    // Each Entry object represents a single post in the XML feed.
                    // This section processes the entries list to combine each entry with HTML markup.
                    // Each entry is displayed in the UI as a link that optionally includes
                    // a text summary.
                    entries.forEach { entry ->
                        append("<p><a href='")
                        append(entry.link)
                        append("'>" + entry.title + "</a></p>")
                        // If the user set the preference to include summary text,
                        // adds it to the display.
                        if (pref) {
                            append(entry.summary)
                        }
                    }
                }.toString()
            }

            // Given a string representation of a URL, sets up a connection and gets
// an input stream.
            @Throws(IOException::class)
            private fun downloadUrl(urlString: String): InputStream? {
                val url = URL(urlString)
                return (url.openConnection() as? HttpURLConnection)?.run {
                    readTimeout = 10000
                    connectTimeout = 15000
                    requestMethod = "GET"
                    doInput = true
                    // Starts the query
                    connect()
                    inputStream
                }


*/






