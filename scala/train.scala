:load scala/histogramGenerator.scala

import java.io.File
import javax.imageio.ImageIO
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.classification.{SVMWithSGD, SVMModel}

val posFileNames = sc.textFile("training_data/positiveExamples")

val pos = posFileNames map (
	(fileName:String) => {
		val file:File = new File(fileName)
		val image:Image = (new Image(ImageIO.read(file))).toBW()
		new LabeledPoint(1.0, imageToHistogram(image)) 
	}
)

val negFileNames = sc.textFile("training_data/negativeExamples")

val neg = (negFileNames flatMap (
	(fileName:String) => {
		val file:File = new File(fileName)
		val image:Image = (new Image(ImageIO.read(file))).toBW()
		val width = image.img.getWidth()
		val xs = Array(scala.util.Random.nextInt(width - 80), scala.util.Random.nextInt(width - 80), scala.util.Random.nextInt(width - 80), scala.util.Random.nextInt(width - 80))
		val images = xs map (x => image.subImage(x, image.img.getHeight() - 175, 80, 160))
		val data = images map (i => imageToHistogram(i))
		data map (x => new LabeledPoint(0.0, x))
	}
)).cache()

val posSample = pos.sample(false, 1200.0 / pos.count).cache()
val data = (posSample ++ neg).cache()


val model = SVMWithSGD.train(data, 100)
model.clearThreshold()


def test(m:SVMModel):Double = {
	val n1 = (posSample filter (x => m.predict(x.features) > 0.0)).count
	val n2 = (neg filter (x => m.predict(x.features) < 0.0)).count
	(n1 + n2) * 1.0 / (posSample.count + neg.count)
}
