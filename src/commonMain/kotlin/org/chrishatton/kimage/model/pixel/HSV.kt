package org.chrishatton.kimage.model.pixel

/**
 * HSV using the range 0 <= 1.
 * Values outside this range will throw an exception.
 */
class HSV(
    val hue        : Double,
    val saturation : Double,
    val value      : Double
) : Pixel {

    init {
        require(hue        in range)
        require(saturation in range)
        require(value      in range)
    }

    constructor(grey: Double) : this(
        hue        = 0.0,
        saturation = 0.0,
        value      = grey
    )

    companion object {
        protected val range : ClosedRange<Double> = 0.0 .. 1.0

        val Black : HSV = HSV(hue = 0.0, saturation = 0.0, value = 0.0)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HSV) return false

        if (hue != other.hue) return false
        if (saturation != other.saturation) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hue.hashCode()
        result = 31 * result + saturation.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }
}

