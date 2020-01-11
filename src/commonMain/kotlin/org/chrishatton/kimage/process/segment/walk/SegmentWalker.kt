package org.chrishatton.kimage.process.segment.walk

import org.chrishatton.kimage.model.segment.Segel
import kimage.process.ProgressObserver
import org.chrishatton.kimage.model.segment.Segment
import org.chrishatton.kimage.model.segment.walk.SegmentWalkResult

class SegmentWalker<T> {
    inline fun <reified TR : SegmentWalkResult> walkAll(
            segments                  : Set<Segment<T>>,
            delegate                  : SegmentWalkDelegate<T,TR>,
            noinline progressObserver : ProgressObserver?
    ): Map<Segment<T>, TR> {
        val results = HashMap<Segment<T>, TR>()

        var walkResult: TR

        segments.forEachIndexed { index, segment ->
            progressObserver?.invoke(index.toFloat() / segments.size.toFloat())

            walkResult = walk(segment, delegate)!!

            results[segment] = walkResult
        }

        progressObserver?.invoke(1f)

        return results
    }

    inline fun <reified TR : SegmentWalkResult> walk(
            segment  : Segment<T>,
            delegate : SegmentWalkDelegate<T,TR>
    ): TR? {
        val result: TR?

        if (delegate.shouldBeginWalkAtSegment(segment)) {
            try {
                result = delegate.createResult()
            } catch (e: Exception) {
                throw RuntimeException("Unknown walk result type: " + TR::class.simpleName)
            }

            val firstStep = Step(segment, null)

            nextWalkStep(firstStep, 0, delegate)
        } else
            result = null

        return result
    }

    fun <TR> nextWalkStep(
        currentStep : Step<T>,
        walkLength  : Int,
        delegate    : SegmentWalkDelegate<T,TR>
    ) {
        var nextStep: Step<T>

        val adjacentSegments = currentStep.segment.adjacentSegments(Segel.NeighbourPattern.Cross)

        for (adjacentSegment in adjacentSegments) {
            if (adjacentSegment !== currentStep.segment) {
                nextStep = Step(adjacentSegment, currentStep)
                nextWalkStep(
                    currentStep = nextStep,
                    walkLength  = walkLength+1,
                    delegate    = delegate
                )
            }
        }
    }

    class Step<T>(internal val segment: Segment<T>, internal val previousStep: Step<T>?)
}
