package com.github.coneys.coroutineLocation.state

import android.location.Location
import android.os.Build
import kotlinx.serialization.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializer(forClass = Location::class)
object LocationSerializer : KSerializer<Location> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("LocationSerializer") {
        element("provider", String.serializer().descriptor)
        element("time", Long.serializer().descriptor)
        element("elapsedRealtimeNanos", Long.serializer().descriptor)
        element("latitude", Double.serializer().descriptor)
        element("longitude", Double.serializer().descriptor)
        element("altitude", Double.serializer().descriptor)
        element("speed", Float.serializer().descriptor)
        element("bearing", Float.serializer().descriptor)

        element("verticalAccuracyMeters", Float.serializer().descriptor, isOptional = true)
        element("speedAccuracyMetersPerSecond", Float.serializer().descriptor, isOptional = true)
        element("bearingAccuracyDegrees", Float.serializer().descriptor, isOptional = true)

        element(
            "elapsedRealtimeUncertaintyNanos",
            Double.serializer().descriptor,
            isOptional = true
        )
    }

    override fun deserialize(decoder: Decoder): Location {
        val dec = decoder.beginStructure(descriptor)
        return Location("").apply {
            loop@ while (true) {
                when (val i = dec.decodeElementIndex(descriptor)) {
                    CompositeDecoder.DECODE_DONE -> break@loop
                    0 -> provider = dec.decodeStringElement(descriptor, i)
                    1 -> time = dec.decodeLongElement(descriptor, i)
                    2 -> elapsedRealtimeNanos = dec.decodeLongElement(descriptor, i)
                    3 -> latitude = dec.decodeDoubleElement(descriptor, i)
                    4 -> longitude = dec.decodeDoubleElement(descriptor, i)
                    5 -> altitude = dec.decodeDoubleElement(descriptor, i)
                    6 -> speed = dec.decodeFloatElement(descriptor, i)
                    7 -> bearing = dec.decodeFloatElement(descriptor, i)
                    8 -> verticalAccuracyMeters = dec.decodeFloatElement(descriptor, i)
                    9 -> speedAccuracyMetersPerSecond = dec.decodeFloatElement(descriptor, i)
                    10 -> bearingAccuracyDegrees = dec.decodeFloatElement(descriptor, i)
                    11 -> elapsedRealtimeUncertaintyNanos = dec.decodeDoubleElement(descriptor, i)
                    else -> throw SerializationException("Unknown index $i")
                }
            }
            dec.endStructure(descriptor)
        }
    }

    override fun serialize(encoder: Encoder, value: Location) {
        value.apply {
            encoder.beginStructure(descriptor).also {
                var index = 0
                it.encodeStringElement(descriptor, index++, provider)
                it.encodeLongElement(descriptor, index++, time)
                it.encodeLongElement(descriptor, index++, elapsedRealtimeNanos)
                it.encodeDoubleElement(descriptor, index++, latitude)
                it.encodeDoubleElement(descriptor, index++, longitude)
                it.encodeDoubleElement(descriptor, index++, altitude)
                it.encodeFloatElement(descriptor, index++, speed)
                it.encodeFloatElement(descriptor, index++, bearing)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    it.encodeFloatElement(descriptor, index++, verticalAccuracyMeters)
                    it.encodeFloatElement(descriptor, index++, speedAccuracyMetersPerSecond)
                    it.encodeFloatElement(descriptor, index++, bearingAccuracyDegrees)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    it.encodeDoubleElement(descriptor, index, elapsedRealtimeUncertaintyNanos)
                }
                it.endStructure(descriptor)
            }
        }
    }
}