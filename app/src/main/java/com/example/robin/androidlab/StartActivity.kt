package com.example.robin.androidlab

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast

class StartActivity : Activity() {
    val ACTIVITYNAME = "  StartActivity.kt"
    var REQCODE:Int = 50
    val REQCODE_CHAT:Int = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) //run the system code then run our custom code

        Log.i(ACTIVITYNAME, "In onCreate()")
        setContentView(R.layout.activity_start)

        var Mybutton = findViewById<Button>(R.id.button)

        //add a click handler:
        Mybutton.setOnClickListener{
            //create an Intent to go to the Activity InformationActivity:
            Log.i(ACTIVITYNAME, "User clicked my button")

            val newActivity = Intent( this, ListItemsActivity::class.java)

            //transition to new activity

            startActivityForResult(newActivity,REQCODE)

        }


        var Chatbutton = findViewById<Button>(R.id.chatButtonId)

        //add a chat button handler:
        Chatbutton.setOnClickListener{
            Log.i(ACTIVITYNAME, "User clicked Start Chat")


            val newActivity = Intent( this, ChatWindow::class.java)

            //transition to new activity

            startActivity(newActivity)

        }



        var Weatherbutton = findViewById<Button>(R.id.weather_button)

        //add a chat button handler:
        Weatherbutton.setOnClickListener{
            Log.i(ACTIVITYNAME, "User clicked Weather Button")


            val newActivity = Intent( this, WeatherForecast::class.java)

            //transition to new activity

            startActivity(newActivity)

        }



        var toolbarbutton = findViewById<Button>(R.id.toolbar_button)

        //add a menu button handler:
        toolbarbutton.setOnClickListener{
            Log.i(ACTIVITYNAME, "User clicked toolbar Button")


            val newActivity = Intent( this, TestToolbar::class.java)

            //transition to new activity

            startActivity(newActivity)

        }






    }


    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent)


    {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i(ACTIVITYNAME, "in onActivityResult")
        if (requestCode == REQCODE) {
            Log.i(ACTIVITYNAME, "Returned to StartActivity.onActivityResult")

            if (resultCode == Activity.RESULT_OK) {   //photo accepted
                val messagePassed = data.getStringExtra("Response")

                Toast.makeText(this, "ListItemsActivity passed: " + messagePassed, Toast.LENGTH_LONG).show()


            }
        }

    }

    override fun onResume(){
        super.onResume() //run the system code then run our custom code

        Log.i(ACTIVITYNAME, "In onResume()")
    }

    override fun onStart(){
        super.onStart() //run the system code then run our custom code

        Log.i(ACTIVITYNAME, "In onStart()")
    }

    override fun onPause(){
        super.onPause() //run the system code then run our custom code

        Log.i(ACTIVITYNAME, "In onPause()")
    }

    override fun onStop(){
        super.onStop()  //run the system code then run our custom code

        Log.i(ACTIVITYNAME, "In onStop()")
    }

    override fun onDestroy(){
        super.onDestroy()  //run the system code then run our custom code

        Log.i(ACTIVITYNAME, "In onDestroy()")
    }



}
