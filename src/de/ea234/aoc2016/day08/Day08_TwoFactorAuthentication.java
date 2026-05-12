package de.ea234.aoc2016.day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 8: Two-Factor Authentication ---
 * https://adventofcode.com/2016/day/8
 * 
 * https://www.reddit.com/r/adventofcode/comments/5h52ro/2016_day_8_solutions/
 *
 *  
 * ####...##.#..#.###..#..#..##..###..#....#...#..##.
 * ...#....#.#..#.#..#.#.#..#..#.#..#.#....#...#...#.
 * ..#.....#.####.#..#.##...#....#..#.#.....#.#....#.
 * .#......#.#..#.###..#.#..#....###..#......#.....#.
 * #....#..#.#..#.#.#..#.#..#..#.#....#......#..#..#.
 * ####..##..#..#.#..#.#..#..##..#....####...#...##..
 *  
 *  ZJHRKCPLYJ
 *  
 * </pre> 
 */
public class Day08_TwoFactorAuthentication
{
  public static void main( String[] args )
  {
    String test_input = "rect 3x2,rotate column x=1 by 1,rotate row y=0 by 4,rotate column x=1 by 1";

    calculatePart01( test_input, 3, 7, true );

    calculate01( getListProd(), 6, 50, true );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, int pRows, int pCols, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pRows, pCols, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, int pRows, int pCols, boolean pKnzDebug )
  {
    int result_part_01 = 0;
    int result_part_02 = 0;

    /*
     * ************************************************************************
     * Create 2D array and initialize it with 0
     * ************************************************************************
     */
    int[][] array = new int[ pRows ][ pCols ];

    for ( int cur_row = 0; cur_row < pRows; cur_row++ )
    {
      for ( int cur_col = 0; cur_col < pCols; cur_col++ )
      {
        array[ cur_row ][ cur_col ] = 0;
      }
    }

    /*
     * ************************************************************************
     * Do the commands from the input
     * ************************************************************************
     */
    for ( String input_str : pListInput )
    {
      if ( !input_str.isBlank() )
      {
        if ( input_str.startsWith( "rotate row y" ) )
        {
          int row_number = Integer.parseInt( getString( input_str, "y=", " by" ) );
          int row_rotate_amount = Integer.parseInt( right( input_str, " by" ).trim() );

          wl( "Rotate Row " + row_number + " By " + row_rotate_amount );

          cmdRotateRow( array, pRows, pCols, row_number, row_rotate_amount );
        }
        else if ( input_str.startsWith( "rotate column x" ) )
        {
          int col_number = Integer.parseInt( getString( input_str, "x=", " by" ) );
          int col_rotate_amount = Integer.parseInt( right( input_str, " by" ).trim() );

          wl( "Rotate Col " + col_number + " By " + col_rotate_amount );

          cmdRotateCol( array, pRows, pCols, col_number, col_rotate_amount );
        }
        else if ( input_str.startsWith( "rect " ) )
        {
          int wide_a = Integer.parseInt( getString( input_str, "rect ", "x" ) );
          int tall_b = Integer.parseInt( right( input_str, "x" ).trim() );

          cmdRect( array, tall_b, wide_a );
        }
      }
    }

    wl( "" );
    wl( "Display\n" + getDisplay( array, pRows, pCols ) );

    result_part_01 = count( array, pRows, pCols );

    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static String getDisplay( int[][] array, int pRows, int pCols )
  {
    String res_string = "";

    for ( int cur_row = 0; cur_row < pRows; cur_row++ )
    {
      for ( int cur_col = 0; cur_col < pCols; cur_col++ )
      {
        res_string += array[ cur_row ][ cur_col ] == 1 ? "#" : ".";
      }

      res_string += "\n";
    }

    return res_string;
  }

  private static int count( int[][] array, int pRows, int pCols )
  {
    int result_value = 0;

    for ( int cur_row = 0; cur_row < pRows; cur_row++ )
    {
      for ( int cur_col = 0; cur_col < pCols; cur_col++ )
      {
        result_value += array[ cur_row ][ cur_col ];
      }
    }

    return result_value;
  }

  private static void cmdRect( int[][] array, int pFillRows, int pFillCols )
  {
    for ( int cur_row = 0; cur_row < pFillRows; cur_row++ )
    {
      for ( int cur_col = 0; cur_col < pFillCols; cur_col++ )
      {
        array[ cur_row ][ cur_col ] = 1;
      }
    }
  }

  private static void cmdRotateRow( int[][] array, int pRows, int pCols, int pRow, int pCount )
  {
    for ( int cur_rotate_nr = 0; cur_rotate_nr < pCount; cur_rotate_nr++ )
    {
      int save_first_value = array[ pRow ][ pCols - 1 ];

      for ( int cur_col = pCols - 1; cur_col >= 1; cur_col-- )
      {
        array[ pRow ][ cur_col ] = array[ pRow ][ cur_col - 1 ];
      }

      array[ pRow ][ 0 ] = save_first_value;
    }
  }

  private static void cmdRotateCol( int[][] array, int pRows, int pCols, int pCol, int pCount )
  {
    for ( int cur_rotate_nr = 0; cur_rotate_nr < pCount; cur_rotate_nr++ )
    {
      int save_first_value = array[ pRows - 1 ][ pCol ];

      for ( int cur_col = pRows - 1; cur_col >= 1; cur_col-- )
      {
        array[ cur_col ][ pCol ] = array[ cur_col - 1 ][ pCol ];
      }

      array[ 0 ][ pCol ] = save_first_value;
    }
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day08_input.txt";

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
