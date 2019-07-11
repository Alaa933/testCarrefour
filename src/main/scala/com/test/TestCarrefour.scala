package com.test


import java.io.File
import scala.collection.mutable.ListBuffer
import scala.io.Source


case class Transaction(datetime: String,magasin:String,produit: Int,qte:Int)
case class ChiffreDaffaire( datetime: String, magasin:String, produit: Int, chiffre: Int)


object test{


  val path = "/home/alaa/IdeaProjects/carrefour/src/main/resources/data"
  val inputTransactionsFiles:ListBuffer[Transaction] = new ListBuffer[Transaction]()
  var listDesMagasins : ListBuffer[String] = ListBuffer[String]()
  val r = new scala.util.Random
  val nbr_magasins: Int = 2


  /**     readTransactionsFiles read the transactions folder
    *
    * @param inputTransactionsFiles  list to hold the data
    * @param path path to the transactions folder
    * @return the list inputTransactionsFiles which holds data as a collection of Transaction
    */
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

  /**method main
    *
    * @param args
    */
  def main(args:Array[String]):Unit ={


    for(i<-1 to nbr_magasins) {
      listDesMagasins.append((r.alphanumeric.take(8).mkString + "-" +
        r.alphanumeric.take(4).mkString + "-" +
        r.alphanumeric.take(4).mkString + "-" +
        r.alphanumeric.take(4).mkString + "-" +
        r.alphanumeric.take(12).mkString)
        .toLowerCase)
    }

    val generateData = new GenerateData()
    generateData.generateTransactions("2019","07",10, 16, listDesMagasins)
    generateData.generateReferences("2019","07",10, 16, listDesMagasins)

    val topCentVente = new TopCentVente
    topCentVente.inputFile(readTransactionFiles(inputTransactionsFiles, path), path)

    val topChiffreAffaire = new TopChiffreAffaire
    topChiffreAffaire.chiffreAffaireParMagasin(path, readTransactionFiles(inputTransactionsFiles,path))


  }

}
