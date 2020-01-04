package com.bignerdranch.android.geoquiz

import androidx.annotation.StringRes

/**
 * CHAPTER 3 CHALLENGE 1 add user answer field to constructor
 */
data class Question(@StringRes val textResId: Int, val answer: Boolean, var answerSubmitted: Boolean)