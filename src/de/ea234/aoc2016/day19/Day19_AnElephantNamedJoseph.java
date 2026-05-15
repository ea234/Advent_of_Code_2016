package de.ea234.aoc2016.day19;

import java.util.Arrays;

/**
 * <pre>
 * 
 * --- Day 19: An Elephant Named Joseph ---
 * https://adventofcode.com/2016/day/19
 * 
 * https://www.reddit.com/r/adventofcode/comments/5j4lp1/2016_day_19_solutions/
 *
 * 
 * Elf Player    0    1  [2, 0, 1, 1, 1] 
 * Elf Player    1    1  [2, 0, 1, 1, 1] 
 * Elf Player    2    3  [2, 0, 2, 0, 1] 
 * Elf Player    3    3  [2, 0, 2, 0, 1] 
 * Elf Player    4    0  [0, 0, 2, 0, 3] 
 * Elf Player    0    0  [0, 0, 2, 0, 3] 
 * Elf Player    1    0  [0, 0, 2, 0, 3] 
 * Elf Player    2    4  [0, 0, 5, 0, 0] 
 * 
 * Result Part 1 3
 * Result Part 2 0
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 1830117
 * Result Part 2 0
 * 
 * </pre> 
 */
public class Day19_AnElephantNamedJoseph
{
  public static void main( String[] args )
  {
    calculate01( 5, true );

    calculate01( 3012210, false );

    System.exit( 0 );
  }

  private static void calculate01( int pNumberOfElves, boolean pKnzDebug )
  {
    int result_part_01 = run( pNumberOfElves, pKnzDebug );
    int result_part_02 = 0;

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static int incIndex( int pIndex, int pMaxIndex )
  {
    if ( ( pIndex + 1 ) >= pMaxIndex )
    {
      return 0;
    }

    return pIndex + 1;
  }

  private static int run( int pNumberOfElves, boolean pKnzDebug )
  {
    int[] elf_vector = new int[ pNumberOfElves ];

    for ( int idx = 0; idx < pNumberOfElves; idx++ )
    {
      elf_vector[ idx ] = 1;
    }

    int elf_player = -1;

    int elf_left   =  0;

    int knz_do_loop = 1;

    while ( knz_do_loop == 1 )
    {
      /*
       * go to the next player elf
       */
      elf_player = incIndex( elf_player, pNumberOfElves );

      /*
       * Check, wether the elf still has presents
       */
      if ( elf_vector[ elf_player ] > 0 )
      {
        /*
         * get the next elf with presents
         */
        elf_left = incIndex( elf_player, pNumberOfElves );

        while ( elf_vector[ elf_left ] == 0 )
        {
          elf_left = incIndex( elf_left, pNumberOfElves );
        }

        /*
         * Check, wether the found elf is itself
         */
        if ( elf_left == elf_player )
        {
          knz_do_loop = 0;
        }
        else
        {
          /*
           * The player elf gets all the presents from the left elf
           */
          elf_vector[ elf_player ] += elf_vector[ elf_left ];

          /*
           * The left elf has no presents anymore
           */
          elf_vector[ elf_left ] = 0;

          /*
           * If the player elf has all the presents, end the loop
           */
          if ( elf_vector[ elf_player ] == pNumberOfElves )
          {
            knz_do_loop = 0;
          }
        }
      }

      if ( pKnzDebug )
      {
        wl( String.format( "Elf Player %4d %4d  %s ", elf_player, elf_left, Arrays.toString( elf_vector ) ) );
      }
    }

    return elf_player + 1;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
