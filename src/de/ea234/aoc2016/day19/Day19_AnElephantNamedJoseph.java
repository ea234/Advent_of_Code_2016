package de.ea234.aoc2016.day19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * 
 * --- Day 19: An Elephant Named Joseph ---
 * https://adventofcode.com/2016/day/19
 * 
 * https://www.reddit.com/r/adventofcode/comments/5j4lp1/2016_day_19_solutions/
 *
 * 
 * --- Day 19: An Elephant Named Joseph ---
 * The Elves contact you over a highly secure emergency channel. Back at the North
 * Pole, the Elves are busy misunderstanding White Elephant parties.
 * 
 * Each Elf brings a present. They all sit in a circle, numbered starting with position
 * 1. Then, starting with the first Elf, they take turns stealing all the presents
 * from the Elf to their left. An Elf with no presents is removed from the circle and
 * does not take turns.
 * 
 * For example, with five Elves (numbered 1 to 5):
 *   1
 * 5   2
 *  4 3
 *  
 *     Elf 1 takes Elf 2's present.
 *     Elf 2 has no presents and is skipped.
 *     Elf 3 takes Elf 4's present.
 *     Elf 4 has no presents and is also skipped.
 *     Elf 5 takes Elf 1's two presents.
 *     Neither Elf 1 nor Elf 2 have any presents, so both are skipped.
 *     Elf 3 takes Elf 5's three presents.
 *     
 * So, with five Elves, the Elf that sits starting in position 3 gets all the presents.
 * 
 * With the number of Elves given in your puzzle input, which Elf gets all the presents?
 * 
 * Your puzzle answer was 1830117.
 * 
 * The first half of this puzzle is complete! It provides one gold star: *
 * 
 * --- Part Two ---
 * Realizing the folly of their present-exchange rules, the Elves agree to instead
 * steal presents from the Elf directly across the circle. If two Elves are across
 * the circle, the one on the left (from the perspective of the stealer) is stolen
 * from. The other rules remain unchanged: Elves with no presents are removed from
 * the circle entirely, and the other elves move in slightly to keep the circle evenly
 * spaced.
 * 
 * For example, with five Elves (again numbered 1 to 5):
 * 
 *     The Elves sit in a circle; Elf 1 goes first:
 *     
 *       1
 *     5   2
 *      4 3
 *      
 *     Elves 3 and 4 are across the circle; Elf 3's present is stolen, being the one
 * to the left. Elf 3 leaves the circle, and the rest of the Elves move in:
 * 
 *       1           1
 *     5   2  -->  5   2
 *      4 -          4
 *      
 *     Elf 2 steals from the Elf directly across the circle, Elf 5:
 *     
 *       1         1 
 *     -   2  -->     2
 *       4         4
 *        
 *     Next is Elf 4 who, choosing between Elves 1 and 2, steals from Elf 1:
 *     
 *      -          2  
 *         2  -->
 *      4          4
 *      
 *     Finally, Elf 2 steals from Elf 4:
 *     
 *      2
 *         -->  2  
 *      -
 * So, with five Elves, the Elf that sits starting in position 2 gets all the presents.
 * 
 * With the number of Elves given in your puzzle input, which Elf now gets all the
 * presents?
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
 * 
 * 
 * 
 * </pre> 
 */
public class Day19_AnElephantNamedJoseph
{
  public static void main( String[] args )
  {
    calculate01( 5, true );

    calculate01( 3_012_210, false );

    System.exit( 0 );
  }

  private static void calculate01( int pNumberOfElves, boolean pKnzDebug )
  {
    int result_part_01 = 0; //run01( pNumberOfElves, pKnzDebug );
    int result_part_02 = run02( pNumberOfElves, pKnzDebug );

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

  private static int run02( int pNumberOfElves, boolean pKnzDebug )
  {
    List< Day19Player > elf_list = new ArrayList< Day19Player >();

    int cur_nr_of_players = pNumberOfElves;

    for ( int idx = 0; idx < cur_nr_of_players; idx++ )
    {
      elf_list.add( new Day19Player( idx ) );
    }

    int elf_player = -1;

    int dbg_count_info = 0;

    String dbg_player_info = "";

    int dbg_count_loop_nr = 0;

    while ( cur_nr_of_players > 1 )
    {
      dbg_count_loop_nr++;

      dbg_count_info++;

      if ( dbg_count_info > 5000 )
      {
        wl( dbg_count_loop_nr + " " + " " + elf_list.size() );

        dbg_count_info = 0;
      }

      /*
       * go to the next player elf
       */
      elf_player = incIndex( elf_player, elf_list.size() );

      /*
       * Check, wether the elf still has presents
       */
      if ( elf_list.get( elf_player ).getNrOfPresents() > 0 )
      {
        String elf_start = getDebugElfNames( cur_nr_of_players, elf_list );

        Day19Player elf_to = elf_list.get( elf_player );

        int elf_start_list_index = elf_player; // getElfListIndex( elf_list, elf_to.getPlayerName() );

        /*
         * Get the middle of the players in the game
         */
        int elf_x = (int) ( cur_nr_of_players / 2 );

        /*
         * Add the middle to the index of the current player-index
         */
        int list_index = elf_start_list_index + elf_x;

        /*
         * Check overflow and adjust the index accordently.
         */
        if ( list_index >= elf_list.size() )
        {
          list_index = list_index - elf_list.size();
        }

        /*
         * 
         */
        Day19Player elf_from_list = elf_list.get( list_index );

        /*
         * Check, wether the found elf is itself
         */
        if ( elf_from_list.getIndexStart() == elf_player )
        {
          //knz_do_loop = 0;
        }
        else
        {
          if ( pKnzDebug )
          {
            //wl( String.format( "TO    Elf Index %6d, Name %6d, Presents %6d", elf_to.getIndexStart(), elf_to.getPlayerName(), elf_to.getNrOfPresents() ) );
            //wl( String.format( "FROM  Elf Index %6d, Name %6d, Presents %6d", elf_from_list.getIndexStart(), elf_from_list.getPlayerName(), elf_from_list.getNrOfPresents() ) );
            //wl( "" );
            //if ( cur_nr_of_players < 20 )
            //{
            //  for ( int idx = 0; idx < elf_list.size(); idx++ )
            //  {
            //    Day19Player dlp = elf_list.get( idx );
            //
            //    wl( String.format( "[%4d] Elf Index %6d, Name %6d, Presents %6d", idx, dlp.getIndexStart(), dlp.getPlayerName(), dlp.getNrOfPresents() ) );
            //  }
            //}

            dbg_player_info = " Elf " + elf_to.getPlayerName() + " steals from " + elf_from_list.getPlayerName() + " ";
          }

          /*
           * The player elf gets all the presents from the left elf
           */
          elf_to.addNrOfPresents( elf_from_list.getNrOfPresents() );

          /*
           * The left elf has no presents anymore
           */
          elf_from_list.setNrOfPresents( 0 );

          elf_list.remove( elf_from_list );

          if ( list_index < elf_player )
          {
            elf_player--;
          }

          cur_nr_of_players--;

          /*
           * If the player elf has all the presents, end the loop
           */
          if ( elf_to.getNrOfPresents() == pNumberOfElves )
          {
            cur_nr_of_players = 0;
          }
        }

        String elf_end = getDebugElfNames( cur_nr_of_players, elf_list );

        if ( cur_nr_of_players < 20 )
        {
          wl( String.format( "%-20s  %-30s  %-20s", elf_start, dbg_player_info, elf_end ) );
        }

      }

      if ( pKnzDebug )
      {
        //wl( String.format( "Elf Player %4d %4d ", elf_vector[ elf_player ].getIndexStart(), elf_vector[ elf_player ].getNrOfPresents() ) );
      }
    }

    return elf_player + 1;
  }

  private static void debugElfList( int cur_nr_of_players, List< Day19Player > elf_list )
  {
    if ( cur_nr_of_players < 20 )
    {
      for ( int idx = 0; idx < elf_list.size(); idx++ )
      {
        Day19Player dlp = elf_list.get( idx );

        wl( String.format( "[%4d] Elf Index %6d, Name %6d, Presents %6d", idx, dlp.getIndexStart(), dlp.getPlayerName(), dlp.getNrOfPresents() ) );
      }
    }

  }

  private static String getDebugElfNames( int cur_nr_of_players, List< Day19Player > elf_list )
  {
    String res_string = "";

    if ( cur_nr_of_players < 20 )
    {
      for ( int idx = 0; idx < elf_list.size(); idx++ )
      {
        Day19Player dlp = elf_list.get( idx );

        res_string += dlp.getPlayerName();
      }
    }

    return res_string;

  }

  private static int getElfListIndex( List< Day19Player > elf_list, int pPlayerName )
  {
    for ( int idx = 0; idx < elf_list.size(); idx++ )
    {
      Day19Player dlp = elf_list.get( idx );

      if ( dlp.getPlayerName() == pPlayerName )
      {
        return idx;
      }
    }

    return -1;
  }

  private static int run01( int pNumberOfElves, boolean pKnzDebug )
  {
    int[] elf_vector = new int[ pNumberOfElves ];

    for ( int idx = 0; idx < pNumberOfElves; idx++ )
    {
      elf_vector[ idx ] = 1;
    }

    int elf_player = -1;
    int elf_left = 0;

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
