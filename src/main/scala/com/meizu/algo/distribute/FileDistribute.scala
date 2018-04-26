package com.meizu.algo.distribute
import org.apache.spark.SparkFiles

import scala.io.Source

/*
dxp: 测试--files提交的文件及 SparkFiles获取文件名及该文件读取
同时，测试mapPartitions时执行任务的ip。
对于分布式环境的executor的调试信息，目前想到的是在map等算子中所调试信息作为数据，通过collect方式收集到driver,并进行打印。
本次测试就是采用这种方法.暂不清楚executor的日志怎么收集到driver或hdfs进行查看.
 */

object  FileDistribute{
  def main(args: Array[String]): Unit = {
    val sparkEnv = new SparkEnv("file-distribute")
    println(MachineInfo.getIP())

    val fileName:String = args(0)

    val testRdd = sparkEnv.sc.parallelize(1 to 12,4)
    val c = testRdd.mapPartitions(it=>{
      val disFile =  SparkFiles.get(fileName)
      val lines:Array[String] ={
        try{
          Source.fromFile(disFile, "utf8").getLines().toArray
        }catch {
          case e:Exception => Array.empty[String]
        }
      }
      it.map(r=>{
        if(lines.length > 0){
          val ip:String = MachineInfo.getIP()
          r.toString + "****" +ip +  "****" + lines(0)
        }else r.toString
      })
    })

    c.collect().foreach(println)

  }

}