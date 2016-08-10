class Rectangle(xc:Int, yc:Int, w:Int, h:Int) {
	val x = xc
	val y = yc
	val width = w
	val height = h
	val area = width * height

	def intersection(other:Rectangle):Rectangle = {
		def hInt(r1:Rectangle, r2:Rectangle):(Rectangle, Rectangle) = {
			if (r1.x > r2.x) {
				hInt(r2, r1)
			}
			else {
				if (r1.x + r1.width < r2.x) {
					(new Rectangle(0,0,0,0), new Rectangle(0,0,0,0))
				}
				else {
					var min = r1.x + r1.width
					if (min > r2.x + r2.width) {
						min = r2.x + r2.width
					}
					(new Rectangle(r2.x, r1.y, min - r2.x, r1.height), new Rectangle(r2.x, r2.y, min - r2.x, r2.height))
				}
			}
		}
		def vInt(r1:Rectangle, r2:Rectangle):(Rectangle, Rectangle) = {
			if (r1.y > r2.y) {
				vInt(r2, r1)
			}
			else {
				if (r1.y + r1.height < r2.y) {
					(new Rectangle(0,0,0,0), new Rectangle(0,0,0,0))
				}
				else {
					var min = r1.y + r1.height
					if (min > r2.y + r2.height) {
						min = r2.y + r2.height
					}
					(new Rectangle(r1.x, r2.y, r1.width, min - r2.y), new Rectangle(r2.x, r2.y, r2.width, min - r2.y))
				}
			}
		}
		val tmp = hInt(this, other)
		vInt(tmp._1, tmp._2)._1 
	}

	def == (other:Rectangle) = {
		x == other.x && y == other.y && width == other.width && height == other.height
	}

	override def toString():String = {
		"(" + x + "," + y + "," + width + "," + height + ")"
	}

}