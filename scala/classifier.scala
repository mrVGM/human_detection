:load scala/histogramGenerator.scala
:load scala/listManipulation.scala

import org.apache.spark.mllib.classification.SVMModel

val svm:String = "scala/SVM2"

object Classifier {
	val model = SVMModel.load(sc, svm)

	def check(window:Rectangle, image:Image) = {
		val img:Image = image.subImage(window.x, window.y, window.width, window.height).scale(80, 160).toBW()
		val histogram = imageToHistogram(img)

		if (model.predict(histogram) > 0.0) {
			true
		}
		else {
			false
		}
		
	}

	def split(a:Int, b:Int, w:Int, eps:Int):((Int, Int), (Int, Int)) = {
		if (b - a < w + 2 * eps) {
			((a, a + w), (b - w, b))
		}
		else {
			val h = w - eps + (b - a - w + eps) / 2
			return ((a, a + h), (b - h, b))
		}
	}

	def search(area:Rectangle, window:Rectangle, eps:Int, image:Image):List[Rectangle] = {
		if (area.width == window.width) {
			if (area.height == window.height) {
				if (check(area, image)) {
					return List(area)
				}
				else {
					return Nil
				}
			}
			val tmp = split(area.y, area.y + area.height, window.height, eps)
			val a1 = new Rectangle(area.x, tmp._1._1, area.width, tmp._1._2 - tmp._1._1)
			val a2 = new Rectangle(area.x, tmp._2._1, area.width, tmp._2._2 - tmp._2._1)
			return addAll (search(a1, window, eps, image), search(a2, window, eps, image))
		}
		else {
			val tmp = split(area.x, area.x + area.width, window.width, eps)
			val a1 = new Rectangle(tmp._1._1, area.y, tmp._1._2 - tmp._1._1, area.height)
			val a2 = new Rectangle(tmp._2._1, area.y, tmp._2._2 - tmp._2._1, area.height)
			return addAll (search(a1, window, eps, image), search(a2, window, eps, image))
		}
	}
}