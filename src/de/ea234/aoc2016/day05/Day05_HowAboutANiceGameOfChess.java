package de.ea234.aoc2016.day05;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
 * Calculate - Salt = reyedfim
 * 
 * 
 * Calculation Time 00:00:03:718
 * 
 * cur_index end          7183956
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 f97c354d
 * Result Part 2 0
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
    
    calculate01( "reyedfim", 255_000_000, false );

    System.exit( 0 );
  }

  private static void calculate01( String pSalt, int pMaxLoop, boolean pKnzDebug )
  {
    String result_part_01 = calculateList( pSalt, -1, pMaxLoop, pKnzDebug );
    int result_part_02 = 0;

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static String calculateList( String pSalt, int pKeyStretchingValue, int pMaxLoop, boolean pKnzDebug )
  {
    long start_time = System.currentTimeMillis();

    String res_string = "";

    wl( "--------------------------------------------------------------------------------" );
    wl( "Calculate - Salt = " + pSalt );
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
    wl( "cur_index end          " + cur_index );
    wl( "" );

    return res_string;
  }

  private static String testMD5( String pInput )
  {
    String md5_hash = getMD5( pInput );

    wl( pInput + " = " + md5_hash );

    return md5_hash;
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

    long m_laufzeit_stunden = 0;
    long m_laufzeit_minuten = 0;
    long m_laufzeit_sekunden = 0;
    long m_laufzeit_milli_s = 0;

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
