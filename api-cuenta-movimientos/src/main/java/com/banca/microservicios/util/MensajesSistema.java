package com.banca.microservicios.util;

public interface MensajesSistema {

    Boolean ACTIVO = true;
    Boolean INACTIVO = false;

    // Mensajes generales
    String TRANSACCION_EXITOSA = "Transacción realizada con éxito";
    String CLIENTE_EXISTE =  "El cliente ya existe registrado";
    String  REGISTRO_CLIENTE = "REGISTRO CLIENTE";
    String  DEPOSITO = "DEPOSITO";
    String  RETIRO = "RETIRO";


}
