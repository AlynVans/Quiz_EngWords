package com.example.quiz_engwords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.quiz_engwords.databinding.ActivityLearnWordBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityLearnWordBinding? = null
    private val binding
        get() = _binding
            ?:throw IllegalStateException("Binding for ActivityLearnWordBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLearnWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trainer = LearnWordsTrainer()
        showNextQuestion(trainer)

        with(binding) {
            btnContinue.setOnClickListener{
                layoutResult.isVisible = false
                markAnswerNeutral(layoutAnswer1,tvVariantNumber1,tvVariantValue1)
                markAnswerNeutral(layoutAnswer2,tvVariantNumber2,tvVariantValue2)
                markAnswerNeutral(layoutAnswer3,tvVariantNumber3,tvVariantValue3)
                markAnswerNeutral(layoutAnswer4,tvVariantNumber4,tvVariantValue4)
                showNextQuestion(trainer)
            }

            btnSkip.setOnClickListener {
                showNextQuestion(trainer)
            }
        }

        binding.pashalochka.setOnClickListener{
            it.isVisible = false
            val toast = Toast.makeText(this, "ПасхалОчка", Toast.LENGTH_SHORT)
            toast.show()
        }

        binding.ibClose.setOnClickListener {
            val toast = Toast.makeText(this, "Мне было лень реализовывать", Toast.LENGTH_SHORT)
            toast.show()
        }

        with(binding){
            ibClose.setOnClickListener {
                ibClose.isVisible = false
                gif.isVisible = true
            }
        }

    }



    private fun showNextQuestion(trainer: LearnWordsTrainer) {
        val firstQuestion: Question? = trainer.getNextQuestion()
        with(binding) {
            if (firstQuestion == null || firstQuestion.variants.size < NUMBER_OF_ANSWER) {
                tvQuestionWord.isVisible = false
                layoutVariants.isVisible = false
                btnSkip.text = "Complete"
            } else {
                btnSkip.isVisible = true
                tvQuestionWord.isVisible = true
                tvQuestionWord.text = firstQuestion.correctAnswer.original

                tvVariantValue1.text = firstQuestion.variants[0].translate
                tvVariantValue2.text = firstQuestion.variants[1].translate
                tvVariantValue3.text = firstQuestion.variants[2].translate
                tvVariantValue4.text = firstQuestion.variants[3].translate

                layoutAnswer1.setOnClickListener {
                    if (trainer.checkAnswer(0)){
                        markAnswerCorrect(layoutAnswer1,tvVariantNumber1,tvVariantValue1)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer1,tvVariantNumber1,tvVariantValue1)
                        showResultMessage(false)
                    }
                }
                layoutAnswer2.setOnClickListener {
                    if (trainer.checkAnswer(1)){
                        markAnswerCorrect(layoutAnswer2,tvVariantNumber2,tvVariantValue2)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer2,tvVariantNumber2,tvVariantValue2)
                        showResultMessage(false)
                    }
                }
                layoutAnswer3.setOnClickListener {
                    if (trainer.checkAnswer(2)){
                        markAnswerCorrect(layoutAnswer3,tvVariantNumber3,tvVariantValue3)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer3,tvVariantNumber3,tvVariantValue3)
                        showResultMessage(false)
                    }
                }
                layoutAnswer4.setOnClickListener {
                    if (trainer.checkAnswer(3)){
                        markAnswerCorrect(layoutAnswer4,tvVariantNumber4,tvVariantValue4)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer4,tvVariantNumber4,tvVariantValue4)
                        showResultMessage(false)
                    }
                }
            }
        }
    }

    private fun markAnswerWrong(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_wrong_answer
        )

        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_wrong
        )

        tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )

        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.wrongAnswer
            )
        )

        binding.btnSkip.isVisible = false
    }

    private fun markAnswerNeutral(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView,
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers
        )

        tvVariantNumber.apply {
            background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_variants,
            )
            setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.textVariantsColor
                )
            )
        }

        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.textVariantsColor
            )
        )
    }

    private fun markAnswerCorrect(
        layoutAnswer: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView,
    ) {
        layoutAnswer.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_containers_correct_answer
        )

        tvVariantNumber.background = ContextCompat.getDrawable(
            this@MainActivity,
            R.drawable.shape_rounded_variants_correct
        )

        tvVariantNumber.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.white
            )
        )

        tvVariantValue.setTextColor(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.correctAnswer
            )
        )
    }

    private fun showResultMessage(isCorrect: Boolean){
        val color: Int
        val messageText: String
        val resultIconResource: Int
        if (isCorrect){
            color = ContextCompat.getColor(this, R.color.correctAnswer)
            resultIconResource = R.drawable.ic_correct
            messageText = "Correct!" //TODO get from string resource
        } else{
            color = ContextCompat.getColor(this, R.color.wrongAnswer)
            resultIconResource = R.drawable.ic_wrong
            messageText = "Incorrect" //TODO get from string resource
        }

        with(binding){
            btnSkip.isVisible = false
            layoutResult.isVisible= true
            btnContinue.setTextColor(color)
            layoutResult.setBackgroundColor(color)
            tvResultMessage.text = messageText
            ivResultIcon.setImageResource(resultIconResource)
        }
    }
}
