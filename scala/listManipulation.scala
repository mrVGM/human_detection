:load scala/rectangle.scala

def add(l:List[Rectangle], r:Rectangle):List[Rectangle] = {
	if (l.length == 0) {
		return List(r)
	}
	else {
		for (i <- l) {
			if ((i intersection r).area > r.area / 2) {
				return l
			}
		}
		return r :: l
	}
}

def addAll(l1:List[Rectangle], l2:List[Rectangle]):List[Rectangle] = {
	var res = l1
	for (r <- l2) {
		res = add(res, r)
	}
	res
} 
