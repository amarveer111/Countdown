package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    //variables
    lateinit var stopwatch: Chronometer //stopwatch
    var running  = false                //Is the stopwatch running
    var offset: Long = 0                //Base offset of the stopwatch

//key string for use with the bundle
    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"      // val variables are fixed , var variables can be changed


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get a reference to the stopwatch
        stopwatch =findViewById<Chronometer>(R.id.stopwatch)

        //restore the previous state (bundle)
        if(savedInstanceState !=null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if(running){
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            }else{
                setBaseTime()
            }
        }

        val startButton =findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener{
            if(!running){
               //set base time- function
                setBaseTime()
                //start the stopwatch
                stopwatch.start()
                //set running = true
                running = true

                 val editText = findViewById<EditText>(R.id.edit_View)
                 val input = Integer.parseInt(editText.text.toString())

                   stopwatch.base += input * 1000






            }

        }

        //the pause button pauses the stopwatch if it's running
        val pauseButton = findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener{
            if(running) {
                //save offset <---reset back down to 0
                saveOffset()
                //stop the stopwatch
                stopwatch.stop()

                //set running = false
                running = false
            }
        }

        //the reset button sets the offset and stopwatch to 0
        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
             //offset set to 0
            offset = 0
            //reset stopwatch to 0
            setBaseTime()
        }


    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY,offset)
        savedInstanceState.putBoolean(RUNNING_KEY,running)
        savedInstanceState.putLong(BASE_KEY,stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }


    override fun onPause() {
        super.onPause()
        if(running) {
            saveOffset()
            stopwatch.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        if(running){
            stopwatch.start()
            offset = 0

        }
    }





    private  fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }

    //update the stopwatch base time, allowing for any offset.
    private fun setBaseTime() {
       stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

}