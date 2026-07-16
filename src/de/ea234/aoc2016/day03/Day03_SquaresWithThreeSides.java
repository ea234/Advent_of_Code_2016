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
 * https://github.com/ea234/Advent_of_Code_2016/blob/main/src/de/ea234/aoc2016/day03/Day03_SquaresWithThreeSides.java
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

    int result_part_02 = 0;

    List< Integer > x_col_a = new ArrayList< Integer >();
    List< Integer > x_col_b = new ArrayList< Integer >();
    List< Integer > x_col_c = new ArrayList< Integer >();

    for ( String input_str : pListInput )
    {
      int triangle_ok = 1;

      if ( !input_str.isBlank() )
      {
        List< String > triangle = Arrays.stream( input_str.trim().replaceAll( " {2,}", " " ).split( " " ) ).map( String::trim ).collect( Collectors.toList() );

        int side_a = Integer.parseInt( triangle.get( 0 ) );
        int side_b = Integer.parseInt( triangle.get( 1 ) );
        int side_c = Integer.parseInt( triangle.get( 2 ) );

        if ( ( side_a + side_b ) <= side_c )
        {
          triangle_ok = 0;
        }
        else if ( ( side_a + side_c ) <= side_b )
        {
          triangle_ok = 0;
        }
        else if ( ( side_c + side_b ) <= side_a )
        {
          triangle_ok = 0;
        }

        x_col_a.add( Integer.valueOf( side_a ) );
        x_col_b.add( Integer.valueOf( side_b ) );
        x_col_c.add( Integer.valueOf( side_c ) );
      }

      wl( input_str + "    " + ( triangle_ok == 1 ? " OK " : "" ) );

      result_part_01 += triangle_ok;
    }

    int count_a = countX( x_col_a );
    int count_b = countX( x_col_b );
    int count_c = countX( x_col_c );

    wl( "" );
    wl( "count_a " + count_a );
    wl( "count_b " + count_b );
    wl( "count_c " + count_c );

    x_col_a.sort( null );
    x_col_b.sort( null );
    x_col_c.sort( null );

    count_a = countX( x_col_a );
    count_b = countX( x_col_b );
    count_c = countX( x_col_c );

    result_part_02 = count_a + count_b + count_c;

    wl( "" );
    wl( "count_a " + count_a );
    wl( "count_b " + count_b );
    wl( "count_c " + count_c );
    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  private static int countX1( List< Integer > pVector )
  {
    int count_nr = 0;

    int idx = 0;

    while ( idx < pVector.size() - 2 )
    {
      int mod_100_a = ( pVector.get( idx ).intValue() / 100 ) % 100;
      int mod_100_b = ( pVector.get( idx + 1 ).intValue() / 100 ) % 100;
      int mod_100_c = ( pVector.get( idx + 2 ).intValue() / 100 ) % 100;

      if ( ( mod_100_a == mod_100_b ) && ( mod_100_a == mod_100_c ) && ( mod_100_b == mod_100_c ) )
      {
        count_nr++;

        idx += 3;
      }
      else
      {
        idx++;
      }
    }

    return count_nr;
  }

  private static int countX( List< Integer > pVector )
  {
    int count_nr = 0;

    int idx = 0;

    while ( idx < pVector.size() - 2 )
    {
      int mod_100_a = ( pVector.get( idx ).intValue() / 100 ) % 100;
      int mod_100_b = ( pVector.get( idx + 1 ).intValue() / 100 ) % 100;
      int mod_100_c = ( pVector.get( idx + 2 ).intValue() / 100 ) % 100;

      if ( ( mod_100_a == mod_100_b ) && ( mod_100_a == mod_100_c ) && ( mod_100_b == mod_100_c ) )
      {
        count_nr++;

        idx += 3;
      }
      else
      {
        idx += 3;
      }
    }

    return count_nr;
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
