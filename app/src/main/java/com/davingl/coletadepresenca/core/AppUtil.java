package com.davingl.coletadepresenca.core;

import android.location.Location;

import java.text.ParseException;
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


    /**
     * Verifica se está dentro do horário de aula.
     * @param diaDaSemanaNumero
     * @return
     */
    public static boolean compararHoras(int diaDaSemanaNumero) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String horaSistema = new SimpleDateFormat("HH:mm:ss").format(new Date());

        try {

            Date horaAtual = sdf.parse(horaSistema);
            Date horaInicial = sdf.parse("19:10:00");
            Date horaFinal;

            switch (diaDaSemanaNumero) {

                case 2:
                    horaFinal = sdf.parse("20:25:00");
                    break;
                default:
                    horaFinal = sdf.parse("21:50:00");
            }

            if ((horaAtual.compareTo(horaInicial) >= 0) && (horaAtual.compareTo(horaFinal) <= 0)) {

                return true;
            } else {

                return false;
            }

        } catch (ParseException e) {

            return false;
        }
    }

}
