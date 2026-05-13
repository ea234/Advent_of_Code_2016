package de.ea234.aoc2016.day09;

public class Day09_ParserVersion1
{
  private static String parser_str_input = "";

  private static char   parser_cur_char  = ' ';

  private static int    parser_index     = 0;

  public static String decrypt( String pInput )
  {
    /*
     * Reusing parser from 
     * https://github.com/ea234/Advent_of_Code_2020/blob/main/src/day_18__Operation_Order.ts
     */

    if ( pInput.isBlank() ) return "";

    parser_str_input = pInput;

    parser_cur_char = ' ';

    parser_index = 0;

    String decryptet_string = "";

    while ( read() )
    {
      if ( ( ( parser_cur_char >= 'A' ) && ( parser_cur_char <= 'Z' ) ) || ( ( parser_cur_char >= 'a' ) && ( parser_cur_char <= 'z' ) ) )
      {
        decryptet_string += parser_cur_char;
      }
      else if ( parser_cur_char == '(' )
      {
        read();

        int marker_character_count = parseNumber();

        //if ( parser_cur_char != 'x' ) wl( "Error: character 'x' expected" );

        read();

        int marker_repeat = parseNumber();

        //if ( parser_cur_char != ')' ) wl( "Error: character ')' expected" );

        String sub_string = parser_str_input.substring( parser_index, parser_index + marker_character_count );

        read();

        //decryptet_string += "[" + marker_character_count + " x " + marker_repeat + "]";

        decryptet_string += sub_string.repeat( marker_repeat );

        parser_index += marker_character_count;

        parser_index--;
      }
    }

    return decryptet_string;
  }
  
  
  private static boolean read()
  {
    /*
     * If no input string is left to read, return false
     */
    if ( parser_index >= parser_str_input.length() ) return false;

    /*
     * Update the cur_char-variable with the current value
     */
    parser_cur_char = parser_str_input.charAt( parser_index );

    /*
     * Move the read-index one further to the string end
     */
    parser_index++;

    /*
     * Return true for one read char
     */
    return true;
  }

  private static int parseNumber()
  {
    /*
     * Consume the first read number.
     */
    int number_read = ( (int) parser_cur_char ) - 48;

    /*
     * Read the rest of the number
     */
    while ( readNumber() )
    {
      number_read = ( number_read * 10 ) + ( ( (int) parser_cur_char ) - 48 );
    }

    if ( parser_index >= parser_str_input.length() ) return number_read;

    // parser_index--;

    return number_read;
  }

  private static boolean readNumber()
  {
    /*
     * Read the next character from the input
     */
    if ( read() == false )
    {
      return false;
    }

    /*
     * return true, if the new character is a number
     */
    return ( ( parser_cur_char >= '0' ) && ( parser_cur_char <= '9' ) );
  }
}
