package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    /**
     * CHAPTER 3 challenge 1 set user answer to false initially
     */
    private val questionBank = listOf(
        Question(R.string.question_australia, true, false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false),
        Question(R.string.question_africa, false, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_asia, true, false)

    )


    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)


        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)

        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()

        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "OnStart(Bundle?) Called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "OnResume(Bundle?) Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "OnPause(Bundle?) Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "OnStop(Bundle?) Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "OnDestroy(Bundle?) Called")
    }

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        //if user submitted answer disable buttons
        setButtonStatus()

    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        }else{
            R.string.incorrect_toast
        }
        // update answer submitted to true
        questionBank[currentIndex].answerSubmitted = true
        setButtonStatus()
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    /**
     * Chapter 3 challenge 1
     */
    private fun setButtonStatus(){
        if(questionBank[currentIndex].answerSubmitted) {
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }else{
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }
}
