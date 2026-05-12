package de.ea234.aoc2016.day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 7: Internet Protocol Version 7 ---
 * https://adventofcode.com/2016/day/7
 * 
 * https://www.reddit.com/r/adventofcode/comments/5gy1f2/2016_day_7_solutions/
 * 
 * Check IP abba[mnop]qrst
 * ABBA-Sequenze: abba at Index 0
 * Supports TLS 
 * Does not support SSL
 * 
 * Check IP abcd[bddb]xyyx
 * ABBA-Sequenze in square brackets: bddb at Index 5
 * Does not support TLS
 * Does not support SSL
 * 
 * Check IP aaaa[qwer]tyui
 * Does not support TLS
 * Does not support SSL
 * 
 * Check IP ioxxoj[asdfgh]zxcvbn
 * ABBA-Sequenze: oxxo at Index 1
 * Supports TLS 
 * Does not support SSL
 * 
 * Check IP aba[bab]xyz
 * SSL Key SET bab  aba
 * SSL Key GET aba  bab
 * Does not support TLS
 * Supports SSL 
 * 
 * Check IP xyx[xyx]xyx
 * SSL Key SET xyx  yxy
 * Does not support TLS
 * Does not support SSL
 * 
 * Check IP aaa[kek]eke
 * SSL Key SET kek  eke
 * SSL Key GET eke  kek
 * Does not support TLS
 * Supports SSL 
 * 
 * Check IP zazbz[bzb]cdb
 * SSL Key SET bzb  zbz
 * SSL Key GET zbz  bzb
 * Does not support TLS
 * Supports SSL 
 * 
 * Result Part 1 2
 * Result Part 2 3
 * 
 * </pre> 
 */
public class Day07__InternetProtocolV7
{
  public static void main( String[] args )
  {
    String test_input = ",abba[mnop]qrst,abcd[bddb]xyyx,aaaa[qwer]tyui,ioxxoj[asdfgh]zxcvbn,aba[bab]xyz,xyx[xyx]xyx,aaa[kek]eke,zazbz[bzb]cdb";

    calculatePart01( test_input, true );

    //calculate01( getListProd(), true );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, boolean pKnzDebug )
  {
    int result_part_01 = 0;

    int result_part_02 = 0;

    for ( String input_str : pListInput )
    {
      if ( !input_str.isBlank() )
      {
        wl( "" );
        wl( "Check IP " + input_str );

        int result_tls_check = checkTransportLayerSnoopingSupport( input_str );

        int result_ssl_check = checkSuperSecretListiningSupport( input_str );

        wl( ( result_tls_check == 1 ? "Supports TLS " : "Does not support TLS" ) );
        wl( ( result_ssl_check == 1 ? "Supports SSL " : "Does not support SSL" ) );

        result_part_01 += result_tls_check;
        result_part_02 += result_ssl_check;
      }
    }

    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  public static int checkTransportLayerSnoopingSupport( String pInput )
  {
    if ( pInput.isBlank() ) return 0;

    int supports_tls = 0;

    boolean knz_in_square_brackets = false;

    for ( int index_4 = 0; index_4 < pInput.length(); index_4++ )
    {
      char char_4 = pInput.charAt( index_4 );

      if ( char_4 == '[' )
      {
        knz_in_square_brackets = true;
      }
      else if ( char_4 == ']' )
      {
        knz_in_square_brackets = false;
      }
      else if ( index_4 > 2 )
      {
        char char_1 = pInput.charAt( index_4 - 3 );
        char char_2 = pInput.charAt( index_4 - 2 );
        char char_3 = pInput.charAt( index_4 - 1 );

        /*
         * Char 1 must be equal to char 4
         * Char 2 must be equal to char 3
         * Char 1 must be different to char 2 (= aaaa does not count as ABBA sequenze)
         */
        if ( ( char_1 == char_4 ) && ( char_2 == char_3 ) && ( char_1 != char_2 ) )
        {
          if ( knz_in_square_brackets )
          {
            wl( "ABBA-Sequenze in square brackets: " + char_1 + char_2 + char_3 + char_4 + " at Index " + ( index_4 - 3 ) );

            supports_tls = 0;

            return 0;
          }
          else
          {
            wl( "ABBA-Sequenze: " + char_1 + char_2 + char_3 + char_4 + " at Index " + ( index_4 - 3 ) );

            supports_tls = 1;
          }
        }
      }
    }

    return supports_tls;
  }

  public static int checkSuperSecretListiningSupport( String pInput )
  {
    if ( pInput.isBlank() ) return 0;

    Properties prop_square_brackets = new Properties();

    int index_plus = 3;

    boolean knz_in_square_brackets = false;

    for ( int index_3 = 0; index_3 < pInput.length(); index_3++ )
    {
      char char_3 = pInput.charAt( index_3 );

      if ( char_3 == '[' )
      {
        knz_in_square_brackets = true;

        index_plus = index_3 + 2;
      }
      else if ( char_3 == ']' )
      {
        knz_in_square_brackets = false;
      }
      else if ( ( index_3 > index_plus ) && ( knz_in_square_brackets ) )
      {
        char char_1 = pInput.charAt( index_3 - 2 );
        char char_2 = pInput.charAt( index_3 - 1 );

        if ( ( char_1 == char_3 ) && ( char_1 != char_2 ) )
        {
          String prop_val = pInput.substring( index_3 - 2, index_3 + 1 );
          String prop_key = "" + char_2 + char_1 + char_2;

          wl( "SSL Key SET " + prop_val + "  " + prop_key );

          prop_square_brackets.setProperty( prop_key, prop_val );
        }
      }
    }

    index_plus = 1;

    knz_in_square_brackets = false;

    for ( int index_3 = 0; index_3 < pInput.length(); index_3++ )
    {
      char char_3 = pInput.charAt( index_3 );

      if ( char_3 == '[' )
      {
        knz_in_square_brackets = true;
      }
      else if ( char_3 == ']' )
      {
        knz_in_square_brackets = false;

        index_plus = index_3 + 2;
      }
      else if ( ( index_3 > index_plus ) && ( !knz_in_square_brackets ) )
      {
        char char_1 = pInput.charAt( index_3 - 2 );
        char char_2 = pInput.charAt( index_3 - 1 );

        if ( ( char_1 == char_3 ) && ( char_1 != char_2 ) )
        {
          String prop_key = "" + char_1 + char_2 + char_1;

          String prop_val = prop_square_brackets.getProperty( prop_key );

          if ( prop_val != null )
          {
            wl( "SSL Key GET " + prop_key + "  " + prop_val );

            return 1;
          }
        }
      }
    }

    return 0;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day07_input.txt";

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

}
