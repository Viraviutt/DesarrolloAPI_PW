package com.shop.eShop.enums;

public enum Estado {

    PENDIENTE((byte) 0), 
    ENVIADO((byte) 1), 
    ENTREGADO((byte) 2);

    private byte numEstado;
	
	private Estado (byte numEstado){
		this.numEstado = numEstado;
	}

	public byte getNumEstado() {
		return numEstado;
	}

}
