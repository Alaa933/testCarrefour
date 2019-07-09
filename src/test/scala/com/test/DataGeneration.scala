package com.test

import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer
import com.test.test._
import scala.io.Source.fromFile


class DataGeneration extends FunSuite{

  test("List should have elements of type Transaction") {

    val path = "/home/alaa/IdeaProjects/carrefour/src/main/resources/"
    val inputTransactionsFiles:ListBuffer[Transaction] = new ListBuffer[Transaction]()

   val res= readTransactionFiles(inputTransactionsFiles, path)
    assert(res.size ==300000)
    assert(res(0) == Transaction("20180613T11629","hods994v-f70u-szxw-lpq3-k8kfm9ag1klj",13589,80))
  }

  test("Test Top Cent vente") {
    val path = "/home/alaa/IdeaProjects/carrefour/src/main/resources/data"
    val topCentVent = new TopCentVente
    topCentVent.inputFile(readTransactionFiles(inputTransactionsFiles, path), path)
    val txFile = fromFile(path + "/input/magasins/reference_prod-0fzcv2ou-goat-wa06-ppen-y2nf7qr7jgn6_20180610.data")
    val actualNbElm = txFile.getLines().map(line => line.split("\\|")).toList.head
    val expectedNbElm = 2
    val col_1 = actualNbElm(0).toInt
    val col_2 = actualNbElm(1).toInt
    assert(actualNbElm.length == expectedNbElm)
    assert(col_1 == 1)
    assert(col_2 == 122)


  }
  test("Test Top chiffre d'affaire") {
    val path = "/home/alaa/IdeaProjects/carrefour/src/main/resources/data"
    val topChiffreAffaire = new TopChiffreAffaire
    topChiffreAffaire.chiffreAffaireParMagasin(path, readTransactionFiles(inputTransactionsFiles, path))
    val txFile = fromFile(path + "/output/5c81g29x-8zs2-8f8u-6c3c-h34zk8hv6w5g.data")
    val actualNbElm = txFile.getLines().map(line => line.split("\\|")).toList.head
    val expectedNbElm = 2
    val col_1 = actualNbElm(0).toInt
    val col_2 = actualNbElm(1).toInt
    assert(actualNbElm.length == expectedNbElm)
    assert(col_1 == 2)
    assert(col_2 == 239856)


  }
}
