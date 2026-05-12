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
 * 
 * Decrypt qzmt-zixmtkozy-ivhz
 * Times   343 % 26 = 5
 * Index    0  ASCII  113  q =  16 to 21  =  v
 * Index    1  ASCII  122  z =  25 to 30  =  e
 * Index    2  ASCII  109  m =  12 to 17  =  r
 * Index    3  ASCII  116  t =  19 to 24  =  y
 * Index    4  ASCII   45  - 
 * Index    5  ASCII  122  z =  25 to 30  =  e
 * Index    6  ASCII  105  i =   8 to 13  =  n
 * Index    7  ASCII  120  x =  23 to 28  =  c
 * Index    8  ASCII  109  m =  12 to 17  =  r
 * Index    9  ASCII  116  t =  19 to 24  =  y
 * Index   10  ASCII  107  k =  10 to 15  =  p
 * Index   11  ASCII  111  o =  14 to 19  =  t
 * Index   12  ASCII  122  z =  25 to 30  =  e
 * Index   13  ASCII  121  y =  24 to 29  =  d
 * Index   14  ASCII   45  - 
 * Index   15  ASCII  105  i =   8 to 13  =  n
 * Index   16  ASCII  118  v =  21 to 26  =  a
 * Index   17  ASCII  104  h =   7 to 12  =  m
 * Index   18  ASCII  122  z =  25 to 30  =  e
 * 
 * 
 * 332  international candy coating containment 
 * 855  magnetic plastic grass sales 
 * 724  classified bunny customer service 
 * 765  cryogenic scavenger hunt financing 
 * 970  magnetic bunny financing 
 * 205  projectile scavenger hunt deployment 
 * 194  international dye acquisition 
 * 931  military grade scavenger hunt laboratory 
 * 413  international rabbit acquisition 
 * 
 * 991  northpole object storage
 *  
 * 282  rampaging plastic grass design 
 * 333  magnetic top secret egg financing 
 * 432  radioactive flower technology 
 * 684  classified chocolate storage 
 * 665  rampaging bunny financing 
 * 694  magnetic basket design 
 * 
 * 
 * Decrypt kloqemlib-lygbzq-pqloxdb-
 * Times   991 % 26 = 3
 * Index    0  ASCII  107  k =  10 to 13  =  n
 * Index    1  ASCII  108  l =  11 to 14  =  o
 * Index    2  ASCII  111  o =  14 to 17  =  r
 * Index    3  ASCII  113  q =  16 to 19  =  t
 * Index    4  ASCII  101  e =   4 to  7  =  h
 * Index    5  ASCII  109  m =  12 to 15  =  p
 * Index    6  ASCII  108  l =  11 to 14  =  o
 * Index    7  ASCII  105  i =   8 to 11  =  l
 * Index    8  ASCII   98  b =   1 to  4  =  e
 * Index    9  ASCII   45  - 
 * Index   10  ASCII  108  l =  11 to 14  =  o
 * Index   11  ASCII  121  y =  24 to 27  =  b
 * Index   12  ASCII  103  g =   6 to  9  =  j
 * Index   13  ASCII   98  b =   1 to  4  =  e
 * Index   14  ASCII  122  z =  25 to 28  =  c
 * Index   15  ASCII  113  q =  16 to 19  =  t
 * Index   16  ASCII   45  - 
 * Index   17  ASCII  112  p =  15 to 18  =  s
 * Index   18  ASCII  113  q =  16 to 19  =  t
 * Index   19  ASCII  108  l =  11 to 14  =  o
 * Index   20  ASCII  111  o =  14 to 17  =  r
 * Index   21  ASCII  120  x =  23 to 26  =  a
 * Index   22  ASCII  100  d =   3 to  6  =  g
 * Index   23  ASCII   98  b =   1 to  4  =  e
 * Index   24  ASCII   45  -
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

    wl( decryptShiftCypher( "qzmt-zixmtkozy-ivhz", 343, true ) );

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

    schreibeDatei( "/mnt/hd4tbb/00_Daten/real_room_names.txt", save_real_names );
  }

  private static int checkRealRoom( String pInput )
  {
    if ( pInput.isBlank() ) return 0;

    int[] vector_ascii = new int[ 130 ];

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

        vector_ascii[ cur_char_code ]++;
      }

      index_parser++;
    }

    /*
     * ***********************************************************************************
     * Reading sector id
     * ***********************************************************************************
     */

    int sector_id_index_start = index_parser;

    int sector_id_value = 0;

    while ( index_parser < pInput.length() )
    {
      cur_char = pInput.charAt( index_parser );

      index_parser++;

      if ( ( cur_char >= '0' ) && ( cur_char <= '9' ) )
      {
        sector_id_value = ( sector_id_value * 10 ) + ( ( (int) cur_char ) - 48 );
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
    String debug_check_string    = "";

    cur_char = 'a';

    int knz_is_real_room = 1;

    int min_value = -1;

    int max_value = 0;

    /*
     * Getting the max frequency of all characters
     */
    for ( int vector_index = 97; vector_index < 124; vector_index++ )
    {
      if ( vector_ascii[ vector_index ] > max_value )
      {
        max_value = vector_ascii[ vector_index ];
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
        /*
         * determine the ascii-code for the current char
         */
        int cur_char_code = (int) cur_char;

        debug_check_string    += "" + cur_char;
        
        debug_check_frequency += ", " + cur_char;

        debug_check_frequency += " = " + vector_ascii[ cur_char_code ] + " ";

        if ( min_value == -1 )
        {
          /*
           * If the min-value wasn't set yet, do it now.
           */
          min_value = vector_ascii[ cur_char_code ];

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
          if ( vector_ascii[ cur_char_code ] < max_value )
          {
            debug_check_frequency += " ## Xa ## ";

            knz_is_real_room = 0;
          }
        }
        else if ( vector_ascii[ cur_char_code ] > min_value )
        {
          /*
           * If the frequency for the current character is greater than 
           * the min value, than its an error.
           */
          debug_check_frequency += " ## G ## ";

          knz_is_real_room = 0;
        }
        else if ( vector_ascii[ cur_char_code ] == 0 )
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
          min_value = vector_ascii[ cur_char_code ];
        }
      }

      index_parser++;
    }

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Room         " + pInput                                                                     );
    wl( "Sector ID    " + sector_id_value                                                            );
    wl( "Check        " + debug_check_string                                                         );
    wl( "             " + debug_check_frequency                                                      );
    wl( "max_value    " + max_value                                                                  );
    wl( "is real room " + ( knz_is_real_room == 1 ? "YES" : "NO" )                                   );

    if ( knz_is_real_room == 1 )
    {
      String real_room_name = decryptShiftCypher( pInput.substring( 0, sector_id_index_start ), sector_id_value, false );

      wl( "room name    " + real_room_name );

      save_real_names += "\n" + sector_id_value + "  " + real_room_name;

      if ( real_room_name.indexOf( "northpole object storage" ) >= 0 )
      {
        wl( "---- North Pole -----" );
      }
    }

    return ( knz_is_real_room == 1 ? sector_id_value : 0 );
  }

  private static String       save_real_names = "";

  private static final String ALPHA_2         = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";

  private static final int    ASCII_a         = 97;

  private static String decryptShiftCypher( String pInput, int pShiftXTimes, boolean pKnzDebug )
  {
    String str_result = "";

    int remainder = pShiftXTimes % 26;

    if ( pKnzDebug )
    {
      wl( "" );
      wl( "" );
      wl( "Decrypt " + pInput );
      wl( "Times   " + pShiftXTimes + " % 26 = " + remainder );
    }

    for ( int current_col = 0; current_col < pInput.length(); current_col++ )
    {
      if ( pInput.charAt( current_col ) == '-' )
      {
        str_result += " ";

        if ( pKnzDebug )
        {
          wl( String.format( "Index %4d  ASCII %4d  %s ", current_col, ( (int) pInput.charAt( current_col ) ), pInput.charAt( current_col ) ) );
        }
      }
      else
      {
        int current_char_code = ( (int) pInput.charAt( current_col ) ) - ASCII_a;

        if ( pKnzDebug )
        {
          wl( String.format( "Index %4d  ASCII %4d  %s =  %2d to %2d  =  %s", current_col, ( (int) pInput.charAt( current_col ) ), pInput.charAt( current_col ), current_char_code, ( current_char_code + remainder ), "" + ALPHA_2.charAt( current_char_code + remainder ) ) );
        }

        str_result += ALPHA_2.charAt( current_char_code + remainder );
      }
    }

    return str_result;
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

  private static boolean schreibeDatei( String pDateiName, String pInhalt )
  {
    try
    {
      FileWriter output_stream = new FileWriter( pDateiName, false );

      if ( pInhalt != null )
      {
        output_stream.write( pInhalt );
      }

      /*
       * Aufruf von "stream.flush()"
       */
      output_stream.flush();

      output_stream.close();

      output_stream = null;

      return true;
    }
    catch ( Exception err_inst )
    {
    }

    return false;
  }
}
