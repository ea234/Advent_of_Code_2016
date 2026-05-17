package de.ea234.aoc2016.day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 22: Grid Computing ---
 * https://adventofcode.com/2016/day/21
 * 
 * https://www.reddit.com/r/adventofcode/comments/5ji29h/2016_day_21_solutions/
 * 
 * ----------------------------------------------------------------------
 * 0123456789 0823456719 CMD swap position 1 with position 8
 * 0123456789 9123456780 CMD swap position 0 with position 9
 * 
 * AAACCCBBBB BBBCCCAAAA CMD swap letter A with letter B
 * 
 * 0123456789 9012345678 CMD rotate right 1 step
 * 0123456789 8901234567 CMD rotate right 2 step
 * 
 * 0123456789 1234567890 CMD rotate left 1 step
 * 0123456789 2345678901 CMD rotate left 2 step
 * 
 * abdec ecabd CMD rotate based on position of letter b
 * ecabd decab CMD rotate based on position of letter d
 * 
 * bcdea bdeac CMD move position 1 to position 4
 * bdeac abdec CMD move position 3 to position 0
 * 
 * 0123456789 4321056789 CMD reverse positions 0 through 4
 * 
 * ----------------------------------------------------------------------
 *  abcde  |  
 *  ebcda  |  swap position 4 with position 0
 *  edcba  |  swap letter d with letter b
 *  abcde  |  reverse positions 0 through 4
 *  bcdea  |  rotate left 1 step
 *  bdeac  |  move position 1 to position 4
 *  abdec  |  move position 3 to position 0
 *  ecabd  |  rotate based on position of letter b
 *  decab  |  rotate based on position of letter d
 * 
 * Result Part 1 decab
 * Result Part 2 0
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 aefgbcdh
 * Result Part 2 0
 * 
 * </pre> 
 */
public class Day21_ScrambledLettersAndHash
{
  public static void main( String[] args )
  {
    test();

    String test_input = "";

    test_input += ",swap position 4 with position 0";
    test_input += ",swap letter d with letter b";
    test_input += ",reverse positions 0 through 4";
    test_input += ",rotate left 1 step";
    test_input += ",move position 1 to position 4";
    test_input += ",move position 3 to position 0";
    test_input += ",rotate based on position of letter b";
    test_input += ",rotate based on position of letter d";

    calculatePart01( test_input, "abcde", true );

    calculate01( getListProd(), "abcdefgh", false );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, String pStart, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pStart, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, String pStart, boolean pKnzDebug )
  {
    wl( "" );
    wl( "----------------------------------------------------------------------" );

    byte[] password = pStart.getBytes();

    int result_part_02 = 0;

    for ( String input_str : pListInput )
    {
      doCmd( password, input_str );

      wl( " " + ( new String( password ) ) + "  |  " + input_str );
    }

    wl( "" );
    wl( "Result Part 1 " + new String( password ) );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static void test()
  {
    String test_input = "0123456789";

    wl( "" );
    wl( "----------------------------------------------------------------------" );

    doTestCmd( test_input, "swap position 1 with position 8" );
    doTestCmd( test_input, "swap position 0 with position 9" );

    wl( "" );
    doTestCmd( "AAACCCBBBB", "swap letter A with letter B" );

    wl( "" );
    doTestCmd( test_input, "rotate right 1 step" );
    doTestCmd( test_input, "rotate right 2 step" );

    wl( "" );
    doTestCmd( test_input, "rotate left 1 step" );
    doTestCmd( test_input, "rotate left 2 step" );

    wl( "" );
    doTestCmd( "abdec", "rotate based on position of letter b" );
    doTestCmd( "ecabd", "rotate based on position of letter d" );

    wl( "" );
    doTestCmd( "bcdea", "move position 1 to position 4" );
    doTestCmd( "bdeac", "move position 3 to position 0" ); // abdec

    wl( "" );
    doTestCmd( "0123456789", "reverse positions 0 through 4" );
  }

  private static void doTestCmd( String pString, String pCmd )
  {
    byte[] byte_array = pString.getBytes();

    doCmd( byte_array, pCmd );

    wl( pString + " " + ( new String( byte_array ) ) + " CMD " + pCmd );
  }

  private static void doCmd( byte[] pByteArray, String pCmd )
  {
    if ( pCmd.isBlank() )
    {
    }
    else if ( pCmd.startsWith( "swap position " ) )
    {
      int position_x = ( (int) pCmd.charAt( 14 ) ) - 48;
      int position_y = ( (int) pCmd.charAt( 30 ) ) - 48;

      byte temp = pByteArray[ position_x ];

      pByteArray[ position_x ] = pByteArray[ position_y ];

      pByteArray[ position_y ] = temp;
    }
    else if ( pCmd.startsWith( "swap letter " ) )
    {
      byte ascii_x = (byte) ( (int) pCmd.charAt( 12 ) );
      byte ascii_y = (byte) ( (int) pCmd.charAt( 26 ) );

      for ( int idx = 0; idx < pByteArray.length; idx++ )
      {
        if ( pByteArray[ idx ] == ascii_x )
        {
          pByteArray[ idx ] = ascii_y;
        }
        else if ( pByteArray[ idx ] == ascii_y )
        {
          pByteArray[ idx ] = ascii_x;
        }
      }
    }
    else if ( pCmd.startsWith( "rotate left " ) )
    {
      int x_steps = ( (int) pCmd.charAt( 12 ) ) - 48;

      for ( int step_counter = 0; step_counter < x_steps; step_counter++ )
      {
        byte save_first_byte = pByteArray[ 0 ];

        for ( int idx = 0; idx < ( pByteArray.length - 1 ); idx++ )
        {
          pByteArray[ idx ] = pByteArray[ idx + 1 ];
        }

        pByteArray[ pByteArray.length - 1 ] = save_first_byte;
      }
    }
    else if ( pCmd.startsWith( "rotate right " ) )
    {
      int x_steps = ( (int) pCmd.charAt( 13 ) ) - 48;

      for ( int step_counter = 0; step_counter < x_steps; step_counter++ )
      {
        byte save_first_byte = pByteArray[ pByteArray.length - 1 ];

        for ( int idx = pByteArray.length - 1; idx > 0; idx-- )
        {
          pByteArray[ idx ] = pByteArray[ idx - 1 ];
        }

        pByteArray[ 0 ] = save_first_byte;
      }
    }
    else if ( pCmd.startsWith( "move position " ) )
    {
      int index_start = ( (int) pCmd.charAt( 14 ) ) - 48;
      int index_end   = ( (int) pCmd.charAt( 28 ) ) - 48;

      byte save_first_byte = pByteArray[ index_start ];

      int idx = index_start;

      if ( index_start < index_end )
      {
        while ( idx != index_end )
        {
          pByteArray[ idx ] = pByteArray[ incIndex( idx, pByteArray.length ) ];

          idx = incIndex( idx, pByteArray.length );
        }
      }
      else
      {
        while ( idx != index_end )
        {
          pByteArray[ idx ] = pByteArray[ decIndex( idx, pByteArray.length ) ];

          idx = decIndex( idx, pByteArray.length );
        }
      }

      pByteArray[ index_end ] = save_first_byte;
    }
    else if ( pCmd.startsWith( "rotate based on position of letter" ) )
    {
      byte ascii_letter = (byte) ( (int) pCmd.charAt( 35 ) );

      int letter_index = 0;

      while ( ( letter_index < pByteArray.length ) && ( pByteArray[ letter_index ] != ascii_letter ) )
      {
        letter_index++;
      }

      int rotate_one_time = 1;

      int rotate_additional_one_time = ( letter_index >= 4 ? 1 : 0 );

      int x_steps = rotate_one_time + letter_index + rotate_additional_one_time;

      for ( int step_counter = 0; step_counter < x_steps; step_counter++ )
      {
        byte save_first_byte = pByteArray[ pByteArray.length - 1 ];

        for ( int idx = pByteArray.length - 1; idx > 0; idx-- )
        {
          pByteArray[ idx ] = pByteArray[ idx - 1 ];
        }

        pByteArray[ 0 ] = save_first_byte;
      }
    }
    else if ( pCmd.startsWith( "reverse positions " ) )
    {
      int index_start = ( (int) pCmd.charAt( 18 ) ) - 48;
      int index_end   = ( (int) pCmd.charAt( 28 ) ) - 48;

      if ( index_end < index_start )
      {
        int index_save = index_end;

        index_end = index_start;

        index_start = index_save;
      }

      while ( ( index_start < index_end ) && ( index_start != index_end ) )
      {
        byte temp = pByteArray[ index_end ];

        pByteArray[ index_end ] = pByteArray[ index_start ];

        pByteArray[ index_start ] = temp;

        index_end--;
        index_start++;
      }
    }
  }

  private static int incIndex( int pIndex, int pMaxIndex )
  {
    if ( ( pIndex + 1 ) >= pMaxIndex ) return 0;

    return pIndex + 1;
  }

  private static int decIndex( int pIndex, int pMaxIndex )
  {
    if ( ( pIndex - 1 ) < 0 ) return pMaxIndex - 1;

    return pIndex - 1;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day21_input.txt";

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
