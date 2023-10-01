package com.example.mobileecogamify

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList
import java.util.List



class QuizActivity : AppCompatActivity() {

    data class Question(
        val questionText: String,
        val option1: String,
        val option2: String,
        val option3: String,
        val option4: String,
        val correctAnswer: String
    )

    private lateinit var Question1TextView: TextView
    private lateinit var Q1Button1: RadioButton
    private lateinit var Q1Button2: RadioButton
    private lateinit var Q1Button3: RadioButton
    private lateinit var Q1Button4: RadioButton// Add more for additional options
    private lateinit var submitButton: Button

    private var currentQuestionIndex = 0
    private var score = 0

    private val questionList = ArrayList<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        Question1TextView = findViewById(R.id.Question1) // Update the ID to Question1TextView
        Q1Button1 = findViewById(R.id.Q1Button1)
        Q1Button2 = findViewById(R.id.Q1Button2)
        Q1Button3 = findViewById(R.id.Q1Button3)
        Q1Button4 = findViewById(R.id.Q1Button4)
        // Initialize other RadioButtons as needed
        submitButton = findViewById(R.id.submitButton)

        // Add the code to find and set up the previousButton
        val previousButton = findViewById<Button>(R.id.previousButton)
        previousButton.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                displayQuestion()
            } else {
                // Already at the first question, show a message or handle as needed
                Toast.makeText(this, "This is the first question.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        initializeQuestions()
        displayQuestion()

        submitButton.setOnClickListener(View.OnClickListener {
            checkAnswer()
        })

        // Your existing code can be placed here or above the quiz code
    }

    private fun initializeQuestions() {
        // Initialize your questionList with questions and correct answers
        //questionList.add(Question("Question 1 Text", "Correct Answer 1"))
        //questionList.add(Question("Question 2 Text", "Correct Answer 2"))
        questionList.add(
            Question(
                "Question 1 : What is the primary greenhouse gas responsible for climate change?",
                "Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen", "Carbon Dioxide"
            )
        )

        questionList.add(
            Question(
                "Question 2 : Which of the following is a non-renewable source of energy?",
                "Wind Power", "Solar Power", "Natural Gas", "Biomass", "Natural Gas"
            )
        )

        questionList.add(
            Question(
                "Question 3 : What is the term for the gradual increase in the Earth's average temperature due to human activities?",
                " Global Cooling",
                "Climate Change",
                "Solar Warming",
                "Atmospheric Shift",
                "Climate Change"
            )
        )

        questionList.add(
            Question(
                "Question 4 : Which type of pollution is caused by excessive nutrients in water, leading to oxygen depletion and harmful algal blooms?",
                "Light Pollution",
                "Noise Pollution",
                "Air Pollution",
                "Water Pollution",
                "Water Pollution"
            )
        )

        questionList.add(
            Question(
                "Question 5 : What is the term for the preservation and sustainable use of Earth's biological diversity?",
                "Conservation", "Deforestation", "Erosion", "Fossilization", "Conservation"
            )
        )

        questionList.add(
            Question(
                "Question 6 : Which international agreement aims to reduce greenhouse gas emissions to combat climate change?",
                "Kyoto Protocol",
                "Berlin Accord",
                "Paris Agreement",
                "Copenhagen Treaty",
                "Paris Agreement"
            )
        )

        questionList.add(
            Question(
                "Question 7 : What is the process by which trees and plants release water vapor into the atmosphere?",
                "Evaporation", "Condensation", "Precipitation", "Transpiration", "Transpiration"
            )
        )

        questionList.add(
            Question(
                "Question 8 : Which of the following is an example of a renewable energy source?",
                "Coal", "Solar Power", "Natural Gas", "Petroleum", "Solar Power"
            )
        )

        questionList.add(
            Question(
                "Question 9 : What is the term for the loss of a species from a particular habitat or from the entire planet?",
                "Extinction", "Migration", "Adaption", "Fossilization", "Extinction"
            )
        )

        questionList.add(
            Question(
                "Question 10 : What are the three Rs often associated with sustainable living and environmental conservation?",
                " Repair, Replace, Refuse",
                "Replant, Regrow, Release",
                "Reduce, Reuse, Recycle",
                "Relocate, Relinquish, Report",
                "Reduce, Reuse, Recycle"
            )
        )
        // Add more questions as needed
    }

    private fun displayQuestion() {
        // Display the current question and answer options
        val currentQuestion = questionList[currentQuestionIndex]
        Question1TextView.text = currentQuestion.questionText // Updated to Question1TextView
        Q1Button1.text = currentQuestion.option1
        Q1Button2.text = currentQuestion.option2
        Q1Button3.text = currentQuestion.option3
        Q1Button4.text = currentQuestion.option4
        // Set other RadioButtons as needed
    }

    private fun checkAnswer() {
        // Check if the selected answer is correct
        val currentQuestion = questionList[currentQuestionIndex]
        //val selectedOptionId = if (Q1Button1.isChecked) Q1Button1.id else Q1Button2.id // Add more for additional options
        val selectedOptionId = if (Q1Button1.isChecked) {
            Q1Button1.id
        } else if (Q1Button2.isChecked) {
            Q1Button2.id
        } else if (Q1Button3.isChecked) {
            Q1Button3.id
        } else {
            Q1Button4.id
        }
        val selectedOption = findViewById<RadioButton>(selectedOptionId)
        val selectedAnswer = selectedOption.text.toString()
        if (selectedAnswer == currentQuestion.correctAnswer) {
            score++
        }

        // Move to the next question or display the score
        currentQuestionIndex++
        if (currentQuestionIndex < questionList.size) {
            displayQuestion()
        } else {
            // Quiz is over, display the score or other end-of-quiz actions
            // For simplicity, we'll just display the score in a Toast
            Toast.makeText(this, "Quiz completed! Score: $score", Toast.LENGTH_LONG).show()
        }
    }
}