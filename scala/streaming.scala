import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._



val ssc = new StreamingContext(sc, Seconds(15))
val frames = ssc.socketTextStream(hostname = "localhost", port = 9999)


:load scala/classifier.scala

frames.foreach((x:RDD[String]) => {

	if (x.count() > 0) {
		val sample:String = (x.takeSample(false, 1))(0)
		val image = transform(sample)
		
		val start = System.currentTimeMillis
		val humans = Classifier.search(new Rectangle(437,221,243,226), new Rectangle(0,0,80,160), 3, image)
		val end = System.currentTimeMillis
		println("Calculation time: " + (end - start))
		for (i <- humans) {
			image.mark(i.x, i.y, i.width, i.height)
		}
		image.display()
	}

})

ssc.start()
ssc.awaitTermination()
