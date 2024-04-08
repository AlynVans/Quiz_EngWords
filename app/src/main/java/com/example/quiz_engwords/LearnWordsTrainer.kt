package com.example.quiz_engwords

data class Word(
    val original: String,
    val translate: String,
    var learned: Boolean = false,
)

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word,
)

class  LearnWordsTrainer {

    private val dictionary =  listOf(
        Word("Aluminium", "Алюминий"),
        Word("Anaesthetist", "анестезиолог"),
        Word("Anonymous", "анонимный"),
        Word("Ethnicity", "этническая или расовая принадлежность"),
        Word("Facilitate", "облегчать"),
        Word("February", "февраль"),
        Word("Hereditary", "наследственный"),
        Word("Hospitable", "гостеприимный"),
        Word("Onomatopoeia", "звукоподражание"),
        Word("Particularly", "в особенности"),
        Word("Phenomenon", "феномен"),
        Word("Philosophical", "философский"),
        Word("Prejudice", "предубеждение"),
        Word("Prioritising", "определение приоритетов"),
        Word("Pronunciation", "произношение"),
        Word("Provocatively", "вызывающе"),
        Word("Regularly", "регулярно"),
        Word("Remuneration", "вознаграждение"),
        Word("Statistics", "статистические данные"),
        Word("Thesaurus", "справочник"),
        Word("Да?", "***Да!"),
    )

    private var currentQuestion: Question? = null

    fun getNextQuestion(): Question? {
        val notLearnedList = dictionary.filter { !it.learned }
        if (notLearnedList.isEmpty()) return null

        val questionWords =
            if (notLearnedList.size < NUMBER_OF_ANSWER ) {
                val learnedList = dictionary.filter { it.learned }.shuffled()
                notLearnedList.shuffled()
                    .take(NUMBER_OF_ANSWER) + learnedList
                    .take(NUMBER_OF_ANSWER - notLearnedList.size)
            } else {
                notLearnedList.shuffled().take(NUMBER_OF_ANSWER)
            }.shuffled()

        val correctAnswer: Word = questionWords.random()

        currentQuestion = Question(
            variants = questionWords,
            correctAnswer = correctAnswer,
        )
        return currentQuestion
    }

    fun checkAnswer(userAnswerIndex: Int?): Boolean{

        return currentQuestion?.let {

            val correctAnswerId = it.variants.indexOf(it.correctAnswer)
            if (correctAnswerId == userAnswerIndex) {
                it.correctAnswer.learned = true
                true
            } else {
                false
            }
        } ?: false
    }
}

const val NUMBER_OF_ANSWER: Int = 4