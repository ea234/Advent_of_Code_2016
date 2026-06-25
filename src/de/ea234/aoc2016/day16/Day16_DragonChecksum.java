package de.ea234.aoc2016.day16;

/**
 * <pre>
 * 
 * --- Day 16: Dragon Checksum ---
 * https://adventofcode.com/2016/day/16
 * 
 * https://www.reddit.com/r/adventofcode/comments/5imh3d/2016_day_16_solutions/
 * 
 * A 1 = 100
 * A 0 = 001
 * A 11111 = 11111000000
 * A 111100001010 = 1111000010100101011110000
 * A 110010110100 CheckSum 100
 * A 00111101111101000 = 00111101111101000011101000001000011
 * A 00111101111101000011101000001000011 = 00111101111101000011101000001000011000111101111101000111101000001000011
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * Round NR   1    Len Input         17 
 * Round NR   2    Len Input         35 
 * Round NR   3    Len Input         71 
 * Round NR   4    Len Input        143 
 * 
 * Result     10011010010010010
 * Fill Size  272
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * Round NR   1    Len Input         17 
 * Round NR   2    Len Input         35 
 * Round NR   3    Len Input         71 
 * Round NR   4    Len Input        143 
 * Round NR   5    Len Input        287 
 * Round NR   6    Len Input        575 
 * Round NR   7    Len Input       1151 
 * Round NR   8    Len Input       2303 
 * Round NR   9    Len Input       4607 
 * Round NR  10    Len Input       9215 
 * Round NR  11    Len Input      18431 
 * Round NR  12    Len Input      36863 
 * Round NR  13    Len Input      73727 
 * Round NR  14    Len Input     147455 
 * Round NR  15    Len Input     294911 
 * Round NR  16    Len Input     589823 
 * Round NR  17    Len Input    1179647 
 * Round NR  18    Len Input    2359295 
 * Round NR  19    Len Input    4718591 
 * Round NR  20    Len Input    9437183 
 * Round NR  21    Len Input   18874367 
 * 
 * Result     10101011110100011
 * Fill Size  35651584
 * 
 * </pre> 
 */
public class Day16_DragonChecksum
{
  private static final String STR_ZERO = "0";

  private static final String STR_ONE  = "1";

  private static final char   CHAR_ONE = '1';

  public static void main( String[] args )
  {
    testDragonAlgo( "1",            0 );
    testDragonAlgo( "0",            0 );
    testDragonAlgo( "11111",        0 );
    testDragonAlgo( "111100001010", 0 );

    testDragonChecksum( "110010110100", 12 );

    calculate01( "00111101111101000",      272, false );

    calculate01( "00111101111101000", 35651584, false );

    System.exit( 0 );
  }

  private static void calculate01( String pInitialState, int pFillSize, boolean pKnzDebug )
  {
    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "" );

    int round_nr = 0;

    String data_a = pInitialState;

    while ( data_a.length() < pFillSize )
    {
      round_nr++;

      data_a = testDragonAlgo( data_a, round_nr );
    }

    String result_part_01 = testDragonChecksum( data_a, pFillSize );

    wl( "" );
    wl( "Result     " + result_part_01 );
    wl( "Fill Size  " + pFillSize );
  }

  private static String testDragonAlgo( String pDataA, int round_nr )
  {
    String result_data_b = doDragonAlgo( pDataA );

    wl( String.format( "Round NR %3d    Len Input %10d ", round_nr, pDataA.length() ) );

    return result_data_b;
  }

  private static String testDragonChecksum( String pDataA, int pFillSize )
  {
    String result_data_b = doDragonChecksum( pDataA, pFillSize );

    if ( pDataA.length() < 40 )
    {
      wl( "A " + pDataA + " CheckSum " + result_data_b + "" );
    }

    return result_data_b;
  }

  private static String doDragonAlgo( String pDataA )
  {
    StringBuilder data_b = new StringBuilder();

    data_b.append( pDataA );

    data_b.append( STR_ZERO );

    for ( int idx = pDataA.length() - 1; idx >= 0; idx-- )
    {
      if ( pDataA.charAt( idx ) == CHAR_ONE )
      {
        data_b.append( STR_ZERO );
      }
      else
      {
        data_b.append( STR_ONE );
      }
    }

    return data_b.toString();
  }

  private static String doDragonChecksum( String pDataA, int pFillSize )
  {
    StringBuilder data_checksum = new StringBuilder();

    data_checksum.append( pDataA.substring( 0, pFillSize ) );

    String data_string = data_checksum.toString();

    boolean is_even = true;

    while ( is_even )
    {
      StringBuilder new_data_string = new StringBuilder();

      for ( int idx = 0; idx < data_string.length(); idx += 2 )
      {
        if ( data_string.charAt( idx ) == data_string.charAt( idx + 1 ) )
        {
          /*
           * If two characters match (00 or 11), the next checksum character is a 1
           */
          new_data_string.append( STR_ONE );
        }
        else
        {
          /*
           * If the characters do not match (01 or 10), the next checksum character is a 0
           */
          new_data_string.append( STR_ZERO );
        }
      }

      data_string = new_data_string.toString();

      /*
       * Something like "Math.isEven" would have been to obvious!
       * Great Job Java-Community.
       */
      is_even = ( data_string.length() % 2 == 0 );
    }

    return data_string;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
