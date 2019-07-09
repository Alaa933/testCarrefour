package com.test

import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.mutable.ListBuffer


class TopCentVente {


  def inputFile(inputTransactionsFiles:ListBuffer[Transaction],path:String): Unit = {

    inputTransactionsFiles.groupBy(_.magasin).foreach(transaction => {
                val file = new File(String.format(path + "/output/top_100_ventes_%s_%s.data",transaction._1,transaction._2.map(_.datetime).head.toString.slice(0,8)))
                val bw = new BufferedWriter(new FileWriter(file))
                transaction._2.sortBy(_.qte)(Ordering[Int].reverse).slice(1,100).foreach(x=>{bw.write(x.produit + "|" + x.qte + "\n")
                })
                bw.close()
              })
            }





}