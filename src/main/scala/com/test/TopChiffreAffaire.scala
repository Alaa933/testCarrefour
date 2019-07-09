package com.test

import java.io.{BufferedWriter, File, FileWriter}

import scala.collection.mutable.ListBuffer
import scala.io.Source

class TopChiffreAffaire {


  def chiffreAffaireParMagasin(path:String, inputTransactionsFiles:ListBuffer[Transaction]): Unit ={
    val path_input = getClass.getResource("/data/input/magasins")
    var chiffreAffaireParMagasin =0
    var list7DerniersJourS: ListBuffer[ChiffreDaffaire]=ListBuffer[ChiffreDaffaire]()


    val folder = new File(path_input.getPath)
    if (folder.exists && folder.isDirectory)
      folder.listFiles
        .toList
        .foreach(file => {
          var sum =0
          var listParMagasinParDate = inputTransactionsFiles.filter(_.datetime.slice(0,8).equals(file.getName.split("_")(2).slice(0,8))).filter(_.magasin.equals(file.getName.split("_")(1).substring(5)))
                 Source.fromFile(file.getAbsolutePath).getLines().foreach(line => {
                 listParMagasinParDate.filter(_.produit ==line.split("\\|")(0).toInt).groupBy(_.produit).foreach(x=>{
                  x._2.foreach(x=>{
                    sum += x.qte*line.split("\\|")(1).toInt
                  })
                  list7DerniersJourS.append(ChiffreDaffaire(file.getName.split("_")(2).slice(0,8),file.getName.split("_")(1).substring(5),x._1, sum))

                })
                })
        })

    list7DerniersJourS.groupBy(_.magasin).foreach(x=>{
      val file = new File(String.format(path+"/output/" + "top_100_ca_%s.data",x._1))
      val bw = new BufferedWriter(new FileWriter(file))
       x._2.groupBy(_.produit).foreach(t=>{
        t._2.foreach(z=> chiffreAffaireParMagasin+=z.chiffre)
        bw.write(t._1 + "|" + chiffreAffaireParMagasin.toString + "\n")
      })
      bw.close()
    })
  }

}
