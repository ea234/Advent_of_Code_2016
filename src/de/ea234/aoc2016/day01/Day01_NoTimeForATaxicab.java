package de.ea234.aoc2016.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
 * 
 * Nr 0 R  2   Row 0 Col 2
 * Nr 1 L  3   Row -3 Col 2
 * 
 * Row 0 Col 2
 * Row -3 Col 0
 * Row 3 Col 2
 * 
 * 
 * Result Part 1 5
 * Result Part 2 0
 * 
 * 
 * Nr 0 R  2   Row 0 Col 2
 * Nr 1 R  2   Row 2 Col 2
 * Nr 2 R  2   Row 2 Col 0
 * 
 * Row 0 Col 0
 * Row 2 Col 0
 * Row 2 Col 0
 * 
 * 
 * Result Part 1 2
 * Result Part 2 0
 * 
 * 
 * Nr 0 R  5   Row 0 Col 5
 * Nr 1 L  5   Row -5 Col 5
 * Nr 2 R  5   Row -5 Col 10
 * Nr 3 R  3   Row -2 Col 10
 * 
 * Row 0 Col 10
 * Row -2 Col 0
 * Row 2 Col 10
 * 
 * 
 * Result Part 1 12
 * Result Part 2 0
 * 
 * 
 * Nr 0 R  8   Row 0 Col 8
 * Nr 1 R  4   Row 4 Col 8
 * Nr 2 R  4   Row 4 Col 4
 * 
 * Visited R0C4 on move nr 1_0_3
 * Row 0 Col 4
 * Row 0 Col 0
 * Row 0 Col 4     = 4
 * 
 * 
 * Nr 3 R  8   Row -4 Col 4
 * 
 * Row 0 Col 4
 * Row -4 Col 0
 * Row 4 Col 4
 * 
 * 
 * Result Part 1 8
 * Result Part 2 4
 * ---------------------------------------------------------------
 * 
 * Nr 0 R  3   Row 0 Col 3
 * Nr 1 L  5   Row -5 Col 3
 * Nr 2 R  2   Row -5 Col 5
 * Nr 3 L  2   Row -7 Col 5
 * Nr 4 R  1   Row -7 Col 6
 * Nr 5 L  3   Row -10 Col 6
 * ...
 * Nr 41 L  4   Row -22 Col 12
 * Nr 42 L  4   Row -22 Col 16
 * Nr 43 L  190   Row -212 Col 16
 * Nr 44 L  5   Row -212 Col 11
 * Nr 45 L  2   Row -210 Col 11
 * ...
 * Nr 59 L  2   Row -143 Col 51
 * 
 * Visited R-143C16 on move nr 1_43_120
 * Row    0 Col 16
 * Row -143 Col  0
 * Row  143 Col 16     = 159
 * 
 * 
 * Nr 60 R  191   Row -143 Col -140
 * Nr 61 R  2   Row -145 Col -140
 * ...
 * Nr 158 L  3   Row -169 Col -129
 * Nr 159 L  4   Row -165 Col -129
 * Nr 160 L  3   Row -165 Col -126
 * 
 * Row    0 Col -126
 * Row -165 Col    0
 * Row  165 Col  126
 * 
 * 
 * Result Part 1 291
 * Result Part 2 159
 * 
 * </pre>
 */
public class Day01_NoTimeForATaxicab
{
  private static final char   FACING_NORTH       = 'N';

  private static final char   FACING_SOUTH       = 'S';

  private static final char   FACING_EAST        = 'E';

  private static final char   FACING_WEST        = 'W';

  private static final char   TURN_LEFT          = 'L';

  private static final char   TURN_RIGHT         = 'R';

  public static void main( String[] args )
  {
    String test_input = "R2, L3;R2, R2, R2;R5, L5, R5, R3;R8, R4, R4, R8";

    calculatePart01( test_input, true );

    calculatePart01( getListProd(), true );

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

    for ( String input_str : direction_list )
    {
      char turn_direction = input_str.charAt( 0 );

      cur_facing = getNewFacing90( cur_facing, turn_direction );

      int forward_blocks = Integer.parseInt( input_str.substring( 1 ) );

      int move_delta_row = 0;
      int move_delta_col = 0;

      if ( cur_facing == FACING_NORTH )
      {
        move_delta_row = -1;
      }
      else if ( cur_facing == FACING_SOUTH )
      {
        move_delta_row = 1;
      }
      else if ( cur_facing == FACING_WEST )
      {
        move_delta_col = -1;
      }
      else if ( cur_facing == FACING_EAST )
      {
        move_delta_col = 1;
      }

      for ( int move_counter = 0; move_counter < forward_blocks; move_counter++ )
      {
        block_row += move_delta_row;
        block_col += move_delta_col;

        String block_key = "R" + block_row + "C" + block_col;

        if ( map_grid_blocks.getProperty( block_key ) != null )
        {
          if ( result_part_02 == 0 )
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
            
            result_part_02 = blocks_row + blocks_col;
          }
        }
        else
        {
          map_grid_blocks.setProperty( block_key, "1_" + nr_move + "_" + move_counter );
        }
      }

      wl( "Nr " + nr_move + " " + turn_direction + "  " + forward_blocks + "   Row " + block_row + " Col " + block_col );

      nr_move++;
    }

    int blocks_row = Math.abs( block_start_row - block_row );
    int blocks_col = Math.abs( block_start_col - block_col );

    wl( "" );
    wl( "Row " + block_start_row + " Col " + block_col       );
    wl( "Row " + block_row       + " Col " + block_start_col );
    wl( "Row " + blocks_row      + " Col " + blocks_col      );

    int result_part_01 = blocks_row + blocks_col;

    wl( "" );
    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
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
