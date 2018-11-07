package com.example.robin.lab7

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.robin.androidlab.R

class MessageDetails : Activity() {
    val ACTIVITYNAME = "MessageDetails.kt"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_details)


        //val extras = intent.extras ?: return

        val extras = intent.extras

        var loadFragment = getFragmentManager().beginTransaction()
        var newMessageFragment = MessageFragment()

newMessageFragment.isTablet= false

        newMessageFragment.arguments = extras
        loadFragment.replace(R.id.fragment_location,newMessageFragment)

        loadFragment.commit()




/*
        //add a click handler:
        val delButton = findViewById(R.id.deleteButton) as? Button
        delButton?.setOnClickListener {

            Log.i(ACTIVITYNAME, "delete button clicked")

        }

*/



    }





/*
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    super.onActivityResult(requestCode, resultCode, data)
    Log.i(ACTIVITYNAME, "In onActivityResult()")

    if (requestCode == 34) {
        if (resultCode == RESULT_OK) {

            val id = data.extras.get("Id")
            val msg = data.extras.get("Message")
        }

    }

}
*/

/*

override fun onViewCreated

        override fun onViewPostCreate(savedInstanceState: Bundle?) {
            super.onPostCreate(savedInstanceState)


            val bundle = this.arguments
            if (bundle != null) {
                val info = bundle.getString("Message", "")
                val id = bundle.getLong("id", 0)

                var msgView = findViewById<TextView>(R.id.chatMessage)
                var detailView = findViewById<TextView>(R.id.chatId)


        }

*/

    }


