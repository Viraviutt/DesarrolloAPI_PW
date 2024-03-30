package com.shop.eShop.enums;

public enum metodoPago {
    EFECTIVO((byte) 0), 
    TARJETA_CREDITO((byte) 1), 
    PAYPAL((byte) 2), 
    NEQUI((byte) 3), 
    DAVIPLATA((byte) 4), 
    PSE((byte) 5);

    private byte numMetodo;

    private metodoPago (byte numMetodo){
        this.numMetodo = numMetodo;
    }

    public byte getNumMetodo() {
        return numMetodo;
    }
}
