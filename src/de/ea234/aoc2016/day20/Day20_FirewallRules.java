package de.ea234.aoc2016.day20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 20: Firewall Rules ---
 * https://adventofcode.com/2016/day/20
 * 
 * https://www.reddit.com/r/adventofcode/comments/5jbeqo/2016_day_20_solutions/
 *
 * 
 * similar to https://adventofcode.com/2025/day/5
 * 
 * https://github.com/ea234/Advent_of_Code_2025/blob/main/src/de/ea234/day5/Day05Cafeteria.java
 * 
 * 
 *
 * ----------------------------------------------------------------------------
 * 
 * Nr   0   Range            0 -     32259705  Count     32259706
 * Nr   1   Range     32259707 -     72469719  Count     40210013
 * Nr   2   Range     72469721 -    110043168  Count     37573448
 * Nr   3   Range    110043170 -    133335483  Count     23292314
 * ...
 * Nr 109   Range   4109128952 -   4149931650  Count     40802699
 * Nr 110   Range   4149931652 -   4201193307  Count     51261656
 * Nr 111   Range   4201193309 -   4226877817  Count     25684509
 * Nr 112   Range   4226877819 -   4272117350  Count     45239532
 * Nr 113   Range   4272117352 -   4294967295  Count     22849944
 * 
 * ranges_from_input 1075
 * ranges_reduced    114
 * merged ranges     961
 * 
 * IP's total   4294967296
 * IP's blocked 4294967183
 * IP's allowed 113
 * 
 * Result Part 1 32259706
 * Result Part 2 113
 * 
 * </pre> 
 */
public class Day20_FirewallRules
{
  private static final int KNZ_RANGE_STATUS_MERGED = 1;

  private static final int KNZ_RANGE_STATUS_NEW    = 3;

  public static void main( String[] args )
  {
    calculate01( getListProd(), true );

    System.exit( 0 );
  }

  private static void calculate01( List< String > pListInput, boolean pKnzDebug )
  {
    wl( "" );
    wl( "----------------------------------------------------------------------" );

    List< Day20Range > range_list = new ArrayList< Day20Range >();

    for ( String input_str : pListInput )
    {
      Day20Range new_range = new Day20Range( input_str );

      range_list.add( new_range );

      wl( input_str );
    }

    range_list.sort( ( range1, range2 ) -> range1.getMinValue().compareTo( range2.getMinValue() ) );

    int ranges_from_input = range_list.size();

    range_list = reduceRanges( range_list, pKnzDebug );

    int ranges_reduced = range_list.size();

    int result_part_01 = 0;

    long ip_addresses_total = 4294967295l + 1;

    long ip_addresses_blocked = 0l;

    long index_nr = 0;

    for ( Day20Range range : range_list )
    {
      ip_addresses_blocked += range.getRangeValues();

      wl( String.format( "Nr %3d ", index_nr ) + "  " + range.toString() );

      index_nr++;
    }

    long ip_addresses_allowed = ip_addresses_total - ip_addresses_blocked;

    wl( "" );
    wl( "ranges_from_input " + ranges_from_input );
    wl( "ranges_reduced    " + ranges_reduced );
    wl( "merged ranges     " + ( ranges_from_input - ranges_reduced ) );
    wl( "" );
    wl( "IP's total   " + ip_addresses_total );
    wl( "IP's blocked " + ip_addresses_blocked );
    wl( "IP's allowed " + ip_addresses_allowed );
    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + ip_addresses_allowed );
    wl( "" );
  }

  private static List< Day20Range > reduceRanges( List< Day20Range > pList, boolean pKnzDebug )
  {
    if ( pKnzDebug )
    {
      wl( "" );
      wl( "Reducing Number of Ranges" );
      wl( "" );
    }

    List< Day20Range > range_list_old = pList;

    List< Day20Range > range_list_new = new ArrayList< Day20Range >();

    int loop_count = 0;

    boolean knz_do_loop = true;

    while ( knz_do_loop )
    {
      loop_count++;

      if ( pKnzDebug )
      {
        wl( "Reducing " + loop_count );
      }

      for ( Day20Range range_from_old_list : range_list_old )
      {
        int range_status = KNZ_RANGE_STATUS_NEW;

        int range_index_while = 0;

        while ( ( range_index_while < range_list_new.size() ) && ( range_status == KNZ_RANGE_STATUS_NEW ) )
        {
          Day20Range range_from_new_list = range_list_new.get( range_index_while );

          if ( range_from_new_list.isValueNeighbour( range_from_old_list.getMinValue() ) )
          {
            range_status = KNZ_RANGE_STATUS_MERGED;

            range_from_new_list.mergeRange( range_from_old_list.getMinValue(), range_from_old_list.getMaxValue() );
          }
          else
          {
            range_index_while++;
          }
        }

        if ( range_status == KNZ_RANGE_STATUS_NEW )
        {
          range_list_new.add( range_from_old_list );
        }
      }

      range_list_new.sort( ( range1, range2 ) -> range1.getMinValue().compareTo( range2.getMinValue() ) );

      if ( range_list_new.size() != range_list_old.size() )
      {
        range_list_old = range_list_new;

        range_list_new = new ArrayList< Day20Range >();
      }
      else
      {
        knz_do_loop = false;
      }
    }

    return range_list_new;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day20_input.txt";

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
