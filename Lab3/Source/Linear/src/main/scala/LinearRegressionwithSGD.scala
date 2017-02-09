import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.regression.LinearRegressionModel
import org.apache.spark.mllib.regression.LinearRegressionWithSGD
//import org.apache.log4j.{Level, Logger}
/**
  * Created by Sidrah on 2/8/2017.
  */
object LinearRegressionwithSGD {

  def main(args: Array[String]): Unit ={


    System.setProperty("hadoop.home.dir","C:\\Users\\sidra\\Documents\\hadoop");

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc=new SparkContext(sparkConf)

    // Turn off Info Logger for Console
    Logger.getLogger("org").setLevel(Level.OFF);
    Logger.getLogger("akka").setLevel(Level.OFF);


    val data_1 = sc.textFile("data\\LR.data")
    val parsed_Data = data_1.map { line =>
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    }.cache()

    parsed_Data.take(1).foreach(f=>println(f))

    // Split data into training (80%) and test (20%).
    val Array(training, test) = parsed_Data.randomSplit(Array(0.80, 0.20))

    // Building the model
    val numIterations = 100
    val stepSize = 0.00000001
    val model_LR = LinearRegressionWithSGD.train(training, numIterations, stepSize)

    // Evaluate model on training examples and compute training error
    val valuesAndPreds = training.map { point =>
      val prediction_training = model_LR.predict(point.features)
      (point.label, prediction_training)
    }
    val Mean_Square_Error = valuesAndPreds.map{ case(v, p) => math.pow((v - p), 2) }.mean()
    println("Mean Square Error of Training Data= " + Mean_Square_Error)

    // Evaluate model on test examples and compute testing error
    val valuesAndPreds2 = test.map { point =>
      val prediction_testing = model_LR.predict(point.features)
      (point.label, prediction_testing)
    }
    val Mean_Square_Error_2 = valuesAndPreds2.map{ case(v, p) => math.pow((v - p), 2) }.mean()
    println("test Mean Squared Error = " + Mean_Square_Error_2)

    // Save and load model
    model_LR.save(sc, "data\\LinearRegressionWithSGDModel")
    val same_Model = LinearRegressionModel.load(sc, "data\\LinearRegressionWithSGDModel")
  }
}
