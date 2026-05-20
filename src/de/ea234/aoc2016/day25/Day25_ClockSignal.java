package de.ea234.aoc2016.day25;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 25: Clock Signal ---
 * https://adventofcode.com/2016/day/25
 * 
 * https://www.reddit.com/r/adventofcode/comments/5k6yfu/2016_day_25_solutions/
 * 
 * 
 * https://adventofcode.com/2016/day/12
 * https://adventofcode.com/2016/day/23
 * 
 * https://github.com/ea234/Advent_of_Code_2016/blob/main/src/de/ea234/aoc2016/day12/Day12_LeonardosMonorail.java
 * https://github.com/ea234/Advent_of_Code_2016/blob/main/src/de/ea234/aoc2016/day23/Day23_SafeCracking.java
 * 
 * 
 *     out x  transmits x (either an integer or the value of a register) as the next
 *            value for the clock signal.
 * 
 *     cpy x y copies x (either an integer or the value of a register) into register y.
 *     
 *     inc x increases the value of register x by one.
 *     
 *     dec x decreases the value of register x by one.
 *     
 *     jnz x y jumps to an instruction y away (positive means forward; negative means backward), 
 *             but only if x is not zero.
 *     
 * 
 * </pre> 
 */

public class Day25_ClockSignal
{
  private static final int NO_INT_NUMBER = -1234;

  public static void main( String[] args )
  {
    String assembunny_src = "";

    assembunny_src += "cpy a d";
    assembunny_src += ",cpy 7 c";
    assembunny_src += ",cpy 365 b";
    assembunny_src += ",inc d";
    assembunny_src += ",dec b";
    assembunny_src += ",jnz b -2"; // b has to be 0
    assembunny_src += ",dec c"; // c is decremented
    assembunny_src += ",jnz c -5"; // 5 back to cpy 365 b = 7  
    assembunny_src += ",cpy d a";
    assembunny_src += ",jnz 0 0";
    assembunny_src += ",cpy a b";
    assembunny_src += ",cpy 0 a";
    assembunny_src += ",cpy 2 c";
    assembunny_src += ",jnz b 2"; // b has to be 0
    assembunny_src += ",jnz 1 6";
    assembunny_src += ",dec b";
    assembunny_src += ",dec c";
    assembunny_src += ",jnz c -4";
    assembunny_src += ",inc a";
    assembunny_src += ",jnz 1 -7";
    assembunny_src += ",cpy 2 b";
    assembunny_src += ",jnz c 2";
    assembunny_src += ",jnz 1 4";
    assembunny_src += ",dec b";
    assembunny_src += ",dec c";
    assembunny_src += ",jnz 1 -4";
    assembunny_src += ",jnz 0 0";
    assembunny_src += ",out b";
    assembunny_src += ",jnz a -19";
    assembunny_src += ",jnz 1 -21";

    calculatePart01( assembunny_src, 2555, true );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, int pInitValueA, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pInitValueA, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, int pInitValueA, boolean pKnzDebug )
  {
    int result_part_01 = 0;

    result_part_01 = run( pListInput, pInitValueA, 0, pKnzDebug );

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "" );
  }

  private static int run( List< String > pListInput, int pInitValueA, int pInitValueC, boolean pKnzDebug )
  {
    if ( pKnzDebug )
    {
      wl( "" );
      wl( "------------------------------------------------------------------------------------------" );
      wl( "RUN " );
      wl( "A init = " + pInitValueA );
      wl( "C init = " + pInitValueC );
      wl( "" );
    }

    int[] register_vector = new int[ 130 ];

    for ( int idx = 97; idx < 130; idx++ )
    {
      register_vector[ idx ] = 0;
    }

    register_vector[ 97 ] = pInitValueA;
    register_vector[ 99 ] = pInitValueC;

    int pgm_counter = 0;
    int step_counter = 0;

    //while ( ( pgm_counter < pListInput.size() ) && ( step_counter < 10_000_000_000 ) )
    while ( ( pgm_counter < pListInput.size() ) && ( step_counter < 100_000 ) )
    {
      String input_str = pListInput.get( pgm_counter );

      char char_register_op = input_str.charAt( 4 );

      if ( !input_str.isBlank() )
      {
        if ( input_str.startsWith( "cpy" ) )
        {
          char char_register_copy_to = input_str.charAt( 6 );

          if ( char_register_op >= 'a' && char_register_op <= 'z' )
          {
            register_vector[ ( (int) char_register_copy_to ) ] = register_vector[ ( (int) char_register_op ) ];

            if ( pKnzDebug )
            {
              wl( String.format( "%6d %4d CPY REG TO \"" + char_register_copy_to + "\"  FROM \"" + char_register_op + "\" = " + register_vector[ ( (int) char_register_copy_to ) ], step_counter, pgm_counter ) );
            }
          }
          else
          {
            int next_space = input_str.indexOf( " ", 5 );

            String sinteger_value = input_str.substring( 4, next_space );

            char_register_copy_to = input_str.charAt( next_space + 1 );

            int i_val = Integer.parseInt( sinteger_value );

            register_vector[ ( (int) char_register_copy_to ) ] = i_val;

            if ( pKnzDebug )
            {
              wl( String.format( "%6d %4d CPY VAL TO \"" + char_register_copy_to + "\"  VALUE \"" + i_val + "\" = " + register_vector[ ( (int) char_register_copy_to ) ], step_counter, pgm_counter ) );
            }
          }

          pgm_counter++;
        }
        else if ( input_str.startsWith( "inc" ) )
        {
          register_vector[ ( (int) char_register_op ) ]++;

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d INC \"" + char_register_op + "\" = " + register_vector[ ( (int) char_register_op ) ], step_counter, pgm_counter ) );
          }

          pgm_counter++;
        }
        else if ( input_str.startsWith( "dec" ) )
        {
          register_vector[ ( (int) char_register_op ) ]--;

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d DEC \"" + char_register_op + "\" = " + register_vector[ ( (int) char_register_op ) ], step_counter, pgm_counter ) );
          }

          pgm_counter++;
        }
        else if ( input_str.startsWith( "out" ) )
        {
          wl( "###OUT### " + register_vector[ ( (int) char_register_op ) ] );

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d OUT \"" + char_register_op + "\" = " + register_vector[ ( (int) char_register_op ) ], step_counter, pgm_counter ) );
          }

          pgm_counter++;
        }
        else if ( input_str.startsWith( "tgl" ) )
        {
          int tgl_x_away = register_vector[ ( (int) char_register_op ) ];

          String x_dbg = toggle( pListInput, pgm_counter, tgl_x_away, pKnzDebug );

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d TGL \"" + char_register_op + "\" = " + register_vector[ ( (int) char_register_op ) ] + "  =>  " + x_dbg, step_counter, pgm_counter ) );
          }

          pgm_counter++;
        }
        else if ( input_str.startsWith( "jnz" ) )
        {
          String[] cmd_parameter = input_str.substring( 4 ).split( " " );

          int register_p1 = getRegisterNumber( cmd_parameter[ 0 ] );
          int register_p2 = getRegisterNumber( cmd_parameter[ 1 ] );

          int value_p1 = getIntNumber( cmd_parameter[ 0 ] );
          int value_p2 = getIntNumber( cmd_parameter[ 1 ] );

          int reg_check = 0;
          int y_away = 0;

          if ( value_p1 == NO_INT_NUMBER )
          {
            reg_check = register_vector[ register_p1 ];
          }
          else
          {
            reg_check = value_p1;
          }

          if ( value_p2 == NO_INT_NUMBER )
          {
            y_away = register_vector[ register_p2 ];
          }
          else
          {
            y_away = value_p2;
          }

          if ( reg_check > 0 )
          {
            if ( pKnzDebug )
            {
              wl( String.format( "%6d %4d JNZ REG \"" + char_register_op + "\" = " + register_vector[ ( (int) char_register_op ) ] + " " + y_away, step_counter, pgm_counter ) );
            }

            pgm_counter += y_away;
          }
          else
          {
            if ( pKnzDebug )
            {
              wl( String.format( "%6d %4d JNZ REG \"" + char_register_op + "\" = " + register_vector[ ( (int) char_register_op ) ] + " #### ", step_counter, step_counter, pgm_counter ) );
            }

            pgm_counter++;
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


  private static int getRegisterNumber( String pString )
  {
    if ( pString.length() == 1 )
    {
      char test_alpha = pString.charAt( 0 );

      if ( test_alpha >= 'a' && test_alpha <= 'z' )
      {
        return ( (int) test_alpha );
      }
    }

    return -1;
  }

  private static int getIntNumber( String pString )
  {
    try
    {
      if ( pString != null )
      {
        return Integer.parseInt( pString.trim() );
      }
    }
    catch ( NumberFormatException err_inst )
    {
      // Keine Fehlermeldung, da im Fehlerfall der Vorgabewert zurueckgegeben wird
    }

    return NO_INT_NUMBER;
  }

  private static String toggle( List< String > pListInput, int pPgmCounter, int pIndexToToggle, boolean pKnzDebug )
  {
    int pgm_counter = pPgmCounter + pIndexToToggle;

    /*
     * If an attempt is made to toggle an instruction outside the program, nothing happens.
     */
    if ( pgm_counter < 0 ) return "pgm_counter " + pgm_counter + " < 0";

    if ( pgm_counter >= pListInput.size() ) return "pgm_counter " + pgm_counter + " > END";

    /*
     * Getting the command to toggle
     */
    String input_str = pListInput.get( pgm_counter );

    String dbg_out_string = "";

    String target_op = null;

    if ( input_str.startsWith( "inc" ) )
    {
      target_op = "dec";

      dbg_out_string = "inc to dec";

      dbg_out_string = "\"" + input_str + "\" to dec";
    }

    if ( input_str.startsWith( "dec" ) )
    {
      target_op = "inc";

      dbg_out_string = "dec to inc";

      dbg_out_string = "\"" + input_str + "\" to inc";
    }

    if ( input_str.startsWith( "cpy" ) )
    {
      target_op = "jnz";

      dbg_out_string = "cpy to jnz";

      dbg_out_string = "\"" + input_str + "\" to jnz";
    }

    if ( input_str.startsWith( "jnz" ) )
    {
      target_op = "cpy";

      dbg_out_string = "jnz to cpy";

      dbg_out_string = "\"" + input_str + "\" to cpy";
    }

    if ( input_str.startsWith( "tgl" ) )
    {
      target_op = "inc";

      dbg_out_string = "tgl to inc";

      dbg_out_string = "\"" + input_str + "\" to inc";
    }

    String out_x = target_op + input_str.substring( 3 );

    pListInput.set( pgm_counter, out_x );

    return dbg_out_string;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
