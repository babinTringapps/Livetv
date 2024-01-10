package com.example.livetv

import android.media.MediaCodecInfo
import android.os.Build

fun List<MediaCodecInfo>.toPrintable() = joinToString(separator = "\n\n") { it.toPrintable() }

fun MediaCodecInfo?.toPrintable(): String {
    return if (this == null)
        "Unknown (Codec object is null)"
    else
        "${if (isEncoder) "Encoder" else "Decoder"}: ${name}\n\n" +
                "Supported Mime Types - [${supportedTypes.joinToString()}], " +
                "Hardware Accelerated - [${isHardwareAcceleratedCodec()}], " +
                "Software Only - [${getPrintableIsSoftwareOnly()}], " +
                "From Vendor - [${isFromVendor()}], " +
                "Is Alias - [${getPrintableAlias()}]\n" +
                supportedTypes.joinToString { getCapabilitiesForType(it).toPrintable() }

}

fun MediaCodecInfo.CodecCapabilities?.toPrintable() =
    "${this?.mimeType} - [" +
            "Max Instances - ${
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    "${this?.maxSupportedInstances}"
                } else {
                    "Unknown"
                }
            }, " +
            "Is Encoder - ${this?.encoderCapabilities != null}, " +
            "Is Audio - ${this?.audioCapabilities != null}, " +
            "Is Video - ${this?.videoCapabilities != null}]"

private fun MediaCodecInfo.isHardwareAcceleratedCodec() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        if (isHardwareAccelerated) "Yes" else "No"
    } else {
        "Unknown"
    }

private fun MediaCodecInfo.getPrintableIsSoftwareOnly() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        if (isSoftwareOnly) "Yes" else "No"
    } else {
        "Unknown"
    }

private fun MediaCodecInfo.getPrintableCanonicalName() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.canonicalName
    } else {
        "Unknown"
    }

private fun MediaCodecInfo.isFromVendor() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        if (this.isVendor) "Yes" else "No"
    } else {
        "Unknown"
    }


private fun MediaCodecInfo.getPrintableAlias() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        if (this.isAlias) "Yes" else "No"
    } else {
        "Unknown"
    }
