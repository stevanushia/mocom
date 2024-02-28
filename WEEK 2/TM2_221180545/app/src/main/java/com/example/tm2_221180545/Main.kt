package com.example.tm2_221180545

fun main(){
    var isRunning = true
    while (isRunning){
        println("""
            ============= 
            Debt Escape 
            ============= 
            1. Start 
            2. Exit 
        """.trimIndent())
        print(">> "); var mainInput = readln().toIntOrNull()

        when (mainInput) {
            2 -> {
                isRunning = false
            }
            1 -> {
                //start logic
            }
            else -> {
                println("""
                    There is no such option!
                    Press ENTER to continue...
                """.trimIndent()); readln()
            }
        }
    }
}