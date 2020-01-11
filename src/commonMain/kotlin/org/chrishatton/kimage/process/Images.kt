package org.chrishatton.kimage.process

import org.chrishatton.kimage.model.Image
import org.chrishatton.kimage.model.pixel.HSV
import org.chrishatton.kimage.model.pixel.RGB
import org.chrishatton.kimage.model.pixel.toHSV
import org.chrishatton.kimage.model.pixel.toRGB

fun Image<RGB>.toHSV() : Image<HSV> = this.map { rgb, _ -> rgb.toHSV() }
fun Image<HSV>.toRGB() : Image<RGB> = this.map { hsv, _ -> hsv.toRGB() }
