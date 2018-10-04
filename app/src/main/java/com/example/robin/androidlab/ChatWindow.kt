package com.example.robin.androidlab

import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


import kotlinx.android.synthetic.main.activity_chat_window.*
import kotlinx.android.synthetic.main.activity_chat_window.view.*
import java.util.ArrayList

class ChatWindow : Activity() {
    val ACTIVITYNAME = "  ***** ChatWindow.kt"


    val CREATION_STATEMENT = "CREATE TABLE MESSAGES ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " aMessage text )"


    var msgList = arrayListOf<String>()

    //var msgList = null as ListView?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)

        var listView = findViewById<ListView>(R.id.ListViewId)

        var doneButton = findViewById<Button>(R.id.SendButtonId)

        var editText = findViewById<EditText>(R.id.EditFieldId)

        Log.i(ACTIVITYNAME, "in onCreate")

        setResult(33)

        doneButton?.setOnClickListener{
            //get the text in the EditText field, and add it to your array list variable.
            Log.i(ACTIVITYNAME, "done Chat Button clicked")

            if (!editText.text.isEmpty()) {
            msgList.add(editText.text.toString())}


            var theadapter = ChatAdapter(this)
            listView.adapter = theadapter

            theadapter.notifyDataSetChanged() //this restarts the process of getCount() & getView()

            editText.setText("")

        }


    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i(ACTIVITYNAME, "is onDestroy")

        // create an Intent to go to the start Activity
        //val nextActivity = Intent( this, StartActivity::class.java);


        //setResult(RESULT_OK);


    }



    inner class ChatAdapter(ctx : Context) : ArrayAdapter<String>(ctx, 0 ) {

        override fun getCount(): Int {
            return msgList.size
        }

        override fun getItem(position: Int): String {
            return msgList.get(position)
        }




        override fun getView(position : Int, convertView: View?, parent : ViewGroup) : View {

            var inflater = LayoutInflater.from(parent.context)

            var thisRow:View?


            if (position % 2 == 0) {

              thisRow = inflater.inflate(R.layout.chat_row_incoming, null)

        }
           else {
                thisRow = inflater.inflate(R.layout.chat_row_outgoing, null)

            }

            thisRow.findViewById<TextView>(R.id.message_text_id)
            //var textView = thisRow.findViewById<TextView>(R.id.out_message_text)

            val message = thisRow?.findViewById(R.id.message_text_id)as TextView

            message.text = getItem(position) // get the string at position

            return thisRow
        }

        override fun getItemId(position: Int): Long {

            //  This is the database id of the item at position. For now, we aren’t using SQL, so just return the number: position.
           // return position as Long
            return 0
        }

    }

}
