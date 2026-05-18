package de.ea234.aoc2016.day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 18: Like a Rogue ---
 * https://adventofcode.com/2016/day/18
 * 
 * https://www.reddit.com/r/adventofcode/comments/5iyp50/2016_day_18_solutions/
 * 
 * 
 * ----------------------------------------------------------------------
 * .^^.^.^^^^
 * ^^^...^..^
 * ^.^^.^.^^.
 * ..^^...^^^
 * .^^^^.^^.^
 * ^^..^.^^..
 * ^^^^..^^^.
 * ^..^^^^.^^
 * .^^^..^.^^
 * ^^.^^^..^^
 * 
 * Tiles Safe    = 38
 * Tiles Trap    = 62
 * Sum           = 100
 * 
 * generate rows = 10
 * pInput length = 10
 * 
 * Grid size     = 100
 * 
 * 
 * result length = 110
 * 
 * Result Part 1 38
 * 
 * 
 * ----------------------------------------------------------------------
 * ...^^^^^..^...^...^.^.^.^^.^^^...^^^^^...^.^^^.^.^.^^.^^^.....^.^^^...^^^^^^.....^.^^...^^^^^...^.^...^.^^..^^.^^......^^^^
 * ..^^...^^^.^.^.^.^......^^.^.^^.^^...^^.^..^.^.....^^.^.^^...^..^.^^.^^....^^...^..^^^.^^...^^.^...^.^..^^^^^^.^^^....^^..^
 * .^^^^.^^.^........^....^^^...^^.^^^.^^^..^^...^...^^^...^^^.^.^^..^^.^^^..^^^^.^.^^^.^.^^^.^^^..^.^...^^^....^.^.^^..^^^^^.
 * ^^..^.^^..^......^.^..^^.^^.^^^.^.^.^.^^^^^^.^.^.^^.^^.^^.^...^^^^^^.^.^^^^..^...^.^...^.^.^.^^^...^.^^.^^..^....^^^^^...^^
 * ^^^^..^^^^.^....^...^^^^.^^.^.^.......^....^.....^^.^^.^^..^.^^....^...^..^^^.^.^...^.^......^.^^.^..^^.^^^^.^..^^...^^.^^^
 * ^..^^^^..^..^..^.^.^^..^.^^....^.....^.^..^.^...^^^.^^.^^^^..^^^..^.^.^.^^^.^....^.^...^....^..^^..^^^^.^..^..^^^^^.^^^.^.^
 * .^^^..^^^.^^.^^....^^^^..^^^..^.^...^...^^...^.^^.^.^^.^..^^^^.^^^......^.^..^..^...^.^.^..^.^^^^^^^..^..^^.^^^...^.^.^....
 * ^^.^^^^.^.^^.^^^..^^..^^^^.^^^...^.^.^.^^^^.^..^^...^^..^^^..^.^.^^....^...^^.^^.^.^.....^^..^.....^^^.^^^^.^.^^.^.....^...
 * ^^.^..^...^^.^.^^^^^^^^..^.^.^^.^......^..^..^^^^^.^^^^^^.^^^....^^^..^.^.^^^.^^....^...^^^^^.^...^^.^.^..^...^^..^...^.^..
 * ^^..^^.^.^^^...^......^^^....^^..^....^.^^.^^^...^.^....^.^.^^..^^.^^^....^.^.^^^..^.^.^^...^..^.^^^....^^.^.^^^^^.^.^...^.
 * ^^^^^^...^.^^.^.^....^^.^^..^^^^^.^..^..^^.^.^^.^...^..^....^^^^^^.^.^^..^....^.^^^....^^^.^.^^..^.^^..^^^...^...^....^.^.^
 * ^....^^.^..^^....^..^^^.^^^^^...^..^^.^^^^...^^..^.^.^^.^..^^....^...^^^^.^..^..^.^^..^^.^...^^^^..^^^^^.^^.^.^.^.^..^.....
 * .^..^^^..^^^^^..^.^^^.^.^...^^.^.^^^^.^..^^.^^^^^....^^..^^^^^..^.^.^^..^..^^.^^..^^^^^^..^.^^..^^^^...^.^^........^^.^....
 * ^.^^^.^^^^...^^^..^.^....^.^^^...^..^..^^^^.^...^^..^^^^^^...^^^....^^^^.^^^^.^^^^^....^^^..^^^^^..^^.^..^^^......^^^..^...
 * ..^.^.^..^^.^^.^^^...^..^..^.^^.^.^^.^^^..^..^.^^^^^^....^^.^^.^^..^^..^.^..^.^...^^..^^.^^^^...^^^^^..^^^.^^....^^.^^^.^..
 * .^.....^^^^.^^.^.^^.^.^^.^^..^^...^^.^.^^^.^^..^....^^..^^^.^^.^^^^^^^^...^^...^.^^^^^^^.^..^^.^^...^^^^.^.^^^..^^^.^.^..^.
 * ^.^...^^..^.^^...^^...^^.^^^^^^^.^^^...^.^.^^^^.^..^^^^^^.^.^^.^......^^.^^^^.^..^.....^..^^^^.^^^.^^..^...^.^^^^.^....^^.^
 * ...^.^^^^^..^^^.^^^^.^^^.^.....^.^.^^.^....^..^..^^^....^...^^..^....^^^.^..^..^^.^...^.^^^..^.^.^.^^^^.^.^..^..^..^..^^^..
 * ..^..^...^^^^.^.^..^.^.^..^...^....^^..^..^.^^.^^^.^^..^.^.^^^^^.^..^^.^..^^.^^^^..^.^..^.^^^......^..^....^^.^^.^^.^^^.^^.
 * .^.^^.^.^^..^....^^.....^^.^.^.^..^^^^^.^^..^^.^.^.^^^^....^...^..^^^^..^^^^.^..^^^...^^..^.^^....^.^^.^..^^^.^^.^^.^.^.^^^
 * ^..^^...^^^^.^..^^^^...^^^......^^^...^.^^^^^^.....^..^^..^.^.^.^^^..^^^^..^..^^^.^^.^^^^^..^^^..^..^^..^^^.^.^^.^^.....^.^
 * .^^^^^.^^..^..^^^..^^.^^.^^....^^.^^.^..^....^^...^.^^^^^^......^.^^^^..^^^.^^^.^.^^.^...^^^^.^^^.^^^^^^^.^...^^.^^^...^...
 * ^^...^.^^^^.^^^.^^^^^.^^.^^^..^^^.^^..^^.^..^^^^.^..^....^^....^..^..^^^^.^.^.^...^^..^.^^..^.^.^.^.....^..^.^^^.^.^^.^.^..
 * ^^^.^..^..^.^.^.^...^.^^.^.^^^^.^.^^^^^^..^^^..^..^^.^..^^^^..^.^^.^^^..^......^.^^^^^..^^^^.......^...^.^^..^.^...^^....^.
 * ^.^..^^.^^.......^.^..^^...^..^...^....^^^^.^^^.^^^^..^^^..^^^..^^.^.^^^.^....^..^...^^^^..^^.....^.^.^..^^^^...^.^^^^..^.^
 * ...^^^^.^^^.....^...^^^^^.^.^^.^.^.^..^^..^.^.^.^..^^^^.^^^^.^^^^^...^.^..^..^.^^.^.^^..^^^^^^...^.....^^^..^^.^..^..^^^...
 * ..^^..^.^.^^...^.^.^^...^...^^......^^^^^^.......^^^..^.^..^.^...^^.^...^^.^^..^^...^^^^^....^^.^.^...^^.^^^^^..^^.^^^.^^..
 * .^^^^^....^^^.^....^^^.^.^.^^^^....^^....^^.....^^.^^^...^^...^.^^^..^.^^^.^^^^^^^.^^...^^..^^^....^.^^^.^...^^^^^.^.^.^^^.
 * ^^...^^..^^.^..^..^^.^.....^..^^..^^^^..^^^^...^^^.^.^^.^^^^.^..^.^^^..^.^.^.....^.^^^.^^^^^^.^^..^..^.^..^.^^...^.....^.^^
 * ^^^.^^^^^^^..^^.^^^^..^...^.^^^^^^^..^^^^..^^.^^.^...^^.^..^..^^..^.^^^.....^...^..^.^.^....^.^^^^.^^...^^..^^^.^.^...^..^^
 * ^.^.^.....^^^^^.^..^^^.^.^..^.....^^^^..^^^^^.^^..^.^^^..^^.^^^^^^..^.^^...^.^.^.^^.....^..^..^..^.^^^.^^^^^^.^....^.^.^^^^
 * .....^...^^...^..^^^.^....^^.^...^^..^^^^...^.^^^^..^.^^^^^.^....^^^..^^^.^......^^^...^.^^.^^.^^..^.^.^....^..^..^....^..^
 * ....^.^.^^^^.^.^^^.^..^..^^^..^.^^^^^^..^^.^..^..^^^..^...^..^..^^.^^^^.^..^....^^.^^.^..^^.^^.^^^^.....^..^.^^.^^.^..^.^^.
 * ...^....^..^...^.^..^^.^^^.^^^..^....^^^^^..^^.^^^.^^^.^.^.^^.^^^^.^..^..^^.^..^^^.^^..^^^^.^^.^..^^...^.^^..^^.^^..^^..^^^
 * ..^.^..^.^^.^.^...^^^^.^.^.^.^^^.^..^^...^^^^^.^.^.^.^.....^^.^..^..^^.^^^^..^^^.^.^^^^^..^.^^..^^^^^.^..^^^^^^.^^^^^^^^^.^
 * .^...^^..^^....^.^^..^.......^.^..^^^^^.^^...^........^...^^^..^^.^^^^.^..^^^^.^...^...^^^..^^^^^...^..^^^....^.^.......^..
 * ^.^.^^^^^^^^..^..^^^^.^.....^...^^^...^.^^^.^.^......^.^.^^.^^^^^.^..^..^^^..^..^.^.^.^^.^^^^...^^.^.^^^.^^..^...^.....^.^.
 * ....^......^^^.^^^..^..^...^.^.^^.^^.^..^.^....^....^....^^.^...^..^^.^^^.^^^.^^......^^.^..^^.^^^...^.^.^^^^.^.^.^...^...^
 * ...^.^....^^.^.^.^^^.^^.^.^....^^.^^..^^...^..^.^..^.^..^^^..^.^.^^^^.^.^.^.^.^^^....^^^..^^^^.^.^^.^....^..^......^.^.^.^.
 * ..^...^..^^^.....^.^.^^....^..^^^.^^^^^^^.^.^^...^^...^^^.^^^....^..^.........^.^^..^^.^^^^..^...^^..^..^.^^.^....^.......^
 * 
 * Tiles Safe    = 2447
 * Tiles Trap    = 2473
 * Sum           = 4920
 * 
 * generate rows = 40
 * pInput length = 123
 * 
 * Grid size     = 4920
 * 
 * result length = 4960
 * 
 * Result Part 1 2447
 * 
 * 
 * ----------------------------------------------------------------------
 * PART 2
 * 
 * Tiles Safe    = 20005203
 * Tiles Trap    = 19994797
 * Sum           = 40000000
 * 
 * generate rows = 400000
 * pInput length = 100
 * 
 * Grid size     = 40000000
 * 
 * Result Part 2 20005203
 * 
 * </pre> 
 */
public class Day18_LikeARogue
{
  public static final char CHAR_TRAP = '^';

  public static final char CHAR_SAFE = '.';

  public static void main( String[] args )
  {
    calculate01( ".^^.^.^^^^", 10, true );
    
    calculate01( "...^^^^^..^...^...^.^.^.^^.^^^...^^^^^...^.^^^.^.^.^^.^^^.....^.^^^...^^^^^^.....^.^^...^^^^^...^.^...^.^^..^^.^^......^^^^", 40, true );
    
    calculate02( "...^^^^^..^...^...^.^.^.^^.^^^...^^^^^...^.^^^.^.^.^^.^^^.....^.^^^...^^^^^^.....^.^^...^^^^^...^.^...^.^^..^^.^^......^^^^", 400000, true );
    
    System.exit( 0 );
  }
  
  private static void calculate01( String pInput, int pRowsToGenerate, boolean pKnzDebug )
  {
    wl( "" );
    wl( "----------------------------------------------------------------------" );

    int result_part_01 = 0;

    String prev_row_str = pInput;

    String result_string = prev_row_str + "\n";

    int generate_rows = pRowsToGenerate;

    for ( int cur_row_nr = 1; cur_row_nr < generate_rows; cur_row_nr++ )
    {
      prev_row_str = getNewRow( prev_row_str );

      result_string += prev_row_str + "\n";
    }

    if ( pKnzDebug )
    {
      wl( result_string );
    }

    int tiles_safe = countTiles( result_string, CHAR_SAFE );
    int tiles_trap = countTiles( result_string, CHAR_TRAP );

    result_part_01 = tiles_safe;

    wl( "Tiles Safe    = " + tiles_safe );
    wl( "Tiles Trap    = " + tiles_trap );
    wl( "Sum           = " + ( tiles_safe + tiles_trap ) );
    wl( "" );
    wl( "generate rows = " + generate_rows );
    wl( "pInput length = " + pInput.length() );
    wl( "" );
    wl( "Grid size     = " + ( generate_rows * pInput.length() ) );
    wl( "" );
    wl( "result length = " + result_string.length() );
    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "" );
  }  
  
  private static void calculate02( String pInput, int pRowsToGenerate, boolean pKnzDebug )
  {
    wl( "" );
    wl( "----------------------------------------------------------------------" );

    int result_part_02 = 0;

    String prev_row_str = pInput;

    String result_string = prev_row_str + "\n";

    int generate_rows = pRowsToGenerate;
    
    int length = pInput.length();
    
    int tiles_safe = countTiles( result_string, CHAR_SAFE );
    int tiles_trap = countTiles( result_string, CHAR_TRAP );

    for ( int cur_row_nr = 1; cur_row_nr < generate_rows; cur_row_nr++ )
    {
      char c_left = CHAR_SAFE;

      char c_center = prev_row_str.charAt( 0 );
      
      String res_string = "";

      for ( int idx = 0; idx < length; idx++ )
      {
        char c_right = ( ( idx + 1 ) == length ? CHAR_SAFE : prev_row_str.charAt( idx + 1 ) );

             if ( ( c_left == CHAR_SAFE ) && ( c_center == CHAR_SAFE ) && ( c_right == CHAR_TRAP ) ) { res_string += CHAR_TRAP; tiles_trap++; }
        else if ( ( c_left == CHAR_SAFE ) && ( c_center == CHAR_TRAP ) && ( c_right == CHAR_TRAP ) ) { res_string += CHAR_TRAP; tiles_trap++; }
        else if ( ( c_left == CHAR_TRAP ) && ( c_center == CHAR_SAFE ) && ( c_right == CHAR_SAFE ) ) { res_string += CHAR_TRAP; tiles_trap++; }
        else if ( ( c_left == CHAR_TRAP ) && ( c_center == CHAR_TRAP ) && ( c_right == CHAR_SAFE ) ) { res_string += CHAR_TRAP; tiles_trap++; }
        else                                                                                         { res_string += CHAR_SAFE; tiles_safe++; }

        c_left = c_center;
        c_center = c_right;
      }

      prev_row_str = res_string;
    }

    result_part_02 = tiles_safe;

    wl( "Tiles Safe    = " + tiles_safe );
    wl( "Tiles Trap    = " + tiles_trap );
    wl( "Sum           = " + ( tiles_safe + tiles_trap ) );
    wl( "" );
    wl( "generate rows = " + generate_rows );
    wl( "pInput length = " + pInput.length() );
    wl( "" );
    wl( "Grid size     = " + ( generate_rows * pInput.length() ) );
    wl( "" );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static String getNewRow( String pPrevRow )
  {
    String res_string = "";

    int length = pPrevRow.length();

    char c_left = CHAR_SAFE;

    char c_center = pPrevRow.charAt( 0 );

    for ( int idx = 0; idx < length; idx++ )
    {
      char c_right = ( ( idx + 1 ) == length ? CHAR_SAFE : pPrevRow.charAt( idx + 1 ) );

      /*
       * sss = 000 = 0 = SAFE
       * sst = 001 = 1 = TRAP = Only its right tile is a trap.
       * sts = 010 = 2 = SAFE
       * stt = 011 = 3 = TRAP = Its center and right tiles are traps, but its left tile is not.
       * tss = 100 = 4 = TRAP = Only its left tile is a trap.
       * tst = 101 = 5 = SAFE
       * tts = 110 = 6 = TRAP = Its left and center tiles are traps, but its right tile is not.
       * ttt = 111 = 7 = SAFE
       * 
       */
           if ( ( c_left == CHAR_SAFE ) && ( c_center == CHAR_SAFE ) && ( c_right == CHAR_TRAP ) ) { res_string += CHAR_TRAP; }
      else if ( ( c_left == CHAR_SAFE ) && ( c_center == CHAR_TRAP ) && ( c_right == CHAR_TRAP ) ) { res_string += CHAR_TRAP; }
      else if ( ( c_left == CHAR_TRAP ) && ( c_center == CHAR_SAFE ) && ( c_right == CHAR_SAFE ) ) { res_string += CHAR_TRAP; }
      else if ( ( c_left == CHAR_TRAP ) && ( c_center == CHAR_TRAP ) && ( c_right == CHAR_SAFE ) ) { res_string += CHAR_TRAP; }
      else                                                                                         { res_string += CHAR_SAFE; }

      c_left = c_center;
      c_center = c_right;
    }

    return res_string;
  }

  private static int countTiles( String pInput, char pCharToCount )
  {
    int tiles_count = 0;

    for ( int idx = 0; idx < pInput.length(); idx++ )
    {
      if ( pInput.charAt( idx ) == pCharToCount )
      {
        tiles_count++;
      }
    }

    return tiles_count;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
