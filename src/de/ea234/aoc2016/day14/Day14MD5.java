package de.ea234.aoc2016.day14;

public class Day14MD5
{
  private String str_input       = null;

  private String str_md5         = null;

  private String str_triple_char = null;

  private int    number_from     = 0;

  private int    number_to       = 0;

  public Day14MD5( int pNumber, String pInput, String pMD5, String pTripleChar )
  {
    number_from = pNumber + 1;

    number_to = number_from + 1000;

    str_input = pInput;

    str_md5 = pMD5;

    str_triple_char = pTripleChar;
  }
  
  public void setNumberTo( int pNumber ) 
  {
    number_to = pNumber;
  }

  public boolean checkActive( int pNumber )
  {
    return ( pNumber > number_from ) && ( pNumber <= number_to );
  }

  public boolean checkTripleChar( String pAllFive )
  {
    return pAllFive.indexOf( str_triple_char ) >= 0;
  }

  public String toString()
  {
    return String.format( "Input %-20s  MD5 %s  %s  = %8d - %8d ", this.str_input, this.str_md5, this.str_triple_char, number_from, number_to );
  }

}
