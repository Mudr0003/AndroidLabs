package com.example.robin.androidlab

import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test_toolbar.*
import android.app.NotificationManager
import android.content.Context


class TestToolbar : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    var response = "You selected item 1"
    lateinit var snackButton:Button


    var ACTIVITYNAME = "TestToolbar.ky"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_toolbar)


        var lab8_toolbar = findViewById<Toolbar>(R.id.lab8_toolbar);
        setSupportActionBar(lab8_toolbar);
        var drawer = findViewById<DrawerLayout>(R.id.drawer);
        var toggle = ActionBarDrawerToggle(this,drawer,lab8_toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        val name = "Channel_name"
        val descriptionText = "Channel_name"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel("Channel_name", name, importance)
        mChannel.description = descriptionText
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
        snackButton = findViewById<Button>(R.id.snackbar_button)
        snackButton.setOnClickListener {

            Snackbar.make(snackButton, "Message to show", Snackbar.LENGTH_LONG).show()
        }

        //setSupportActionBar(lab8_toolbar) ;




        //drawer

        //var mydrawer = findViewById<DrawerLayout>(R.id.drawer)

        val mynavigation= findViewById<NavigationView>(R.id.navmenu);

        mynavigation.setNavigationItemSelectedListener(this);
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

                var mBuilder = NotificationCompat.Builder(this, "Channel_name")
                        .setSmallIcon(R.drawable.dog)
                        .setAutoCancel(true)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

                val newActivity = Intent( this, ChatWindow::class.java)

                var resultPendingIntent = PendingIntent.getActivity( this, 0, newActivity, PendingIntent.FLAG_UPDATE_CURRENT)
                mBuilder.setContentIntent(resultPendingIntent);
                //transition to new activity

                val mNotificationId = 1
                val mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                mNotifyMgr !!.notify(mNotificationId, mBuilder.build())
                //startActivity(newActivity)


            }

            R.id.listitem -> {


                Log.i(ACTIVITYNAME, "User clicked my button")

                val newListActivity = Intent( this, ListItemsActivity::class.java)

                Log.i(ACTIVITYNAME, "menu Start Chat")

                var mBuilder = NotificationCompat.Builder(this, "Channel_name")
                        .setSmallIcon(R.drawable.frog)
                        .setAutoCancel(true)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

                val newActivity = Intent( this, ChatWindow::class.java)

                var resultPendingIntent = PendingIntent.getActivity( this, 0, newListActivity, PendingIntent.FLAG_UPDATE_CURRENT)
                mBuilder.setContentIntent(resultPendingIntent);
                //transition to new activity

                val mNotificationId = 2
                val mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                mNotifyMgr !!.notify(mNotificationId, mBuilder.build())
            }

            R.id.contactitem -> {



                // create an Intent to go to the Activity InformationActivity:

                var emailintent = Intent(Intent.ACTION_SENDTO)
                var emailPendingIntent = PendingIntent.getActivity( this, 0, emailintent, PendingIntent.FLAG_UPDATE_CURRENT)


                emailintent.setData(Uri.parse("mailto:nobody@nowhere.com"));

               // startActivity(emailintent)


                var txtintent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:"))
                var txtPendingIntent = PendingIntent.getActivity( this, 0, txtintent, PendingIntent.FLAG_UPDATE_CURRENT)

                txtintent.type="text/plain"
                txtintent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
               // startActivity(Intent.createChooser(txtintent,getString(R.string.send_to)))
               // startActivity(Intent.createChooser(txtintent,"hello"))


                val newListActivity = Intent( this, ListItemsActivity::class.java)

                Log.i(ACTIVITYNAME, "menu Start Chat")

                var mBuilder = NotificationCompat.Builder(this, "Channel_name")
                        .setSmallIcon(R.drawable.hotdog)
                        .setAutoCancel(true)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .addAction(R.drawable.pizza, "send email", emailPendingIntent)
                        .addAction(R.drawable.newmsg, "send txt", txtPendingIntent)


                val mNotificationId = 3
                val mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                mNotifyMgr !!.notify(mNotificationId, mBuilder.build())

              //  startActivity(emailintent)
              //  startActivity(txtintent)

            }

        }

         return true

    }




}
