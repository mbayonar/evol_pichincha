package com.evol.pichincha.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SistemaUtil {

    private static Logger logger = LogManager.getLogger(SistemaUtil.class);

    public static final Character ESPACIO = ' ';

    public static String obtenerFiltroComoString(Map<String, Object> buscar, String nombrePropiedad) {
        String f;
        Object objetoObtenido = buscar.get(nombrePropiedad);
        f = null;
        if (sonNoNulos(buscar, objetoObtenido)) {
            try {
                f = String.valueOf(objetoObtenido);
            } catch (ClassCastException ex) {
                f = objetoObtenido + "";
            }
        }
        return f;
    }

    public static Boolean esCadenaValidaParaBusquedas(String cadena) {
        return (esNoNulo(cadena) && !cadena.isEmpty());
    }

    public static Long obtenerFiltroComoLong(Map<String, Object> buscar, String nombrePropiedad) {
        String f;
        Long filtro;
        filtro = null;
        f = obtenerFiltroComoString(buscar, nombrePropiedad);
        if (esNoNulo(f)) {
            try {
                filtro = Long.valueOf(f);
            } catch (NumberFormatException ex) {
                filtro = null;
            }
        }
        return filtro;
    }

    public static Integer obtenerFiltroComoInteger(Map<String, Object> buscar, String nombrePropiedad) {
        String f;
        Integer filtro;
        filtro = null;
        f = obtenerFiltroComoString(buscar, nombrePropiedad);
        if (esNoNulo(f)) {
            try {
                filtro = Integer.valueOf(f);
            } catch (NumberFormatException ex) {
                filtro = null;
            }
        }
        return filtro;
    }

    public static Timestamp obtenerFiltroComoTimestamp(Map<String, Object> buscar, String nombrePropiedad) {
        Timestamp timestamp;
        Long milis;
        timestamp = null;
        try {
            milis = obtenerFiltroComoLong(buscar, nombrePropiedad);
            timestamp = new Timestamp(milis);
        } catch (Exception exception) {
            timestamp = null;
        }
        return timestamp;
    }

    public static Date obtenerFiltroComoDate(Map<String, Object> buscar, String nombrePropiedad) {
        Date date = null;
        String fechaCadena;
        try {
            fechaCadena = obtenerFiltroComoString(buscar, nombrePropiedad);
            date = convertirStringToDate(fechaCadena);
        } catch (Exception exception) {
            date = null;
        }
        return date;
    }

    public static Boolean obtenerFiltroComoBoolean(Map<String, Object> buscar, String nombrePropiedad) {
        String f;
        Boolean filtro;
        filtro = null;
        f = obtenerFiltroComoString(buscar, nombrePropiedad);
        if (esNoNulo(f)) {
            try {
                filtro = Boolean.valueOf(f);
            } catch (NumberFormatException ex) {
                filtro = null;
            }
        }
        return filtro;
    }

    public static BigDecimal obtenerFiltroComoBigDecimal(Map<String, Object> buscar, String nombrePropiedad) {
        String f;
        BigDecimal filtro;
        filtro = null;
        f = obtenerFiltroComoString(buscar, nombrePropiedad);
        if (esNoNulo(f)) {
            try {
                filtro = new BigDecimal(f);
            } catch (NumberFormatException ex) {
                filtro = null;
            }
        }
        return filtro;
    }

    public static Object obtenerFiltroComoObject(Map<String, Object> buscar, String nombrePropiedad) {
        return buscar.get(nombrePropiedad);
    }

    public static Boolean esNoNulo(Object o) {
        return o != null;
    }

    public static Boolean esNoNuloVerdadero(Object o) {
        return esNoNulo(o) && (boolean) o;
    }

    public static Boolean esNulo(Object o) {
        return o == null;
    }

    public static Timestamp convertirStringToDate(String fechaCadena) throws ParseException, IllegalArgumentException {
        Timestamp fecha = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = simpleDateFormat.parse(fechaCadena);
            fecha = new Timestamp(date.getTime());
        } catch (ParseException | IllegalArgumentException e) {
            throw e;
        }
        return fecha;
    }

    public static Boolean sonNoNulos(Object... obs) {
        for (Object o : obs) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }

    public static String completarNumeroConCeros(Integer len, Long value) {
        return String.format("%0" + len + "d", value);
    }

    public static String completarNumeroConCeros(Integer len, Integer value) {
        return String.format("%0" + len + "d", value);
    }

    public static byte[] convertirPdfAByteArray(String rutaArchivo) {

        InputStream inputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            inputStream = new FileInputStream(rutaArchivo);

            byte[] buffer = new byte[1024];
            baos = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return baos.toByteArray();
    }

    public static Timestamp obtenerInicioDia(Timestamp fecha) {
        Calendar cal;
        cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        return new Timestamp(cal.getTimeInMillis());
    }

    public static Date agregarDias(Date date, int dias) {

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(new Timestamp(date.getTime()));

        calendario.add(Calendar.DAY_OF_WEEK, dias);
        Timestamp finalDate = new Timestamp(calendario.getTime().getTime());
        return new Date(finalDate.getTime());
    }

    public static Timestamp agregarDias(Timestamp date, int dias) {

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(new Timestamp(date.getTime()));

        calendario.add(Calendar.DAY_OF_WEEK, dias);
        Timestamp finalDate = new Timestamp(calendario.getTime().getTime());
        return new Timestamp(finalDate.getTime());
    }

    public static boolean esNumero(String cadena) {
        boolean isDecimal = false;
        if (cadena == null || cadena.isEmpty()) {
            return false;
        }
        int i = 0;
        if (cadena.charAt(0) == '-') {
            if (cadena.length() > 1) {
                i++;
            } else {
                return false;
            }
        }
        for (; i < cadena.length(); i++) {
            if (!Character.isDigit(cadena.charAt(i))) {
                if (!isDecimal && (cadena.charAt(i) != '.' || cadena.charAt(i) != ',')) {
                    isDecimal = true;
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    public static Timestamp obtenerFechaHoraActual() {
        java.util.Date date = new java.util.Date();
        Timestamp timeStampTransaction = new Timestamp(date.getTime());
        return timeStampTransaction;
    }

    public static Timestamp obtenerFechaActual() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        return new Timestamp(date.getTime().getTime());
    }

    public static Date obtenerFechaActualDate() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        return new Date(date.getTime().getTime());
    }

    public static String obtenerFechaComoString(Date fecha) {
        String formato = "yyyy/MM/dd"; // Si no pasan el formato como parámetro, le asignamos uno por defecto
        return obtenerFechaComoString(fecha, formato);
    }

    public static String obtenerFechaComoString(Date fecha, String formato) {
        String fechaString;
        SimpleDateFormat sdf;

        sdf = new SimpleDateFormat(formato);
        fechaString = sdf.format(fecha);

        return fechaString;
    }

    public static Integer obtenerAnioIntDeFecha(Timestamp fecha) {
        Calendar c;
        c = Calendar.getInstance();
        c.setTimeInMillis(fecha.getTime());
        return c.get(Calendar.YEAR);
    }

    public static Integer obtenerMesDeFecha(Timestamp fecha) {
        Calendar c;
        c = Calendar.getInstance();
        c.setTimeInMillis(fecha.getTime());
        return c.get(Calendar.MONTH) + 1;
    }

    public static HttpSession obtenerSesion() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getSession(false);
    }

    public static Integer obtenerAnioActual() {
        Calendar calendar;
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static Integer obtenerMesActual() {
        Calendar calendar;
        calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) + 1);
    }

    public static BigDecimal redondearBigDecimal(BigDecimal valor, Integer precision) {
        return valor.setScale(precision, BigDecimal.ROUND_HALF_UP);
    }

    public static String rellenarConEspacios(String cadena, Integer longitudMaxima) {

        if (esNoNulo(cadena) && cadena.length() > longitudMaxima) {
            return cadena;
        }

        String out;
        Integer n;

        n = longitudMaxima;
        out = "";
        if (esNoNulo(cadena)) {
            n = longitudMaxima - cadena.length();
        }
        for (int i = 0; i < n; i++) {
            out += " ";
        }
        if (esNoNulo(cadena)) {
            out += cadena;
        }
        return out;
    }

    public static Date obtenerInicioDia(Date fecha) {
        Calendar cal;
        cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        return new Timestamp(cal.getTimeInMillis());
    }

    public static void actualizarContadorMap(Map<String, Long> contador, String key) {
        Long cont = contador.containsKey(key) ? contador.get(key) : 0L;
        contador.put(key, cont + 1L);
    }

    public static <E extends Object> E buscarObjetoEnLista(List<E> lista, E valor) {
        Optional<E> busqueda = lista.stream().filter(p -> p.equals(valor)).findFirst();
        return busqueda.isPresent() ? busqueda.get() : null;
    }

    public static Boolean esCadenaValida(String cadena) {
        return (cadena != null && !cadena.isEmpty());
    }

    public static void escribirArchivo(List<String> lista, String rutaArchivo, String nombreArchivo, Boolean escribirVacio) throws FileNotFoundException {
        PrintStream stream;
        String ruta;

        if (escribirVacio || !listaEstaVacia(lista)) {
            ruta = rutaArchivo + nombreArchivo;
            logger.info("Escribiendo el archivo: " + ruta);
            stream = new PrintStream(new FileOutputStream(ruta));

            lista.forEach((valor) -> {
                stream.println(valor);
            });
            logger.info("Archivo escrito exitosamente");
            stream.close();
        }
    }

    public static Boolean listaEstaVacia(List lista) {
        return !esNoNulo(lista) || lista.isEmpty();
    }

    public static String completarCadenaConCeros(Integer length, String cadena) {
        return (cadena.length() < length)
                ? String.format("%0" + String.valueOf(length - cadena.length()) + "d%s", 0, cadena) : cadena;
    }

//    public static <E extends AuditoriaEntidad> E buscarEntidadEnLista(List<E> lista, E valor) {
//        Optional<E> busqueda = lista.stream()
//                .filter(p -> !p.esNuevo() && !valor.esNuevo() && Objects.equals(p.getId(), valor.getId())).findFirst();
//        return busqueda.isPresent() ? busqueda.get() : null;
//    }
    public static String convertirFechaParaBusqueda(String fecha) {
        String fechaConvertida;
        String año = fecha.substring(0, 4);
        String mes = fecha.substring(5, 7);
        String dia = fecha.substring(8, 10);
        fechaConvertida = año + mes + dia;
        return fechaConvertida;
    }

    public static Boolean sonFechasIguales(Timestamp f1, Timestamp f2) {
        Timestamp f1t = obtenerInicioDia(f1);
        Timestamp f2t = obtenerInicioDia(f2);
        return f1t.getTime() == f2t.getTime();
    }

    public static String formatDate(Date date, String format, Boolean retornarCadVaciaSiEsNull, Integer lenCadena) {
        if (retornarCadVaciaSiEsNull && esNulo(date)) {
            return rellenarConEspacios(ESPACIO.toString(), lenCadena);
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String obtenerFechaActualFormato(String formato) {
        return formatDate(new Date(), formato, Boolean.FALSE, null);
    }

    public static String formatMoneda(BigDecimal moneda) {
        String respuesta = "";
        if (moneda != null) {
            DecimalFormat twoPlaces = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.US));
            respuesta = twoPlaces.format(moneda);
        }
        return respuesta;
    }

    public static String obtenerFechaHoraActualFormato() {
        Timestamp ahora = obtenerFechaHoraActual();
        SimpleDateFormat format;
        format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(new Date(ahora.getTime()));
    }

    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_VIA");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
