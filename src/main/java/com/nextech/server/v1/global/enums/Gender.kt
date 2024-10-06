package com.nextech.server.v1.global.enums

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

enum class Gender(val value: Int) {
    MAN(1), WOMAN(2);

    companion object {
        fun fromValue(value: Int): Gender {
            return entries.firstOrNull { it.value == value }
                ?: throw IllegalArgumentException("Invalid gender value: $value")
        }
    }
}

@Converter(autoApply = true)
class GenderConverter : AttributeConverter<Gender, Int> {
    override fun convertToDatabaseColumn(attribute: Gender?): Int? {
        return attribute?.value
    }

    override fun convertToEntityAttribute(dbData: Int?): Gender? {
        return dbData?.let { Gender.fromValue(it) }
    }
}