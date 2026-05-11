package de.ea234.aoc2016.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import de.ea234.util.FkProperties;

/**
 * <pre>
 * --- Day 2: Bathroom Security ---
 * https://adventofcode.com/2016/day/2
 * 
 * https://www.reddit.com/r/adventofcode/comments/5g1hfm/2016_day_2_solutions/
 * 
 * 
 * Properties for keypad-definition   1, 234,56789, ABC,  D
 * 
 *  R1C3=1
 *  R2C2=2
 *  R2C3=3
 *  R2C4=4
 *  R3C1=5
 *  R3C2=6
 *  R3C3=7
 *  R3C4=8
 *  R3C5=9
 *  R4C2=A
 *  R4C3=B
 *  R4C4=C
 *  R5C3=D
 * 
 * 
 * Nr 0  Row 3 Col 1 Number 5
 * 
 *   + keypad hit R key R3C2 = 6
 *   + keypad hit R key R3C3 = 7
 *   + keypad hit D key R4C3 = B
 *   + keypad hit D key R5C3 = D
 * Nr 1  Row 5 Col 3 Number D
 * 
 *   + keypad hit U key R4C3 = B
 *   + keypad hit R key R4C4 = C
 *   + keypad hit L key R4C3 = B
 * Nr 2  Row 4 Col 3 Number B
 * 
 *   + keypad hit U key R3C3 = 7
 *   + keypad hit U key R2C3 = 3
 *   + keypad hit U key R1C3 = 1
 *   + keypad hit D key R2C3 = 3
 * Nr 3  Row 2 Col 3 Number 3
 * 
 * 
 * Result Part 1 1985
 * Result Part 2 5DB3
 * 
 * 
 * </pre>
 */
public class Day02_BathroomSecurity
{
  public static void main( String[] args )
  {
    String test_input = "ULL,RRDDD,LURDL,UUUUD";

    calculatePart01( test_input, true );

    calculate01( getListProd(), true );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, boolean pKnzDebug )
  {
    /*
     * *******************************************************************************************************
     * Initializing Variables
     * *******************************************************************************************************
     */
    String result_part_01 = "";
    String result_part_02 = "";

    result_part_01 = doMovement( pListInput, 2, 2, "123,456,789" );
    result_part_02 = doMovement( pListInput, 3, 1, "  1, 234,56789, ABC,  D" );

    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  public static void setPropertiesKeyPad( Properties pProperties, String pKeyPadDefinition )
  {
    List< String > key_pad_rows = Arrays.stream( pKeyPadDefinition.split( "," ) ).collect( Collectors.toList() );

    int cur_nr_row = 1;

    for ( String cur_row : key_pad_rows )
    {
      for ( int current_col = 0; current_col < cur_row.length(); current_col++ )
      {
        char current_char = cur_row.charAt( (int) current_col );

        if ( current_char != ' ' )
        {
          String keypad_coords = "R" + cur_nr_row + "C" + ( current_col + 1 );

          pProperties.setProperty( keypad_coords, "" + current_char );
        }
      }

      cur_nr_row++;
    }
  }

  private static String doMovement( List< String > pListInput, int pKeypadStartRow, int pKeypadStartCol, String pKeypadDefinition )
  {
    String result_key = "";

    Properties key_pad = new Properties();

    setPropertiesKeyPad( key_pad, pKeypadDefinition );

    wl( "" );
    wl( "Properties for keypad-definition " + pKeypadDefinition );
    wl( "" );
    wl( FkProperties.getStrPropertiesKeyValue( key_pad, true ) );
    wl( "" );

    int keypad_row = pKeypadStartRow;

    int keypad_col = pKeypadStartCol;

    int nr_move = 0;

    for ( String input_str : pListInput )
    {
      for ( int current_col = 0; current_col < input_str.length(); current_col++ )
      {
        char current_char = input_str.charAt( (int) current_col );

        int move_delta_col = 0;
        int move_delta_row = 0;

        if ( current_char == 'U' )
        {
          move_delta_row = -1;
        }
        else if ( current_char == 'D' )
        {
          move_delta_row = 1;
        }
        else if ( current_char == 'L' )
        {
          move_delta_col = -1;
        }
        else if ( current_char == 'R' )
        {
          move_delta_col = 1;
        }

        String keypad_key = "R" + ( keypad_row + move_delta_row ) + "C" + ( keypad_col + move_delta_col );

        if ( key_pad.getProperty( keypad_key ) != null )
        {
          wl( "  + keypad hit " + current_char + " key " + keypad_key + " = " + key_pad.getProperty( keypad_key ) );

          keypad_row += move_delta_row;
          keypad_col += move_delta_col;
        }
      }

      String keypad_number = key_pad.getProperty( "R" + keypad_row + "C" + keypad_col );

      result_key += keypad_number;

      wl( "Nr " + nr_move + "  Row " + keypad_row + " Col " + keypad_col + " Number " + keypad_number );
      wl( "" );
      
      nr_move++;
    }

    return result_key;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2016__day02_input.txt";

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
