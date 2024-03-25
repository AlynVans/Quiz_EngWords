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

        binding.layoutAnswer2.setOnClickListener{
            markAnswerWrong2(
                binding.layoutAnswer2,
                binding.tvVariantNumber2,
                binding.tvVariantValue2,
            )
            showResultMessage(false)
        }

        binding.layoutAnswer3.setOnClickListener {
            markAnswerCorrect(
                binding.layoutAnswer3,
                binding.tvVariantNumber3,
                binding.tvVariantValue3,
            )
            showResultMessage(true)
        }

        binding.pashalochka.setOnClickListener{
            it.isVisible = false
            val toast = Toast.makeText(this, "ПасхалОчка", Toast.LENGTH_SHORT)
            toast.show()
        }

        binding.btnContinue.setOnClickListener {
            markAnswerNeutral(
                binding.layoutAnswer2,
                binding.tvVariantNumber2,
                binding.tvVariantValue2,
            )
            markAnswerNeutral(
                binding.layoutAnswer3,
                binding.tvVariantNumber3,
                binding.tvVariantValue3,
            )
        }
    }

    private fun markAnswerWrong2(
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
