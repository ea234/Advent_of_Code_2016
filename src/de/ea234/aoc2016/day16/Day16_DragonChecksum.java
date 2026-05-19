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
 * Result Part 1 10011010010010010
 * 
 * </pre> 
 */
public class Day16_DragonChecksum
{
  public static void main( String[] args )
  {
    testDragonAlgo( "1" );
    testDragonAlgo( "0" );
    testDragonAlgo( "11111" );
    testDragonAlgo( "111100001010" );

    testDragonChecksum( "110010110100", 12 );

    calculate01( "00111101111101000", 272, false );

    //calculate01( "00111101111101000", 35651584, false );

    System.exit( 0 );
  }

  private static void calculate01( String pInitialState, int pFillSize, boolean pKnzDebug )
  {
    String data_a = pInitialState;

    while ( data_a.length() < pFillSize )
    {
      data_a = testDragonAlgo( data_a );
    }

    String result_part_01 = testDragonChecksum( data_a, pFillSize );

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
  }

  private static String testDragonAlgo( String pDataA )
  {
    String result_data_b = doDragonAlgo( pDataA );

    if ( pDataA.length() < 40 )
    {
      wl( "A " + pDataA + " = " + result_data_b );
    }

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
    String data_b = pDataA + "0";

    for ( int idx = pDataA.length() - 1; idx >= 0; idx-- )
    {
      if ( pDataA.charAt( idx ) == '1' )
      {
        data_b += "0";
      }
      else
      {
        data_b += "1";
      }
    }

    return data_b;
  }

  private static String doDragonChecksum( String pDataA, int pFillSize )
  {
    String data_checksum = pDataA.substring( 0, pFillSize );

    boolean is_even = true;

    while ( is_even )
    {
      String data_string = data_checksum;

      data_checksum = "";

      for ( int idx = 0; idx < data_string.length(); idx += 2 )
      {
        if ( data_string.charAt( idx ) == data_string.charAt( idx + 1 ) )
        {
          /*
           * If two characters match (00 or 11), the next checksum character is a 1
           */
          data_checksum += "1";
        }
        else
        {
          /*
           * If the characters do not match (01 or 10), the next checksum character is a 0
           */
          data_checksum += "0";
        }
      }

      /*
       * Something like "Math.isEven" would have been to obvious!
       * Great Job Java-Community.
       */
      is_even = ( data_checksum.length() % 2 == 0 );
    }

    return data_checksum;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
