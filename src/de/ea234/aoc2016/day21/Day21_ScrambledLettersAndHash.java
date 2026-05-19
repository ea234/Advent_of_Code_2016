package de.ea234.aoc2016.day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 21: Scrambled Letters and Hash ---
 * https://adventofcode.com/2016/day/21
 * 
 * https://www.reddit.com/r/adventofcode/comments/5ji29h/2016_day_21_solutions/
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 aefgbcdh
 * 
 * ----------------------------------------------------------------------
 * 0123456789 0823456719 CMD swap position 1 with position 8
 * 0123456789 9123456780 CMD swap position 0 with position 9
 * 
 * AAACCCBBBB BBBCCCAAAA CMD swap letter A with letter B
 * 
 * 0123456789 9012345678 CMD rotate right 1 step
 * 0123456789 8901234567 CMD rotate right 2 step
 * 
 * 0123456789 1234567890 CMD rotate left 1 step
 * 0123456789 2345678901 CMD rotate left 2 step
 * 
 * abdec ecabd CMD rotate based on position of letter b
 * ecabd decab CMD rotate based on position of letter d
 * 
 * bcdea bdeac CMD move position 1 to position 4
 * bdeac abdec CMD move position 3 to position 0
 * 
 * 0123456789 4321056789 CMD reverse positions 0 through 4
 * 
 * ----------------------------------------------------------------------
 * Test Reverse
 * 
 *  Input " A   B " To " B   A " Back " A   B "  Equal  OK  CMD swap position 1 with position 5
 *  Input " A   B " To " B   A " Back " A   B "  Equal  OK  CMD swap letter A with letter B
 *  Input "0123456" To "0231456" Back "0123456"  Equal  OK  CMD move position 1 to position 3
 *  Input " A B C " To "A B C  " Back " A B C "  Equal  OK  CMD rotate left 1 step
 *  Input " A B C " To " B C  A" Back " A B C "  Equal  OK  CMD rotate left 2 step
 *  Input " A B C " To "A B C  " Back " A B C "  Equal  OK  CMD rotate left 8 step
 *  Input " A B C " To "  A B C" Back " A B C "  Equal  OK  CMD rotate right 1 step
 *  Input " A B C " To "C  A B " Back " A B C "  Equal  OK  CMD rotate right 2 step
 *  Input " A B C " To "  A B C" Back " A B C "  Equal  OK  CMD rotate right 8 step
 *  Input "0123456" To "4321056" Back "0123456"  Equal  OK  CMD reverse positions 0 through 4
 *  Input "abdec" To "ecabd" Back "abdec"  Equal  OK  CMD rotate based on position of letter b
 * 
 * Test rotate left
 * Rotation Nr 0    cabde
 * Rotation Nr 1    abdec
 * Rotation Nr 2    bdeca
 * Rotation Nr 3    decab
 * Rotation Nr 4    ecabd
 * 
 * ----------------------------------------------------------------------
 * Input abcde
 * 
 * NORMAL  abcde  |  
 * NORMAL  ebcda  |  swap position 4 with position 0
 * NORMAL  edcba  |  swap letter d with letter b
 * NORMAL  abcde  |  reverse positions 0 through 4
 * NORMAL  bcdea  |  rotate left 1 step
 * NORMAL  bdeac  |  move position 1 to position 4
 * NORMAL  abdec  |  move position 3 to position 0
 * NORMAL  ecabd  |  rotate based on position of letter b
 * NORMAL  decab  |  rotate based on position of letter d
 * 
 *        ----- Reverse ----
 * 
 * REVERSE ecabd  |  rotate based on position of letter d
 * REVERSE abdec  |  rotate based on position of letter b
 * REVERSE bdeac  |  move position 3 to position 0
 * REVERSE bcdea  |  move position 1 to position 4
 * REVERSE abcde  |  rotate left 1 step
 * REVERSE edcba  |  reverse positions 0 through 4
 * REVERSE ebcda  |  swap letter d with letter b
 * REVERSE abcde  |  swap position 4 with position 0
 * REVERSE abcde  |  
 * 
 * Result Part 1 decab
 * Result Part 2 abcde
 * 
 * ----------------------------------------------------------------------
 * Input decab
 * 
 * REVERSE ecabd  |  rotate based on position of letter d
 * REVERSE abdec  |  rotate based on position of letter b
 * REVERSE bdeac  |  move position 3 to position 0
 * REVERSE bcdea  |  move position 1 to position 4
 * REVERSE abcde  |  rotate left 1 step
 * REVERSE edcba  |  reverse positions 0 through 4
 * REVERSE ebcda  |  swap letter d with letter b
 * REVERSE abcde  |  swap position 4 with position 0
 * REVERSE abcde  |  
 * 
 * Result Part 2 abcde
 * 
 * 
 * ----------------------------------------------------------------------
 * Input fbgdceah
 * 
 * REVERSE fbgdecah  |  reverse positions 4 through 5
 * REVERSE fbgedcah  |  reverse positions 3 through 4
 * REVERSE fbdegcah  |  swap letter d with letter g
 * REVERSE fbgedcah  |  swap position 2 with position 4
 * REVERSE cahfbged  |  rotate right 5 steps
 * ...
 * REVERSE cahbfgde  |  move position 5 to position 4
 * REVERSE cahbfged  |  reverse positions 6 through 7
 * REVERSE cgahbfed  |  move position 1 to position 5
 * REVERSE cdahbfeg  |  swap letter g with letter d
 * REVERSE egcdahbf  |  rotate based on position of letter a
 * 
 * Result Part 2 egcdahbf
 * 
 * 
 * </pre> 
 */
public class Day21_ScrambledLettersAndHash
{
  public static void main( String[] args )
  {
    test();

    String test_input = "";

    test_input += ",swap position 4 with position 0";
    test_input += ",swap letter d with letter b";
    test_input += ",reverse positions 0 through 4";
    test_input += ",rotate left 1 step";
    test_input += ",move position 1 to position 4";
    test_input += ",move position 3 to position 0";
    test_input += ",rotate based on position of letter b";
    test_input += ",rotate based on position of letter d";

    calculatePart01( test_input, "abcde", true );
    calculatePart02( test_input, "decab", true );
    
    //calculate01( getListProd(), "abcdefgh", false );
    calculate02(  getListProd(), "fbgdceah", false );
    
    System.exit( 0 );
  }
  
  private static void calculatePart01( String pString, String pStart, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pStart, pKnzDebug );
  }

  private static void calculatePart02( String pString, String pStart, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculate02( converted_string_list, pStart, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, String pStart, boolean pKnzDebug )
  {
    wl( "" );
    wl( "----------------------------------------------------------------------" );
    wl( "Input " + pStart );
    wl( "" );

    byte[] password = pStart.getBytes();

    for ( String input_str : pListInput )
    {
      doCmd( password, input_str );

      wl( "NORMAL  " + ( new String( password ) ) + "  |  " + input_str );
    }

    String result_part_01 = new String( password );

    wl( "" );
    wl( "       ----- Reverse ----" );
    wl( "" );

    for ( int index = pListInput.size() - 1; index >= 0; index-- ) 
    {
      String input_str = pListInput.get( index );
        
      doCmdReverse( password, input_str );

      wl( "REVERSE " + ( new String( password ) ) + "  |  " + input_str );
    }

    String result_part_02 = new String( password );

    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }
  
  private static void calculate02( List< String > pListInput, String pStart, boolean pKnzDebug )
  {
    wl( "" );
    wl( "----------------------------------------------------------------------" );
    wl( "Input " + pStart );
    wl( "" );

    byte[] password = pStart.getBytes();

    for ( int index = pListInput.size() - 1; index >= 0; index-- ) 
    {
      String input_str = pListInput.get( index );
        
      doCmdReverse( password, input_str );

      wl( "REVERSE " + ( new String( password ) ) + "  |  " + input_str );
    }

    String result_part_02 = new String( password );

    wl( "" );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }
  
  private static void test()
  {
    String test_input = "0123456789";

    wl( "" );
    wl( "----------------------------------------------------------------------" );

    doTestCmdNormal( test_input, "swap position 1 with position 8" );
    doTestCmdNormal( test_input, "swap position 0 with position 9" );

    wl( "" );
    doTestCmdNormal( "AAACCCBBBB", "swap letter A with letter B" );

    wl( "" );
    doTestCmdNormal( test_input, "rotate right 1 step" );
    doTestCmdNormal( test_input, "rotate right 2 step" );

    wl( "" );
    doTestCmdNormal( test_input, "rotate left 1 step" );
    doTestCmdNormal( test_input, "rotate left 2 step" );

    wl( "" );
    doTestCmdNormal( "abdec", "rotate based on position of letter b" );
    doTestCmdNormal( "ecabd", "rotate based on position of letter d" );

    wl( "" );
    doTestCmdNormal( "bcdea", "move position 1 to position 4" );
    doTestCmdNormal( "bdeac", "move position 3 to position 0" );

    wl( "" );
    doTestCmdNormal( "0123456789", "reverse positions 0 through 4" );

    wl( "" );
    wl( "----------------------------------------------------------------------" );
    wl( "Test Reverse" );
    wl( "" );

    doTestCmdReverse( " A   B ", "swap position 1 with position 5" );

    doTestCmdReverse( " A   B ", "swap letter A with letter B" );

    doTestCmdReverse( "0123456", "move position 1 to position 3" );

    doTestCmdReverse( " A B C ", "rotate left 1 step" );
    doTestCmdReverse( " A B C ", "rotate left 2 step" );
    doTestCmdReverse( " A B C ", "rotate left 8 step" );

    doTestCmdReverse( " A B C ", "rotate right 1 step" );
    doTestCmdReverse( " A B C ", "rotate right 2 step" );
    doTestCmdReverse( " A B C ", "rotate right 8 step" );

    doTestCmdReverse( "0123456", "reverse positions 0 through 4" );

    doTestCmdReverse( "abdec", "rotate based on position of letter b" );

    wl( "" );
    wl( "Test rotate left" );

    String string_result_to_be = "ecabd";

    String string_string = string_result_to_be + string_result_to_be;

    int len_input = string_result_to_be.length();
    int start_idx = 1;

    for ( int rot_nr = 0; rot_nr < string_result_to_be.length(); rot_nr++ )
    {
      wl( "Rotation Nr " + rot_nr + "    " + string_string.substring( start_idx, start_idx + len_input ) );

      start_idx++;
    }
  }

  private static void doTestCmdNormal( String pString, String pCmd )
  {
    byte[] byte_array = pString.getBytes();

    doCmd( byte_array, pCmd );

    wl( pString + " " + ( new String( byte_array ) ) + " CMD " + pCmd );
  }

  private static void doTestCmdReverse( String pString, String pCmd )
  {
    byte[] byte_array = pString.getBytes();

    doCmd( byte_array, pCmd );

    String result_forwad = new String( byte_array );

    byte[] byte_array_reverse = result_forwad.getBytes();

    doCmdReverse( byte_array_reverse, pCmd );

    String result_reverse = new String( byte_array_reverse );

    boolean knz_equal = pString.equals( result_reverse );

    wl( " Input \"" + pString + "\" To \"" + result_forwad + "\" Back \"" + result_reverse + "\"  Equal " + ( knz_equal ? " OK " : "####" ) + " CMD " + pCmd );
  }

  private static void doCmd( byte[] pByteArray, String pCmd )
  {
    if ( pCmd.isBlank() )
    {
    }
    else if ( pCmd.startsWith( "swap position " ) )
    {
      int position_x = ( (int) pCmd.charAt( 14 ) ) - 48;
      int position_y = ( (int) pCmd.charAt( 30 ) ) - 48;

      byte temp = pByteArray[ position_x ];

      pByteArray[ position_x ] = pByteArray[ position_y ];

      pByteArray[ position_y ] = temp;
    }
    else if ( pCmd.startsWith( "swap letter " ) )
    {
      byte ascii_x = (byte) ( (int) pCmd.charAt( 12 ) );
      byte ascii_y = (byte) ( (int) pCmd.charAt( 26 ) );

      for ( int idx = 0; idx < pByteArray.length; idx++ )
      {
        if ( pByteArray[ idx ] == ascii_x )
        {
          pByteArray[ idx ] = ascii_y;
        }
        else if ( pByteArray[ idx ] == ascii_y )
        {
          pByteArray[ idx ] = ascii_x;
        }
      }
    }
    else if ( pCmd.startsWith( "rotate left " ) )
    {
      int x_steps = ( (int) pCmd.charAt( 12 ) ) - 48;

      for ( int step_counter = 0; step_counter < x_steps; step_counter++ )
      {
        byte save_first_byte = pByteArray[ 0 ];

        for ( int idx = 0; idx < ( pByteArray.length - 1 ); idx++ )
        {
          pByteArray[ idx ] = pByteArray[ idx + 1 ];
        }

        pByteArray[ pByteArray.length - 1 ] = save_first_byte;
      }
    }
    else if ( pCmd.startsWith( "rotate right " ) )
    {
      int x_steps = ( (int) pCmd.charAt( 13 ) ) - 48;

      for ( int step_counter = 0; step_counter < x_steps; step_counter++ )
      {
        byte save_first_byte = pByteArray[ pByteArray.length - 1 ];

        for ( int idx = pByteArray.length - 1; idx > 0; idx-- )
        {
          pByteArray[ idx ] = pByteArray[ idx - 1 ];
        }

        pByteArray[ 0 ] = save_first_byte;
      }
    }
    else if ( pCmd.startsWith( "move position " ) )
    {
      int index_start = ( (int) pCmd.charAt( 14 ) ) - 48;
      int index_end   = ( (int) pCmd.charAt( 28 ) ) - 48;

      byte save_first_byte = pByteArray[ index_start ];

      if ( index_start < index_end )
      {
        int idx = index_start;

        while ( idx != index_end )
        {
          pByteArray[ idx ] = pByteArray[ incIndex( idx, pByteArray.length ) ];

          idx = incIndex( idx, pByteArray.length );
        }
      }
      else
      {
        int idx = index_start;

        while ( idx != index_end )
        {
          pByteArray[ idx ] = pByteArray[ decIndex( idx, pByteArray.length ) ];

          idx = decIndex( idx, pByteArray.length );
        }
      }

      pByteArray[ index_end ] = save_first_byte;
    }
    else if ( pCmd.startsWith( "rotate based on position of letter" ) )
    {
      byte ascii_letter = (byte) ( (int) pCmd.charAt( 35 ) );

      int letter_index = 0;

      while ( ( letter_index < pByteArray.length ) && ( pByteArray[ letter_index ] != ascii_letter ) )
      {
        letter_index++;
      }

      int rotate_one_time = 1;

      int rotate_additional_one_time = ( letter_index >= 4 ? 1 : 0 );

      int x_steps = rotate_one_time + letter_index + rotate_additional_one_time;

      for ( int step_counter = 0; step_counter < x_steps; step_counter++ )
      {
        byte save_first_byte = pByteArray[ pByteArray.length - 1 ];

        for ( int idx = pByteArray.length - 1; idx > 0; idx-- )
        {
          pByteArray[ idx ] = pByteArray[ idx - 1 ];
        }

        pByteArray[ 0 ] = save_first_byte;
      }
    }
    else if ( pCmd.startsWith( "reverse positions " ) )
    {
      int index_start = ( (int) pCmd.charAt( 18 ) ) - 48;
      int index_end   = ( (int) pCmd.charAt( 28 ) ) - 48;

      if ( index_end < index_start )
      {
        int index_save = index_end;

        index_end = index_start;

        index_start = index_save;
      }

      while ( ( index_start < index_end ) && ( index_start != index_end ) )
      {
        byte temp = pByteArray[ index_end ];

        pByteArray[ index_end ] = pByteArray[ index_start ];

        pByteArray[ index_start ] = temp;

        index_end--;
        index_start++;
      }
    }
  }

  private static void doCmdReverse( byte[] pByteArray, String pCmd )
  {
    if ( pCmd.isBlank() )
    {
    }
    else if ( pCmd.startsWith( "swap position " ) )
    {
      int position_x = ( (int) pCmd.charAt( 14 ) ) - 48;
      int position_y = ( (int) pCmd.charAt( 30 ) ) - 48;

      byte temp = pByteArray[ position_x ];

      pByteArray[ position_x ] = pByteArray[ position_y ];

      pByteArray[ position_y ] = temp;
    }
    else if ( pCmd.startsWith( "swap letter " ) )
    {
      byte ascii_x = (byte) ( (int) pCmd.charAt( 12 ) );
      byte ascii_y = (byte) ( (int) pCmd.charAt( 26 ) );

      for ( int idx = 0; idx < pByteArray.length; idx++ )
      {
        if ( pByteArray[ idx ] == ascii_x )
        {
          pByteArray[ idx ] = ascii_y;
        }
        else if ( pByteArray[ idx ] == ascii_y )
        {
          pByteArray[ idx ] = ascii_x;
        }
      }
    }
    else if ( pCmd.startsWith( "move position " ) )
    {
      int index_start = ( (int) pCmd.charAt( 28 ) ) - 48;
      int index_end   = ( (int) pCmd.charAt( 14 ) ) - 48;

      byte save_first_byte = pByteArray[ index_start ];

      if ( index_start < index_end )
      {
        int idx = index_start;

        while ( idx != index_end )
        {
          pByteArray[ idx ] = pByteArray[ incIndex( idx, pByteArray.length ) ];

          idx = incIndex( idx, pByteArray.length );
        }
      }
      else
      {
        int idx = index_start;

        while ( idx != index_end )
        {
          pByteArray[ idx ] = pByteArray[ decIndex( idx, pByteArray.length ) ];

          idx = decIndex( idx, pByteArray.length );
        }
      }

      pByteArray[ index_end ] = save_first_byte;
    }
    else if ( pCmd.startsWith( "rotate left " ) )
    {
      int x_steps = ( (int) pCmd.charAt( 12 ) ) - 48;

      for ( int step_counter = 0; step_counter < x_steps; step_counter++ )
      {
        byte save_first_byte = pByteArray[ pByteArray.length - 1 ];

        for ( int idx = pByteArray.length - 1; idx > 0; idx-- )
        {
          pByteArray[ idx ] = pByteArray[ idx - 1 ];
        }

        pByteArray[ 0 ] = save_first_byte;
      }
    }
    else if ( pCmd.startsWith( "rotate right " ) )
    {
      int x_steps = ( (int) pCmd.charAt( 13 ) ) - 48;

      for ( int step_counter = 0; step_counter < x_steps; step_counter++ )
      {
        byte save_first_byte = pByteArray[ 0 ];

        for ( int idx = 0; idx < ( pByteArray.length - 1 ); idx++ )
        {
          pByteArray[ idx ] = pByteArray[ idx + 1 ];
        }

        pByteArray[ pByteArray.length - 1 ] = save_first_byte;
      }
    }
    else if ( pCmd.startsWith( "reverse positions " ) )
    {
      int index_start = ( (int) pCmd.charAt( 18 ) ) - 48;
      int index_end   = ( (int) pCmd.charAt( 28 ) ) - 48;

      if ( index_end < index_start )
      {
        int index_save = index_end;

        index_end = index_start;

        index_start = index_save;
      }
      while ( ( index_start < index_end ) && ( index_start != index_end ) )
      {
        byte temp = pByteArray[ index_end ];

        pByteArray[ index_end ] = pByteArray[ index_start ];

        pByteArray[ index_start ] = temp;

        index_end--;
        index_start++;
      }
    }
    else if ( pCmd.startsWith( "rotate based on position of letter" ) )
    {
      byte ascii_letter = (byte) ( (int) pCmd.charAt( 35 ) );

      doCmdRotateReverse( pByteArray, ascii_letter );
    }
  }

  private static void doCmdRotateReverse( byte[] pByteArray, byte pAsciiLetter )
  {
    // ecabd -- abdec

    String string_result_to_be = new String( pByteArray );

    String string_string = string_result_to_be + string_result_to_be;

    int len_input = string_result_to_be.length();
    int start_idx = 1;

    for ( int rot_nr = 0; rot_nr < string_result_to_be.length(); rot_nr++ )
    {
      String cur_input_restored = string_string.substring( start_idx, start_idx + len_input );

      //wl( "Rotation Nr. " + rot_nr + "    " + cur_input_restored );

      start_idx++;

      /*
       * do the rotate cmd the normal way
       */

      byte[] byte_array_temp = cur_input_restored.getBytes();

      int letter_index = 0;

      while ( ( letter_index < byte_array_temp.length ) && ( byte_array_temp[ letter_index ] != pAsciiLetter ) )
      {
        letter_index++;
      }

      int rotate_one_time = 1;

      int rotate_additional_one_time = ( letter_index >= 4 ? 1 : 0 );

      int x_steps = rotate_one_time + letter_index + rotate_additional_one_time;

      for ( int step_counter = 0; step_counter < x_steps; step_counter++ )
      {
        byte save_first_byte = byte_array_temp[ byte_array_temp.length - 1 ];

        for ( int idx = byte_array_temp.length - 1; idx > 0; idx-- )
        {
          byte_array_temp[ idx ] = byte_array_temp[ idx - 1 ];
        }

        byte_array_temp[ 0 ] = save_first_byte;
      }

      /*
       * check if the result is the input string
       */
      String string_result_act = new String( byte_array_temp );

      if ( string_result_act.equals( string_result_to_be ) )
      {
        //wl( "Found Input " + cur_input_restored );

        byte[] byte_array_temp1 = cur_input_restored.getBytes();

        for ( int idx = 0; idx < byte_array_temp1.length; idx++ )
        {
          pByteArray[ idx ] = byte_array_temp1[ idx ];
        }

        return;
      }
    }
  }

  private static int incIndex( int pIndex, int pMaxIndex )
  {
    if ( ( pIndex + 1 ) >= pMaxIndex ) return 0;

    return pIndex + 1;
  }

  private static int decIndex( int pIndex, int pMaxIndex )
  {
    if ( ( pIndex - 1 ) < 0 ) return pMaxIndex - 1;

    return pIndex - 1;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day21_input.txt";

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
