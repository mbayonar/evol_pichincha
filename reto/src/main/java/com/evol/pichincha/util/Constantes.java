package com.evol.pichincha.util;

public class Constantes {

    public static Integer NUMERO_DECIMALES_MONEDA = 2;

    public static abstract class RESPUESTA_CONTROLADOR {

        public final static String ESTADO_ERROR = "error";
        public final static String ESTADO_EXITO = "exito";
        public final static String MENSAJE_ERROR_AUTENTICACION = "Credenciales incorrectas";
        public final static String MENSAJE_ERROR_AUTENTICACION_REQUERIDA = "Autenticación requerida";
        public final static String MENSAJE_ERROR_VALIDAR_USUARIO = "Ocurrió un error al validar usuario";
        public final static String MENSAJE_EXITO_AUTENTICACION = "Usuario autenticado correctamente";
        public final static String MENSAJE_DUPLICADO = "Ya existe otro {0} con el mismo {1}";
        public final static String MENSAJE_CREAR_EXITO = "{0} creado exitosamente";
        public final static String MENSAJE_NO_ENCONTRE_ID = "Id de {0} no encontrado";
        public final static String MENSAJE_ACTUALIZAR_EXITO = "{0} actualizado exitosamente";
        public final static String MENSAJE_ELIMINAR_EXITO = "{0} eliminado exitosamente";
        public final static String MENSAJE_ELIMINAR_ERROR_HIJOS = "{0} tiene una o más {1} asociados y no se puede eliminar";
        public final static String MENSAJE_OBTENER_ERROR = "Ocurrió un error al obtener {0}";
        public final static String MENSAJE_LISTAR_ERROR = "Ocurrió un error al listar {0}";

    }

}
