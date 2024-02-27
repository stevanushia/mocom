package com.example.pembahasanmateri1

/**
 * Sebagai catatan tambahan
 *
 * 1. Perbedaan readLine dan readln
 * readLine merupakan perintah kotlin yang lebih lama untuk mengambil input
 * readLine akan mengembalikan tipe String?, artinya bisa jadi null
 * readln merupakan perintah baru mulai Kotlin 1.6 dan akan mengembalikan String
 * saat menggunakan readLine, perlu menggunakan safe call sedangkan pada readln tidak
 * Apabila input ingin dikonversi, gunakan konversi yang bisa mengembalikan null
 * contohnya toIntOrNull, toDoubleOrNull, dll
 * Jangan lupa tambahkan elvis operator untuk default value
 *
 * val input = readln().toIntOrNull() ?: 0
 *
 * 2. Cara melakukan modifikasi pada hasil Map/Filter/dll
 * hasil kembalian Map/Filter/dll merupakan List, sehingga bersifat Immutable
 * Ubahlah menjadi MutableList dengan perintah toMutableList
 *
 * val f = l.filter { it % 2 == 0 }
 * val nf = f.toMutableList()
 * vf[0] = 1000
 *
 * 3. Cara melakukan join array menjadi string
 * Gunakan joinToString untuk join array menjadi string
 * val l = listOf(1,2,3,4,5)
 * val strl = l.joinToString(separator="")
 *
 *
 * 4. Cara melakukan pengubahan character dalam string
 * String pada kotlin bersifat immutable(tidak dapat diubah melalui index)
 * Untuk mengubah terdapat 2 cara, yaitu:
 * a. Mengkonversi menjadi MutableList<Char>
 *     Konversi string menjadi list char menggunakan toCharArray.
 *     lalu sambungkan dengan toMutableList untuk membuatnya menjadi mutable
 *     val str = "abcde"
 *     val listChar = str.toCharArray().toMutableList()
 *     listChar[0] = 'Z'
 *     val newStr = listChar.joinToString(separator="")
 *
 * b. Menggunakan StringBuilder
 *     StringBuilder memberikan akses untuk modifikasi string lebih mudah
 *     val str = "abcde"
 *     val builder = StringBuilder(str)
 *     builder[0] = 'Z'
 *     print(builder)
 *
 *     atau cara oneline
 *     val newStr = StringBuilder(str).also { it.setCharAt(index=0, ch='Z') }
 *
 * 5. Cara menggambar
 *    val SIZE = 5
 *     for (i in 1..SIZE){
 *         for(j in 1..SIZE){
 *             if(i == 1 || i == SIZE || j == 1 || j == SIZE){
 *                 print("#")
 *             }
 *             else{
 *                 print(" ")
 *             }
 *         }
 *         println()
 *     }
 */

fun main(){
    var isRunning = true
    val menu = """
        Kotlin Basics
        1. Tebak kalimat
        2. Rata-rata nilai
        3. Beli rumah
        4. Logout
    """.trimIndent()

    while(isRunning){
        println(menu)
        val menuInput = readln().toIntOrNull() ?: 0
        when(menuInput){
            1 -> tebakKalimat()
            2 -> rataRataNilai()
            3 -> beliRumah()
            4 -> isRunning = false
        }
    }
}

fun maskInput(input:String) :List<Char>{
    val result = input.map { if(it in 'A'..'z') '*' else it }
    return result

    /*
    //Cara kerja code diatas adalah:

    var maskedInputKalimat = ""
    for(karakter in inputKalimat){
        if(karakter in 'A'..'Z' || karakter in 'a'..'z'){
        //mengambil hanya karakter alphabet A-Z dan a-z
        // atau bisa menggunakan fungsi isLetter() milik Char
            maskedInputKalimat += '*'
        }
        else{
            maskedInputKalimat += karakter
        }
    }
     */
}

fun tebakKalimat(){
    var score = 3
    var isPlaying = true

    print("Input kalimat: ")
    val inputKalimat = readln()
    var maskedInput = maskInput(inputKalimat).toMutableList()

    println("GAME DIMULAI")
    while(isPlaying){
        println("SKOR = $score")
        println("Kalimat yang ditebak: ${maskedInput.joinToString("")}")
        print("Tebakkan : ")
        val tebak = readln()[0]
        if (tebak in inputKalimat){
            // Benar
            for ((idx, k) in inputKalimat.withIndex()){
                if(k == tebak){
                    maskedInput[idx] = k
                    score++
                }
            }
        }
        else{
            // Salah
            score--
        }

        //Cek menang
        // sama dengan cek array.size == 0
        if(maskedInput.filter({ it == '*' }).isEmpty()){
            println("Anda menang")
            return
        }
        //cek kalah
        if(score == 0){
            println("Anda kalah")
            return
        }
    }
}

fun getSortedNilai(nilai: MutableList<Int>): MutableList<Int>{
    for((indexI, i) in nilai.withIndex()) {
        for((indexJ, j) in nilai.withIndex()){
            if(indexI != indexJ){
                if(j < i){
                    val temp = j
                    nilai[indexJ] = i
                    nilai[indexI] = temp
                }
            }
        }
    }
    return nilai
}

fun rataRataNilai(){
    print("Input panjang list :")
    val N = readln().toIntOrNull() ?: 0
    val listUser = mutableListOf<String>()
    for (i in 1..N){
        print("Input User $i :")
        val newUser = readln()
        listUser.add(newUser)
    }
    print("Input User yang akan dicari:")
    val searchUser = readln()
    val findUser = listUser.find { searchUser in it }
    if (findUser == null){
        print("User tidak ditemukan")
    }
    else{
        val nilaiStr = findUser.split(' ').toMutableList()
        nilaiStr.removeAt(0)
        var nilai = nilaiStr.map{ it.toInt() }.toMutableList()
        nilai = getSortedNilai(nilai)
        println("Nilai $searchUser: ${nilai.joinToString(" ")}")
        println("Rata-rata $searchUser: ${nilai.sum().toDouble()/nilai.size}")
    }
}

fun beliRumah(){
    println("""
====================
    FITUR RUMAH 
====================
    """.trimIndent())
    print("Panjang Tanah: ")
    var panjangTanah = readln().toIntOrNull() ?: 1
    print("Lebar Tanah: ")
    var lebarTanah = readln().toIntOrNull() ?: 1

    print("Panjang Bangunan: ")
    var panjangBangunan = readln().toIntOrNull() ?: 1
    print("Lebar Bangunan: ")
    var lebarBangunan = readln().toIntOrNull() ?: 1

    print("Umur Bangunan: ")
    var umurBangunan = readln().toIntOrNull() ?: 1
    print("Jumlah Kamar Tidur: ")
    var jumlahKamarTidur = readln().toIntOrNull() ?: 1
    print("Jumlah Kamar Mandi: ")
    var jumlahKamarMandi = readln().toIntOrNull() ?: 1

    print("Ada Kolam Renang[Y/N]: ")
    var adaKolamRenang = readln()
    var panjangKolamRenang = 0
    var lebarKolamRenang = 0
    if(adaKolamRenang == "Y"){
        print("Panjang Bangunan: ")
        panjangKolamRenang = readln().toIntOrNull() ?: 1
        print("Lebar Bangunan: ")
        lebarKolamRenang = readln().toIntOrNull() ?: 1
    }

    print("Ada Garasi[Y/N]: ")
    var adaGarasi = readln()

    var total = (panjangTanah*lebarTanah) * 3_750_000 + (panjangBangunan*lebarBangunan) * 4_500_000
    when (umurBangunan){
        in 1..5 -> total += 15_000_000 - (500_000 * umurBangunan)
        in 6..10 -> total += 10_000_000 - (1_000_000 * umurBangunan)
        else -> total += 5_000_000
    }
    total += jumlahKamarMandi * 4_900_000 + jumlahKamarTidur * 5_700_000
    if(adaKolamRenang == "Y"){
        total += (panjangKolamRenang*lebarKolamRenang) * 3_000_000 + 5_500_000
    }
    if(adaGarasi == "Y"){
        total += 6_500_000
    }
    println("Harga Total: $total")
    print("""
        Cicilan Pembayaran:
        1. 10 Tahun
        2. 15 Tahun
        3. 20 Tahun
        >> 
    """.trimIndent())
    val pilihanCicilan = readln().toIntOrNull() ?:1
    var cicilan = 0
    when(pilihanCicilan){
        1 -> cicilan = 10
        2 -> cicilan = 15
        3 -> cicilan = 20
    }
    var wajibBayarTahunan = total/cicilan
    var sisaBiaya = total
    var totalBayar = 0
    for(i in 1..cicilan){
        val persenan = (sisaBiaya * 0.01).toInt()
        val bayarTahunIni = wajibBayarTahunan + persenan
        println("Tahun $i: $wajibBayarTahunan + $persenan = $bayarTahunIni")
        sisaBiaya -= wajibBayarTahunan
        totalBayar += bayarTahunIni
    }
    println("Total Pembayaran: $totalBayar")
}