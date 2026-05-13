package de.ea234.aoc2016.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 9: Explosives in Cyberspace ---
 * https://adventofcode.com/2016/day/9
 * 
 * https://www.reddit.com/r/adventofcode/comments/5hbygy/2016_day_9_solutions/
 * 
 *  
 * ADVENT              =     6  ADVENT
 * A(1X5)BC            =     7  ABBBBBC
 * (3X3)XYZ            =     9  XYZXYZXYZ
 * A(2X2)BCD(2X2)EFG   =    11  ABCBCDEFEFG
 * (6X1)(1X3)A         =     6  (1x3)A
 * X(8X2)(3X3)ABCY     =    18  X(3x3)ABC(3x3)ABCY
 * 
 * </pre> 
 */
public class Day09_ExplosivesInCyberspace
{
  public static void main( String[] args )
  {
    String test_input = "ADVENT,A(1x5)BC,(3x3)XYZ,A(2x2)BCD(2x2)EFG,(6x1)(1x3)A,X(8x2)(3x3)ABCY,";

    calculatePart01( test_input, true );

    calculate01( getListProd(), false );

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
        String decrypted_string = decrypt( input_str );

        if ( pKnzDebug )
        {
          wl( String.format( "%-19S = %5d  %s", input_str, decrypted_string.length(), decrypted_string ) );

          result_part_01 = decrypted_string.length();
        }
      }
    }

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static String parser_str_input = "";

  private static char   parser_cur_char  = ' ';

  private static int    parser_index     = 0;

  public static String decrypt( String pInput )
  {
    /*
     * Reusing parser from 
     * https://github.com/ea234/Advent_of_Code_2020/blob/main/src/day_18__Operation_Order.ts
     */

    if ( pInput.isBlank() ) return "";

    parser_str_input = pInput;

    parser_cur_char = ' ';

    parser_index = 0;

    String decryptet_string = "";

    while ( read() )
    {
      if ( ( ( parser_cur_char >= 'A' ) && ( parser_cur_char <= 'Z' ) ) || ( ( parser_cur_char >= 'a' ) && ( parser_cur_char <= 'z' ) ) )
      {
        decryptet_string += parser_cur_char;
      }
      else if ( parser_cur_char == '(' )
      {
        if ( parser_index > 5010 ) 
        {
          wl( "Break" );
        }
        
        read();

        int marker_character_count = parseNumber();

        if ( parser_cur_char != 'x' ) wl( "Error: character 'x' expected" );

        read();

        int marker_repeat = parseNumber();

        if ( parser_cur_char != ')' ) wl( "Error: character ')' expected" );

        String sub_string = parser_str_input.substring( parser_index, parser_index + marker_character_count );

        //try
        //{
        //  sub_string = parser_str_input.substring( parser_index, parser_index + marker_character_count );
        //}
        //catch ( Exception exp )
        //{
        //  wl( exp.toString() );
        //
        //  wl( "[" + marker_character_count + " x " + marker_repeat + "] " + parser_index + " " + parser_str_input.length() );
        //
        //  wl( parser_str_input.substring( Math.max( parser_index - 40, 0 ), Math.min( parser_index + 10, parser_str_input.length() ) ) );
        //}

        read();

        //decryptet_string += "[" + marker_character_count + " x " + marker_repeat + "]";

        decryptet_string += sub_string.repeat( marker_repeat );

        parser_index += marker_character_count;

        parser_index--;

      }
    }

    return decryptet_string;
  }

  private static boolean read()
  {
    /*
     * If no input string is left to read, return false
     */
    if ( parser_index >= parser_str_input.length() ) return false;

    /*
     * Update the cur_char-variable with the current value
     */
    parser_cur_char = parser_str_input.charAt( parser_index );

    /*
     * Move the read-index one further to the string end
     */
    parser_index++;

    /*
     * Return true for one read char
     */
    return true;
  }

  private static int parseNumber()
  {
    /*
     * Consume the first read number.
     */
    int number_read = ( (int) parser_cur_char ) - 48;

    /*
     * Read the rest of the number
     */
    while ( readNumber() )
    {
      number_read = ( number_read * 10 ) + ( ( (int) parser_cur_char ) - 48 );
    }

    if ( parser_index >= parser_str_input.length() ) return number_read;

    // parser_index--;

    return number_read;
  }

  private static boolean readNumber()
  {
    /*
     * Read the next character from the input
     */
    if ( read() == false )
    {
      return false;
    }

    /*
     * return true, if the new character is a number
     */
    return ( ( parser_cur_char >= '0' ) && ( parser_cur_char <= '9' ) );
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day09_input.txt";

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
