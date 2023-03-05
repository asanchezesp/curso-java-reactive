package es.asanchez.service;

/**
 * Clase para simular que un proceso tarda mucho en hacerse
 */
public class SleepUtil {

    public static void sleepSeconds(int secods){
        try {
            Thread.sleep(secods* 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
