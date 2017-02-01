import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by sidra 2/1/2017
  */
object SparkTransformation {


  def main(args: Array[String]): Unit = {


    System.setProperty("hadoop.home.dir", "C:\\Users\\sidra\\Documents\\hadoop");

    val sparkConf = new SparkConf().setAppName("SparkTransformation").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    val input=sc.textFile("input")
    //Here each line in the input file will be checked and splitted on the basis of whitespaces and punctuation mark
    //Here we select distinct words to avoid repetition

    val wc=input.flatMap((line=>{line.split("\\W").filter(_.nonEmpty)})).distinct()

    //here filter is applied so the words only having length 9 will be saved in main_filter
    val main_filter = (wc.filter(e => e.trim.length==9)).map(word=>(word,1)).cache()
   //Return all the elements of the input file  as an array at the driver program
    main_filter.collect()
    //Write the elements of the input file as a text file (or set of text files) in a given directory in the local filesystem
    main_filter.saveAsTextFile("output")

    main_filter.foreach(println(_))
  }

}
