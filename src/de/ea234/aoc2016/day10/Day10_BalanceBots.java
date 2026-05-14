package de.ea234.aoc2016.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 10: Balance Bots ---
 * https://adventofcode.com/2016/day/10
 * 
 * https://www.reddit.com/r/adventofcode/comments/5hijk5/2016_day_10_solutions/
 * 
 * You come upon a factory in which many robots are zooming around handing small 
 * microchips to each other.
 * 
 * Upon closer examination, you notice that each bot only proceeds when it has two
 * microchips, and once it does, it gives each one to a different bot or puts it in
 * a marked "output" bin. Sometimes, bots take microchips from "input" bins, too.
 * 
 * Inspecting one of the microchips, it seems like they each contain a single number;
 * the bots must use some logic to decide what to do with each chip. You access the
 * local control computer and download the bots' instructions (your puzzle input).
 * 
 * Some of the instructions specify that a specific-valued microchip should be given
 * to a specific bot; the rest of the instructions indicate what a given bot should
 * do with its lower-value or higher-value chip.
 * 
 * For example, consider the following instructions:
 * 
 * value 5 goes to bot 2
 * bot 2 gives low to bot 1 and high to bot 0
 * value 3 goes to bot 1
 * bot 1 gives low to output 1 and high to bot 0
 * bot 0 gives low to output 2 and high to output 0
 * value 2 goes to bot 2
 * 
 * 
 * - Initially, bot 1 starts with a value-3 chip, and bot 2 starts with a 
 *   value-2 chip and a value-5 chip.
 *   
 * - Because bot 2 has two microchips, 
 *      it gives its lower one  (2) to bot 1 
 *      and      its higher one (5) to bot 0.
 *      
 * - Then, bot 1 has two microchips; 
 *      it puts the value-2 chip in output 1 
 *      and gives the value-3 chip to bot 0.
 *      
 * - Finally, bot 0 has two microchips; 
 *      it puts the 3 in output 2 
 *      and the 5 in output 0.
 *      
 *      
 * In the end, 
 *      output bin 0 contains a value-5 microchip, 
 *      output bin 1 contains a value-2 microchip, 
 *      output bin 2 contains a value-3 microchip. 
 *      
 * In this configuration, bot number 2 is responsible for comparing value-5 microchips with value-2 microchips.
 * 
 * Based on your instructions, what is the number of the bot that is responsible for
 * comparing value-61 microchips with value-17 microchips?
 * 
 * To play, please identify yourself via one of these services:
 * [GitHub] [Google] [Twitter] [Reddit] - [How Does Auth Work?] * 
 * </pre> 
 */
public class Day10_BalanceBots
{
  public static void main( String[] args )
  {
    String test_input = "value 5 goes to bot 2,bot 2 gives low to bot 1 and high to bot 0,value 3 goes to bot 1,bot 1 gives low to output 1 and high to bot 0,bot 0 gives low to output 2 and high to output 0,value 2 goes to bot 2";

    calculatePart01( test_input, true );

    calculate01( getListProd(), false );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pKnzDebug );
  }

  private static HashMap< String, Day10Bot > hash_map_bots = new HashMap< String, Day10Bot >();

  private static Day10Bot getBot( int pBotNumber )
  {
    Day10Bot return_bot = hash_map_bots.get( "" + pBotNumber );

    if ( return_bot == null )
    {
      return_bot = new Day10Bot( pBotNumber );

      hash_map_bots.put( "" + pBotNumber, return_bot );
    }

    return return_bot;
  }

  private static void calculate01( List< String > pListInput, boolean pKnzDebug )
  {
    int result_part_01 = 0;
    int result_part_02 = 0;

    Properties out_put = new Properties();

    for ( String input_str : pListInput )
    {
      if ( !input_str.isBlank() )
      {
        wl( input_str );

        if ( input_str.startsWith( "value " ) )
        {
          int value = Integer.parseInt( getString( input_str, "value ", " goes " ) );
          int bot_number = Integer.parseInt( right( input_str, "to bot " ).trim() );

          Day10Bot bot_inst = getBot( bot_number );

          bot_inst.setValue( value );
        }
        else if ( input_str.startsWith( "bot " ) )
        {
          int bot_number_source = Integer.parseInt( getString( input_str, "bot ", " gives " ) );

          Day10Bot bot_source = getBot( bot_number_source );

          if ( bot_source.hasTwoMicrochips() )
          {

            int bot_source_low_value = bot_source.removeLowValue();
            int bot_source_high_value = bot_source.removeHighValue();

            int pos_low_to_bot = input_str.indexOf( " low to bot " );
            int pos_high_to_bot = input_str.indexOf( " high to bot " );

            int pos_low_to_output = input_str.indexOf( " low to output " );
            int pos_high_to_output = input_str.indexOf( " high to output " );

            if ( pos_low_to_bot > 0 )
            {
              int bot_number_low_value = Integer.parseInt( getString( input_str, " low to bot ", " and " ) );

              Day10Bot bot_inst = getBot( bot_number_low_value );

              bot_inst.setValue( bot_source_low_value );
            }
            else if ( pos_low_to_output > 0 )
            {
              int output_number_low_value = Integer.parseInt( getString( input_str, " low to output ", " and " ) );

              out_put.setProperty( "output_" + output_number_low_value, "" + bot_source_low_value );
            }

            if ( pos_high_to_bot > 0 )
            {
              int bot_number_high_value = Integer.parseInt( right( input_str, " high to bot " ).trim() );

              Day10Bot bot_inst = getBot( bot_number_high_value );

              bot_inst.setValue( bot_source_low_value );
            }
            else if ( pos_high_to_output > 0 )
            {
              int output_number_high_value = Integer.parseInt( right( input_str, " high to output " ).trim() );

              out_put.setProperty( "output_" + output_number_high_value, "" + bot_source_high_value );
            }
          }
        }
      }
    }

    wl( "" );
    wl( "" );
    wl( "" );

    for ( Map.Entry< String, Day10Bot > entry : hash_map_bots.entrySet() )
    {
      String key = entry.getKey();

      Day10Bot value = entry.getValue();

      wl( value.toString() );
    }

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day10_input.txt";

    try
    {
      string_array = Files.readAllLines( Path.of( datei_input ) );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }

    return string_array;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }

  /**
   * <pre>
   * Sucht in pString die Start- und die Endzeichenfolge und gibt den 
   * "eingeschlossenen" Teilstring zurueck. 
   * 
   * Es wird ein String zurueckgeben, wenn die Start- und Endzeichenfolge 
   * gefunden wurden und hintereinander stehen. Es erfolgt keine automatische
   * Umkehrung, wenn der Start hinter dem Ende gefunden wird.  
   * In allen anderen Faellen wird null zurueckgegeben. 
   * 
   * FkString.Mid( "1234567890", "5", "6" )                                = ""
   * FkString.Mid( "1234567890", "6", "5" )                                = null
   * FkString.Mid( "1234567890", "4", "7" )                                = "56"
   * FkString.Mid( "1234567890", "3", "8" )                                = "4567"
   * 
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Zwei",  "Fuenf"  )  = " Drei Vier "
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Fuenf", "Zwei"   )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Zwei",  "Neun"   )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Null",  "Fuenf"  )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Null",  "Neun"   )  = null
   * FkString.Mid( null,                              "Zwei",  "Fuenf"  )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", null,    "Fuenf"  )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Zwei",  null     )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", null,    null     )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Sechs", "Fuenf"  )  = null
   * </pre>
   * 
   * @param pString der String mit dem herauszutrennenden Teilstring
   * @param pAbString Suchzeichenfolge fuer den Start
   * @param pBisString Suchzeichenfolge fuer das Ende
   * @return den Teilstring, wenn Start und Ende gefunden wurden, sonst null
   */
  private static String getString( String pString, String pAbString, String pBisString )
  {
    /*
     * Pruefung: Sind die Parameter ungleich null?
     */
    if ( ( pString != null ) && ( pAbString != null ) && ( pBisString != null ) )
    {
      /*
       * Position der Startzeichenfolge suchen.
       */
      int ab_position = pString.indexOf( pAbString );

      /*
       * Pruefung: Startzeichenfolge gefunden?
       * Wurde die Startzeichenfolge nicht gefunden, bekommt der Aufrufer "null" zurueck.
       */
      if ( ab_position >= 0 )
      {
        /* 
         * Wurde die Startzeichenfolge gefunden, muss die Ab-Position um die 
         * Laenge der Startzeichenfolge erhoeht werden. Die Startzeichenfolge 
         * ist nicht Bestandteil der Rueckgabe.  
         */
        ab_position += pAbString.length();

        /*
         * Die Bis-Zeichenfolge wird ab der Ab-Positon gesucht.
         */
        int bis_position = pString.indexOf( pBisString, ab_position );

        /*
         * Pruefung: Bis-Zeichenfolge gefunden?
         * Wurde zwar die Startzeichenfolge gefunden, aber die Bis-Zeichenfolge 
         * nicht im Suchstring enthalten ist, bekommt der Aufrufer ebenfalls null zurueck.
         */
        if ( bis_position >= 0 )
        {
          /*
           * Rueckgabe des eingeschlossenen Strings
           */
          return pString.substring( ab_position, bis_position );
        }
      }
    }

    /*
     * Als Standardrueckgabe wird eine null-Referenz zurueckgegeben. 
     */
    return null;
  }

  /**
   * <pre>
   * Liefert von pString alle Zeichen bis zum ersten Vorkommen des Trennzeichens von rechts.
   * 
   * FkString.right( "ABC.DEF.GHI.JKL",   "." ) = JKL
   * FkString.right( "ABC.DEF.GHI.JKL", "DEF" ) = ".GHI.JKL"
   * FkString.right( "ABC.DEF.GHI.JKL",   "A" ) = "BC.DEF.GHI.JKL"
   * FkString.right( "ABC.DEF.GHI.JKL",  "KL" ) = ""
   * 
   * Trennzeichen ist null oder wird nicht gefunden = Rueckgabe Eingabestring
   * 
   * FkString.right( "ABC.DEF.GHI.JKL", "XYZ" ) = "ABC.DEF.GHI.JKL"
   * FkString.right( "ABC.DEF.GHI.JKL",  null ) = "ABC.DEF.GHI.JKL"
   *  
   * </pre>
   * 
   * @param pString der String 
   * @param pTrennzeichen das Trennzeichen (oder Trennstring)
   * @return den sich ergebenden String bis zur Fundstelle des Trennzeichens
   */
  private static String right( String pString, String pTrennzeichen )
  {
    /*
     * Es wird die Variable "pos_trennzeichen" deklariert und 
     * mit -1 vorbelegt. 
     */
    int pos_trennzeichen = -1;

    /*
     * Pruefung: Sind beide Parameter ungleich "null" ?
     * 
     * Wenn beide Parameter ungleich "null" sind, wird die 
     * letzte Position des Trennzeichens gesucht. Das 
     * Ergebnis wird in der Variablen "pos_trennzeichen"
     * gespeichert.
     */
    if ( ( pString != null ) && ( pTrennzeichen != null ) )
    {
      pos_trennzeichen = pString.lastIndexOf( pTrennzeichen );
    }

    /*
     * Wurde das Trennzeichen nicht gefunden, kann das Ergebnis nur 
     * der EingabeString sein. Ist der Eingabestring selber null 
     * erhaelt der Aufrufer "null" zurueck.
     */
    if ( pos_trennzeichen == -1 )
    {
      return pString;
    }

    /*
     * Wurde das Trennzeichen gefunden 
     */
    return pString.substring( pos_trennzeichen + pTrennzeichen.length(), pString.length() );
  }
}
