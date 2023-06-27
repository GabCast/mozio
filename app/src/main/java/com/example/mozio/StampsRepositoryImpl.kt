package com.example.mozio

import javax.inject.Inject

class StampsRepositoryImpl @Inject constructor() : StampsRepository {

    override suspend fun calculate(
        firstPersonStamps: Array<String>,
        secondPersonStamps: Array<String>
    ): Pair<Array<String>, Array<String>> {

        // final variable
        val firstGets = mutableListOf<String>()
        val secondGets = mutableListOf<String>()

        // gets counts
        val firstCounts = firstPersonStamps.groupingBy { it }.eachCount()
        val secondCounts = secondPersonStamps.groupingBy { it }.eachCount()

        // check is the first person has more than two equal stamps and check if the second has it
        for ((stamp, count) in firstCounts) {
            if (count > 1 && (secondCounts[stamp] == null || secondCounts[stamp] == 1)) {
                val collection = generateSequence { stamp }.take(count - 2).toMutableList()
                secondGets.addAll(collection)
            }
        }

        // check is the second person has more than two equal stamps and check if the first has it
        for ((stamp, count) in secondCounts) {
            if (count > 1 && (firstCounts[stamp] == null || firstCounts[stamp] == 1)) {
                val collection = generateSequence { stamp }.take(count - 2).toMutableList()
                firstGets.addAll(collection)
            }
        }

        // return values
        return firstGets.toTypedArray() to secondGets.toTypedArray()
    }
}
