package de.ea234.util;

public class FkCsvList
{
  public static String getCsvList( String[] pList )
  {
    String csv_string = "";

    for ( int index = 0; index < pList.length; index++ )
    {
      if ( index > 0 )
      {
        csv_string += ",";
      }

      csv_string += pList[ index ];
    }

    return csv_string;
  }

  public static String getCsvList( int[] pList )
  {
    String csv_string = "";

    for ( int index = 0; index < pList.length; index++ )
    {
      if ( index > 0 )
      {
        csv_string += ",";
      }

      csv_string += pList[ index ];
    }

    return csv_string;
  }

}
