package de.ea234.aoc2016.day20;

import java.math.BigInteger;

public class Day20Range
{
  private BigInteger m_min_value       = null;

  private BigInteger m_max_value       = null;

  private BigInteger m_bd_range_values = null;

  private BigInteger m_minus_one       = null;

  private BigInteger m_plus_one        = null;

  private BigInteger BIG_DECIMAL_1     = new BigInteger( "1" );

  public Day20Range( String pRange )
  {
    String[] range_split = pRange.trim().split( "-" );

    m_min_value = new BigInteger( range_split[ 0 ] );
    m_max_value = new BigInteger( range_split[ 1 ] );

    recalcRangeVal();
  }

  private boolean isValueInRange( BigInteger pBigInteger )
  {
    if ( pBigInteger == null )
    {
      return false;
    }

    if ( pBigInteger.compareTo( m_min_value ) < 0 )
    {
      return false;
    }

    if ( pBigInteger.compareTo( m_max_value ) > 0 )
    {
      return false;
    }

    return true;
  }

  public boolean isValueNeighbour( BigInteger pBigInteger )
  {
    if ( pBigInteger == null )
    {
      return false;
    }

    if ( pBigInteger.compareTo( m_minus_one ) < 0 )
    {
      return false;
    }

    if ( pBigInteger.compareTo( m_plus_one ) > 0 )
    {
      return false;
    }

    return true;
  }

  public void mergeRange( BigInteger pBigIntegerMin, BigInteger pBigIntegerMax )
  {
    m_min_value = min( m_min_value, pBigIntegerMin );

    m_max_value = max( m_max_value, pBigIntegerMax );

    recalcRangeVal();
  }

  private void recalcRangeVal()
  {
    m_minus_one = m_min_value.subtract( BIG_DECIMAL_1 );

    m_plus_one = m_max_value.add( BIG_DECIMAL_1 );

    m_bd_range_values = m_max_value.subtract( m_min_value ).add( BIG_DECIMAL_1 );
  }

  private static BigInteger max( BigInteger a, BigInteger b )
  {
    return a.compareTo( b ) >= 0 ? a : b;
  }

  private static BigInteger min( BigInteger a, BigInteger b )
  {
    return a.compareTo( b ) >= 0 ? b : a;
  }

  public long getMinValueLong()
  {
    return m_min_value.longValue();
  }

  public long getMaxValueLong()
  {
    return m_max_value.longValue();
  }

  public BigInteger getMinValue()
  {
    return m_min_value;
  }

  public BigInteger getMaxValue()
  {
    return m_max_value;
  }

  public long getRangeValues()
  {
    return m_bd_range_values.longValue();
  }

  public String toString()
  {
    return String.format( "Range %12s - %12s  Count %12s", m_min_value.toString(), m_max_value.toString(), m_bd_range_values.toString() );
  }
}
