package com.davingl.coletadepresenca.core;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUtil {

    /**
     * Retorna o dia da semana.
     * @return
     */
    public static int buscarDiaDaSemana(){

        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

    }

    /**
     * Retorna a data corrente no formato dd/mm/aaaa
     * @return
     */
    public static String buscarDataFormatada(){

        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());

    }

    /**
     * Compara se duas localizações são iguais
     * @param location1 Localização 1
     * @param location2 Localização 2
     * @return
     */
    public static boolean compararLocalizacoes(Location location1, Location location2){

        return  (location1.getLatitude()    == location2.getLatitude() ) &&
                (location1.getLongitude()   == location2.getLongitude() );

    }

}
