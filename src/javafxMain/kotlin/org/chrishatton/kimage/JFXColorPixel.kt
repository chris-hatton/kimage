package org.chrishatton.kimage

import javafx.scene.paint.Color
import org.chrishatton.kimage.model.pixel.Pixel
import org.chrishatton.kimage.model.pixel.RGB

data class JFXColorPixel( var color: Color) : Pixel {
    constructor( rgb: RGB) : this( color = rgb.toJFXColor() )
}