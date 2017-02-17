import org.apache.log4j.{Logger, Level}
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.Vectors

/**
  * Created by Manikanta on 1/31/2017.
  */
object kMeansClustering {

  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir","C:\\Users\\sidra\\Documents\\hadoop");

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc=new SparkContext(sparkConf)

    // Turn off Info Logger for Consolexxx
    Logger.getLogger("org").setLevel(Level.OFF);
    Logger.getLogger("akka").setLevel(Level.OFF);
    // Load and parse the data
    val data1 = sc.textFile("data/KMean.txt")
    val parsed_Data = data1.map(s => Vectors.dense(s.split(' ').map(_.toDouble))).cache()

    //Look at how training data is!
    parsed_Data.foreach(f=>println(f))

    // Cluster the data into two classes using KMeans
    val numClusters = 2
    val numIterations = 20
    val clusters = KMeans.train(parsed_Data, numClusters, numIterations)

    // Evaluate clustering by computing Within Set Sum of Squared Errors
    val WSSSE = clusters.computeCost(parsed_Data)
    println("Within Set Sum of Squared Errors = " + WSSSE)

    //Look at how the clusters are in training data by making predictions
    println("Clustering on training data: ")
    clusters.predict(parsed_Data).zip(parsed_Data).foreach(f=>println(f._2,f._1))

    // Save and load model
    clusters.save(sc, "data/KMeansModel")
    val same_Model = KMeansModel.load(sc, "data/KMeansModel")


  }


}