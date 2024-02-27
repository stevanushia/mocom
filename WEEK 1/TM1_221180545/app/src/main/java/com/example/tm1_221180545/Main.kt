package com.example.tm1_221180545

var arrNotes = arrayListOf<ArrayList<String>>()
var password: String? = ""

fun main(){
    var isRunning = true


    while (isRunning){
        println("""
                My Notes
            =================
            1. Tambah Note
            2. Lihat Note
            3. Set Password
            4. Exit
            =================
        """.trimIndent())
        print(">> "); var input = readln()

        if (input.toInt() == 4){ // exit
            isRunning = false
        }
        else if (input.toInt() == 1){ //Tambah Note
            println("""
                  My Notes
                =============
            """.trimIndent())
            print("Judul : "); var judul:String? = readln()
            println("Isi : ")

            var arrIsi = arrayListOf<String>()
            var isi = ""
            var cancel = false

            while (isi != "<FINISH>" || isi != "<CANCEL>"){
                isi = readln()
                if (isi == "<FINISH>"){
                    break
                }

                if (isi == "<CANCEL>"){
                    arrIsi.clear()
                    cancel = !cancel
                    println("!! NOTE CANCELED !!")
                    print("Press ENTER to continue..."); readln()
                    break
                }
                arrIsi.add(isi)
            }

            if (!cancel){
                if (judul == ""){
                    judul = null
                }
                addNote(judul?: "Untitled #", arrIsi) //elvis operator disini. readln() jika di klik enter isinya bukan null, tapi "". Jadi mau gakmau demi 1 poin itu harus di begituin deh. maaf gaktau lagi ngakalinnya gimana
                println("!! NOTE CREATED SUCCESSFULLY !!")
                print("Press ENTER to continue..."); readln()

            }
            else{ //toggle cancel from true to false
                cancel = !cancel
            }
        }

        else if (input.toInt() == 2){ //Lihat Note
            seeNotes()
        }

        else if (input.toInt() == 3){ // Set Password
            print("Input your new password : "); var newPassword = readln()
            password = newPassword
            println("Password Changed!")
            println("Press ENTER to continue..."); readln()
        }
    }
}

fun seeNotes(){
    var isSeeingNote = true

    while (isSeeingNote){
        println("""
               My Notes
            ==============
        """.trimIndent())
        var idx = 0
        if (arrNotes.isEmpty()){
            println("")
            println("!! THERE ARE CURRENTLY NO NOTES !!")
            println("")
        }

        else{
            for (i in arrNotes){
                idx++
                println(idx.toString() + ". " + i[0])
            }
        }
        println("==============")
        print(">> "); var input = readLine()

        if (input == "back"){ // back to main menu
            isSeeingNote = false
        }

        if (input!!.contains("open", true)){ // open notes
            openNotes(input, arrNotes)
        }

        if (input!!.contains("search", true)){ // search
            search(input)
        }

    }
}


fun search(input: String) {
    var isSearching = true

    while (isSearching){
        var splittedWord = input.split(" ")

        if (splittedWord.size < 2) {
            println("PLEASE ENTER THE KEYWORD!!")
            print("Press ENTER to continue...")
            readLine()
        }
        else {
            val keyword = splittedWord[1]
            var foundNotes: ArrayList<ArrayList<String>> = arrNotes.filter { sublist -> sublist.any { it.contains(keyword,true) } } as ArrayList<ArrayList<String>>

            if (foundNotes.isNotEmpty()) {
                println("""
                      Search : '$keyword'
                    ==================
                """.trimIndent())

                foundNotes.forEachIndexed { index, sublist ->
                    println("${index + 1}: ${sublist[0]}")
                }

                println("==================")
                print(">> "); var searchInput = readln()

                if (searchInput == "clear"){
                    isSearching = false
                }

                if (searchInput.contains("open", true)){
                    openNotes(searchInput, foundNotes)
                }
            }
            else {
                println("No notes found containing '$keyword'.")
                print("Press ENTER to continue...")
                readLine()
            }
        }
    }
}
var encrypted = false
fun openNotes(input: String, arr: ArrayList<ArrayList<String>>) {
    var isOpeningNotes = true

    while (isOpeningNotes){
        var splittedWord = input.split(" ")
        var idx = splittedWord[1].toInt()

        var selectednote = arr[idx-1]

        println("""
            ${selectednote[0]}
            ==============
        """.trimIndent())
        for (i in selectednote){
            if (i == selectednote[0]){

            }else{
                println(i)
            }
        }
        println("==============")
        print(">> "); var input = readln()

        if (input == "back"){ //exit
            isOpeningNotes = false
        }

        if (input == "delete"){
            val iter = arrNotes.iterator()
            while (iter.hasNext()) {
                val sublist = iter.next()
                if (sublist == selectednote) {
                    iter.remove()
                    break
                }
            }
            isOpeningNotes = false
        }


        if (input == "encrypt"){
            encrypted = true
            encrypt(idx)
        }

        if (input == "decrypt"){
            if (encrypted){
                print("Enter your password : "); var passwordDecrypt = readln()
                if (password == passwordDecrypt){
                    decrypt(idx)
                    encrypted = false
                }
                else{
                    println("Wrong Password!")
                    print("Press ENTER to continue..."); readln()
                }
            }
            else{
                println("There is nothing to decrypt..")
                print("Press ENTER to continue..."); readln()
            }
        }
    }
}

fun addNote(judul: String, isi: ArrayList<String>): Unit {
    val size = arrNotes.size + 1
    val temp = arrayListOf<String>()
    var fixedJudul = judul + size.toString()

    if (judul.contains("Untitled", true)){
        fixedJudul = judul + size.toString()
    }
    else{
        fixedJudul = judul
    }

    temp.add(fixedJudul)
    temp.addAll(isi)

    arrNotes.add(temp)
    println(arrNotes)

}

fun encrypt(idx: Int) {
    var selectednote = arrNotes[idx - 1]
    var temp = arrayListOf<String>()
    for (i in selectednote) { //iterate every item in selected note
        if (i != selectednote[0]) { // check it's a title or not (bcs title is always on index 0)
            val shifted = StringBuilder() // Use StringBuilder for efficiency
            for (char in i) { // iterate every char in this item
                val ascii = char.code
                val shiftedAscii = when {
                    char.isLowerCase() -> ((ascii - 'a'.code + 5) % 26 + 'a'.code).toChar()
                    char.isUpperCase() -> ((ascii - 'A'.code + 5) % 26 + 'A'.code).toChar()
                    else -> char
                }
                shifted.append(shiftedAscii) // Append to StringBuilder
            }
            temp.add(shifted.toString()) // Convert StringBuilder to String and add to temp list
        } else {
            temp.add(i) // Add the title as it is
        }
    }
    arrNotes[idx - 1] = temp
    println("Encrypt success!");
    print("Press ENTER to continue..."); readln()
}

fun decrypt(idx: Int) {
    var selectednote = arrNotes[idx - 1]
    var temp = arrayListOf<String>()
    for (i in selectednote) { // iterate every item in selected note
        if (i != selectednote[0]) { // check if it's a title or not (because the title is always at index 0)
            val decrypted = StringBuilder() // Use StringBuilder for efficiency
            for (char in i) { // iterate every char in this item
                val ascii = char.code
                val decryptedAscii = when {
                    char.isLowerCase() -> ((ascii - 'a'.code - 5 + 26) % 26 + 'a'.code).toChar()
                    char.isUpperCase() -> ((ascii - 'A'.code - 5 + 26) % 26 + 'A'.code).toChar()
                    else -> char
                }
                decrypted.append(decryptedAscii) // Append to StringBuilder
            }
            temp.add(decrypted.toString()) // Convert StringBuilder to String and add to temp list
        } else {
            temp.add(i) // Add the title as it is
        }
    }
    arrNotes[idx - 1] = temp
    println("Decrypt success!"); readln()
    print("Press ENTER to continue..."); readln()
}


//fun main() {
//    val originalText = "a"
//    val shiftedText = shiftAlphabetBy5(originalText)
//    println("Original: $originalText")
//    println("Shifted:  $shiftedText")
//}
