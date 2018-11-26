package com.example.robin.androidlab

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test_toolbar.*


class TestToolbar : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

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




        //drawer

        var mydrawer = findViewById<DrawerLayout>(R.id.drawer)

        val mynavigation= findViewById<NavigationView>(R.id.navmenu)

        mynavigation.setNavigationItemSelectedListener(this)
       // mynavigation.setNavigationItemSelectedListener { menuItem ->

           // menuItem.isChecked = true
        //    mydrawer.closeDrawers()

         //       true
       // }









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


     override fun onNavigationItemSelected(item: MenuItem):Boolean{

        var id = item.itemId
        when (id)
        {
            R.id.chatitem -> {

                Log.i(ACTIVITYNAME, "menu Start Chat")


                val newActivity = Intent( this, ChatWindow::class.java)

                //transition to new activity

                startActivity(newActivity)


            }

            R.id.listitem -> {


                Log.i(ACTIVITYNAME, "User clicked my button")

                val newActivity = Intent( this, ListItemsActivity::class.java)

                //transition to new activity

                startActivityForResult(newActivity,55)




            }

            R.id.contactitem -> {



                // create an Intent to go to the Activity InformationActivity:

                var emailintent = Intent(Intent.ACTION_SENDTO)


                emailintent.setData(Uri.parse("mailto:nobody@nowhere.com"));

               // startActivity(emailintent)


                var txtintent = Intent(Intent.ACTION_SEND)

                txtintent.type="text/plain"
                txtintent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
               // startActivity(Intent.createChooser(txtintent,getString(R.string.send_to)))
                startActivity(Intent.createChooser(txtintent,"hello"))



               // txtintent.setData("somestring");

               // startActivity(txtintent)



               /* var dialogStuff = layoutInflater.inflate(R.layout.dialog_stuff,null)
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

*/


                startActivity(emailintent)
                startActivity(txtintent)

            }

        }

         return true

    }




}
