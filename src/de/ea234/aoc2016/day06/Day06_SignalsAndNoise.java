package de.ea234.aoc2016.day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 6: Signals and Noise ---
 * https://adventofcode.com/2016/day/6
 * 
 * https://www.reddit.com/r/adventofcode/comments/5gr0xf/2016_day_6_solutions/
 * 
 * 
 * Col 0 Max 101 e = 3  Min 97  a = 1
 * Col 1 Max 97  a = 3  Min 100 d = 1
 * Col 2 Max 115 s = 3  Min 118 v = 1
 * Col 3 Max 116 t = 3  Min 101 e = 1
 * Col 4 Max 101 e = 3  Min 110 n = 1
 * Col 5 Max 114 r = 3  Min 116 t = 1
 * 
 * Result Part 1 easter
 * Result Part 2 advent
 * 
 * 
 * Col 0 Max 109 m = 22  Min 97  a = 20
 * Col 1 Max 115 s = 22  Min 112 p = 20
 * Col 2 Max 104 h = 22  Min 102 f = 20
 * Col 3 Max 106 j = 22  Min 101 e = 20
 * Col 4 Max 110 n = 22  Min 101 e = 20
 * Col 5 Max 100 d = 22  Min 101 e = 20
 * Col 6 Max 117 u = 22  Min 98  b = 20
 * Col 7 Max 99  c = 22  Min 122 z = 20
 * 
 * Result Part 1 mshjnduc
 * Result Part 2 apfeeebz
 * 
 * </pre> 
 */
public class Day06_SignalsAndNoise
{
  public static void main( String[] args )
  {
    String test_input = "eedadn,drvtee,eandsr,raavrd,atevrs,tsrnev,sdttsa,rasrtv,nssdts,ntnada,svetve,tesnvt,vntsnd,vrdear,dvrsen,enarar";

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
    String result_part_01 = "";

    String result_part_02 = "";

    int input_length = pListInput.get( 0 ).length();

    for ( int cur_col = 0; cur_col < input_length; cur_col++ )
    {
      int[] vektor_ascii = new int[ 130 ];

      for ( String input_str : pListInput )
      {
        if ( !input_str.isBlank() )
        {
          int cur_char_code = input_str.charAt( cur_col );

          vektor_ascii[ cur_char_code ]++;
        }
      }

      int max_occurance_val = 0;
      int max_occurance_idx = 0;

      int min_occurance_val = Integer.MAX_VALUE;
      int min_occurance_idx = 0;

      for ( int i = 32; i < 127; i++ )
      {
        if ( vektor_ascii[ i ] > max_occurance_val )
        {
          max_occurance_val = vektor_ascii[ i ];
          max_occurance_idx = i;
        }

        if ( ( vektor_ascii[ i ] > 0 ) && ( vektor_ascii[ i ] < min_occurance_val ) )
        {
          min_occurance_val = vektor_ascii[ i ];
          min_occurance_idx = i;
        }
      }

      wl( "Col " + cur_col + " Max " + max_occurance_idx + " " + ( (char) max_occurance_idx ) + " = " + max_occurance_val + "  Min " + min_occurance_idx + " " + ( (char) min_occurance_idx ) + " = " + min_occurance_val );

      result_part_01 += "" + ( (char) max_occurance_idx );
      result_part_02 += "" + ( (char) min_occurance_idx );
    }

    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day06_input.txt";

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
