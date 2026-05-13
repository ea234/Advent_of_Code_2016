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
    String test_input = "ADVENT,A(1x5)BC,(3x3)XYZ,A(2x2)BCD(2x2)EFG,(6x1)(1x3)A,X(8x2)(3x3)ABCY,(27x12)(20x12)(13x14)(7x10)(1x12)A,(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN";

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
        String decrypted_string = Day09_ParserVersion1.decrypt( input_str );

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
