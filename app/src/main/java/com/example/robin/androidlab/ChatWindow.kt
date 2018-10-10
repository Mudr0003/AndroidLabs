package com.example.robin.androidlab

import android.os.Bundle
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.MediaStore
import android.support.constraint.solver.widgets.Helper
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


        //db stuff
        val dbHelper = ChatDatabaseHelper()
        val db = dbHelper.writableDatabase


        val results = db.query(dbHelper.TABLE_NAME, arrayOf(dbHelper.KEY_MESSAGE,dbHelper.KEY_ID),null,null,null,null,null,null)

        var numRows = results.getCount()
        results.moveToFirst()  //read from first row

        val keyIndex = results.getColumnIndex(dbHelper.KEY_MESSAGE)

        while (!results.isAfterLast)
        {
            var thisMessage = results.getString(keyIndex)
            msgList.add(thisMessage)
            results.moveToNext()

        }






        Log.i(ACTIVITYNAME, "in onCreate")

        setResult(33)

        doneButton?.setOnClickListener{
            //get the text in the EditText field, and add it to your array list variable.
            Log.i(ACTIVITYNAME, "done Chat Button clicked")

            if (!editText.text.isEmpty()) {


            msgList.add(editText.text.toString())}



            //db stuff
            var newRow = ContentValues()
            newRow.put(dbHelper.KEY_MESSAGE, msgList.last())

            db.insert(dbHelper.TABLE_NAME,"",newRow)





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


        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            var inflater = LayoutInflater.from(parent.context)

            var thisRow: View?


            if (position % 2 == 0) {

                thisRow = inflater.inflate(R.layout.chat_row_incoming, null)

            } else {
                thisRow = inflater.inflate(R.layout.chat_row_outgoing, null)

            }

            thisRow.findViewById<TextView>(R.id.message_text_id)
            //var textView = thisRow.findViewById<TextView>(R.id.out_message_text)

            val message = thisRow?.findViewById(R.id.message_text_id) as TextView

            message.text = getItem(position) // get the string at position

            return thisRow
        }

        override fun getItemId(position: Int): Long {

            //  This is the database id of the item at position. For now, we aren’t using SQL, so just return the number: position.
            // return position as Long
            return 0
        }

    }


        val DATABASE_NAME = "myfile.db"
        val VERSION_NUM = 1

        inner class ChatDatabaseHelper : SQLiteOpenHelper(this@ChatWindow, DATABASE_NAME, null, VERSION_NUM){

                val KEY_ID = "_id"
                val TABLE_NAME = "ChatMessages"
                val KEY_MESSAGE = "Messages"

                override fun onCreate(db:SQLiteDatabase) {
                  //  db.execSQL("CREATE_TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " TEXT)")
                    db.execSQL("CREATE TABLE " + TABLE_NAME + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " MESSAGE TEXT)")
            }

            override fun onUpgrade(db:SQLiteDatabase, oldVersion: Int, newVersion: Int){
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
                onCreate(db)
            }

        }





}
