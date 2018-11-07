package com.example.robin.androidlab

import android.os.Bundle
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.robin.lab7.MessageDetails
import com.example.robin.lab7.MessageFragment




    class ChatWindow : Activity() {
        val ACTIVITYNAME = "  ***** ChatWindow.kt"
var messagepos = 0

        val CREATION_STATEMENT = "CREATE TABLE MESSAGES ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " aMessage text )"

lateinit var  dbHelper : ChatDatabaseHelper

        var msgList = arrayListOf<String>()
        lateinit var db : SQLiteDatabase
        //var db : SQLiteDatabase? = null
        //var msgList = null as ListView?

        lateinit var results:Cursor


        lateinit var theadapter : ChatAdapter




        override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_chat_window)

            Log.i(ACTIVITYNAME, "starting onCreate")


            var listView = findViewById<ListView>(R.id.ListViewId)

            var doneButton = findViewById<Button>(R.id.SendButtonId)

            var editText = findViewById<EditText>(R.id.EditFieldId)


            var fragmentlocation = findViewById<FrameLayout>(R.id.fragment_location)

            var iAmLandscape = (fragmentlocation != null)

            Log.i(ACTIVITYNAME,"I AM LANDSCAPE=" + iAmLandscape)









            //db stuff
             dbHelper = ChatDatabaseHelper()
            db = dbHelper.writableDatabase


            results = db.query(dbHelper.TABLE_NAME, arrayOf(dbHelper.KEY_MESSAGE,dbHelper.KEY_ID),null,null,null,null,null,null)

            var numRows = results.getCount()
            var numColumns = results.getColumnCount()

            results.moveToFirst()  //read from first row


            val keyIndex = results.getColumnIndex(dbHelper.KEY_MESSAGE)

            for (x in 0..numColumns-1){

                results.getColumnName(x)
            }

            while (!results.isAfterLast)
            {
                var thisMessage = results.getString(keyIndex)
                msgList.add(thisMessage)
                results.moveToNext()

                Log.i(ACTIVITYNAME,"SQL message=" + thisMessage)

                Log.i(ACTIVITYNAME, "Cursor column count =" + numColumns );



            }
            theadapter = ChatAdapter(this)
            listView.adapter = theadapter









            listView.setOnItemClickListener { parent, view, position, id ->


                messagepos = position
                var infoToPass = Bundle()
                infoToPass.putString("Message",msgList.get(position))
                infoToPass.putLong("id",id)

                if (iAmLandscape) {
                    //landscape / tablet only

                    var loadFragment = getFragmentManager().beginTransaction()
                    var newMessageFragment = MessageFragment()

                    newMessageFragment.parent = this

                    newMessageFragment.arguments = infoToPass
                    loadFragment.replace(R.id.fragment_location,newMessageFragment)

                    loadFragment.commit()



                } else {
                    //portrait /  phone only


                    //add a chat button handler:

                        val newActivity = Intent( this, MessageDetails::class.java)

                    newActivity.putExtras(infoToPass)

                    //transition to new activity

                        startActivityForResult(newActivity,34)





                }

            }






            theadapter.notifyDataSetChanged()

            setResult(33)

            doneButton?.setOnClickListener{
                //get the text in the EditText field, and add it to your array list variable.
                Log.i(ACTIVITYNAME, "done Chat Button clicked")

                if (!editText.text.isEmpty()) {

                    var userTyped = editText.text.toString()

                    msgList.add(userTyped)


                    //db stuff
                    var newRow = ContentValues()
                    newRow.put(dbHelper.KEY_MESSAGE, userTyped)

                    db.insert(dbHelper.TABLE_NAME, "", newRow)


                    results = db.query(dbHelper.TABLE_NAME, arrayOf(dbHelper.KEY_MESSAGE,dbHelper.KEY_ID),null,null,null,null,null,null)

                    //       var theadapter = ChatAdapter(this)
              //      listView                results = db.query(dbHelper.TABLE_NAME, arrayOf(dbHelper.KEY_MESSAGE,dbHelper.KEY_ID),null,null,null,null,null,null)        results = db.query(dbHelper.TABLE_NAME, arrayOf(dbHelper.KEY_MESSAGE,dbHelper.KEY_ID),null,null,null,null,null,null).adapter = theadapter

                   theadapter.notifyDataSetChanged() //this restarts the process of getCount() & getView()

                    editText.setText("")
                }

            }


        }



        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
            super.onActivityResult(requestCode, resultCode, data)
            Log.i(ACTIVITYNAME, "In onActivityResult()")



            if (requestCode == 34 && resultCode== RESULT_OK){

                deleteMessage(data.getLongExtra("ID", 0)          )
            }
            /*
            setContentView(R.layout.activity_list_items)


            }*/

        }





        override fun onDestroy() {
            super.onDestroy()
            Log.i(ACTIVITYNAME, "is onDestroy")

            // create an Intent to go to the start Activity
            //val nextActivity = Intent( this, StartActivity::class.java);

              db?.close()
            //setResult(RESULT_OK);


        }

        val DATABASE_NAME = "Messages.db"
        val VERSION_NUM = 2
        val gKEY_ID = "_id"

        val gTABLE_NAME = "ChatMessages"
        val gKEY_MESSAGE = "Messages"

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
                val dbHelper = ChatDatabaseHelper()
                //  This is the database id of the item at position. For now, we aren’t using SQL, so just return the number: position.
                // return position as Long

               // var column = results.getColumnIndex(dbHelper.KEY_ID)
                var column = results.getColumnIndex("_id")

                results.moveToPosition(position)

                var resid = results.getInt(column).toLong() //id is in the second column



                return resid


            }

        }




            inner class ChatDatabaseHelper : SQLiteOpenHelper(this@ChatWindow, DATABASE_NAME, null, VERSION_NUM){


                val KEY_ID = gKEY_ID
                val TABLE_NAME = gTABLE_NAME
                val KEY_MESSAGE = gKEY_MESSAGE
                //as an object?


                    override fun onCreate(db:SQLiteDatabase) {
                        Log.i(ACTIVITYNAME,"ChatDatabaseHelper > onCreate");


                       // db.execSQL("CREATE TABLE " + TABLE_NAME + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " MESSAGE TEXT)")
                        db.execSQL("CREATE TABLE $TABLE_NAME ( _id INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_MESSAGE MESSAGE TEXT)")

                    }

                override fun onUpgrade(db:SQLiteDatabase, oldVersion: Int, newVersion: Int){
                    Log.i(ACTIVITYNAME,"ChatDatabaseHelper > onUpgrade oldVersion="+oldVersion+" new version="+newVersion);

                    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
                    onCreate(db)
                }





            }

        fun deleteMessage(id: Long){

            //as an object?


                db.delete(gTABLE_NAME, "_id = $id" , null)
//
  //          msgList.removeAt(messageClicked)
            msgList.removeAt(messagepos)

            results = db.query(dbHelper.TABLE_NAME, arrayOf(dbHelper.KEY_MESSAGE,dbHelper.KEY_ID),null,null,null,null,null,null)


            theadapter.notifyDataSetChanged()

        }



    }
