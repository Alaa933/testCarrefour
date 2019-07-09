package com.test

import java.io.{BufferedWriter, File, FileWriter}
import java.util.Date

class GénérerInput(path:String) extends Thread{
  val nbr_transactions: Int = 4
  val nbr_magasins: Int = 2
  val nbr_produit : Int = 4
  val qté_max : Int = 100
  val prix_max = 500

  val random = new scala.util.Random


  //Générer une liste qui contient les jours dont les transactions sont éffectués
  var listdesjours : List[String]= Nil
  def generatelistdays(year:String,month: String,firstday:Int,lastday:Int): List[String] ={
    val date = new Date()

    for(i <- firstday to lastday){

      var Iso:String = year+ month +i.toString+'T'+date.getHours.toString +date.getMinutes.toString + date.getSeconds.toString
      listdesjours = listdesjours ::: List(Iso)
    }
    listdesjours
  }

  val listofdays = generatelistdays("2018","06",10,15)



  // Generate a random string of length n from the given alphabet
  def randomString(alphabet: String)(n: Int): String =
    Stream.continually(random.nextInt(alphabet.size)).map(alphabet).take(n).mkString


  // Générer une liste qui contient nbr_magasins de magasin
  var listdesmagasins : List[String] = Nil
  for(i <- 1 to nbr_magasins ) {

    var magasin_id : String =this.randomString("abcdefghijklmnopqrstuvwxyz0123456789")(8) + "-" + this.randomString("abcdefghijklmnopqrstuvwxyz0123456789")(4)+"-"+ this.randomString("abcdefghijklmnopqrstuvwxyz0123456789")(4)+"-"+this.randomString("abcdefghijklmnopqrstuvwxyz0123456789")(4)+
      "-"+ this.randomString("abcdefghijklmnopqrstuvwxyz0123456789")(12)

    listdesmagasins = listdesmagasins:::List(magasin_id)
  }

  //Générer un fichier de transaction pour chaque jour dans listofdays
  def generertransactions(listofdays: List[String], nbr_transactions:Int,listdesmagasins : List[String]): Unit = {

    for (day <- listofdays) {

      val file = new File(String.format(path+"/data/input/transactions/transactions_%s.data", day.split("T")(0))) // File name
      val bw = new BufferedWriter(new FileWriter(file)) // open writer
      for (i <- 1 to nbr_transactions) {
        bw.write(i + "|" + day + "|" + listdesmagasins(random.nextInt(listdesmagasins.length)) + "|" + (random.nextInt(nbr_produit)+1) + "|" + random.nextInt(qté_max) + "\n")
      }

      bw.close() //close writer
    }
  }

  def genererreferentielproduit(listofdays: List[String],nbr_produit: Int, listdesmagasins: List[String]) = {

    for (day <- listofdays) {

      for (magasin <- listdesmagasins) {

        val file = new File(String.format(path+"/data/input/magasins/reference_prod-%s_%s.data", magasin, day.split("T")(0)))

        val bw = new BufferedWriter(new FileWriter(file))

        for (i <- 1 to nbr_produit) {

          bw.write(i + "|" + random.nextInt(prix_max) + "\n")

        }

        bw.close()
      }
    }
  }

  override def run() {

    generertransactions(listofdays,nbr_transactions,listdesmagasins)
    genererreferentielproduit(listofdays,nbr_produit,listdesmagasins)

  }





}


