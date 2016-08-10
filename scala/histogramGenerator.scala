:cp java/ImageLib/ImageLib.jar

import org.apache.spark.mllib.linalg.Vectors
import collection.JavaConverters._
import imageLib.Image
import java.awt.image.BufferedImage
import org.apache.spark.rdd.RDD


def transform(image:String):Image = {
	val rows:Array[String] = image.split(",")
	val matrix:Array[Array[Int]] = rows map (x => (x.split(" ") map (y => y.toInt) ))
	

	val tmp:BufferedImage = new BufferedImage(matrix(0).length, matrix.length, BufferedImage.TYPE_INT_RGB);
	
	for (i <- 0 until matrix.length) {
		val cur = matrix(i)
		for (j <- 0 until cur.length) {
			tmp.setRGB(j, i, cur(j))
		}
	}

	new Image(tmp)
}

def imageToHistogram(image:Image) = {
	val toCheck = image.histogram()
	Vectors.dense((toCheck.asScala.map(_.doubleValue)).toArray)
}

