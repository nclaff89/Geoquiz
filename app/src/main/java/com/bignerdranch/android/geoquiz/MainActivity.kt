package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT= 0

/**
 * chapter 7 challenge 2
 */
private const val KEY_REMAINING_CHEATS = "remaining_cheats"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    /**
     * Chapter 7 challenge 2
     */
    private lateinit var cheatsRemainingTV: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX,0) ?: 0
        quizViewModel.currentIndex = currentIndex

        /**
         * Chapter 7 challenge 2
         */
        val currentCheatStatus = savedInstanceState?.getInt(KEY_REMAINING_CHEATS, 0) ?: 3
        quizViewModel.availableCheats = currentCheatStatus



        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)

        /**
         * Chapter 7 challenge 2
         */
        cheatsRemainingTV = findViewById(R.id.cheats_remaining)
        cheatButtonstatus()


        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)

        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()

        }

        cheatButton.setOnClickListener {
            //start cheat activity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

        updateQuestion()
    }

    /**
     * Chapter 7 challenge 2
     */
    private fun cheatButtonstatus(){
        cheatButton.isEnabled = quizViewModel.availableCheats > 0
        var cheatMessage = resources.getString(R.string.cheats_remaining) +
                " ${quizViewModel.availableCheats}"
        cheatsRemainingTV.text = cheatMessage
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK){
            return
        }
        if(requestCode == REQUEST_CODE_CHEAT){
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            quizViewModel.availableCheats -= 1
            cheatButtonstatus()
        }
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

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG,"onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        /**
         * Chapter 7 challenge 2
         */
        savedInstanceState.putInt(KEY_REMAINING_CHEATS, quizViewModel.availableCheats)
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
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = when{
            quizViewModel.isCheater -> R.string.judgement_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }


        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}
