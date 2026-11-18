package kr.ac.kumoh.s20260000.w26w12_carddealer.service

import kr.ac.kumoh.s20260000.w26w12_carddealer.model.Card
import kr.ac.kumoh.s20260000.w26w12_carddealer.repository.CardRepository
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class CardService(
    private val repository: CardRepository
) {
    fun getCards(): List<Card> = repository.cards

    fun dealCards(count: Int = 5) {
        val suits = arrayOf("spades", "diamonds", "hearts", "clubs")
        val ranks = arrayOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace")

        repository.deleteAll()

        val uniqueCards = mutableSetOf<Card>()
        while (uniqueCards.size < count) {
            val randomSuit = suits[Random.nextInt(suits.size)]
            val randomRank = ranks[Random.nextInt(ranks.size)]
            uniqueCards.add(Card(randomRank, randomSuit))
        }

        val sortedCards = uniqueCards.toList()
            .sortedWith(compareBy({ suits.indexOf(it.suit) }, { ranks.indexOf(it.rank) }))

        sortedCards.forEach { repository.save(it) }
    }
}