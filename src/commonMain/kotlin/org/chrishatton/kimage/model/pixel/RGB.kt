package org.chrishatton.kimage.model.pixel

import kotlin.jvm.JvmStatic
import kotlin.math.max
import kotlin.math.min

/**
 * RGB colour where each channel is expressed in the range 0 <= 1.
 * Values outside this range will throw an error.
 */
open class RGB(
    val red   : Double,
    val green : Double,
    val blue  : Double
) : Pixel {

    init {
        require(red   in range)
        require(green in range)
        require(blue  in range)
    }

    /**
     * Integer constructor, accepting the range 0 <= 255.
     */
    constructor(
        red   : Int,
        green : Int,
        blue  : Int
    ) : this( red/255.0, green/255.0, blue/255.0 )

    constructor(grey: Double) : this(red = grey, green = grey, blue = grey)
    constructor(grey: Int   ) : this(red = grey, green = grey, blue = grey)

    companion object {
        @JvmStatic
        protected val range : ClosedRange<Double> = 0.0 .. 1.0

        val black : RGB = RGB(0.0, 0.0, 0.0)
        val white : RGB = RGB(1.0, 1.0, 1.0)
    }

    val rInt : Int get() = (red   * 255.0).toInt()
    val gInt : Int get() = (green * 255.0).toInt()
    val bInt : Int get() = (blue  * 255.0).toInt()

    infix operator fun minus( other: RGB) = RGB(
            red   = (red   - other.red  ).coerceIn(range),
            green = (green - other.green).coerceIn(range),
            blue  = (blue  - other.blue ).coerceIn(range)
    )

    infix operator fun plus( other: RGB) = RGB(
            red   = (red   + other.red   ).coerceIn(range),
            green = (green + other.green ).coerceIn(range),
            blue  = (blue  + other.blue  ).coerceIn(range)
    )

    infix operator fun times( other: RGB) = RGB(
            red   = (red   * other.red  ).coerceIn(range),
            green = (green * other.green).coerceIn(range),
            blue  = (blue  * other.blue ).coerceIn(range)
    )

    infix operator fun div( other: RGB) = RGB(
            red   = (red   / other.red  ).coerceIn(range),
            green = (green / other.green).coerceIn(range),
            blue  = (blue  / other.blue ).coerceIn(range)
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RGB) return false

        if (red != other.red) return false
        if (green != other.green) return false
        if (blue != other.blue) return false

        return true
    }

    override fun hashCode(): Int {
        var result = red.hashCode()
        result = 31 * result + green.hashCode()
        result = 31 * result + blue.hashCode()
        return result
    }
}