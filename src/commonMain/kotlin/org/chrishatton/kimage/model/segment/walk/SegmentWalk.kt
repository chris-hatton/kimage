package org.chrishatton.kimage.model.segment.walk

import org.chrishatton.kimage.model.segment.Segment

open class SegmentWalk<T> {

    internal var path: MutableList<Segment<T>> = mutableListOf<Segment<T>>()

    fun didVisit(segment: Segment<T>) {
        path.add(segment)
    }
}
