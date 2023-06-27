package com.example.mozio

interface StampsRepository {

    suspend fun calculate(
        firstPersonStamps: Array<String>,
        secondPersonStamps: Array<String>
    ): Pair<Array<String>, Array<String>>

}
