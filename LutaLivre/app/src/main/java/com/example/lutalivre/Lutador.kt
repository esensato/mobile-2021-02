package com.example.lutalivre

import java.util.Random

class Lutador (var nome:String) {

    var ataque = 0
    var defesa = 0

    init {

        val random = Random()
        this.ataque = random.nextInt(10) + 1
        this.defesa = random.nextInt(10) + 1
        println("Ataque: ${this.ataque} - Defesa: ${this.defesa}")
    }

    fun atacar(defensor:Lutador):String {
        if (this.ataque > defensor.defesa) {
            return this.nome
        } else if (this.ataque < defensor.defesa) {
            return defensor.nome
        } else {
            return "Empate"
        }

    }
}