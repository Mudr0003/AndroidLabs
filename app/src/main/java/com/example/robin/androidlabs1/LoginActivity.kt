package com.example.robin.androidlabs1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class LoginActivity : Activity() {

    val ACTIVITYNAME = "  LoginActivity.kt"

    override fun onCreate(savedInstanceState: Bundle?) { Log.i(ACTIVITYNAME, "In onCreate()");
        super.onCreate(savedInstanceState) //run the system code then run our custom code
        Log.i(ACTIVITYNAME, "In onCreate()");

        setContentView(R.layout.activity_login)

        //get the EditText fields
        val editEaddr = findViewById<EditText>(R.id.editText)
        //val editPwd = findViewById<EditText>(R.id.editPassword)

        //Find the button
        var buttonLogin = findViewById(R.id.loginButton) as? Button

        //open the sharedpreferences file called SavedData
        val prefsfile = getSharedPreferences("SavedData", Context.MODE_PRIVATE)
        //Look for the value reserved under the name UserInput. If not there, return Default answer
        val emailString = prefsfile.getString("UserEmail", "email@domain.com")

        //A logging message at the error level of priority
        Log.i(ACTIVITYNAME, "prefsfile user is:" + emailString)

        //put the string found from the user preferences into the EditText field
        editEaddr.setText(emailString)


        //add a click handler:
        buttonLogin?.setOnClickListener( View.OnClickListener
          {

            // Get what the user typed in the editTextval
            //val editEaddr = findViewById<EditText>(R.id.editText)
            val typedString = editEaddr.text.toString()


            //get an editor object to save to SharedPreferences:
            val prefs = prefsfile.edit()
            //Under the name UserEmail, save what the user typed:
            prefs.putString("UserEmail", typedString)
            prefs.commit()//writes the file


            // create an Intent to go to the Activity InformationActivity:
              val newActivity = Intent( this, StartActivity::class.java);

            //Put the user string under the reservation "UserName" to send to the next page
            newActivity.putExtra("UserName", typedString)

            //transition to the new page:
            startActivity(newActivity)
          })


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
