package com.davingl.coletadepresenca.core;

import android.location.Location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUtil {

    public static final double LATITUDE_UNICID = -23.53628;
    public static final double LONGITUDE_UNICID = -46.56033;

    //Vetor auxiliar de dias da semana
    public static final String arrayDiasSemana[] =
            {"Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"};

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
     * Verifica se determinada localização está na UNICID.
     * @param lat latitude.
     * @param lon longitude.
     * @return true se bater com a localização da UNICID, false se não bater.
     */
    public static boolean checarLocalizacao(double lat, double lon){

        return lat == LATITUDE_UNICID && lon == LONGITUDE_UNICID;

    }


    /**
     * Verifica se está dentro do horário de aula.
     * @param diaDaSemanaNumero
     * @return
     */
    public static boolean checarHorario(int diaDaSemanaNumero) {

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
