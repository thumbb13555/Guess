package com.example.guess

import kotlin.random.Random

class SecretNumber {
    val secret = Random.nextInt(10)+1
    var count = 0

    fun validate(number:Int) :Int{
        return  number - secret
    }


}
fun main(){
    val secretNumber = SecretNumber()
    println(secretNumber.secret)
    println(secretNumber.validate(2))
}