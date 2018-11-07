package com.example.robin.lab7


import android.os.Bundle
import android.app.Fragment
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.robin.androidlab.R
import android.R.attr.defaultValue
import android.R.attr.key
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.robin.androidlab.ChatWindow


val ACTIVITYNAME = "MessageFragment.kt"

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MessageFragment : Fragment() {

    lateinit var parent: ChatWindow
    var isTablet = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val bundle = this.arguments

            val info = bundle.getString("Message", "")
            val id = bundle.getLong("id", 0)

            Log.i(ACTIVITYNAME, "In onCreate()")
            var screen =inflater.inflate(R.layout.fragment_message, container, false)


            var delButton = screen.findViewById<Button>(R.id.deleteButton)

            delButton.setOnClickListener( {
                if(isTablet) {

              parent.deleteMessage(id)
            parent.fragmentManager.beginTransaction().remove(this).commit()
            }
                else
                {
                    var delIntent = Intent()
                    activity.setResult(RESULT_OK, delIntent)
                    delIntent.putExtra("ID", id)
                    activity.finish()
                }

            })

            ///pass data to details section???
            var msgView = screen.findViewById<TextView>(R.id.chatMessage)
            var detailView = screen.findViewById<TextView>(R.id.chatId)



            detailView.text = "message information: " + info
            msgView.text = "message id is: " + id.toString()






        return screen

    }


    override fun onAttach(context: Activity?) {
        super.onAttach(context)
        if(isTablet)
            parent = context as ChatWindow



    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bundle = this.arguments
        if (bundle != null) {
            val info = bundle.getString("Message", "")
            val id = bundle.getLong("id", 0)

            ///pass data to details section???
            var msgView = getView().findViewById<TextView>(R.id.chatMessage)
            var detailView = getView().findViewById<TextView>(R.id.chatId)



            detailView.text = "message information: " + info
            msgView.text = "message id is: " + id.toString()



        }




    }













}
