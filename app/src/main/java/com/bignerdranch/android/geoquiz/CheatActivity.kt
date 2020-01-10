package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"

private const val EXTRA_ANSWER_IS_TRUE =
    "com.bignerdranch.android.geoquiz.answer_is_true"

/**
 * Reusing chapter 6 challenge 1 code for
 * chapter 7 cahllenge 2
 */
private const val KEY_CHEATER = "cheater" // create a key for cheater status for saveInstaneState

private const val TAG = "CheatActivity" //TAG for log cat debugging


class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    private var answerIsTrue = false

    /**
     * Challenge
     */
    private var cheaterStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        /**
         * Chapter 6 challenge 1
         */
        //get savedInstnaceState and set cheater status accordingly
        cheaterStatus = savedInstanceState?.getBoolean(KEY_CHEATER, false) ?: false

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)

        showAnswerButton = findViewById(R.id.show_answer_button)

        /**
         * Chapter 6 challeneg 1 / chap 7 challenge 2
         */
        if(cheaterStatus){
            cheatButtonClicked()
        }


        //eliminate duplicate code by creating function to handle it
        showAnswerButton.setOnClickListener {
            cheatButtonClicked()

        }
    }

    /**
     * Chapter 6 challenge 1 / chap 7 challenge 2
     */
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG,"onSaveInstanceState")
        savedInstanceState.putBoolean(KEY_CHEATER, cheaterStatus)
    }

    /**
     * Chapter 6 challenge 1 // chapter 7 challenge 2
     * function to just eliminate some duplicate code
     */
    private fun cheatButtonClicked(){
        val answerText = when{
            answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }
        answerTextView.setText(answerText)
        setAnswerShownResult(true)

        //just a quick way to track whether user cheated or not
        cheaterStatus = true
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean){
        val data =  Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object{
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}
