package de.ea234.aoc2016.day03;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * --- Day 3: Squares With Three Sides ---
 * https://adventofcode.com/2016/day/3
 * 
 * https://www.reddit.com/r/adventofcode/comments/5g80ck/2016_day_3_solutions/
 * 
 * 
 * </pre>
 */
public class Day03_SquaresWithThreeSides
{
  public static void main( String[] args )
  {
    String test_input = " 5 10 25, 523  604  270,  855  318    7";

    calculatePart01( test_input, true );

    calculate01( getListProd(), true );

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
    
    String result_part_02 = "";
    
    for ( String input_str : pListInput )
    {
      int triangle_ok = checkTriangle( input_str );
      
      wl( input_str + "    " + ( triangle_ok == 1 ? " OK " : "" ) );
      
      result_part_01 += triangle_ok; 
    }

    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  public static int checkTriangle( String pInput )
  {
    if ( pInput.isBlank() ) return 0;
    
    List< String > triangle = Arrays.stream( pInput.trim().replaceAll(" {2,}", " ").split( " " ) ).map( String::trim ).collect( Collectors.toList() );

    int side_a = Integer.parseInt( triangle.get( 0 ) );
    int side_b = Integer.parseInt( triangle.get( 1 ) );
    int side_c = Integer.parseInt( triangle.get( 2 ) );

    if ( ( side_a + side_b ) <= side_c ) { return 0; }
    if ( ( side_a + side_c ) <= side_b ) { return 0; }
    if ( ( side_c + side_b ) <= side_a ) { return 0; }
    
    return 1;    
  }


  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day03_input.txt";

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
