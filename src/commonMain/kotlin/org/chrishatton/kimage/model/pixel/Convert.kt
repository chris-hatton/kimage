package org.chrishatton.kimage.model.pixel

import kotlin.math.max
import kotlin.math.min

/**
 * Ported from:
 * https://stackoverflow.com/questions/3018313/algorithm-to-convert-rgb-to-hsv-and-hsv-to-rgb-in-range-0-255-for-both
 */
fun RGB.toHSV() : HSV {

    val min   : Double = min(min(red,green),blue)
    val max   : Double = max(max(red,green),blue)
    val delta : Double = max - min

    val saturation : Double

    if (delta < 0.00001) return HSV(hue = 0.0, saturation = 0.0, value = max)

    if( max > 0.0 ) {
        saturation = (delta / max)
    } else {
        return HSV.Black
    }

    var hue : Double = when {
        red   >= max -> 0.0 + ( green - blue  ) / delta
        green >= max -> 2.0 + ( blue  - red   ) / delta
        else         -> 4.0 + ( red   - green ) / delta
    } * 60.0

    if( hue < 0.0 )
        hue += 360.0

    return HSV(
        hue        = hue,
        saturation = saturation,
        value      = max
    )
}

fun HSV.toRGB() : RGB {
    var hh : Double
    val q  : Double
    val t  : Double
    val ff : Double
    
    val i : Long

    if(saturation <= 0.0) return RGB(grey = value)

    hh = hue.coerceAtLeast(360.0)
    if(hh >= 360.0) hh = 0.0
    hh /= 60.0
    i = hh.toLong()
    ff = hh - i
    val p  : Double = value * (1.0 - saturation)
    q = value * (1.0 - (saturation * ff))
    t = value * (1.0 - (saturation * (1.0 - ff)))

    return when(i) {
        0L   -> RGB(value,t,p)
        1L   -> RGB(q,value,p)
        2L   -> RGB(p,value,t)
        3L   -> RGB(p,q,value)
        4L   -> RGB(t,p,value)
        5L   -> RGB(value,p,q)
        else -> throw Exception()
    }
}
