package com.mmbackendassignment.mmbackendassignment.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConvertTest {

    @Test
    void checkValuesAfterConvertEntityToDtoWithSameProperties() {

        EntityProperties entity = new EntityProperties();
        entity.setValInt(1);
        entity.setValLong(1);
        entity.setValFloat(1.0f);
        entity.setValString("string");
        entity.setValShort( (short) 1 );
        entity.setValChar('m');
        entity.setValLocalDate( LocalDate.now() );

        DtoProperties dto = (DtoProperties) Convert.objects( entity, new DtoProperties() );

        assertEquals( dto.valInt, entity.getValInt() );
        assertEquals( dto.valLong, entity.getValLong() );
        assertEquals( dto.valFloat, entity.getValFloat() );
        assertEquals( dto.valString, entity.getValString() );
        assertEquals( dto.valBoolean, entity.isValBoolean() );
        assertEquals( dto.valShort, entity.getValShort() );
        assertEquals( dto.valChar, entity.getValChar() );
        assertEquals( dto.valLocalDate, entity.getValLocalDate() );

    }

    @Test
    void checkValuesAfterConvertDtoToEntityWithSameProperties() {

        DtoProperties dto = new DtoProperties();
        dto.valInt = 1;
        dto.valLong = 1;
        dto.valFloat = 1.0f;
        dto.valString = "string";
        dto.valBoolean = true;
        dto.valShort = 1;
        dto.valChar = 'm';
        dto.valLocalDate = LocalDate.now();

        EntityProperties entity = (EntityProperties) Convert.objects( dto, new EntityProperties() );

        assertEquals( dto.valInt, entity.getValInt() );
        assertEquals( dto.valLong, entity.getValLong() );
        assertEquals( dto.valFloat, entity.getValFloat() );
        assertEquals( dto.valString, entity.getValString() );
        assertEquals( dto.valBoolean, entity.isValBoolean() );
        assertEquals( dto.valShort, entity.getValShort() );
        assertEquals( dto.valChar, entity.getValChar() );
        assertEquals( dto.valLocalDate, entity.getValLocalDate() );

    }
}

class DtoProperties {

    public int valInt;
    public float valFloat;
    public long valLong;
    public double valDouble;
    public boolean valBoolean;
    public short valShort;
    public byte valByte;
    public char valChar;
    public String valString;
    public LocalDate valLocalDate;
}

class EntityProperties {

    private int valInt;
    private float valFloat;
    private long valLong;
    private double valDouble;
    private boolean valBoolean;
    private short valShort;
    private byte valByte;
    private char valChar;
    private String valString;
    private LocalDate valLocalDate;

    public int getValInt() {
        return valInt;
    }

    public void setValInt(int valInt) {
        this.valInt = valInt;
    }

    public float getValFloat() {
        return valFloat;
    }

    public void setValFloat(float valFloat) {
        this.valFloat = valFloat;
    }

    public long getValLong() {
        return valLong;
    }

    public void setValLong(long valLong) {
        this.valLong = valLong;
    }

    public double getValDouble() {
        return valDouble;
    }

    public void setValDouble(double valDouble) {
        this.valDouble = valDouble;
    }

    public boolean isValBoolean() {
        return valBoolean;
    }

    public void setValBoolean(boolean valBoolean) {
        this.valBoolean = valBoolean;
    }

    public short getValShort() {
        return valShort;
    }

    public void setValShort(short valShort) {
        this.valShort = valShort;
    }

    public byte getValByte() {
        return valByte;
    }

    public void setValByte(byte valByte) {
        this.valByte = valByte;
    }

    public char getValChar() {
        return valChar;
    }

    public void setValChar(char valChar) {
        this.valChar = valChar;
    }

    public String getValString() {
        return valString;
    }

    public void setValString(String valString) {
        this.valString = valString;
    }

    public LocalDate getValLocalDate() {
        return valLocalDate;
    }

    public void setValLocalDate(LocalDate valLocalDate) {
        this.valLocalDate = valLocalDate;
    }
}

