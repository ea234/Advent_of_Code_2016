
package de.ea234.aoc2016.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <pre>
 * --- Day 1: No Time for a Taxicab ---
 * https://adventofcode.com/2016/day/1
 * 
 * https://www.reddit.com/r/adventofcode/comments/5fur6q/2016_day_1_solutions
 * 
 * </pre>
 */
public class Day01_NoTimeForATaxicab
{
  private static final char   FACING_NORTH          = 'N';

  private static final char   FACING_SOUTH          = 'S';

  private static final char   FACING_EAST           = 'E';

  private static final char   FACING_WEST           = 'W';

  private static final char   TURN_LEFT             = 'L';

  private static final char   TURN_RIGHT            = 'R';

  private static final String STR_COMBINE_SPACER    = "      ";

  public static void main( String[] args )
  {
    String test_input = "R2, L3;R2, R2, R2;R5, L5, R5, R3;R8, R4, R4, R8";

    calculatePart01( test_input, true );

    //calculatePart01( getListProd(), true );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( ";" ) ).map( String::trim ).collect( Collectors.toList() );

    for ( String input_str : converted_string_list )
    {
      calculate01( input_str, pKnzDebug );
    }
  }

  private static char getNewFacing90( char pCurFacing, char pTurnDirection )
  {
    if ( pTurnDirection == TURN_LEFT )
    {
      if ( pCurFacing == FACING_NORTH ) return FACING_WEST;
      if ( pCurFacing == FACING_WEST  ) return FACING_SOUTH;
      if ( pCurFacing == FACING_SOUTH ) return FACING_EAST;
      //if ( pCurFacing === FACING_EAST  ) return FACING_NORTH;
    }
    else if ( pTurnDirection == TURN_RIGHT )
    {
      if ( pCurFacing == FACING_NORTH ) return FACING_EAST;
      if ( pCurFacing == FACING_EAST  ) return FACING_SOUTH;
      if ( pCurFacing == FACING_SOUTH ) return FACING_WEST;
      //if ( pCurFacing === FACING_WEST  ) return FACING_NORTH;
    }

    return FACING_NORTH;
  }

  private static void calculate01( String pListInput, boolean pKnzDebug )
  {
    /*
     * *******************************************************************************************************
     * Initializing Variables
     * *******************************************************************************************************
     */
    int result_part_02 = 0;

    List< String > direction_list = Arrays.stream( pListInput.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    /*
     * *******************************************************************************************************
     * Doing the moves
     * *******************************************************************************************************
     */

    Properties map_grid_blocks = new Properties();

    int block_start_row = 0;
    int block_start_col = 0;

    int block_row = block_start_row;
    int block_col = block_start_col;

    int nr_move = 0;

    char cur_facing = FACING_NORTH;

    String input_csv_device = null;

    for ( String input_str : direction_list )
    {
      char turn_direction = input_str.charAt( 0 );

      int forward_blocks = Integer.parseInt( input_str.substring( 1 ) );

      cur_facing = getNewFacing90( cur_facing, turn_direction );

      if ( cur_facing == FACING_NORTH )
      {
        block_row -= forward_blocks;
      }
      else if ( cur_facing == FACING_SOUTH )
      {
        block_row += forward_blocks;
      }
      else if ( cur_facing == FACING_WEST )
      {
        block_col -= forward_blocks;
      }
      else if ( cur_facing == FACING_EAST )
      {
        block_col += forward_blocks;
      }

      String block_key = "R" + block_row + "C" + block_col;

      if ( map_grid_blocks.getProperty( block_key ) != null )
      {
        wl( "" );
        wl( "Visited " + block_key + " on move nr " + map_grid_blocks.getProperty( block_key ) );
        
        int blocks_row = Math.abs( block_start_row - block_row );
        int blocks_col = Math.abs( block_start_col - block_col );

        wl( "Row " + block_start_row + " Col " + block_col );
        wl( "Row " + block_row       + " Col " + block_start_col );
        wl( "Row " + blocks_row      + " Col " + blocks_col + "     = " + ( blocks_row + blocks_col ) );
        wl( "" );
        wl( "" );

        if ( result_part_02 == 0 )
        {
          result_part_02 = blocks_row + blocks_col;
        }
      }
      else
      {
        map_grid_blocks.setProperty( block_key, "" + nr_move );
      }

      wl( "Nr " + nr_move + " " + turn_direction + "  " + forward_blocks + "   Row " + block_row + " Col " + block_col );

      nr_move++;
    }

    int blocks_row = Math.abs( block_start_row - block_row );
    int blocks_col = Math.abs( block_start_col - block_col );

    wl( "" );
    wl( "Row " + block_start_row + " Col " + block_col );
    wl( "Row " + block_row + " Col " + block_start_col );
    wl( "Row " + blocks_row + " Col " + blocks_col );

    int result_part_01 = blocks_row + blocks_col;

    wl( "" );
    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  private static String getListProd()
  {
    String string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day01_input.txt";

    try
    {
      string_array = Files.readString( Path.of( datei_input ) );
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

