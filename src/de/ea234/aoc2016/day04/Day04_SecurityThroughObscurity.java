package de.ea234.aoc2016.day04;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 4: Security Through Obscurity ---
 * https://adventofcode.com/2016/day/4
 * 
 * https://www.reddit.com/r/adventofcode/comments/5gdvve/2016_day_4_solutions/
 *
 * 
 * ------------------------------------------------------------------------------------------
 * Room         aaaaa-bbb-z-y-x-123[abxyz]
 * Sector ID    123
 * Check        abxyz
 *              , a = 5 , b = 3 , x = 1 , y = 1 , z = 1 
 * max_value    5
 * is real room YES
 * 
 * ------------------------------------------------------------------------------------------
 * Room         a-b-c-d-e-f-g-h-987[abcde]
 * Sector ID    987
 * Check        abcde
 *              , a = 1 , b = 1 , c = 1 , d = 1 , e = 1 
 * max_value    1
 * is real room YES
 * 
 * ------------------------------------------------------------------------------------------
 * Room         not-a-real-room-404[oarel]
 * Sector ID    404
 * Check        oarel
 *              , o = 3 , a = 2 , r = 2 , e = 1 , l = 1 
 * max_value    3
 * is real room YES
 * 
 * ------------------------------------------------------------------------------------------
 * Room         totally-real-room-200[decoy]
 * Sector ID    200
 * Check        decoy
 *              , d = 0  ## 0a ##  ## Xa ## , e = 1  ## G ## , c = 0  ## 0b ## , o = 3  ## G ## , y = 1  ## G ## 
 * max_value    3
 * is real room NO
 * 
 * ------------------------------------------------------------------------------------------
 * Room         hjgbwuladw-kusnwfywj-zmfl-ugflsafewfl-450[aezbn]
 * Sector ID    450
 * Check        aezbn
 *              , a = 2  ## Xa ## , e = 1 , z = 1 , b = 1 , n = 1 
 * max_value    5
 * is real room NO
 * 
 * ------------------------------------------------------------------------------------------
 * Room         ktiaaqnqml-ntwemz-uizsmbqvo-642[azvew]
 * Sector ID    642
 * Check        azvew
 *              , a = 2  ## Xa ## , z = 2 , v = 1 , e = 1 , w = 1 
 * max_value    3
 * is real room NO
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 1514
 * Result Part 2 0
 * 
 * </pre> 
 */
public class Day04_SecurityThroughObscurity
{
  public static void main( String[] args )
  {
    String test_input = "aaaaa-bbb-z-y-x-123[abxyz],a-b-c-d-e-f-g-h-987[abcde],not-a-real-room-404[oarel],totally-real-room-200[decoy],hjgbwuladw-kusnwfywj-zmfl-ugflsafewfl-450[aezbn],ktiaaqnqml-ntwemz-uizsmbqvo-642[azvew]";

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
        int result_tls_check = checkRealRoom( input_str );

        result_part_01 += result_tls_check;
      }
    }

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  public static int checkRealRoom( String pInput )
  {
    if ( pInput.isBlank() ) return 0;

    int[] vektor_ascii = new int[ 130 ];

    int sector_id = 0;

    char cur_char = 'a';

    /*
     * ***********************************************************************************
     * Calculating frequency of lower case characters
     * ***********************************************************************************
     */
    int index_parser = 0;

    while ( index_parser < pInput.length() )
    {
      cur_char = pInput.charAt( index_parser );

      if ( cur_char >= '0' && cur_char <= '9' )
      {
        break;
      }

      if ( cur_char >= 'a' && cur_char <= 'z' )
      {
        int cur_char_code = (int) cur_char;

        vektor_ascii[ cur_char_code ]++;
      }

      index_parser++;
    }

    /*
     * ***********************************************************************************
     * Reading sector id
     * ***********************************************************************************
     */
    while ( index_parser < pInput.length() )
    {
      cur_char = pInput.charAt( index_parser );

      index_parser++;

      if ( ( cur_char >= '0' ) && ( cur_char <= '9' ) )
      {
        sector_id = ( sector_id * 10 ) + ( ( (int) cur_char ) - 48 );
      }
      else
      {
        break;
      }
    }

    /*
     * ***********************************************************************************
     * Checking Checksum
     * ***********************************************************************************
     */
    String debug_check_frequency = "";
    String debug_check_string = "";

    cur_char = 'a';

    int knz_is_real_room = 1;

    int min_value = -1;

    int max_value = 0;

    /*
     * Getting the max frequency of all characters
     */
    for ( int i = 32; i < 130; i++ )
    {
      if ( vektor_ascii[ i ] > max_value )
      {
        max_value = vektor_ascii[ i ];
      }
    }

    while ( ( index_parser < pInput.length() ) && ( cur_char >= 'a' && cur_char <= 'z' ) )
    {
      /*
       * get the current character from input
       */
      cur_char = pInput.charAt( index_parser );

      /*
       * check if its a lower case character
       */
      if ( cur_char >= 'a' && cur_char <= 'z' )
      {
        debug_check_string += "" + cur_char;
        debug_check_frequency += ", " + cur_char;

        /*
         * determine the ascii-code for the current char
         */
        int cur_char_code = (int) cur_char;

        debug_check_frequency += " = " + vektor_ascii[ cur_char_code ] + " ";

        if ( min_value == -1 )
        {
          /*
           * If the min-value wasn't set yet, do it now.
           */
          min_value = vektor_ascii[ cur_char_code ];

          /*
           * If the current frequency is 0, than it's an error
           */
          if ( min_value == 0 )
          {
            debug_check_frequency += " ## 0a ## ";

            knz_is_real_room = 0;
          }

          /*
           * If the first frequency is below the max frequency,
           * than its an error. 
           */
          if ( vektor_ascii[ cur_char_code ] < max_value )
          {
            debug_check_frequency += " ## Xa ## ";

            knz_is_real_room = 0;
          }
        }
        else if ( vektor_ascii[ cur_char_code ] > min_value )
        {
          /*
           * If the frequency for the current character is greater than 
           * the min value, than its an error.
           */
          debug_check_frequency += " ## G ## ";

          knz_is_real_room = 0;
        }
        else if ( vektor_ascii[ cur_char_code ] == 0 )
        {
          /*
           * If the frequency for the current character is 0, than its an error
           */
          debug_check_frequency += " ## 0b ## ";

          knz_is_real_room = 0;
        }
        else
        {
          /*
           * If the frequency for the current character is equal or lower 
           * than the current minimum, than set the minimum to the current 
           * character frequency.
           */
          min_value = vektor_ascii[ cur_char_code ];
        }
      }

      index_parser++;
    }

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Room         " + pInput                                   );
    wl( "Sector ID    " + sector_id                                );
    wl( "Check        " + debug_check_string                       );
    wl( "             " + debug_check_frequency                    );
    wl( "max_value    " + max_value                                );
    wl( "is real room " + ( knz_is_real_room == 1 ? "YES" : "NO" ) );

    return ( knz_is_real_room == 1 ? sector_id : 0 );
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day04_input.txt";

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
