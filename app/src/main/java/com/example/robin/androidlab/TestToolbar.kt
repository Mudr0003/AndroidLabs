package com.example.robin.androidlab

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test_toolbar.*


class TestToolbar : AppCompatActivity() {

    var response = "You selected item 1"
    lateinit var snackButton:Button


    var ACTIVITYNAME = "TestToolbar.ky"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_toolbar)


        var lab8_toolbar = findViewById<Toolbar>(R.id.lab8_toolbar);
        setSupportActionBar(lab8_toolbar)

        snackButton = findViewById<Button>(R.id.snackbar_button)
        snackButton.setOnClickListener {

            Snackbar.make(snackButton, "Message to show", Snackbar.LENGTH_LONG).show()
        }

        setSupportActionBar(lab8_toolbar) ;




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.toolbar_menu, menu);

        return true;

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_one -> {

                Snackbar.make(snackButton, response, Snackbar.LENGTH_LONG).show()
            }
            R.id.action_two -> {
                var dialogStuff = layoutInflater.inflate(R.layout.dialog_stuff,null)
                var editText = dialogStuff.findViewById<EditText>(R.id.new_message)

                var builder = AlertDialog.Builder(this);

                builder.setTitle("Do you want to go back?")

                builder.setPositiveButton("Ok",{

                    dialog,id -> response = editText.text.toString()
                    finish()
                })

                builder.setNegativeButton("Cancel",null)

                // Create the AlertDialog
                var dialog = builder.create()
                dialog.show();


            }
            R.id.action_three -> {

                var dialogStuff = layoutInflater.inflate(R.layout.dialog_stuff,null)
                var editText = dialogStuff.findViewById<EditText>(R.id.new_message)

                var builder = AlertDialog.Builder(this);
                builder.setView(dialogStuff)
                builder.setTitle("Set New Message")

                builder.setPositiveButton("Ok",{
                    dialog,id -> response = editText.text.toString()
                })

                builder.setNegativeButton("Cancel",null)

                // Create the AlertDialog
                var dialog = builder.create()
                dialog.show();




            }
            R.id.action_four -> {
                Toast.makeText(this,"You clicked on dog",Toast.LENGTH_LONG).show()
            }

            R.id.action_five -> {
                Toast.makeText(this,"Version 1.0, by Robin",Toast.LENGTH_LONG).show()
            }


        }
        return true;
    }



}
