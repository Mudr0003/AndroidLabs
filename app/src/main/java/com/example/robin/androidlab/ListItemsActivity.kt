package com.example.robin.androidlab

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_list_items.*


class ListItemsActivity : Activity() {

    val ACTIVITYNAME = "  ListItemsActivity.kt"
    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) //run the system code then run our custom code

        Log.i(ACTIVITYNAME, "In onCreate()");
        setContentView(R.layout.activity_list_items)


        //add a click handler:
        imageButText?.setOnClickListener( View.OnClickListener
        {

            // create an Intent to go to the Activity InformationActivity:

             Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(packageManager)?.also {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

                    }
                 }

        })


        //add a switch changed handler:
        switchText?.setOnCheckedChangeListener{_ , isChecked ->

            if (isChecked) {
                // The switch is enabled/checked
                Toast.makeText(this, R.string.swON, Toast.LENGTH_SHORT).show()

            } else {
                // The switch is disabled
                Toast.makeText(this, R.string.swOFF, Toast.LENGTH_LONG).show()
            }

        }


        //add a check changed handler:
        checkText?.setOnCheckedChangeListener{_ , _ ->

            val builder = AlertDialog.Builder(this)

            builder.setTitle(R.string.confirm_message)
            builder.setMessage(R.string.dialog_message)

            builder.setPositiveButton(R.string.yes_button) { dialog, _ ->
                // Do nothing but close the dialog

                dialog.dismiss()

                val resultIntent = Intent( )

                //var resp = R.string.response
                resultIntent.putExtra("Response","This is my response")

                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

            builder.setNegativeButton(R.string.no_button) { dialog, _ ->
                // Do nothing
                dialog.dismiss()
            }

            val alert = builder.create()
            alert.show()

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i(ACTIVITYNAME, "In onActivityResult()");

        setContentView(R.layout.activity_list_items)

        //val imageButton = ImageButton(this)
        var imageButton = findViewById(R.id.imageButText) as? ImageButton

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data.extras.get("data") as Bitmap
            imageButton?.setImageBitmap(imageBitmap)
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
