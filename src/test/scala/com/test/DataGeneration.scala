package com.test

import java.io.File
import org.scalatest.FunSuite
import scala.collection.mutable.ListBuffer
import scala.io.Source.fromFile

class DataGeneration extends FunSuite{

  test("List should have elements of type Transaction") {
    val generateData = new GenerateData("/home/alaa/IdeaProjects/carrefour/data/test")
    val path = "/home/alaa/IdeaProjects/carrefour/src/main/resources/"
    val inputTransactionsFiles:ListBuffer[Transaction] = new ListBuffer[Transaction]()
    generateData.generateTransactions("2019","07",10, 16, ListBuffer("g5o4pkwb-prkk-sg4d-clsa-nrqsgdmdry6n", "ryh5spkf-la8x-jiwm-gqbf-hcsuts5nadlx"))
    val folder = new File("/home/alaa/IdeaProjects/carrefour/data/test/input/transcations")
    if (folder.exists && folder.isDirectory)
      assert(folder.listFiles.toList.size == 7)

  }

  test("Test Top Cent vente") {
    val path = "/home/alaa/IdeaProjects/carrefour/data/test"
    val topCentVent = new TopCentVente
    val res:ListBuffer[Transaction]= ListBuffer(Transaction("20190710T000612+0100", "ryh5spkf-la8x-jiwm-gqbf-hcsuts5nadlx",4, 62), Transaction("20190710T014224+0100", "g5o4pkwb-prkk-sg4d-clsa-nrqsgdmdry6n",3, 14))
    topCentVent.inputFile(res, path)
    val txFile = fromFile(path + "/output/top_100_ventes_g5o4pkwb-prkk-sg4d-clsa-nrqsgdmdry6n_20190710.data")
    val actualNbElm = txFile.getLines().map(line => {
      assert(line.split("\\|").size == 2)
    })

  }

}
