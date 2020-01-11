package org.chrishatton.kimage.model.pixel

import kotlin.math.min

/**
 * RGB colour where each channel is expressed in the range 0 <= 1.
 * Values outside this range will throw an error.
 */
class RGBA(
    red   : Double,
    green : Double,
    blue  : Double,
    val alpha : Double
) : RGB(red,green,blue) {

    init {
        require(alpha in range)
    }

    /**
     * Integer constructor, accepting the range 0 <= 255.
     */
    constructor(
            red   : Int,
            green : Int,
            blue  : Int,
            alpha : Int
    ) : this( red/255.0, green/255.0, blue/255.0, alpha/255.0 )

    constructor(grey: Double, alpha : Double = 1.0) : this(red = grey, green = grey, blue = grey, alpha = alpha)
    constructor(grey: Int   , alpha : Int    = 255) : this(red = grey, green = grey, blue = grey, alpha = alpha)

    companion object {
        val black : RGB = RGBA(0.0, 0.0, 0.0, 1.0)
        val white : RGB = RGBA(1.0, 1.0, 1.0, 1.0)
    }

    val aInt : Int get() = (alpha * 255.0).toInt()

    infix operator fun minus( other: RGBA) = RGBA(
            red   = (red   - other.red  ).coerceIn(range),
            green = (green - other.green).coerceIn(range),
            blue  = (blue  - other.blue ).coerceIn(range),
            alpha = alpha
    )

    infix operator fun plus( other: RGBA) = RGBA(
        red   = (red   + other.red   ).coerceIn(range),
        green = (green + other.green ).coerceIn(range),
        blue  = (blue  + other.blue  ).coerceIn(range),
        alpha = alpha
    )

    infix operator fun times( other: RGBA) = RGBA(
        red   = (red   * other.red  ).coerceIn(range),
        green = (green * other.green).coerceIn(range),
        blue  = (blue  * other.blue ).coerceIn(range),
        alpha = alpha
    )

    infix operator fun div( other: RGBA) = RGBA(
        red   = (red   / other.red  ).coerceIn(range),
        green = (green / other.green).coerceIn(range),
        blue  = (blue  / other.blue ).coerceIn(range),
        alpha = alpha
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RGBA) return false
        if (!super.equals(other)) return false

        if (alpha != other.alpha) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + alpha.hashCode()
        return result
    }
}