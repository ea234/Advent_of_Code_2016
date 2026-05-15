package de.ea234.aoc2016.day05;

import java.security.MessageDigest;

/**
 * <pre>
 * 
 * --- Day 5: How About a Nice Game of Chess? ---
 * https://adventofcode.com/2016/day/5
 * 
 * https://www.reddit.com/r/adventofcode/comments/5gk2yv/2016_day_5_solutions/
 * 
 * 
 * --------------------------------------------------------------------------------
 * Calculate 01 - Salt = abc
 * 
 * 
 * Calculation Time 00:00:03:998
 * 
 * cur_index end    8605829
 * res_string       18f47a30
 * 
 * --------------------------------------------------------------------------------
 * Calculate 02 - Salt = abc
 * 
 * Found index 1 5  00000155f8105dff7f56ee10fa9b9abd    3231929
 * Found index 4 e  000004e597bd77c5cd2133e9d885fe7e    5357525
 * Found index 7 3  0000073848c9ff7a27ca2e942ac10a4c    5708769
 * Found index 3 c  000003c75169d14fdb31ec1593915cff    8036669
 * Found index 0 0  0000000ea49fd3fc1b2f10e02d98ee96    8605828
 * Found index 6 e  000006e42e097c536b8be5179d65f327    8609554
 * Found index 5 8  0000058939cbc6a1d1ab3bf7d29b0764    13666005
 * Found index 2 a  000002af5a2d97ef50063c37644d0166    13753421
 * 
 * Calculation Time 00:00:06:338
 * 
 * cur_index end    13753422
 * res_string       05ace8e3
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 18f47a30
 * Result Part 2 05ace8e3
 *
 * 
 * 
 * --------------------------------------------------------------------------------
 * Calculate 01 - Salt = reyedfim
 * 
 * 
 * Calculation Time 00:00:03:598
 * 
 * cur_index end    7183956
 * res_string       f97c354d
 * 
 * --------------------------------------------------------------------------------
 * Calculate 02 - Salt = reyedfim
 * 
 * Found index 7 7  00000774278f87486dd763de7c36d7ac    1617991
 * Found index 3 d  000003dbf0ad65c48ec74313dde4ea8d    2564359
 * Found index 5 e  000005e7d422c718d47e47dfff429cbb    2834991
 * Found index 4 d  000004d93de3e18869925a23989f0753    3605750
 * Found index 0 8  000000832f3a209acfbdfe86964ced01    12187005
 * Found index 2 3  0000023c990be097d3ec134b259cc95d    13432325
 * Found index 1 6  00000160ed216eb038fb450fb38bb34f    21679503
 * Found index 6 2  000006274ce1d3978e2396f6456c4adc    25067104
 * 
 * Calculation Time 00:00:11:502
 * 
 * cur_index end    25067105
 * res_string       863dde27
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 f97c354d
 * Result Part 2 863dde27
 * 
 * </pre> 
 */
public class Day05_HowAboutANiceGameOfChess
{
  public static void main( String[] args )
  {
    /*
     * Reusing MD5-Source
     * https://github.com/ea234/Advent_of_Code_2016/blob/main/src/de/ea234/aoc2016/day14/Day14MD5.java
     */

    calculate01( "abc", 255_000_000, true );

    //calculate01( "reyedfim", 255_000_000, false );

    System.exit( 0 );
  }

  private static void calculate01( String pSalt, int pMaxLoop, boolean pKnzDebug )
  {
    String result_part_01 = calculateList1( pSalt, pMaxLoop, pKnzDebug );
    String result_part_02 = calculateList2( pSalt, pMaxLoop, pKnzDebug );

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static String calculateList1( String pSalt, int pMaxLoop, boolean pKnzDebug )
  {
    long start_time = System.currentTimeMillis();

    String res_string = "";

    wl( "--------------------------------------------------------------------------------" );
    wl( "Calculate 01 - Salt = " + pSalt );
    wl( "" );

    int result_index = 0;

    int cur_index = 0;

    while ( ( result_index == 0 ) && ( cur_index < pMaxLoop ) )
    {
      String cur_md5 = getMD5( pSalt + cur_index );

      if ( cur_md5.startsWith( "00000" ) )
      {
        res_string += "" + cur_md5.charAt( 5 );

        if ( res_string.length() == 8 )
        {
          result_index = cur_index;
        }
      }

      cur_index++;
    }

    long end_time = System.currentTimeMillis();

    wl( "" );
    wl( "Calculation Time " + getDebugLaufzeit( end_time - start_time ) );
    wl( "" );
    wl( "cur_index end    " + cur_index );
    wl( "res_string       " + res_string );
    wl( "" );

    return res_string;
  }

  private static String calculateList2( String pSalt, int pMaxLoop, boolean pKnzDebug )
  {
    long start_time = System.currentTimeMillis();

    String res_string = "";

    wl( "--------------------------------------------------------------------------------" );
    wl( "Calculate 02 - Salt = " + pSalt );
    wl( "" );

    int result_index = 0;

    int[] register_vector = new int[ 10 ];

    int cur_index = 0;

    while ( ( result_index == 0 ) && ( cur_index < pMaxLoop ) )
    {
      String cur_md5 = getMD5( pSalt + cur_index );

      if ( cur_md5.startsWith( "00000" ) )
      {
        int char_6 = cur_md5.charAt( 5 );

        if ( ( char_6 >= '0' ) && ( char_6 <= '9' ) )
        {
          int vector_nr = ( (int) char_6 ) - 48;

          if ( ( vector_nr >= 0 ) && ( vector_nr <= 7 ) )
          {
            if ( register_vector[ vector_nr ] == 0 )
            {
              wl( "Found index " + vector_nr + " " + cur_md5.charAt( 6 ) + "  " + cur_md5 + "    " + cur_index );

              register_vector[ vector_nr ] = cur_md5.charAt( 6 );

              result_index = 1;

              for ( int idx = 0; idx <= 7; idx++ )
              {
                if ( register_vector[ idx ] == 0 )
                {
                  result_index = 0;

                  break;
                }
              }
            }
          }
        }
      }

      cur_index++;
    }

    long end_time = System.currentTimeMillis();

    for ( int idx = 0; idx <= 7; idx++ )
    {
      res_string += ( (char) register_vector[ idx ] );
    }

    wl( "" );
    wl( "Calculation Time " + getDebugLaufzeit( end_time - start_time ) );
    wl( "" );
    wl( "cur_index end    " + cur_index );
    wl( "res_string       " + res_string );
    wl( "" );

    return res_string;
  }

  private static String getMD5( String pInput )
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance( "MD5" );

      byte[] md5_digest = md.digest( pInput.getBytes( "UTF-8" ) );

      StringBuilder hex_string = new StringBuilder();

      for ( byte cur_byte : md5_digest )
      {
        //hex_string.append( String.format( "%x", cur_byte ) ); // false if value is 0, you get only a 0

        //String hex_number = Integer.toHexString( 0xFF & cur_byte ); // uneccessary, because input cur_byte is already byte (=below 256)

        String hex_number = Integer.toHexString( 0xFF & cur_byte );

        if ( hex_number.length() == 1 ) hex_string.append( '0' );

        hex_string.append( hex_number );
      }

      return hex_string.toString();
    }
    catch ( Exception err_inst )
    {
      wl( err_inst.getMessage() );
    }

    return null;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }

  private static String getDebugLaufzeit( long pAnzahlMillisekunden )
  {
    long zeit_differenz_betrag = pAnzahlMillisekunden;

    long m_laufzeit_stunden   = 0;
    long m_laufzeit_minuten   = 0;
    long m_laufzeit_sekunden  = 0;
    long m_laufzeit_milli_s   = 0;

    if ( zeit_differenz_betrag > 0 )
    {
      m_laufzeit_milli_s = (long) ( zeit_differenz_betrag % 1000 );

      zeit_differenz_betrag /= 1000;

      m_laufzeit_sekunden = (long) ( zeit_differenz_betrag % 60 );

      zeit_differenz_betrag /= 60;

      m_laufzeit_minuten = (long) ( zeit_differenz_betrag % 60 );

      m_laufzeit_stunden = (long) zeit_differenz_betrag / 60;
    }

    return ( m_laufzeit_stunden < 10 ? "0" : "" ) + m_laufzeit_stunden + ":" + ( m_laufzeit_minuten < 10 ? "0" : "" ) + m_laufzeit_minuten + ":" + ( m_laufzeit_sekunden < 10 ? "0" : "" ) + m_laufzeit_sekunden + ":" + ( m_laufzeit_milli_s < 10 ? "00" : ( m_laufzeit_milli_s < 100 ? "0" : "" ) ) + m_laufzeit_milli_s;
  }
}
