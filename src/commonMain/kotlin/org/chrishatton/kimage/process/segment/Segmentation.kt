package kimage.process.segment

import org.chrishatton.kimage.model.Image
import org.chrishatton.kimage.model.KImage
import org.chrishatton.kimage.model.pixel.Pixel
import org.chrishatton.kimage.model.Point
import org.chrishatton.kimage.model.segment.Segel
import org.chrishatton.kimage.model.segment.Segment

typealias Segmenter<PixelType,T> = (image: Image<PixelType>, point: Point)-> Segment<T>

fun <PixelType: Pixel,T> Image<PixelType>.segmentBy(segmenter: Segmenter<PixelType, T>) : KImage<Segel<T>> {
    return this.map { _, point -> Segel<T>(point) }.apply {
        forEach { segel,_ -> segel.image = this }
        forEach { segel,_ -> segel.segment = segmenter( this@segmentBy, segel.point ) }
    }
}

typealias NeighbourSegmenter<PixelType,T> = (image: Image<PixelType>, point: Point, neighbours: Set<Segment<T>> )-> Segment<T>

fun <PixelType: Pixel,T> Image<PixelType>.segmentBy(segmenter: NeighbourSegmenter<PixelType, T> ) : KImage<Segel<T>> {

    val segmentCache : MutableMap<Point, Segment<T>> = HashMap()

    return this.map { _, point -> Segel<T>(point) }.apply {
        forEach { segel,_ ->
            segel.image = this
            val point = segel.point
            val neighbouringSegments = Segel.NeighbourPattern.NorthAndWest.offsets
                    .mapNotNull { offset -> segmentCache[point + offset] }
                    .toSet()

            val segment = segmenter( this@segmentBy, segel.point, neighbouringSegments )
            segmentCache.put(point,segment)
            segel.segment = segment
        }
    }
}

fun <T> Image<Segel<T>>.segments() : Set<Segment<T>> {
    return this.fold( initial = HashSet<Segment<T>>() ) { segments, pixel, _, _ ->
        pixel.segment?.let { segments.add( it ) }
        segments
    }
}
