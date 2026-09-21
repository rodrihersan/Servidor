package utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

    public class Lecturas {

        private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // ─────────────────────────────────────────────
        // LECTURA BÁSICA
        // ─────────────────────────────────────────────

        /**
         * Lee una línea de texto. Repite si está vacía.
         */
        public static String leerString(String mensaje) {
            String valor = "";
            while (valor.isEmpty()) {
                System.out.print(mensaje);
                try {
                    valor = br.readLine().trim();
                    if (valor.isEmpty()) {
                        System.out.println("ERROR: El campo no puede estar vacío.");
                    }
                } catch (IOException e) {
                    System.out.println("ERROR: Problema al leer la entrada.");
                }
            }
            return valor;
        }

        /**
         * Lee un número entero. Repite si no es un número válido.
         */
        public static int leerEntero(String mensaje) {
            int valor = 0;
            boolean valido = false;
            while (!valido) {
                System.out.print(mensaje);
                try {
                    valor = Integer.parseInt(br.readLine().trim());
                    valido = true;
                } catch (IOException e) {
                    System.out.println("ERROR: Problema al leer la entrada.");
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Introduce un número entero válido.");
                }
            }
            return valor;
        }

        /**
         * Lee un número decimal. Repite si no es un número válido.
         */
        public static double leerDouble(String mensaje) {
            double valor = 0;
            boolean valido = false;
            while (!valido) {
                System.out.print(mensaje);
                try {
                    valor = Double.parseDouble(br.readLine().trim().replace(",", "."));
                    valido = true;
                } catch (IOException e) {
                    System.out.println("ERROR: Problema al leer la entrada.");
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Introduce un número decimal válido.");
                }
            }
            return valor;
        }


        /**
         * Lee un número long. Repite si no es un número válido.
         */
        public static long leerLong(String mensaje) {
            long valor = 0;
            boolean valido = false;
            while (!valido) {
                System.out.print(mensaje);
                try {
                    // Usamos Long.parseLong en lugar de Integer.parseInt
                    valor = Long.parseLong(br.readLine().trim());
                    valido = true;
                } catch (IOException e) {
                    System.out.println("ERROR: Problema al leer la entrada.");
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Introduce un número válido.");
                }
            }
            return valor;
        }

        // ─────────────────────────────────────────────
        // LECTURA CON VALIDACIÓN DE RANGO
        // ─────────────────────────────────────────────

        /**
         * Lee un entero dentro de un rango [min, max]. Repite si está fuera de rango.
         */
        public static int leerEnteroEnRango(String mensaje, int min, int max) {
            int valor;
            do {
                valor = leerEntero(mensaje);
                if (valor < min || valor > max) {
                    System.out.println("ERROR: El valor debe estar entre " + min + " y " + max + ".");
                }
            } while (valor < min || valor > max);
            return valor;
        }

        /**
         * Lee un double mayor que un mínimo (exclusivo). Repite si no cumple.
         */
        public static double leerDoubleMayorQue(String mensaje, double minExclusivo) {
            double valor;
            do {
                valor = leerDouble(mensaje);
                if (valor <= minExclusivo) {
                    System.out.println("ERROR: El valor debe ser mayor que " + minExclusivo + ".");
                }
            } while (valor <= minExclusivo);
            return valor;
        }

        /**
         * Lee un entero mayor que un mínimo (exclusivo). Repite si no cumple.
         */
        public static int leerEnteroMayorQue(String mensaje, int minExclusivo) {
            int valor;
            do {
                valor = leerEntero(mensaje);
                if (valor <= minExclusivo) {
                    System.out.println("ERROR: El valor debe ser mayor que " + minExclusivo + ".");
                }
            } while (valor <= minExclusivo);
            return valor;
        }

        // ─────────────────────────────────────────────
        // LECTURA CON VALIDACIÓN DE OPCIONES
        // ─────────────────────────────────────────────

        /**
         * Lee un String que debe coincidir con una de las opciones permitidas (sin distinguir mayúsculas).
         * Repite si no es una opción válida.
         *
         * Ejemplo de uso:
         *   String modulo = Lecturas.leerOpcion("Módulo: ", new String[]{"Programacion", "BBDD", "LLMM", "Sistemas"});
         */
        public static String leerOpcion(String mensaje, String[] opciones) {
            String valor = "";
            boolean valido = false;
            while (!valido) {
                valor = leerString(mensaje);
                for (String opcion : opciones) {
                    if (opcion.equalsIgnoreCase(valor)) {
                        valor = opcion; // devuelve con el formato original
                        valido = true;
                        break;
                    }
                }
                if (!valido) {
                    System.out.print("ERROR: Opción no válida. Las opciones son: ");
                    for (int i = 0; i < opciones.length; i++) {
                        System.out.print(opciones[i]);
                        if (i < opciones.length - 1) System.out.print(", ");
                    }
                    System.out.println(".");
                }
            }
            return valor;
        }

        /**
         * Lee una respuesta s/n. Devuelve true si es 's', false si es 'n'.
         * Repite si no es ninguna de las dos.
         */
        public static boolean leerSiNo(String mensaje) {
            String valor;
            do {
                valor = leerString(mensaje + " (s/n): ").toLowerCase();
                if (!valor.equals("s") && !valor.equals("n")) {
                    System.out.println("ERROR: Introduce 's' o 'n'.");
                }
            } while (!valor.equals("s") && !valor.equals("n"));
            return valor.equals("s");
        }
    }
}
