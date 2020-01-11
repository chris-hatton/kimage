package org.chrishatton.kimage.process.segment.walk

import org.chrishatton.kimage.model.segment.Segment
import org.chrishatton.kimage.model.segment.walk.SegmentWalk

interface SegmentWalkDelegate<T,TR> {

    fun createResult() : TR

    fun shouldBeginWalkAtSegment(startSegment: Segment<T>): Boolean

    fun shouldWalkInto(currentStep: SegmentWalker.Step<T>, segment: Segment<T>): Boolean {
        return true
    }

    fun didWalkInto(segmentWalk: SegmentWalk<T>, segment: Segment<T>) {
        segmentWalk.didVisit(segment)
    }

    fun didEndWalk(segmentWalk: SegmentWalk<T>)
}
