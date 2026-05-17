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
 * </pre> 
 */
public class Day21_ScrambledLettersAndHash
{
  public static void main( String[] args )
  {
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

    //calculate01( getListProd(), "abcdefgh" false );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, String pStart, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pStart, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, String pStart, boolean pKnzDebug )
  {
    byte[] password = pStart.getBytes();

    int result_part_01 = 0;
    int result_part_02 = 0;

    for ( String input_str : pListInput )
    {
      if ( input_str.isBlank() )
      {
      }
      else if ( input_str.startsWith( "swap position " ) )
      {
        int position_x = ( (int) input_str.charAt( 14 ) ) - 48;
        int position_y = ( (int) input_str.charAt( 30 ) ) - 48;

        byte temp = password[ position_x ];

        password[ position_x ] = password[ position_y ];

        password[ position_y ] = temp;

        wl( "swap position X with position Y  -  " + position_x + " " + position_y );
      }
      else if ( input_str.startsWith( "swap letter " ) )
      {
        byte ascii_x = (byte) ( (int) input_str.charAt( 12 ) );
        byte ascii_y = (byte) ( (int) input_str.charAt( 26 ) );

        for ( int idx = 0; idx < password.length; idx++ )
        {
          if ( password[ idx ] == ascii_x )
          {
            password[ idx ] = ascii_y;
          }
          else if ( password[ idx ] == ascii_y )
          {
            password[ idx ] = ascii_x;
          }
        }

        wl( "swap letter X with letter Y" );
      }
      else if ( input_str.startsWith( "rotate left " ) )
      {
        int x_steps = ( (int) input_str.charAt( 12 ) ) - 48;

        for ( int step_counter = 0; step_counter < x_steps; step_counter++ )
        {
          byte save_first_byte = password[ 0 ];

          for ( int idx = 0; idx < ( password.length - 1 ); idx++ )
          {
            password[ idx ] = password[ idx + 1 ];
          }

          password[ password.length - 1 ] = save_first_byte;
        }

        wl( "rotate left X steps" );
      }
      else if ( input_str.startsWith( "rotate right " ) )
      {
        int x_steps = ( (int) input_str.charAt( 13 ) ) - 48;

        for ( int step_counter = 0; step_counter < x_steps; step_counter++ )
        {
          byte save_first_byte = password[ password.length - 1 ];

          for ( int idx = password.length - 1; idx > 0; idx-- )
          {
            password[ idx ] = password[ idx - 1 ];
          }

          password[ 0 ] = save_first_byte;
        }

        wl( "rotate right X steps" );
      }
      else if ( input_str.startsWith( "move position " ) )
      {
        wl( "move position X to position Y" );
      }
      else if ( input_str.startsWith( "rotate based on position of letter" ) )
      {
        wl( "rotate based on position of letter X" );
      }
      else if ( input_str.startsWith( "reverse positions " ) )
      {
        wl( "reverse positions X through Y" );
      }
    }

    wl( new String( password ) );
    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
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
