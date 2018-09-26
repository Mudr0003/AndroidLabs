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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) //run the system code then run our custom code

        Log.i(ACTIVITYNAME, "In onCreate()");
        setContentView(R.layout.activity_start)

        var Mybutton = findViewById(R.id.button) as? Button

        //add a click handler:
        Mybutton?.setOnClickListener( View.OnClickListener
        {
            // create an Intent to go to the Activity InformationActivity:
            val newActivity = Intent( this, ListItemsActivity::class.java);

            //transition to new activity

            startActivityForResult(newActivity,REQCODE)

        })

    }


    override fun onActivityResult(requestCode:Int, responseCode:Int, data:Intent)


    {
        if (requestCode == REQCODE) {
            Log.i(ACTIVITYNAME, "Returned to StartActivity.onActivityResult");

            if (responseCode == Activity.RESULT_OK) {   //photo accepted
                val messagePassed = data.getStringExtra("Response")

                Toast.makeText(this, "ListItemsActivity passed: " + messagePassed, Toast.LENGTH_LONG).show()


            }
        }

    }

    override fun onResume(){
        super.onResume() //run the system code then run our custom code

        Log.i(ACTIVITYNAME, "In onResume()");
    }

    override fun onStart(){
        super.onStart() //run the system code then run our custom code

        Log.i(ACTIVITYNAME, "In onStart()");
    }

    override fun onPause(){
        super.onPause() //run the system code then run our custom code

        Log.i(ACTIVITYNAME, "In onPause()");
    }

    override fun onStop(){
        super.onStop()  //run the system code then run our custom code

        Log.i(ACTIVITYNAME, "In onStop()");
    }

    override fun onDestroy(){
        super.onDestroy()  //run the system code then run our custom code

        Log.i(ACTIVITYNAME, "In onDestroy()");
    }



}
