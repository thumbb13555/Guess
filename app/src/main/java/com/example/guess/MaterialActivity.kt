package com.example.guess

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.guess.data.GameDatabase
import com.example.guess.data.Record
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {
    private val REQUEST_RECORD = 100
    val secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)
        Log.d(TAG, "onCreate: ")

        fab.setOnClickListener { view ->
            replay()
        }
        counter.setText(secretNumber.count.toString())
        Log.d(TAG, "onCreate: ${secretNumber.secret}");
        val count = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getInt("REC_COUNTER", -1)
        val nick = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getString("REC_NICKNAME", null)
        Log.d(TAG, "data: $count / $nick");
        //Room test
        /*AsyncTask.execute {
            val list = GameDatabase.getInstance(this)?.recordDao()?.getAll()
            list?.forEach {
                Log.d(TAG, "record:$${it.nickname} ${it.counter}");
            }

        }*/

    }//onCreate

    private fun replay() {
        AlertDialog.Builder(this)
            .setTitle("Replay game")
            .setMessage("Are you sure?")
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                secretNumber.reset()
                counter.setText(secretNumber.count.toString())
                ed_number.setText("")
            }
            .setNeutralButton("Cancel", null)
            .show()
    }

    override fun onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart()
    }

    override fun onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop()
    }

    override fun onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause()
    }

    override fun onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume()
    }

    override fun onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy()
    }

    fun check(view: View) {

        val n = ed_number.text.toString().toInt()
        Log.d(TAG, "number: $n")
        val diff = secretNumber.validate(n)
        var message = getString(R.string.yes_u_got_it)
        if (diff < 0) {
            message = getString(R.string.bigger)
        } else if (diff > 0) {
            message = getString(R.string.smaller)
        }
        counter.setText(secretNumber.count.toString())
//        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
//        val builer = AlertDialog.Builder(this)
//        val dialog = builer.create()
//        dialog.setCanceledOnTouchOutside(true)
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), { dialog, which ->
                if (diff == 0) {
                    val intent = Intent(this, RecordActivity::class.java)
                    intent.putExtra("COUNTER", secretNumber.count)
//                    startActivity(intent)
                    startActivityForResult(intent, REQUEST_RECORD)
                }
            })
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECORD) {
            if (resultCode == Activity.RESULT_OK) {
                val nickname = data?.getStringExtra("NICK")
                Log.d(TAG, "onActivityResult:${nickname} ");
                replay()
            }
        }
    }
}
