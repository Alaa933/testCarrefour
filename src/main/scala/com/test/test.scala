package com.test


import java.io.File

import scala.collection.mutable.ListBuffer
import scala.io.Source


case class Transaction(datetime: String,magasin:String,produit: Int,qte:Int)
case class ChiffreDaffaire( datetime: String, magasin:String, produit: Int, chiffre: Int)


object test{


  val path = "/home/alaa/IdeaProjects/carrefour/src/main/resources/data"
  val inputTransactionsFiles:ListBuffer[Transaction] = new ListBuffer[Transaction]()

  def readTransactionFiles(inputTransactionsFiles: ListBuffer[Transaction], path:String): ListBuffer[Transaction] = {
    val path = getClass.getResource("/data/input/transactions")
    val folder = new File(path.getPath)
    if (folder.exists && folder.isDirectory)
      folder.listFiles
        .toList
        .foreach(file => {
          Source.fromFile(file.getAbsoluteFile).getLines().
            foreach { line => {
              val cols = line.split("\\|")
              inputTransactionsFiles.append(Transaction(cols(1), cols(2), cols(3).toInt, cols(4).toInt))

            }
            }
        })

    inputTransactionsFiles
  }

  /**/








  def main(args:Array[String]):Unit ={

//    val t = new GénérerInput("/home/alaa/IdeaProjects/carrefour/target/classes/")
//         t.start()

    //   Thread.sleep(1000)

         val topCentVente = new TopCentVente
         topCentVente.inputFile(readTransactionFiles(inputTransactionsFiles, path), path)

         val topChiffreAffaire = new TopChiffreAffaire
         topChiffreAffaire.chiffreAffaireParMagasin(path, readTransactionFiles(inputTransactionsFiles,path))



  }


}
