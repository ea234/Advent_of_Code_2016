package de.ea234.aoc2016.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 12: Leonardo's Monorail ---
 * https://adventofcode.com/2016/day/12
 * 
 * https://www.reddit.com/r/adventofcode/comments/5hus40/2016_day_12_solutions/
 * 
 * ------------------------------------------------------------------------------------------
 * RUN  Cinit = 0
 * 
 *      0    0 CPY VAL TO "a"  VALUE "41" = 41
 *      1    1 INC "a" = 42
 *      2    2 INC "a" = 43
 *      3    3 DEC "a" = 42
 *      4    4 JNZ REG "a" = 42 2
 * 
 * Register  97  a  = 42
 * Register  98  b  = 0
 * Register  99  c  = 0
 * Register 100  d  = 0
 * 
 * ------------------------------------------------------------------------------------------
 * RUN  Cinit = 1
 * 
 *      0    0 CPY VAL TO "a"  VALUE "41" = 41
 *      1    1 INC "a" = 42
 *      2    2 INC "a" = 43
 *      3    3 DEC "a" = 42
 *      4    4 JNZ REG "a" = 42 2
 * 
 * Register  97  a  = 42
 * Register  98  b  = 0
 * Register  99  c  = 1
 * Register 100  d  = 0
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 42
 * Result Part 2 42
 * 
 * 
 * Register  97  a  = 318117
 * Register  98  b  = 196418
 * Register  99  c  = 0
 * Register 100  d  = 0
 * 
 * Register  97  a  = 9227771
 * Register  98  b  = 5702887
 * Register  99  c  = 0
 * Register 100  d  = 0
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 318117
 * Result Part 2 9227771
 * 
 * 
 * </pre> 
 */
public class Day12_LeonardosMonorail
{
  public static void main( String[] args )
  {
    String test_input = "cpy 41 a,inc a,inc a,dec a,jnz a 2,dec a";

    calculatePart01( test_input, true );

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

    result_part_01 = run( pListInput, 0, pKnzDebug );
    result_part_02 = run( pListInput, 1, pKnzDebug );

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static int run( List< String > pListInput, int pInitValueC, boolean pKnzDebug )
  {
    if ( pKnzDebug )
    {
      wl( "" );
      wl( "------------------------------------------------------------------------------------------" );
      wl( "RUN  Cinit = " + pInitValueC );
      wl( "" );
    }

    int[] register_vector = new int[ 130 ];

    for ( int idx = 97; idx < 130; idx++ )
    {
      register_vector[ idx ] = 0;
    }

    register_vector[ 99 ] = pInitValueC;

    int pgm_counter  = 0;
    int step_counter = 0;

    while ( ( pgm_counter < pListInput.size() ) && ( step_counter < 1_000_000_000 ) )
    {
      String input_str = pListInput.get( pgm_counter );

      char char_register_a = input_str.charAt( 4 );

      if ( !input_str.isBlank() )
      {
        if ( input_str.startsWith( "cpy" ) )
        {
          char char_register_copy_to = input_str.charAt( 6 );

          if ( char_register_a >= '0' && char_register_a <= '9' )
          {
            String sinteger_value = "" + char_register_a;

            if ( input_str.charAt( 5 ) != ' ' )
            {
              sinteger_value += "" + input_str.charAt( 5 );

              char_register_copy_to = input_str.charAt( 7 );
            }

            int i_val = Integer.parseInt( sinteger_value );

            register_vector[ ( (int) char_register_copy_to ) ] = i_val;

            if ( pKnzDebug )
            {
              wl( String.format( "%6d %4d CPY VAL TO \"" + char_register_copy_to + "\"  VALUE \"" + i_val + "\" = " + register_vector[ ( (int) char_register_copy_to ) ], step_counter, pgm_counter ) );
            }
          }
          else
          {
            register_vector[ ( (int) char_register_copy_to ) ] = register_vector[ ( (int) char_register_a ) ];

            if ( pKnzDebug )
            {
              wl( String.format( "%6d %4d CPY REG TO \"" + char_register_copy_to + "\"  FROM \"" + char_register_a + "\" = " + register_vector[ ( (int) char_register_copy_to ) ], step_counter, pgm_counter ) );
            }
          }

          pgm_counter++;
        }
        else if ( input_str.startsWith( "inc" ) )
        {
          register_vector[ ( (int) char_register_a ) ]++;

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d INC \"" + char_register_a + "\" = " + register_vector[ ( (int) char_register_a ) ], step_counter, pgm_counter ) );
          }

          pgm_counter++;
        }
        else if ( input_str.startsWith( "dec" ) )
        {
          register_vector[ ( (int) char_register_a ) ]--;

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d DEC \"" + char_register_a + "\" = " + register_vector[ ( (int) char_register_a ) ], step_counter, pgm_counter ) );
          }

          pgm_counter++;
        }
        else if ( input_str.startsWith( "jnz" ) )
        {
          if ( char_register_a >= '0' && char_register_a <= '9' )
          {
            if ( char_register_a != '0' )
            {
              int y_away = Integer.parseInt( input_str.substring( 6 ) );

              if ( pKnzDebug )
              {
                wl( String.format( "%6d %4d JNZ VAL \"" + char_register_a + "\" " + y_away, step_counter, pgm_counter ) );
              }

              pgm_counter += y_away;
            }
            else
            {
              if ( pKnzDebug )
              {
                wl( String.format( "%6d %4d JNZ VAL \"" + char_register_a + "\" #### ", step_counter, pgm_counter ) );
              }

              pgm_counter++;
            }
          }
          else
          {
            if ( register_vector[ ( (int) char_register_a ) ] > 0 )
            {
              int y_away = Integer.parseInt( input_str.substring( 6 ) );

              if ( pKnzDebug )
              {
                wl( String.format( "%6d %4d JNZ REG \"" + char_register_a + "\" = " + register_vector[ ( (int) char_register_a ) ] + " " + y_away, step_counter, pgm_counter ) );
              }

              pgm_counter += y_away;
            }
            else
            {
              if ( pKnzDebug )
              {
                wl( String.format( "%6d %4d JNZ REG \"" + char_register_a + "\" = " + register_vector[ ( (int) char_register_a ) ] + " #### ", step_counter, step_counter, pgm_counter ) );
              }

              pgm_counter++;
            }
          }
        }
      }

      step_counter++;
    }

    wl( "" );

    for ( int idx = 97; idx < 101; idx++ )
    {
      wl( "Register " + idx + "  " + ( (char) idx ) + "  = " + register_vector[ idx ] );
    }

    return register_vector[ 97 ]; // 97 = ASCII a
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day12_input.txt";

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
