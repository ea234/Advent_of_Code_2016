package de.ea234.aoc2016.day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 22: Grid Computing ---
 * https://adventofcode.com/2016/day/22
 * 
 * https://www.reddit.com/r/adventofcode/comments/5jor9q/2016_day_22_solutions/
 * 
 * 
 *    1 A    0 B  439 = USED A   66  AVAIL B    94 
 *    2 A    1 B  439 = USED A   66  AVAIL B    94 
 *    3 A    2 B  439 = USED A   65  AVAIL B    94 
 *    4 A    3 B  439 = USED A   67  AVAIL B    94 
 *    5 A    4 B  439 = USED A   67  AVAIL B    94 
 *    6 A    5 B  439 = USED A   69  AVAIL B    94 
 *    7 A    6 B  439 = USED A   64  AVAIL B    94 
 *    8 A    7 B  439 = USED A   71  AVAIL B    94 
 *    9 A    8 B  439 = USED A   71  AVAIL B    94 
 *   10 A    9 B  439 = USED A   64  AVAIL B    94 
 *   11 A   10 B  439 = USED A   72  AVAIL B    94 
 *   12 A   11 B  439 = USED A   72  AVAIL B    94
 *   ...
 *  947 A  979 B  439 = USED A   67  AVAIL B    94 
 *  948 A  980 B  439 = USED A   69  AVAIL B    94 
 *  949 A  981 B  439 = USED A   67  AVAIL B    94 
 *  950 A  982 B  439 = USED A   67  AVAIL B    94 
 *  951 A  983 B  439 = USED A   67  AVAIL B    94 
 *  952 A  984 B  439 = USED A   70  AVAIL B    94 
 *  953 A  985 B  439 = USED A   73  AVAIL B    94 
 *  954 A  986 B  439 = USED A   66  AVAIL B    94 
 *  955 A  987 B  439 = USED A   73  AVAIL B    94 
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 955
 * Result Part 2 0
 * 
 * 
 * </pre> 
 */
public class Day22_GridComputing
{
  public static void main( String[] args )
  {
    String test_input = "";

    test_input += "root@ebhq-gridcenter# df -h,";
    test_input += "Filesystem              Size  Used  Avail  Use%,";
    test_input += "/dev/grid/node-x0-y0     85T   66T    19T   77%,";
    test_input += "/dev/grid/node-x0-y1     93T   66T    27T   70%,";
    test_input += "/dev/grid/node-x0-y2     93T   65T    28T   69%,";
    test_input += "/dev/grid/node-x0-y3     90T   67T    23T   74%,";
    test_input += "/dev/grid/node-x0-y4     94T   67T    27T   71%,";
    test_input += "/dev/grid/node-x0-y5     92T   69T    23T   75%,";
    test_input += "/dev/grid/node-x0-y6     88T   64T    24T   72%,";
    test_input += "/dev/grid/node-x0-y7     86T   71T    15T   82%,";
    test_input += "/dev/grid/node-x0-y8     94T   71T    23T   75%,";
    test_input += "/dev/grid/node-x0-y9     92T   64T    28T   69%,";
    test_input += "/dev/grid/node-x0-y10    91T   72T    19T   79%,";
    test_input += "/dev/grid/node-x0-y11    87T   72T    15T   82%,";

    //calculatePart01( test_input, true );

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

    List< Day22Node > node_list = new ArrayList< Day22Node >();

    for ( String input_str : pListInput )
    {
      if ( input_str.startsWith( "/dev/grid/node" ) )
      {
        node_list.add( new Day22Node( input_str ) );
      }
    }

    int size_list = node_list.size();

    for ( int idx_a = 0; idx_a < size_list; idx_a++ )
    {
      Day22Node node_a = node_list.get( idx_a );

      if ( node_a.getUsed() > 0 )
      {
        for ( int idx_b = 0; idx_b < size_list; idx_b++ )
        {
          Day22Node node_b = node_list.get( idx_b );

          if ( idx_a != idx_b )
          {
            if ( node_a.getUsed() <= node_b.getAvail() )
            {
              result_part_01++;

              wl( String.format( "%4d A %4d B %4d = USED A %4d  AVAIL B  %4d ", result_part_01, idx_a, idx_b, node_a.getUsed(), node_b.getAvail() ) );

              break;
            }
          }
        }
      }
    }

    wl( "" );
    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day22_input.txt";

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
