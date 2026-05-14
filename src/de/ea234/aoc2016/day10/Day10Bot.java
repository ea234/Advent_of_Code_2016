package de.ea234.aoc2016.day10;

public class Day10Bot
{
  private int bot_number = 0;

  private int low_value  = 0;

  private int high_value = 0;

  Day10Bot( int pNumber )
  {
    this.bot_number = pNumber;
  }

  public void setValue( int pValue )
  {
    if ( pValue < low_value )
    {
      high_value = low_value;

      low_value = pValue;
    }
    else if ( pValue > high_value )
    {
      low_value = high_value;

      high_value = pValue;
    }
    else // if ( ( pValue == low_value ) && ( pValue == high_value ) )
    {
      low_value = pValue;
    }
  }

  public int removeHighValue()
  {
    int ret_val = high_value;

    high_value = 0;

    return ret_val;
  }

  public int removeLowValue()
  {
    int ret_val = low_value;

    low_value = 0;

    return ret_val;
  }

  public boolean hasTwoMicrochips()
  {
    return ( high_value > 0 ) && ( low_value > 0 );
  }

  public String toString()
  {
    return String.format( "Bot %3d  low %3d  high %3d ", bot_number, low_value, high_value );
  }

}
