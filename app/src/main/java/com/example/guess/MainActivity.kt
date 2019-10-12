package com.example.guess

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.view.textclassifier.TextLinks
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_function.view.*

class MainActivity : AppCompatActivity() {
    private val RESQUEST_CODE_CAMERA = 100
    val functios = listOf<String>(
        "Camera",
        "Guess game",
        "Record list",
        "Download coupons",
        "News",
        "Maps")
    val secretNumber = SecretNumber()
    val TAG = MainActivity::class.java.simpleName

    var cacheServie: Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //RecyclerView

        recyler.layoutManager = LinearLayoutManager(this)
        recyler.setHasFixedSize(true)
        recyler.adapter = FunctionAdapter()
        //spinner
        val colers = arrayOf("Red","Green","Blue")
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,colers)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d(TAG, "onItemSelected: ${colers[position]}");
            }

        }
    }
    inner class FunctionAdapter :RecyclerView.Adapter<FunctionHolder>(){


        override fun getItemCount(): Int {
            return functios.size
        }

        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            holder.nameText.text = functios.get(position)
            holder.itemView.setOnClickListener {
                functionClicked(position)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_function,parent,false)
            val holder = FunctionHolder(view)

            return holder
        }

    }

    private fun functionClicked(position: Int) {
        when(position){
            0 -> {
                val permission =  ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                if(permission == PackageManager.PERMISSION_GRANTED){
                    takePhoto()
                }else{
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.CAMERA),RESQUEST_CODE_CAMERA)
                }

            }
            1 -> startActivity(Intent(this,MaterialActivity::class.java))
            2 -> startActivity(Intent(this,RecordListActivity::class.java))
            4 -> startActivity(Intent(this,NewsActivity::class.java))
            else -> return

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == RESQUEST_CODE_CAMERA){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takePhoto()
            }
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }

    class  FunctionHolder(view : View) : RecyclerView.ViewHolder(view){
        var nameText :TextView = view.name

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_cache){
            Log.d(TAG, "Cache selected ");
            cacheServie = Intent(this,CacheService::class.java)
            startService(cacheServie)
            startService(Intent(this,CacheService::class.java))
            startService(Intent(this,CacheService::class.java))
        }
        return super.onOptionsItemSelected(item)
    }


}
