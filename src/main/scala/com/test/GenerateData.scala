package com.test

import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.mutable.ListBuffer

class GenerateData(path: String){

  val r = new scala.util.Random
  val nbr_transactions: Int = 4
  val nbr_produit : Int = 4
  val qté_max : Int = 100
  val prix_max = 500

  /** generate the transactions data
    *
    * @param year The year of data generation
    * @param month the month of data generation
    * @param firstDay  the first day of data generation
    * @param lastDay the last day of data generation
    * @param listDesMagasins  the list of stores ids
    */
  def generateTransactions(year:String, month:String,firstDay:Int, lastDay:Int,listDesMagasins:ListBuffer[String]): Unit = {
    for(day <- firstDay to lastDay){
      var date:String = year+ month +day.toString
      val file = new File(String.format(path+"/input/transactions/transactions_%s.data", date))
      val bw = new BufferedWriter(new FileWriter(file))
      for (i <- 1 to nbr_transactions) {
        bw.write(i
          + "|" + date
          +'T'+r.nextInt(2)+r.nextInt(2)+r.nextInt(5)+r.nextInt(9)+r.nextInt(5)+r.nextInt(9) + "+0100"
          + "|" + listDesMagasins(r.nextInt(listDesMagasins.length))
          + "|" + (r.nextInt(nbr_produit)+1)
          + "|" + r.nextInt(qté_max) + "\n")
      }
      bw.close()
    }
  }

  /** generate the references data
    *
    * @param year The year of data generation
    * @param month the month of data generation
    * @param firstDay  the first day of data generation
    * @param lastDay the last day of data generation
    * @param listDesMagasins  the list of stores ids
    */
  def generateReferences(year:String, month:String,firstDay:Int, lastDay:Int, listDesMagasins:ListBuffer[String]): Unit = {
    for(day <- firstDay to lastDay){
      var date:String = year+ month +day.toString
      for(magasin <- listDesMagasins){
        val file = new File(String.format(path +"/input/magasins/reference_prod-%s_%s.data", magasin, date))
        val bw = new BufferedWriter(new FileWriter(file))
        for (i <- 1 to nbr_produit) {
          bw.write(i+ "|" + r.nextInt(prix_max) + "\n")
        }
        bw.close()
      }
    }
  }
}
