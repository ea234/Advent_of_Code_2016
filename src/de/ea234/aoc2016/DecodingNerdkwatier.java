package de.ea234.aoc2016;

public class DecodingNerdkwatier
{
  public static void main( String[] args )
  {
    /*
     * AOC 2016 - Sponsor
     * 
     * https://adventofcode.com/2016/sponsors/redirect?url=https%3A%2F%2Finfi%2Enl%2Fvacature%2Finfi%2Dzoekt%2Deen%2Dontwikkelaar%2F
     * 
     * Infi - Fvzcry gbpu? Xbz arkg-yriry glcra va Hgerpug bc baf areqxjnegvre!
     * 
     */

    String input = "Fvzcry gbpu? Xbz arkg-yriry glcra va Hgerpug bc baf areqxjnegvre";
    //Simpel toch? Kom next-level typen in Utrecht op ons nerdkwartier

    wl( rot13( input ) );

    System.exit( 0 );
  }

  private static String rot13( String pString )
  {
    String ergebnis = "";

    if ( pString != null )
    {
      int akt_index = 0;

      while ( akt_index < pString.length() )
      {
        char aktuelles_zeichen = pString.charAt( akt_index );

        if ( aktuelles_zeichen >= 'a' && aktuelles_zeichen <= 'm' ) aktuelles_zeichen += 13;
        else if ( aktuelles_zeichen >= 'n' && aktuelles_zeichen <= 'z' ) aktuelles_zeichen -= 13;
        else if ( aktuelles_zeichen >= 'A' && aktuelles_zeichen <= 'M' ) aktuelles_zeichen += 13;
        else if ( aktuelles_zeichen >= 'N' && aktuelles_zeichen <= 'Z' ) aktuelles_zeichen -= 13;

        ergebnis = ergebnis + aktuelles_zeichen;

        akt_index++;
      }
    }

    return ergebnis;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
