

package de.ea234.util;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

/**
 * <pre>
 * Die Funktionen sollen moeglichst nicht miteinander verwoben werden, damit
 * jede einzelne Funktion fuer sich allein funktionsfaehig und kopierbar bleibt.
 * 
 * Die Funktionen sollen Java 1.3 kompatibel sein, daher keine Funktionsaufrufe 
 * von neueren Java-Funktionen. Warnungen "Unessecary Cast" wurden nicht entfernt, 
 * da in einer anderen Umgebung der Cast evtl. nicht mehr "Unessecary" ist.
 * 
 * 
 * Properties test_properties = FkTest.getTestDaten();
 * 
 * String str_text         = FkProperties.getStrPropertiesKeyValue( test_properties, true, 50 );
 * String str_trennzeichen = null;
 * String str_praefix      = null;
 * String str_suffix       = null;
 *
 * int max_breite          = 1000;
 * 
 * FkDatei.schreibeDatei( FkSystem.getStdRootVerzeichnis() + "propertie_ausgabe.txt", FkStringFeld.getStringMaxBreite( str_text, str_trennzeichen, max_breite, str_praefix, str_suffix ) );
 *
 * </pre>
 */
public class FkProperties
{
  /** Eine "Instanz" von java.util.Properties welche \"null\" ist */
  public static Properties PROPERTIES_NULL = null;

  /**
   * <pre>
   * Fuehrt einen Class-Cast auf die Klasse "java.util.Properties" auf das uebergebene Objekt aus. 
   *
   * Im Falle einer ClassCastException wird "null" zurueckgegeben.
   * Ist das Objekt selber "null" wird "null" zurueckgegeben.
   *
   * Properties propertie_instanz = FkProperties.getClassCast( java_object );
   *
   * if ( propertie_instanz == null )
   * {
   *   str_fehler = "Die Instanz \"java_object\" ist keine Instanz der Klasse \"java.util.Properties\" ";
   *
   *   return;
   * }
   *
   * </pre>
   *
   * @param  pObjekt        das zu castende Objekt
   * @return das Objekt als Instanz von "java.util.Properties", oder "null" im Fehlerfall
   */
  public static Properties getClassCast( Object pObjekt )
  {
    /*
     * Pruefung: pObjekt ungleich null ?
     */
    if ( pObjekt != null )
    {
      try
      {
        return (Properties) pObjekt;
      }
      catch ( ClassCastException abgf_fehler )
      {
        // keine Aktion im Fehlerfall, da Rueckgabe von null
      }
    }

    return null;
  }

  /**
   * <pre>
   * Musterfunktion fuer eine grundlegende Schleife durch eine Propertieliste.
   * </pre>
   * 
   * @param pProperties die auszugebenden Properties
   */
  public static void printProperties( Properties pProperties )
  {
    if ( pProperties != null )
    {
      Enumeration enumeration_keys = pProperties.keys();

      String property_key = null;

      String property_value = null;

      while ( enumeration_keys.hasMoreElements() )
      {
        property_key = (String) enumeration_keys.nextElement();

        property_value = pProperties.getProperty( property_key );

        if ( property_value != null )
        {
          // pProperties.setProperty( property_key, FkStringFeld.left( property_value, "." ) );

          System.out.println( property_key + "=" + property_value );
        }
      }

      enumeration_keys = null;

      property_key = null;

      property_value = null;
    }
  }

  /**
   * <pre>
   * Fuegt der Propertie-Instanz den Schluessel mit dem Wert hinzu. 
   * 
   * Alle Parameter muessen gesetzt sein.
   * 
   * Ist ein Parameterwert gleich null, wird kein Wert gespeichert.
   * </pre>
   * 
   * @param pProperties die Propertie-Instanz, in welcher der Key-Wert gespeichert werden soll
   * @param pKey der Propertie-Key, muss gesetzt sein und eine Laenge groesser 0 haben
   * @param pValue der Propertie-Wert, muss gesetzt sein, darf eine Laenge von 0 haben
   */
  public static void addProperty( Properties pProperties, String pKey, String pValue )
  {
    try
    {
      /*
       * Pruefung: Alle Parameter muessen ungleich null sein
       * 
       * Der Propertie-Wert wird nur gesetzt, wenn alle Parameter ungleich null sind.
       *  
       * Ist ein Parameter null, wird keine Aktion ausgefuehrt.
       */
      if ( ( pProperties != null ) && ( ( pKey != null ) && ( pKey.length() > 0 ) ) && ( pValue != null ) )
      {
        pProperties.setProperty( pKey, pValue );
      }
    }
    catch ( Exception err_inst )
    {
      // keine Fehlerbehandlung
    }
  }

  /**
   * <pre>
   * Liest Properties aus einer Datei.
   * 
   * Kommt es zu einem Fehler, wird null zurueckgegeben.
   * 
   * 
   * String datei_properties = "C:/dir1/properties_config.txt";
   *
   * Properties inst_properties_aus_datei = FkProperties.ladePropertiesFromDatei( datei_properties );
   *
   * if ( inst_properties_aus_datei == null )
   * {
   *   wl( "Properties nicht geladen " + datei_properties );
   * }
   * else
   * {
   *   wl( "Konfiguration geladen" );
   *   
   *   wl( FkProperties.getStrProperties( inst_properties_aus_datei, true, "", "=", "", 30, -1, false, false, false ) );
   * }
   * 
   * </pre>
   * 
   * @param pDateiName der Dateiname auf die INI-Datei
   * @return eine Instanz von java.util.Properties mit den gelesenen Schluessel/Wert-Paaren aus der Datei
   * @throws Exception wenn was schiefgeht
   */
  public static Properties ladePropertiesFromDatei( String pDateiName ) throws Exception
  {
    Properties inst_properties_aus_datei = new Properties();

    try
    {
      inst_properties_aus_datei.load( new FileInputStream( pDateiName ) );
    }
    catch ( Exception err_inst )
    {
      return null;
    }

    return inst_properties_aus_datei;
  }

  /**
   * Ermittelt die maximale Laenge aus den gespeicherten Werten der Properties.
   * 
   * @param pProperties die zu untersuchende Propertieinstanz
   * @return die maximale Stringlaenge aus den Propertie-Werten
   */
  public static int getMaxLenValue( Properties pProperties )
  {
    int max_len_propertie_value = 0;

    if ( pProperties != null )
    {
      Enumeration enumeration_keys = pProperties.keys();

      String property_key = null;

      String property_value = null;

      while ( enumeration_keys.hasMoreElements() )
      {
        property_key = (String) enumeration_keys.nextElement();

        property_value = pProperties.getProperty( property_key );

        try
        {
          if ( property_value != null )
          {
            if ( property_value.length() > max_len_propertie_value )
            {
              max_len_propertie_value = property_value.length();
            }
          }
        }
        catch ( Exception err_inst )
        {
          max_len_propertie_value = 1;
        }
      }

      enumeration_keys = null;

      property_key = null;

      property_value = null;
    }

    return max_len_propertie_value;
  }

  /**
   * Ermittelt die maximale Laenge aus den gespeicherten Schluesseln der Properties.
   * 
   * @param pProperties die zu untersuchende Propertieinstanz
   * @return die maximale Stringlaenge aus den Propertie-Schluesseln
   */
  public static int getMaxLenKey( Properties pProperties )
  {
    int max_len_propertie_key = 0;

    if ( pProperties != null )
    {
      Enumeration enumeration_keys = pProperties.keys();

      String property_key = null;

      while ( enumeration_keys.hasMoreElements() )
      {
        property_key = (String) enumeration_keys.nextElement();

        if ( property_key != null )
        {
          if ( property_key.length() > max_len_propertie_key )
          {
            max_len_propertie_key = property_key.length();
          }
        }
      }

      enumeration_keys = null;

      property_key = null;
    }

    return max_len_propertie_key;
  }

  /**
   * <pre>
   * Geht die Properties durch und setzt den Propertie-Wert auf den Propertie-Schluessel,
   * wenn der Wert des Properties "null" oder ein Leerstring ist.
   * 
   * Die Anzahl der geaenderten Propertie-Schluessel wird zurueckgegeben.
   * 
   * </pre>
   * 
   * @param pProperties die zu vervollstaendigenden Properties
   * @return die Anzahl der geaenderten Propertie-Schluessel
   */
  public static int setNullValues2Key( Properties pProperties )
  {
    int anzahl_properties_geaendert = 0;

    /*
     * Pruefung: Parameter "pProperties" gesetzt ?
     * 
     * Ist der Parameter gleich "null" wird keine Verarbeitung gestartet.
     */
    if ( pProperties != null )
    {
      /*
       * Die Enumeration der Propertie-Schluessel aus der Propertie-Instanz holen 
       */
      Enumeration enumeration_keys = pProperties.keys();

      /*
       * Hilfsvariablen deklarieren
       */
      String property_key = null;

      String property_value = null;

      /*
       * While-Schleife ueber alle Propertie-Schluessel
       */
      while ( enumeration_keys.hasMoreElements() )
      {
        /*
         * Aktuellen Propertie-Schluessel aus der Enumeration ermitteln
         */
        property_key = (String) enumeration_keys.nextElement();

        /*
         * Fuer den Schluessel den Schluesselwert ermitteln
         */
        property_value = pProperties.getProperty( property_key );

        try
        {
          /*
           * Pruefung: Propertie-Value gleich "null" ?
           * 
           * Ist der Propertie-Value gleich null, wird der Schluesselwert 
           * auf den Schluesselnamen abgeaendert und der Ersatzzaehler erhoeht.
           * 
           * Unter "java.util.properties" kann es nicht vorkommen dass ein 
           * Schluessel den Wert "null" speichern kann. 
           * 
           * Es kann aber andere Propertieklassen geben, welche das koennen.
           * Darum wird dieser Fall hier behandelt.
           */
          if ( property_value == null )
          {
            pProperties.setProperty( property_key, property_key );

            anzahl_properties_geaendert++;
          }
          /*
           * Pruefung: Schluesselwert getrimmt ein Leerstring ?
           * 
           * Ist der Schluesselwert gleich einem Leerstring, wird ebenfalls 
           * der Wert auf den Schluesselnamen abgeaendert und der Ersatzzaehler 
           * erhoeht.
           */
          else if ( property_value.trim().length() == 0 )
          {
            pProperties.setProperty( property_key, property_key );

            anzahl_properties_geaendert++;
          }
        }
        catch ( Exception err_inst )
        {
          // keine Fehlerbehandlung bei Exceptions
        }
      }

      /*
       * Nach der While-Schleife werden die Resourcen wieder freigegeben.
       */
      enumeration_keys = null;

      property_key = null;

      property_value = null;
    }

    /*
     * Der Aufrufer bekommt die Anzahl der geaenderten Schluessel zurueck.
     */
    return anzahl_properties_geaendert;
  }

  /**
   * <pre>
   * Addiert die einzelnen Hashwerte der Propertie-Werte zusammen.
   * Ist der Parameter pProperties null, wird 0 zurueckgegeben.
   * 
   * Properties properties_feld_werte = new Properties();
   * 
   * properties_feld_werte.setProperty( "key_1", "a1" );
   * properties_feld_werte.setProperty( "key_2", "b2" );
   * 
   * FkLogger.wl( FkProperties.getHashCode( properties_feld_werte ) );
   * 
   * </pre>
   * 
   * @param pProperties die Propertie-Instanz mit den Strings, aus welchen der Hashwert berechnet werden soll.
   * @return Hashwert
   */
  public static int getHashCode( Properties pProperties )
  {
    int hash_code = 0;

    if ( pProperties != null )
    {
      Enumeration enumeration_keys = pProperties.keys();

      String property_key = null;

      String property_value = null;

      while ( enumeration_keys.hasMoreElements() )
      {
        property_key = (String) enumeration_keys.nextElement();

        property_value = pProperties.getProperty( property_key );

        try
        {
          if ( property_value != null )
          {
            // System.out.println( "property_value >" + property_value + "< property_value.hashCode() =>" + property_value.hashCode() + "<" );

            hash_code += property_value.hashCode();
            //hash_code += property_key.hashCode();
          }
        }
        catch ( Exception err_inst )
        {
          hash_code = 1;
        }
      }

      enumeration_keys = null;

      property_key = null;

      property_value = null;
    }

    return hash_code;
  }

  /**
   * <pre>
   * Addiert die einzelnen Hashwerte der Propertie-Werte zusammen.
   * Ist der Parameter pProperties null, wird 0 zurueckgegeben.
   * 
   * Es werden nur die Schluessel aus dem Stringarray fuer die Berechnung genommen. 
   * Ist ein Schluessel nicht in den Properties vorhanden wird dieser Schluessel uebelesen.
   * 
   *  Properties properties_feld_werte = new Properties();
   * 
   *  properties_feld_werte.setProperty( "key_1", "a1" );
   *  properties_feld_werte.setProperty( "key_2", "b2" );
   * 
   *  String[] hash_keys = { "key_1", null, "key_2", "key_5" };
   * 
   *  FkLogger.wl( FkProperties.getHashCode( properties_feld_werte ) );
   * 
   *  properties_feld_werte.setProperty( "key_3", "c3" );
   *  properties_feld_werte.setProperty( "key_4", "d4" );
   * 
   *  FkLogger.wl( FkProperties.getHashCode( properties_feld_werte ) );
   *  FkLogger.wl( FkProperties.getHashCode( properties_feld_werte, hash_keys ) );
   * 
   * </pre>
   * 
   * @param pProperties die Propertie-Instanz mit den Strings, aus welchen der Hashwert berechnet werden soll.
   * @param pPropertieKeys Array mit den abzufragenden Propertie-Werten
   * @return Hashwert
   */
  public static int getHashCode( Properties pProperties, String[] pPropertieKeys )
  {
    int hash_code = 0;

    if ( ( pProperties != null ) && ( pPropertieKeys != null ) )
    {
      String property_key = null;

      String property_value = null;

      int akt_index = 0;

      while ( akt_index < pPropertieKeys.length )
      {
        try
        {
          property_key = pPropertieKeys[ akt_index ];

          if ( property_key != null )
          {
            property_value = pProperties.getProperty( property_key );

            if ( property_value != null )
            {
              // System.out.println( "property_key  >" + property_key + "< property_value >" + property_value + "< property_value.hashCode() =>" + property_value.hashCode() + "<" );

              hash_code += property_value.hashCode();
              //hash_code += property_key.hashCode();
            }
          }
        }
        catch ( Exception err_inst )
        {
          hash_code = 1;
        }

        akt_index++;
      }

      property_key = null;

      property_value = null;
    }

    return hash_code;
  }

  public static int getHashCode( Properties pProperties, String pPropertieKey )
  {
    int hash_code = 0;

    if ( ( pProperties != null ) && ( pPropertieKey != null ) )
    {
      String property_value = null;

      try
      {
        property_value = pProperties.getProperty( pPropertieKey );

        if ( property_value != null )
        {
          //System.out.println( "property_key  >" + property_key + "< property_value >" + property_value + "< property_value.hashCode() =>" + property_value.hashCode() + "<" );

          hash_code += property_value.hashCode();
        }
      }
      catch ( Exception err_inst )
      {
        hash_code = 1;
      }

      property_value = null;
    }

    return hash_code;
  }

  /**
   * Ist der Parameter "pProperties" ungleich null, wird dort die Funktion "clear" aufgerufen.
   * 
   * @param pProperties die zu "clearende" Propertie-Instanz
   */
  public static void clear( Properties pProperties )
  {
    if ( pProperties != null )
    {
      pProperties.clear();
    }
  }

  /**
   * @return einen String mit den Systemproperties
   */
  public static String getSystemProperties()
  {
    return getStrProperties( System.getProperties() );
  }

  /**
   * @param pHashTable die Hashtable mit den Werten
   * @return eine Instanz von Java.Util.Properties mit den konvertierten Schluessel/Wert-Paren
   */
  public static Properties getProperties( Hashtable pHashTable )
  {
    Properties ergebnis_properties = null;

    if ( pHashTable != null )
    {
      ergebnis_properties = new Properties();

      Map.Entry[] hash_table_entries = new Map.Entry[ pHashTable.size() ];

      pHashTable.entrySet().toArray( hash_table_entries );

      int akt_index = 0;

      while ( akt_index < hash_table_entries.length )
      {
        ergebnis_properties.setProperty( hash_table_entries[ akt_index ].getKey().toString(), hash_table_entries[ akt_index ].getValue().toString() );

        akt_index++;
      }
    }

    return ergebnis_properties;
  }

  /**
   * Sortiert die Schluessel aus der Parameterinstanz nach dem Alphabeth.
   * 
   * @param pProperties die Propertie-Instanz mit zu sortierenden Schluesseln
   * @return einen Vektor mit den alphabetisch sortierten Key's.
   */
  public static Vector getSortedKeys( Properties pProperties )
  {
    /*
     * Ein Arry der Klasse "Map.Entry" fuer die Aufnahme der Propertie-Keys erstellen.
     */
    Map.Entry[] hash_table_entries = new Map.Entry[ pProperties.size() ];

    try
    {
      /*
       * Die Propertie-Keys in die Entry-Map schreiben
       */
      pProperties.entrySet().toArray( hash_table_entries );

      /*
       * Aufruf der Quick-Sort-Funktion fuer den Map.Entry-Array
       */
      quickSort( hash_table_entries, 0, hash_table_entries.length - 1, true );
    }
    catch ( Exception err_inst )
    {
      //
    }

    /*
     * Ergebnisaufbau
     * Ueber eine While-Schleife werden die sortierten Keys in einen Vektor geschrieben.
     * Die Klasse Vektor ist eine einfachere Klasse als ein Map.Entry-Array.
     * Der Aufrufer soll ueber die konkrete Implementation keine Information bekommen. 
     * Der Aufrufer soll einen Java-Vektor bekommen und sich nicht mit weiterer
     * Technik rumschlagen muessen. 
     */
    Vector ergebnis_vektor = new Vector();

    int akt_index = 0;

    while ( akt_index < hash_table_entries.length )
    {
      ergebnis_vektor.add( hash_table_entries[ akt_index ].getKey().toString() );

      akt_index++;
    }

    /*
     * Am Funktionsende wird der Vektor zurueckgegebe. 
     */
    return ergebnis_vektor;
  }

  /**
   * Quicksort fuer den Parameterarray
   * 
   * @param pArray ein Array mit Instanzen von "Map.Entry"
   * @param pIndexStart der Startindex
   * @param pIndexEnde der Endindex
   * @param pKnzAufsteigend Kennzeichen, ob Aufsteingend sortiert werden soll
   */
  public static void quickSort( Map.Entry[] pArray, int pIndexStart, int pIndexEnde, boolean pKnzAufsteigend )
  {
    int index_start = pIndexStart;

    int index_end = pIndexEnde;

    if ( pArray.length > 0 )
    {
      String vergleichs_string_mitte = pArray[ ( pIndexStart + pIndexEnde ) / 2 ].getKey().toString();

      while ( index_start <= index_end )
      {
        if ( pKnzAufsteigend )
        {
          while ( pArray[ index_start ].getKey().toString().compareTo( vergleichs_string_mitte ) < 0 )
          {
            index_start++;
          }

          while ( pArray[ index_end ].getKey().toString().compareTo( vergleichs_string_mitte ) > 0 )
          {
            index_end--;
          }
        }
        else
        {
          while ( pArray[ index_start ].getKey().toString().compareTo( vergleichs_string_mitte ) > 0 )
          {
            index_start++;
          }

          while ( pArray[ index_end ].getKey().toString().compareTo( vergleichs_string_mitte ) < 0 )
          {
            index_end--;
          }
        }

        if ( index_start <= index_end )
        {
          Map.Entry temp_objekt = pArray[ index_start ];

          pArray[ index_start ] = pArray[ index_end ];

          pArray[ index_end ] = temp_objekt;

          index_start++;
          index_end--;
        }
      }

      if ( pIndexStart < index_end ) quickSort( pArray, pIndexStart, index_end, pKnzAufsteigend );

      if ( index_start < pIndexEnde ) quickSort( pArray, index_start, pIndexEnde, pKnzAufsteigend );
    }
  }

  /**
   * @param pProperties die Propertie-Instanz mit den auszugebenden Werten
   * @return einen String mit den Schluessel und Werten der uebergebenen Propertie-Instanz
   */
  public static String getStrPropertieValues( Properties pProperties )
  {
    return FkProperties.getStrPropertieValues( pProperties, false );
  }

  /**
   * @param pProperties die Propertie-Instanz mit den auszugebenden Werten
   * @param pKnzSortieren Kennzeichen, ob die Schluessel sortiert werden sollen
   * @return einen String mit den Schluessel und Werten der uebergebenen Propertie-Instanz
   */
  public static String getStrPropertieValues( Properties pProperties, boolean pKnzSortieren )
  {
    return getStrPropertieValues( pProperties, "", pKnzSortieren );
  }

  /**
   * @param pProperties die Propertie-Instanz mit den auszugebenden Werten
   * @param pPraefix eine Zeichenfolge fuer den Anfang einer jeden Ausgabezeile
   * @param pKnzSortieren Kennzeichen, ob die Schluessel sortiert werden sollen
   * @return einen String mit den Schluessel und Werten der uebergebenen Propertie-Instanz
   */
  public static String getStrPropertieValues( Properties pProperties, String pPraefix, boolean pKnzSortieren )
  {
    if ( pProperties == null )
    {
      return "";
    }

    StringBuffer str_buffer = new StringBuffer();

    String property_key = null;

    String property_value = null;

    Enumeration enumeration_keys = pProperties.keys();

    if ( pKnzSortieren )
    {
      Vector vector_keys = getSortedKeys( pProperties );

      if ( vector_keys != null )
      {
        enumeration_keys = vector_keys.elements();
      }
    }

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      property_value = pProperties.getProperty( property_key );

      str_buffer.append( pPraefix );

      str_buffer.append( property_value );

      str_buffer.append( "\n" );
    }

    enumeration_keys = null;

    property_key = null;

    property_value = null;

    return str_buffer.toString();
  }

  /**
   * <pre>
   * Sucht in der zu durchsuchenden Zeichenfolge nach Vorkommen von den 
   * Schluesseln aus der uebergebenen Propertie-Sammlung. Bei der ersten 
   * uebereinstimmung wird der entsprechende Schluesselwert dem Aufrufer
   * zurueckgegeben. 
   * 
   * Wird keine Uebereinstimmung gefunden, wird der Vorgabewert zurueckgegeben. 
   * 
   * Der Aufrufer ist fuer die Gross- Kleinschreibung verantwortlich.
   * </pre>
   * 
   * @param pZuDurchsuchenderString String in welchen nach den Vorkommen der Schluessel gesucht werden soll
   * @param pProperties die Schluessel als Suchbegriffe, die Schluesselwerte als Rueckgabewerte
   * @param pVorgabe Rueckgabewert, sofern keine uebereinstimmung gefunden wird. 
   * @return Schluesselwert des gefundenen Schluessels, oder der Vorgabewert
   */
  public static String sucheSchluesselWert( String pZuDurchsuchenderString, Properties pProperties, String pVorgabe )
  {
    if ( pZuDurchsuchenderString == null ) return pVorgabe;

    if ( pProperties == null ) return pVorgabe;

    Enumeration enumeration_keys = pProperties.keys();

    String property_key = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      if ( pZuDurchsuchenderString.indexOf( property_key ) >= 0 )
      {
        return pProperties.getProperty( property_key );
      }
    }

    enumeration_keys = null;

    property_key = null;

    return pVorgabe;
  }

  /**
   * Erzeugt eine Aufstellung von Schluesseln und deren Werten als String.
   * 
   * @param pProperties die Properties
   * @return einen String mit einer Aufstellung der Schluessel und Werte
   */
  public static String getStrProperties( Properties pProperties )
  {
    if ( pProperties == null )
    {
      return "";
    }

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = pProperties.keys();

    str_buffer.append( "\n" );

    String property_key = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      str_buffer.append( " " );
      str_buffer.append( property_key );
      str_buffer.append( "=" );
      str_buffer.append( pProperties.getProperty( property_key ) );
      str_buffer.append( "\n" );
    }

    enumeration_keys = null;

    property_key = null;

    return str_buffer.toString();
  }

  /**
   * @param pProperties die Properties
   * @param pKnzSortieren ein Kennzeichen, ob das Ergebnis nach Schluesseln sortiert werden soll
   * @return einen String mit der Auflistung der Properties und den WErten
   */
  public static String getStrPropertiesKeyValue( Properties pProperties, boolean pKnzSortieren )
  {
    if ( pProperties == null )
    {
      return "";
    }

    Enumeration enumeration_keys = pProperties.keys();

    if ( pKnzSortieren )
    {
      Vector vector_keys = getSortedKeys( pProperties );

      if ( vector_keys != null )
      {
        enumeration_keys = vector_keys.elements();
      }
    }

    StringBuffer str_buffer = new StringBuffer();

    String property_key = null;

    str_buffer.append( "\n" );

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      str_buffer.append( " " );
      str_buffer.append( property_key );
      str_buffer.append( "=" );
      str_buffer.append( pProperties.getProperty( property_key ) );
      str_buffer.append( "\n" );
    }

    enumeration_keys = null;

    property_key = null;

    return "\n" + str_buffer.toString();
  }

  public static String getStrPropertiesKeyValue( Properties pProperties, boolean pKnzSortieren, int pBreitePropKey )
  {
    if ( pProperties == null )
    {
      return "";
    }

    Enumeration enumeration_keys = pProperties.keys();

    if ( pKnzSortieren )
    {
      Vector vector_keys = getSortedKeys( pProperties );

      if ( vector_keys != null )
      {
        enumeration_keys = vector_keys.elements();
      }
    }

    StringBuffer str_buffer = new StringBuffer();

    String property_key = null;

    int breite_key = ( pBreitePropKey < 0 ? 1 : ( pBreitePropKey > 300 ? 300 : pBreitePropKey ) );

    str_buffer.append( "\n" );

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      str_buffer.append( " " );
      str_buffer.append( FkStringFeld.getFeldLinksMin( property_key, breite_key ) );
      str_buffer.append( "=" );
      str_buffer.append( pProperties.getProperty( property_key ) );
      str_buffer.append( "\n" );
    }

    enumeration_keys = null;
    property_key = null;

    return "\n" + str_buffer.toString();
  }

  public static String getAAStrPropertiesKeyValue( Properties pProperties, boolean pKnzSortieren, int pBreitePropKey )
  {
    if ( pProperties == null )
    {
      return "";
    }

    Enumeration enumeration_keys = pProperties.keys();

    if ( pKnzSortieren )
    {
      Vector vector_keys = getSortedKeys( pProperties );

      if ( vector_keys != null )
      {
        enumeration_keys = vector_keys.elements();
      }
    }

    StringBuffer str_buffer = new StringBuffer();

    String property_key = null;

    int breite_key = ( pBreitePropKey < 0 ? 1 : ( pBreitePropKey > 300 ? 300 : pBreitePropKey ) );

    str_buffer.append( "\n" );

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      str_buffer.append( " " );
      str_buffer.append( FkStringFeld.getFeldLinksMin( property_key, breite_key ) );
      str_buffer.append( "=" );
      str_buffer.append( pProperties.getProperty( property_key ) );
      str_buffer.append( "\n" );
    }

    enumeration_keys = null;

    property_key = null;

    return "\n" + str_buffer.toString();
  }

  public static String getStrPropertieDebugGenerator( Properties pProperties )
  {
    // FkLogger.wl( FkProperties.getStrPropertieDebugGenerator( System.getProperties() ));

    if ( pProperties == null )
    {
      return "";
    }

    int breite_prop_key = 30;

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = getSortedKeys( pProperties ).elements();

    String property_key = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      str_buffer.append( "      my_string += \"" + FkStringFeld.getFeldLinksMin( property_key, breite_prop_key ) + " \" + System.getProperty( \"" + FkStringFeld.getFeldLinksMin( property_key, breite_prop_key ) + "\", null ) + \" \"; // = " + pProperties.getProperty( property_key ) + "\n" );
    }

    enumeration_keys = null;

    property_key = null;

    return str_buffer.toString();
  }

  public static String getStrPropertieDebugGeneratorSet( Properties pProperties, int pBreitePropKey, int pBreitePropValue )
  {
    if ( pProperties == null )
    {
      return "";
    }

    int breite_prop_key = ( pBreitePropKey <= 0 ? 10 : ( pBreitePropKey > 300 ? 300 : pBreitePropKey ) );

    int breite_prop_value = ( pBreitePropValue <= 0 ? 10 : pBreitePropValue );

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = getSortedKeys( pProperties ).elements();

    String property_key = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      str_buffer.append( "propertie_instanz.setProperty( \"" + FkStringFeld.getFeldLinksMin( property_key, breite_prop_key ) + "\", \"" + FkStringFeld.getFeldLinksMin( pProperties.getProperty( property_key ) + "\"", breite_prop_value ) + " )\n" );
    }

    enumeration_keys = null;

    property_key = null;

    return str_buffer.toString();
  }

  /**
   * <pre>
   * Liefert die Schluessel und Werte als Stringauflistung zurueck.
   * 
   * Properties propertie_instanz = new Properties();
   * 
   * propertie_instanz.setProperty( "RESTSCHULD"                ,"restschuld"                );
   * propertie_instanz.setProperty( "SONSTIGE"                  ,"sonstige"                  );
   * propertie_instanz.setProperty( "SONSTIGE_AUSGABEN"         ,"sonstigeAusgaben"          );
   * propertie_instanz.setProperty( "SONSTIGE_VERBINDLICHKEITEN","sonstigeVerbindlichkeiten" );
   * propertie_instanz.setProperty( "STAATSANGEHOERIGKEIT"      ,"staatsangehoerigkeit"      );
   * propertie_instanz.setProperty( "STRASSE"                   ,"strasse"                   );
   * propertie_instanz.setProperty( "TELEFON"                   ,"telefon"                   );
   * propertie_instanz.setProperty( "TELEFON_GESCHAEFTLICH"     ,"telefonGeschaeftlich"      );
   * propertie_instanz.setProperty( "TELEFON_PRIVAT"            ,"telefonPrivat"             );
   * propertie_instanz.setProperty( "TITEL"                     ,"titel"                     );
   * 
   * String str_vorlauf   = "private static String XML_TAG_NAME_";
   * String str_mitte     = " = \"";
   * String str_nachlauf  = "\"; ";
   *  
   * String generator_string = FkProperties.getStrProperties( propertie_instanz, str_vorlauf, str_mitte, str_nachlauf );
   *  
   * FkLogger.wl( generator_string );
   * 
   * private static String XML_TAG_NAME_RESTSCHULD = "restschuld"; 
   * private static String XML_TAG_NAME_SONSTIGE = "sonstige"; 
   * private static String XML_TAG_NAME_SONSTIGE_AUSGABEN = "sonstigeAusgaben"; 
   * private static String XML_TAG_NAME_SONSTIGE_VERBINDLICHKEITEN = "sonstigeVerbindlichkeiten"; 
   * private static String XML_TAG_NAME_STAATSANGEHOERIGKEIT = "staatsangehoerigkeit"; 
   * private static String XML_TAG_NAME_STRASSE = "strasse"; 
   * private static String XML_TAG_NAME_TELEFON = "telefon"; 
   * private static String XML_TAG_NAME_TELEFON_GESCHAEFTLICH = "telefonGeschaeftlich"; 
   * private static String XML_TAG_NAME_TELEFON_PRIVAT = "telefonPrivat"; 
   * private static String XML_TAG_NAME_TITEL = "titel";
   * 
   * </pre>
   * 
   * @param pProperties die Propertie-Instanz mit den auszugebenden Werten
   * @param pVorlauf der Vorlauf fuer jede Zeile (null wird zu "")
   * @param pMitte eine Zeichenfolge zum Trennen von Schluessel und Wert (null wird zu "=")
   * @param pNachlauf eine Zeichenfolge fuer den Zeilenabschluss (null wird zu "")
   * @return einen String mit den Schluessel und Werten der uebergebenen Propertie-Instanz
   */
  public static String getStrProperties( Properties pProperties, String pVorlauf, String pMitte, String pNachlauf )
  {
    if ( pProperties == null )
    {
      return "";
    }

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = getSortedKeys( pProperties ).elements();

    String property_key = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      str_buffer.append( pVorlauf == null ? "" : pVorlauf );
      str_buffer.append( property_key );
      str_buffer.append( pMitte == null ? "=" : pMitte );
      str_buffer.append( pProperties.getProperty( property_key ) );
      str_buffer.append( pNachlauf == null ? "" : pNachlauf );
      str_buffer.append( "\n" );
    }

    enumeration_keys = null;

    property_key = null;

    return str_buffer.toString();
  }

  /**
   * @param pProperties die Propertie-Instanz mit den auszugebenden Werten
   * @param pKnzSortieren ein Kennzeichen, ob das Ergebnis nach Schluesseln sortiert werden soll
   * @param pVorlauf der Vorlauf fuer jede Zeile (null wird zu "")
   * @param pMitte eine Zeichenfolge zum Trennen von Schluessel und Wert (null wird zu "=")
   * @param pNachlauf eine Zeichenfolge fuer den Zeilenabschluss (null wird zu "")
   * @return einen String mit den Schluessel und Werten der uebergebenen Propertie-Instanz
   */
  public static String getStrProperties( Properties pProperties, boolean pKnzSortieren, String pVorlauf, String pMitte, String pNachlauf )
  {
    if ( pProperties == null )
    {
      return "";
    }

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = null;

    if ( pKnzSortieren )
    {
      enumeration_keys = getSortedKeys( pProperties ).elements();
    }
    else
    {
      enumeration_keys = pProperties.keys();
    }

    String property_key = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      str_buffer.append( pVorlauf == null ? "" : pVorlauf );
      str_buffer.append( property_key );
      str_buffer.append( pMitte == null ? "=" : pMitte );
      str_buffer.append( pProperties.getProperty( property_key ) );
      str_buffer.append( pNachlauf == null ? "" : pNachlauf );
      str_buffer.append( "\n" );
    }

    enumeration_keys = null;

    property_key = null;

    return str_buffer.toString();
  }

  /**
   * @param pProperties die Propertie-Instanz mit den auszugebenden Werten
   * @param pKnzSortieren ein Kennzeichen, ob das Ergebnis nach Schluesseln sortiert werden soll
   * @param pVorlauf der Vorlauf fuer jede Zeile (null wird zu "")
   * @param pMitte eine Zeichenfolge zum Trennen von Schluessel und Wert (null wird zu "=")
   * @param pNachlauf eine Zeichenfolge fuer den Zeilenabschluss (null wird zu "")
   * @param pKnzKeyValueUmdrehen Kennzeichen, ob die Key/Value-Paare in der Reihenfolge des Auftretens umgedreht werden sollen
   * @return einen String mit den Schluessel und Werten der uebergebenen Propertie-Instanz
   */
  public static String getStrProperties( Properties pProperties, boolean pKnzSortieren, String pVorlauf, String pMitte, String pNachlauf, boolean pKnzKeyValueUmdrehen )
  {
    if ( pProperties == null )
    {
      return "";
    }

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = null;

    if ( pKnzSortieren )
    {
      enumeration_keys = getSortedKeys( pProperties ).elements();
    }
    else
    {
      enumeration_keys = pProperties.keys();
    }

    String property_key = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      str_buffer.append( pVorlauf == null ? "" : pVorlauf );
      str_buffer.append( ( pKnzKeyValueUmdrehen ? pProperties.getProperty( property_key ) : property_key ) );
      str_buffer.append( pMitte == null ? "=" : pMitte );
      str_buffer.append( ( pKnzKeyValueUmdrehen ? property_key : pProperties.getProperty( property_key ) ) );
      str_buffer.append( pNachlauf == null ? "" : pNachlauf );
      str_buffer.append( "\n" );
    }

    enumeration_keys = null;

    property_key = null;

    return str_buffer.toString();
  }

  public static String getStrProperties( Properties pProperties, boolean pKnzSortieren, String pVorlauf, String pMitte, String pNachlauf, int pBreitePropKey )
  {
    if ( pProperties == null )
    {
      return "";
    }

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = null;

    if ( pKnzSortieren )
    {
      enumeration_keys = getSortedKeys( pProperties ).elements();
    }
    else
    {
      enumeration_keys = pProperties.keys();
    }

    String property_key = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      str_buffer.append( pVorlauf == null ? "" : pVorlauf );
      str_buffer.append( property_key );
      str_buffer.append( pMitte == null ? "=" : pMitte );
      str_buffer.append( pProperties.getProperty( property_key ) );
      str_buffer.append( pNachlauf == null ? "" : pNachlauf );
      str_buffer.append( "\n" );
    }

    enumeration_keys = null;

    property_key = null;

    return str_buffer.toString();
  }

  /**
   * <pre>
   * Kopiert die Properties aus der Quellinstanz in die Zielinstanz.
   * 
   * Ist die Quelle <code>null</code>, wird nichts gemacht.
   * Ist das Ziel <code>null</code>, wird nichts gemacht.
   * </pre>
   * 
   * @param pQuellProperties die Quellinstanz mit den zu kopierenden Werten
   * @param pZielProperties die Zielinstanz
   */
  public static Properties getCopyProperties( Properties pQuellProperties )
  {
    if ( pQuellProperties == null )
    {
      return null; // keine Quelle -> keine Verarbeitung
    }

    Properties ziel_properties = new Properties();

    Enumeration enumeration_keys = pQuellProperties.keys();

    String property_key = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      ziel_properties.setProperty( property_key, pQuellProperties.getProperty( property_key ) );
    }

    enumeration_keys = null;

    property_key = null;

    return ziel_properties;
  }

  /**
   * <pre>
   * Kopiert die Properties aus der Quellinstanz in die Zielinstanz.
   * 
   * Ist die Quelle <code>null</code>, wird nichts gemacht.
   * Ist das Ziel <code>null</code>, wird nichts gemacht.
   * </pre>
   * 
   * @param pQuellProperties die Quellinstanz mit den zu kopierenden Werten
   * @param pZielProperties die Zielinstanz
   */
  public static void copyProperties( Properties pQuellProperties, Properties pZielProperties )
  {
    if ( pQuellProperties == null ) return; // keine Quelle -> keine Verarbeitung

    if ( pZielProperties == null ) return; // kein Ziel -> keine Verarbeitung

    Enumeration enumeration_keys = pQuellProperties.keys();

    String property_key = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      pZielProperties.setProperty( property_key, pQuellProperties.getProperty( property_key ) );
    }

    enumeration_keys = null;

    property_key = null;
  }

  public static Properties setNrUndKeyAlsPropWert( Properties pProperties, boolean pKnzSortieren )
  {
    if ( pProperties == null )
    {
      return null; // keine Quelle -> keine Verarbeitung
    }

    Properties ergebnis_prop = new Properties();

    Enumeration enumeration_keys = pProperties.keys();

    if ( pKnzSortieren )
    {
      Vector vector_keys = getSortedKeys( pProperties );

      if ( vector_keys != null )
      {
        enumeration_keys = vector_keys.elements();
      }
    }

    String property_key = null;

    int akt_nr = 0;

    while ( enumeration_keys.hasMoreElements() )
    {
      akt_nr++;
      property_key = (String) enumeration_keys.nextElement();

      ergebnis_prop.setProperty( "" + akt_nr, property_key );
    }

    enumeration_keys = null;

    property_key = null;

    return ergebnis_prop;
  }

  public static void setKeyAsValue( Properties pProperties )
  {
    if ( pProperties != null )
    {
      Enumeration enumeration_keys = pProperties.keys();

      String property_key = null;

      while ( enumeration_keys.hasMoreElements() )
      {
        property_key = (String) enumeration_keys.nextElement();

        pProperties.setProperty( property_key, property_key );
      }

      enumeration_keys = null;

      property_key = null;
    }
  }

  public static Properties swapKeyValue( Properties pProperties )
  {
    if ( pProperties == null )
    {
      return null; // keine Quelle -> keine Verarbeitung
    }

    Properties ergebnis_properties = new Properties();

    Enumeration enumeration_keys = pProperties.keys();

    String property_key = null;

    String property_value = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      property_value = pProperties.getProperty( property_key );

      try
      {
        ergebnis_properties.setProperty( property_value, property_key );
      }
      catch ( Exception err_inst )
      {
      }
    }

    enumeration_keys = null;

    property_key = null;

    property_value = null;

    return ergebnis_properties;
  }

  public static Properties swapKeyValueSetPraefix( Properties pProperties, String pPraefixKey, String pPraefixValue )
  {
    if ( pProperties == null )
    {
      return null; // keine Quelle -> keine Verarbeitung
    }

    String praefix_key = ( pPraefixKey == null ? "" : pPraefixKey );

    String praefix_value = ( pPraefixValue == null ? "" : pPraefixValue );

    Properties ergebnis_properties = new Properties();

    Enumeration enumeration_keys = pProperties.keys();

    String property_key = null;

    String property_value = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      property_value = pProperties.getProperty( property_key );

      try
      {
        ergebnis_properties.setProperty( praefix_key + property_value, praefix_value + property_key );
      }
      catch ( Exception err_inst )
      {
      }
    }

    enumeration_keys = null;

    property_key = null;

    property_value = null;

    return ergebnis_properties;
  }

  public static String getKey( Properties pProperties, String pValue, String pDefaultValue )
  {
    /*
     * Pruefung: Parameter "pProperties" ungleich "null" ?
     * 
     * Ist der Parameter "pProperties" gleich "null", wird der Vorgabewert zurueckgegeben.
     */
    if ( pProperties == null )
    {
      return pDefaultValue;
    }

    /*
     * Pruefung: Suchparameter angegeben ?
     * 
     * Ist der Parameter "pValue" gleich "null" oder ein Leerstring, wird 
     * der Vorgabewert zurueckgegeben.
     */
    if ( ( pValue == null ) || ( pValue.length() == 0 ) )
    {
      return pDefaultValue;
    }

    Enumeration enumeration_keys = pProperties.keys();

    String str_ergebnis = null;

    String property_key = null;

    String property_value = null;

    /*
     * 
     */
    while ( ( enumeration_keys.hasMoreElements() ) && ( str_ergebnis == null ) )
    {
      property_key = (String) enumeration_keys.nextElement();

      property_value = pProperties.getProperty( property_key );

      try
      {
        if ( property_value != null )
        {
          if ( property_value.compareTo( pValue ) == 0 )
          {
            str_ergebnis = property_key;
          }
        }
      }
      catch ( Exception err_inst )
      {
        System.out.println( err_inst.getMessage() );
      }
    }

    enumeration_keys = null;

    property_key = null;

    property_value = null;

    if ( str_ergebnis == null )
    {
      return pDefaultValue;
    }

    return str_ergebnis;
  }

  /**
   * @param pProperties die Propertie-Instanz mit den auszugebenden Werten
   * @param pKnzSortieren Kennzeichen ob die Schluessel sortiert werden sollen 
   * @return einen String mit den Schluessel und Werten der uebergebenen Propertie-Instanz
   */
  public static String getStrPropertieKey( Properties pProperties, boolean pKnzSortieren )
  {
    if ( pProperties == null )
    {
      return "";
    }

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = pProperties.keys();

    if ( pKnzSortieren )
    {
      Vector vector_keys = getSortedKeys( pProperties );

      if ( vector_keys != null )
      {
        enumeration_keys = vector_keys.elements();
      }
    }

    while ( enumeration_keys.hasMoreElements() )
    {
      str_buffer.append( (String) enumeration_keys.nextElement() );
      str_buffer.append( "\n" );
    }

    enumeration_keys = null;

    return str_buffer.toString();
  }

  /**
   * @param pProperties die Propertie-Instanz mit den auszugebenden Werten
   * @param pKnzSortieren Kennzeichen ob die Schluessel sortiert werden sollen 
   * @return einen String mit den Schluesselwerten der Propertieinstanz
   */
  public static String getStrPropertieValue( Properties pProperties, boolean pKnzSortieren )
  {
    return getStrPropertieValue1( pProperties, pKnzSortieren, "\n" );
  }

  public static String getStrPropertieValue1( Properties pProperties, boolean pKnzSortieren, String pZeilenUmbruch )
  {
    if ( pProperties == null )
    {
      return "";
    }

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = pProperties.keys();

    if ( pKnzSortieren )
    {
      Vector vector_keys = getSortedKeys( pProperties );

      if ( vector_keys != null )
      {
        enumeration_keys = vector_keys.elements();
      }
    }

    String property_value = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_value = pProperties.getProperty( (String) enumeration_keys.nextElement() );

      if ( property_value != null )
      {
        str_buffer.append( property_value );

        str_buffer.append( pZeilenUmbruch );
      }
    }

    enumeration_keys = null;

    property_value = null;

    return str_buffer.toString();
  }

  /**
   * @param pProperties die Propertie-Instanz mit den auszugebenden Werten
   * @param pKnzSortieren Kennzeichen ob die Schluessel sortiert werden sollen 
   * @param pPropKeyStart wenn ungleich null, werden nur Eintraege aufgenommen, welche mit der uebergebenen Zeichenfolge beginnen
   * @return einen String mit den Schluesselwerten der Propertieinstanz
   */
  public static String getStrPropertieValue( Properties pProperties, boolean pKnzSortieren, String pPropKeyStart )
  {
    if ( pProperties == null )
    {
      return "";
    }

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = pProperties.keys();

    if ( pKnzSortieren )
    {
      Vector vector_keys = getSortedKeys( pProperties );

      if ( vector_keys != null )
      {
        enumeration_keys = vector_keys.elements();
      }
    }

    String property_key = null;
    String property_value = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();
      property_value = pProperties.getProperty( property_key );

      if ( property_value != null )
      {
        if ( pPropKeyStart == null )
        {
          str_buffer.append( property_value );
          str_buffer.append( "\n" );
        }
        else if ( property_key.startsWith( pPropKeyStart ) )
        {
          str_buffer.append( property_value );
          str_buffer.append( "\n" );
        }
      }
    }

    enumeration_keys = null;
    property_key = null;
    property_value = null;

    return str_buffer.toString();
  }


  public static String getStrProperties( Properties pProperties, boolean pKnzSortieren, String pVorlauf, String pMitte, String pNachlauf, int pBreitePropKey, int pBreiteValue, boolean pKnzKeyInAnfuehrungszeichen, boolean pKnzValueInAnfuehrungszeichen )
  {
    return getStrProperties( pProperties, pKnzSortieren, pVorlauf, pMitte, pNachlauf, pBreitePropKey, pBreiteValue, pKnzKeyInAnfuehrungszeichen, pKnzValueInAnfuehrungszeichen, false );
  }

  /**
   * <pre>
   * Erstellt eine Ausgabe aus den gemachten Vorgaben.
   * 
   * Properties formular_feld_inhalte = new Properties();
   * 
   * formular_feld_inhalte.setProperty( "FeldName1", "FeldWert1" );
   * formular_feld_inhalte.setProperty( "FeldName2", "FeldWert2" );
   * formular_feld_inhalte.setProperty( "FeldName3", "FeldWert3" );
   * formular_feld_inhalte.setProperty( "FeldName4", "FeldWert4" );
   * 
   * String datei_name_map_string            = "c:/Daten/MapString.txt"; 
   * 
   * String str_vorlauf                      = "map_string += \", ";
   * String str_mitte                        = " = ";
   * String str_nachlauf                     = " \"; ";
   * 
   * int breite_key                          = FkProperties.getMaxLenKey(   formular_feld_inhalte );
   * int breite_value                        = FkProperties.getMaxLenValue( formular_feld_inhalte );
   * 
   * boolean knz_nach_key_feldern_sortieren  = true;
   * boolean knz_key_in_anfuehrungszeichen   = false;
   * boolean knz_value_in_anfuehrungszeichen = false;
   * boolean knz_key_value_umdrehen          = true;
   * 
   * String generator_map_string = FkProperties.getStrProperties( formular_feld_inhalte, knz_nach_key_feldern_sortieren, str_vorlauf, str_mitte, str_nachlauf, breite_key, breite_value, knz_key_in_anfuehrungszeichen, knz_value_in_anfuehrungszeichen, knz_key_value_umdrehen );
   * 
   * FkLogger.wl( generator_map_string );
   * 
   * FkDatei.schreibeDatei( datei_name_map_string, generator_map_string );
   * 
   * FkLogger.wl( "Map-String-Datei = " + datei_name_map_string );
   * 
   * 
   * map_string += ", FeldWert4  = FeldName4  ";
   * map_string += ", FeldWert3  = FeldName3  ";
   * map_string += ", FeldWert2  = FeldName2  ";
   * map_string += ", FeldWert1  = FeldName1  ";
   *   
   * -------------------------------------------------------------------------------------     
   *  
   * Properties ausgabe_properties = gv_z.getPropErg();
   * 
   * ausgabe_properties = new PropertieWriterX().getPropertiesX( instanz_ausgabe );
   * 
   * String str_vorlauf  = "propertie_instanz.setProperty( ";
   * String str_mitte    = " , ";
   * String str_nachlauf = " ); ";
   * 
   * int breite_key      = FkProperties.getMaxLenKey( ausgabe_properties  );
   * int breite_value    = FkProperties.getMaxLenValue( ausgabe_properties  );
   * 
   * boolean knz_nach_key_feldern_sortieren  = true;
   * boolean knz_key_in_anfuehrungszeichen   = true;
   * boolean knz_value_in_anfuehrungszeichen = true;
   * boolean knz_key_value_umdrehen          = false;
   * 
   * String ausgabe_string = FkProperties.getStrProperties( ausgabe_properties, knz_nach_key_feldern_sortieren, str_vorlauf, str_mitte, str_nachlauf, breite_key, breite_value, knz_key_in_anfuehrungszeichen, knz_value_in_anfuehrungszeichen, knz_key_value_umdrehen );
   * 
   * FkDatei.schreibeDatei( FkSystem.getStdRootVerzeichnis() + "ausgabe_properties.txt",  "    Properties properties_neu = new Properties();\n " + ausgabe_string );
   *   
   * -------------------------------------------------------------------------------------     
   *  
   * Properties formular_feld_inhalte = new Properties();
   * 
   * formular_feld_inhalte.setProperty( "FeldName1", "FeldWert1" );
   * formular_feld_inhalte.setProperty( "FeldName2", "FeldWert2" );
   * formular_feld_inhalte.setProperty( "FeldName3", "FeldWert3" );
   * formular_feld_inhalte.setProperty( "FeldName4", "FeldWert4" );
   * 
   * String str_vorlauf                      = "<tr><td>";
   * String str_mitte                        = "</td><td>";
   * String str_nachlauf                     = "</td></tr>";
   * 
   * int breite_key                          = FkProperties.getMaxLenKey(   formular_feld_inhalte );
   * int breite_value                        = FkProperties.getMaxLenValue( formular_feld_inhalte );
   * 
   * boolean knz_nach_key_feldern_sortieren  = true;
   * boolean knz_key_in_anfuehrungszeichen   = false;
   * boolean knz_value_in_anfuehrungszeichen = false;
   * boolean knz_key_value_umdrehen          = false;
   * 
   * String html_prop_tabelle = "";
   * 
   * html_prop_tabelle += "<table>\n";
   * 
   * html_prop_tabelle +=  FkProperties.getStrProperties( formular_feld_inhalte, knz_nach_key_feldern_sortieren, str_vorlauf, str_mitte, str_nachlauf, breite_key, breite_value, knz_key_in_anfuehrungszeichen, knz_value_in_anfuehrungszeichen, knz_key_value_umdrehen );
   * 
   * html_prop_tabelle += "</table>\n";
   * 
   * FkLogger.wl( html_prop_tabelle );
   * 
   * -------------------------------------------------------------------------------------     
   *   
   * Properties properties_json_feld_namen = new Properties();
   * 
   * properties_json_feld_namen.setProperty( "json_feld_name_1", "feld_wert_1" );
   * properties_json_feld_namen.setProperty( "json_feld_name_2", "feld_wert_2" );
   * properties_json_feld_namen.setProperty( "json_feld_name_3", "feld_wert_3" );
   * 
   * String str_vorlauf  = "json_string += \"\\n  \\\"\" + ";
   * String str_mitte    = " + \"\\\": \" + convertToJsonString( ";
   * String str_nachlauf = ", str_nicht_vorhanden ) + \",\";";
   *    
   * int breite_key   = FkProperties.getMaxLenKey(   properties_json_feld_namen );
   * int breite_value = FkProperties.getMaxLenValue( properties_json_feld_namen );
   *    
   * boolean knz_nach_key_feldern_sortieren  = true;
   * boolean knz_key_in_anfuehrungszeichen   = true;
   * boolean knz_value_in_anfuehrungszeichen = false;
   * boolean knz_key_value_umdrehen          = false;
   *    
   * String generator_json_string = FkProperties.getStrProperties( properties_json_feld_namen, knz_nach_key_feldern_sortieren, str_vorlauf, str_mitte, str_nachlauf, breite_key, breite_value, knz_key_in_anfuehrungszeichen, knz_value_in_anfuehrungszeichen, knz_key_value_umdrehen );
   * 
   * FkLogger.wl( generator_json_string );
   * 
   * json_string += "\n  \"" + "json_feld_name_1" + "\": " + convertToJsonString( feld_wert_1, str_nicht_vorhanden ) + ",";
   * json_string += "\n  \"" + "json_feld_name_2" + "\": " + convertToJsonString( feld_wert_2, str_nicht_vorhanden ) + ",";
   * json_string += "\n  \"" + "json_feld_name_3" + "\": " + convertToJsonString( feld_wert_3, str_nicht_vorhanden ) + ",";
   * 
   * -------------------------------------------------------------------------------------
   * 
   * Properties properties_json_feld_namen = new Properties();
   * 
   * properties_json_feld_namen.setProperty( "json_feld_name_1", "feld_wert_1" );
   * properties_json_feld_namen.setProperty( "json_feld_name_2", "feld_wert_2" );
   * properties_json_feld_namen.setProperty( "json_feld_name_3", "feld_wert_3" );
   * 
   * String str_vorlauf  = "\"";
   * String str_mitte    = "\": \"";
   * String str_nachlauf = "\",";
   *    
   * int breite_key   = FkProperties.getMaxLenKey(   properties_json_feld_namen );
   * int breite_value = FkProperties.getMaxLenValue( properties_json_feld_namen );
   *    
   * boolean knz_nach_key_feldern_sortieren  = true;
   * boolean knz_key_in_anfuehrungszeichen   = false;
   * boolean knz_value_in_anfuehrungszeichen = false;
   * boolean knz_key_value_umdrehen          = false;
   *    
   * String generator_json_string = FkProperties.getStrProperties( properties_json_feld_namen, knz_nach_key_feldern_sortieren, str_vorlauf, str_mitte, str_nachlauf, breite_key, breite_value, knz_key_in_anfuehrungszeichen, knz_value_in_anfuehrungszeichen, knz_key_value_umdrehen );
   * 
   * FkLogger.wl( generator_json_string );
   *   
   * "json_feld_name_1": "feld_wert_1",
   * "json_feld_name_2": "feld_wert_2",
   * "json_feld_name_3": "feld_wert_3",
   * 
   * -------------------------------------------------------------------------------------
   * 
   * Properties properties_mit_ausgabefeldern = new Properties();
   * 
   * properties_mit_ausgabefeldern.setProperty( "json_feld_name_1", "feld_wert_1" );
   * properties_mit_ausgabefeldern.setProperty( "json_feld_name_2", "feld_wert_2" );
   * properties_mit_ausgabefeldern.setProperty( "json_feld_name_3", "feld_wert_3" );
   * 
   * String str_vorlauf  = "PROP_AUSGABE_PRAEFIX ";
   * String str_mitte    = " = ";
   * String str_nachlauf = "";
   *    
   * int breite_key   = FkProperties.getMaxLenKey(   properties_mit_ausgabefeldern );
   * int breite_value = FkProperties.getMaxLenValue( properties_mit_ausgabefeldern );
   *    
   * boolean knz_nach_key_feldern_sortieren  = true;
   * boolean knz_key_in_anfuehrungszeichen   = false;
   * boolean knz_value_in_anfuehrungszeichen = false;
   * boolean knz_key_value_umdrehen          = false;
   *    
   * String erstellter_ausgabe_string = FkProperties.getStrProperties( properties_mit_ausgabefeldern, knz_nach_key_feldern_sortieren, str_vorlauf, str_mitte, str_nachlauf, breite_key, breite_value, knz_key_in_anfuehrungszeichen, knz_value_in_anfuehrungszeichen, knz_key_value_umdrehen );
   * 
   * DrLogger.wlX( erstellter_ausgabe_string );
   * 
   * </pre>
   * 
   * @param pProperties die Instanz mit den Werten
   * @param pKnzSortieren Kennzeichen, ob die Properties sortiert werden sollen
   * @param pVorlauf der String, welcher vor dem Propertie-Key vorangestellt wird (null = Leerstring)
   * @param pMitte der String, welcher nach dem Propertie-Key und vor dem Propertie-Value gesetzt wird (null = Leerstring)
   * @param pNachlauf der String, welcher nach dem Propertie-Wert angehaengt wird (null = Leerstring)
   * @param pBreitePropKey die minimale Feldbreite fuer den Propertie Key
   * @param pBreiteValue die minimale Feldbreite fuer den Wert
   * @param pKnzKeyInAnfuehrungszeichen Kennzeichen, ob der Key in Anfuehrungszeichen gesetzt werden soll
   * @param pKnzValueInAnfuehrungszeichen Kennzeichen, ob der Wert in Anfuerhungszeichen gesetzt werden soll
   * @param pKnzKeyValueUmdrehen Kennzeichen, ob Key und Wert umgedreht werden sollen.
   * @return den sich ergebenden String
   */
  public static String getStrProperties( Properties pProperties, boolean pKnzSortieren, String pVorlauf, String pMitte, String pNachlauf, int pBreitePropKey, int pBreiteValue, boolean pKnzKeyInAnfuehrungszeichen, boolean pKnzValueInAnfuehrungszeichen, boolean pKnzKeyValueUmdrehen )
  {
    /*
     * Pruefung: Parameter pProperties gesetzt?
     * Ist der Parameter null, bekommt der Aufrufer einen Leerstring zurueck
     */
    if ( pProperties == null )
    {
      return "";
    }

    /*
     * Stringbuffer fuer die Rueckgabe erstellen
     */
    StringBuffer str_buffer = new StringBuffer();

    /*
     * Eine Variable fuer die auszugebenden Schluessel der Propertieinstanz erstellen. 
     * 
     * Sollen die Schluesselnamen sortiert ausgegeben werden, wird die Funktion "getSortetKeys" aufgerufen.
     * 
     * Ist die Reihenfolge egal, wird die Enumeration aus den Properties geholt.
     */
    Enumeration enumeration_keys = null;

    if ( pKnzSortieren )
    {
      enumeration_keys = getSortedKeys( pProperties ).elements();
    }
    else
    {
      enumeration_keys = pProperties.keys();
    }

    /*
     * Breitenangaben fuer Key und Value setzen. 
     * Dabei gilt die Untergrenze 0 und die Obergrenze 200 Zeichen.
     */
    int breite_feld_key = ( pBreitePropKey < 0 ? 0 : ( pBreitePropKey > 100 ? 100 : pBreitePropKey ) );

    int breite_feld_value = ( pBreiteValue < 0 ? 0 : ( pBreiteValue > 200 ? 200 : pBreiteValue ) );

    int breite_key_plus = ( pKnzKeyInAnfuehrungszeichen ? 2 : 0 );

    int breite_value_plus = ( pKnzValueInAnfuehrungszeichen ? 2 : 0 );

    /*
     * Anfuehrungzeichen
     * Die Key und Values koennen in Anfuehrungszeichen gesetzt werden. 
     * Hier werden die beiden boolschen Parameter ausgewertet und die 
     * Stringvariablen entsprechend gesetzt.
     */
    String str_anfuehrungszeichen_key = ( pKnzKeyInAnfuehrungszeichen ? "\"" : "" );

    String str_anfuehrungszeichen_value = ( pKnzValueInAnfuehrungszeichen ? "\"" : "" );

    String temp_string = null;
    String property_key = null;
    String property_value = null;

    /*
     * While-Schleife ueber die Propertie-Keys. 
     * Die While-Schleife wird solange ausgefuehrt, wie in der Enumeration 
     * der Keys noch Werte vorhanden sind.
     */
    while ( enumeration_keys.hasMoreElements() )
    {
      /*
       * Propertie Key aus der Enumeration holen.        
       */
      property_key = (String) enumeration_keys.nextElement();

      /*
       * Den Propertie Value aus den Eingabeproperties holen.
       * Ist der Key nicht vorhanden, wird ein Leerzeichen als Vorgabe geommen.
       */
      property_value = pProperties.getProperty( property_key, "" );

      /*
       * Pruefung: Key Value umdrehen?
       * 
       * Sollen Key und Value vertauscht werden, wird ein solches hier gemacht
       */
      if ( pKnzKeyValueUmdrehen )
      {
        temp_string = property_key;

        property_key = property_value;

        property_value = temp_string;
      }

      /*
       * Ergebnisaubau
       * 1. String aus "pVorlauf" einfuegen, sofern vorhanden
       * 2. Propertie Key mit der Mindestbreite setzten und eventuell in Klammern setzen
       * 3. String aus "pMitte" einfuegen, wenn nicht gesetzt wird "=" genommen
       * 4. Propertie Value mit der Mindestbreite setzen und eventuell in Klammen setzen
       * 5. String aus "pNachlauf" setzen, sofern vorhanden
       * 6. Zeilenumbruch
       */
      str_buffer.append( pVorlauf == null ? "" : pVorlauf );

      str_buffer.append( FkStringFeld.getFeldLinksMin( str_anfuehrungszeichen_key + property_key + str_anfuehrungszeichen_key, breite_feld_key + breite_key_plus ) );

      str_buffer.append( pMitte == null ? "=" : pMitte );

      str_buffer.append( FkStringFeld.getFeldLinksMin( str_anfuehrungszeichen_value + property_value + str_anfuehrungszeichen_value, breite_feld_value + breite_value_plus ) );

      str_buffer.append( pNachlauf == null ? "" : pNachlauf );

      str_buffer.append( "\n" );
    }

    /*
     * Am Funktionsende, den aufgebauten String zurueckgeben.
     */
    return str_buffer.toString();
  }


  /**
   * <pre>
   * Erstellt eine Ausgabe aus den gemachten Vorgaben.
   * 
   * Properties formular_feld_inhalte = new Properties();
   * 
   * formular_feld_inhalte.setProperty( "Vorname",  "Maria"      );
   * formular_feld_inhalte.setProperty( "Nachname", "Huana"      );
   * formular_feld_inhalte.setProperty( "Strasse",  "Feldweg 7"  );
   * formular_feld_inhalte.setProperty( "Wohnort",  "Ortshausen" );
   * 
   * String pVorlauf                        = "// \" + prop_x.getProperty( ";
   * String pNachlauf                       = ", \"\" ) + \" ";
   * int pBreitePropKey                     = 12;
   * boolean pKnzKeyInAnfuehrungszeichen    = true;
   * 
   * FkLogger.wl( FkProperties.getStrPropertieKeys( formular_feld_inhalte, false, pVorlauf, pNachlauf, pBreitePropKey,  pKnzKeyInAnfuehrungszeichen ) );
   * 
   * // " + prop_x.getProperty( "Nachname"  , "" ) + " 
   * // " + prop_x.getProperty( "Strasse"   , "" ) + " 
   * // " + prop_x.getProperty( "Vorname"   , "" ) + " 
   * // " + prop_x.getProperty( "Wohnort"   , "" ) + " 
   * 
   * FkDatei.schreibeDatei( "c:/Daten/ausgabe_alle_properties.txt", FkProperties.getStrPropertieKeys( formular_feld_inhalte, true, "", "", -1, false ) );
   *   
   * </pre>
   * 
   * @param pProperties die Instanz mit den Werten
   * @param pKnzSortieren Kennzeichen, ob die Properties sortiert werden sollen
   * @param pVorlauf der String, welcher vor dem Propertie-Key vorangestellt wird (null = Leerstring)
   * @param pNachlauf der String, welcher nach dem Propertie-Wert angehaengt wird (null = Leerstring)
   * @param pBreitePropKey die minimale Feldbreite fuer den Propertie Key
   * @param pKnzKeyInAnfuehrungszeichen Kennzeichen, ob der Key in Anfuehrungszeichen gesetzt werden soll
   * @return den sich ergebenden String
   */
  public static String getStrPropertieKeys( Properties pProperties, boolean pKnzSortieren, String pVorlauf, String pNachlauf, int pBreitePropKey, boolean pKnzKeyInAnfuehrungszeichen )
  {
    if ( pProperties == null )
    {
      return "";
    }

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = null;

    if ( pKnzSortieren )
    {
      enumeration_keys = getSortedKeys( pProperties ).elements();
    }
    else
    {
      enumeration_keys = pProperties.keys();
    }

    int breite_feld_key = ( pBreitePropKey < 0 ? 0 : ( pBreitePropKey > 100 ? 100 : pBreitePropKey ) );

    String str_anfuehrungszeichen_key = ( pKnzKeyInAnfuehrungszeichen ? "\"" : "" );

    String property_key = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      str_buffer.append( pVorlauf == null ? "" : pVorlauf );
      str_buffer.append( FkStringFeld.getFeldLinksMin( str_anfuehrungszeichen_key + property_key + str_anfuehrungszeichen_key, breite_feld_key ) );
      str_buffer.append( pNachlauf == null ? "" : pNachlauf );
      str_buffer.append( "\n" );
    }

    enumeration_keys = null;

    property_key = null;

    return str_buffer.toString();
  }

  /**
   * <pre>
   * Erstellt eine Ausgabe aus den gemachten Vorgaben.
   * 
   * Es wird nur der Propertie-Wert fuer die Ausgabe genommen. 
   * Es wird aber nach den Propertie-Values sortiert.
   * 
   * Properties properties_json_feld_namen = new Properties();
   * 
   * properties_json_feld_namen.setProperty( "json_feld_1", "feld_name_1" );
   * properties_json_feld_namen.setProperty( "json_feld_2", "feld_name_2" );
   * properties_json_feld_namen.setProperty( "json_feld_3", "feld_name_3" );
   * 
   * String str_vorlauf  = " private String m_";
   * String str_nachlauf = " = \"\";";
   * 
   * int breite_value    = 12;
   * 
   * boolean knz_nach_key_feldern_sortieren  = true;
   * boolean knz_value_in_anfuehrungszeichen = false;
   * 
   * String str_temp = FkProperties.getStrPropertieValues( properties_json_feld_namen, knz_nach_key_feldern_sortieren, str_vorlauf, str_nachlauf, breite_value, knz_value_in_anfuehrungszeichen );
   * 
   * System.out.println( str_temp );
   *
   *
   * private String m_feld_name_1  = "";
   * private String m_feld_name_2  = "";
   * private String m_feld_name_3  = "";
   *   
   * </pre>
   * 
   * @param pProperties die Instanz mit den Werten
   * @param pKnzSortieren Kennzeichen, ob die Properties sortiert werden sollen
   * @param pVorlauf der String, welcher vor dem Propertie-Wert vorangestellt wird (null = Leerstring)
   * @param pNachlauf der String, welcher nach dem Propertie-Wert angehaengt wird (null = Leerstring)
   * @param pBreitePropKey die minimale Feldbreite fuer den Propertie-Wert
   * @param pKnzValueInAnfuehrungszeichen Kennzeichen, ob der Wert in Anfuehrungszeichen gesetzt werden soll
   * @return den sich ergebenden String
   */
  public static String getStrPropertieValues( Properties pProperties, boolean pKnzSortieren, String pVorlauf, String pNachlauf, int pBreitePropValue, boolean pKnzValueInAnfuehrungszeichen )
  {
    if ( pProperties == null )
    {
      return "";
    }

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = null;

    if ( pKnzSortieren )
    {
      enumeration_keys = getSortedKeys( pProperties ).elements();
    }
    else
    {
      enumeration_keys = pProperties.keys();
    }

    int breite_feld_value = ( pBreitePropValue < 0 ? 0 : ( pBreitePropValue > 100 ? 100 : pBreitePropValue ) );

    String str_anfuehrungszeichen_key = ( pKnzValueInAnfuehrungszeichen ? "\"" : "" );

    String property_key = null;
    String property_value = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      property_value = pProperties.getProperty( property_key, "" );

      str_buffer.append( pVorlauf == null ? "" : pVorlauf );
      str_buffer.append( FkStringFeld.getFeldLinksMin( str_anfuehrungszeichen_key + property_value + str_anfuehrungszeichen_key, breite_feld_value ) );
      str_buffer.append( pNachlauf == null ? "" : pNachlauf );
      str_buffer.append( "\n" );
    }

    enumeration_keys = null;

    property_key = null;

    return str_buffer.toString();
  }

  /**
   * <pre>
   * Erstellt eine Ausgabe aus den gemachten Vorgaben.
   * 
   *  Properties properties_pdf_feld_namen = new Properties();
   *
   *  properties_pdf_feld_namen.setProperty( "FELD_1", "X" );
   *  properties_pdf_feld_namen.setProperty( "FELD_2", "X" );
   *  properties_pdf_feld_namen.setProperty( "FELD_3", "X" );
   *  properties_pdf_feld_namen.setProperty( "FELD_4", "X" );
   *  properties_pdf_feld_namen.setProperty( "FELD_5", "X" );
   *
   *  Properties map_properties = new Properties();
   *
   *  map_properties.setProperty( "FELD_1", "AntragstellerVorname" );
   *  map_properties.setProperty( "FELD_2", "AntragstellerNachname" );
   *  map_properties.setProperty( "FELD_3", "EhepartnerVorname" );
   *  map_properties.setProperty( "FELD_4", "EhepartnerNachname" );
   *
   *  String pVorlauf = "// pdf_ausfuellwerte.setProperty( ";
   *  
   *  String pNachlauf = ", \"\" );";
   *  
   *  int pBreitePropKey = 12;
   *  
   *  boolean pKnzKeyInAnfuehrungszeichen = true;
   *
   *  FkLogger.wl( FkProperties.getStrPropertieKeys( properties_pdf_feld_namen, false, pVorlauf, pNachlauf, pBreitePropKey, pKnzKeyInAnfuehrungszeichen, map_properties ) );
   *
   *  // pdf_ausfuellwerte.setProperty( "FELD_5"    , "" );
   *  // pdf_ausfuellwerte.setProperty( "EhepartnerNachname", "" );
   *  // pdf_ausfuellwerte.setProperty( "EhepartnerVorname", "" );
   *  // pdf_ausfuellwerte.setProperty( "AntragstellerNachname", "" );
   *  // pdf_ausfuellwerte.setProperty( "AntragstellerVorname", "" );
   * 
   *   FkDatei.schreibeDatei( "c:/Daten/ausgabe_alle_properties.txt", FkProperties.getStrPropertieKeys( formular_feld_inhalte, true, "", "", -1, false , map_properties) );
   *   
   * </pre>
   * 
   * @param pProperties die Instanz mit den Werten
   * @param pKnzSortieren Kennzeichen, ob die Properties sortiert werden sollen
   * @param pVorlauf der String, welcher vor dem Propertie-Key vorangestellt wird (null = Leerstring)
   * @param pNachlauf der String, welcher nach dem Propertie-Wert angehaengt wird (null = Leerstring)
   * @param pBreitePropKey die minimale Feldbreite fuer den Propertie Key
   * @param pKnzKeyInAnfuehrungszeichen Kennzeichen, ob der Key in Anfuehrungszeichen gesetzt werden soll
   * @return den sich ergebenden String
   */
  public static String getStrPropertieKeys( Properties pProperties, boolean pKnzSortieren, String pVorlauf, String pNachlauf, int pBreitePropKey, boolean pKnzKeyInAnfuehrungszeichen, Properties pMapProperties )
  {
    if ( pProperties == null )
    {
      return "";
    }

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = null;

    if ( pKnzSortieren )
    {
      enumeration_keys = getSortedKeys( pProperties ).elements();
    }
    else
    {
      enumeration_keys = pProperties.keys();
    }

    int breite_feld_key = ( pBreitePropKey < 0 ? 0 : ( pBreitePropKey > 100 ? 100 : pBreitePropKey ) );

    String str_anfuehrungszeichen_key = ( pKnzKeyInAnfuehrungszeichen ? "\"" : "" );

    String property_key = null;

    String map_prop_key_value = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      if ( pMapProperties != null )
      {
        map_prop_key_value = pMapProperties.getProperty( property_key );
      }
      else
      {
        map_prop_key_value = null;
      }

      str_buffer.append( pVorlauf == null ? "" : pVorlauf );
      str_buffer.append( FkStringFeld.getFeldLinksMin( str_anfuehrungszeichen_key + ( map_prop_key_value != null ? map_prop_key_value : property_key ) + str_anfuehrungszeichen_key, breite_feld_key ) );
      str_buffer.append( pNachlauf == null ? "" : pNachlauf );
      str_buffer.append( "\n" );
    }

    enumeration_keys = null;

    property_key = null;

    return str_buffer.toString();
  }

  /**
   * <pre>
   * Hilfsfunktion fuer Generatoren, um die Schluessel auch als Value zu bekommen.
   * 
   *  Properties properties = new Properties();
   *
   *  properties.setProperty( "KEY_A1", "Value a1" );
   *  properties.setProperty( "KEY_B2", "Value b2" );
   *  properties.setProperty( "KEY_C3", "Value c3" );
   *  properties.setProperty( "KEY_D4", "Value d4" );
   *  properties.setProperty( "KEY_E5", "Value e5" );
   *  properties.setProperty( "KEY_F6", "Value f6" );
   *
   *  Properties properties_key_key = FkProperties.getPropKeyKey( properties );
   *
   *  FkLogger.wl( FkProperties.getStrProperties( properties_key_key ) );
   *  
   *  KEY_A1=KEY_A1
   *  KEY_B2=KEY_B2
   *  KEY_C3=KEY_C3
   *  KEY_D4=KEY_D4
   *  KEY_E5=KEY_E5
   *  KEY_F6=KEY_F6
   * 
   * </pre>
   * 
   * @param pProperties die Propertieinstanz mit den zu kopierenden Schluesseln
   * @return eine Propertieinstanz bei welcher die Key-Values gleich den Key-Namen entspricht
   */
  public static Properties getPropKeyKey( Properties pProperties )
  {
    if ( pProperties == null )
    {
      return null; // keine Eingabe => Rueckgabe von null
    }

    Properties prop_reduziert = new Properties();

    Enumeration enumeration_keys = pProperties.keys();

    String property_key = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      prop_reduziert.setProperty( property_key, property_key );
    }

    enumeration_keys = null;

    property_key = null;

    return prop_reduziert;
  }

  /**
   * <pre>
   * Liefert eine zeilenbasierte Ausgabe der Keys oder der Values aus dem Parameter pProperties. 
   * 
   *  Properties prop_eingabe = new Properties();
   *  
   *  int zaehler = 0;
   *  
   *  while ( zaehler < 4 )
   *  {
   *    prop_eingabe.setProperty( "KEY_" + zaehler + "_AA",    "VALUE_" + zaehler + "_A" );
   *    prop_eingabe.setProperty( "KEY_" + zaehler + "_BBBB",  "VALUE_" + zaehler + "_B" );
   *    prop_eingabe.setProperty( "KEY_" + zaehler + "_CCCCC", "VALUE_" + zaehler + "_C" );
   * 
   *    zaehler++;
   *  }
   *
   *  prop_eingabe.setProperty( "KEY_234_EA", "VALUE_234_EA" ); // fuer eine ungrade Anzahl von Prop-Eintraegen sorgen
   *
   *  int anzahl_spalten        = 3;
   *  
   *  String str_zeilen_praefix = "x_string += \" ";
   *  String str_zeilen_suffix  = " \"; ";
   *  String str_prop_praefix   = " '";
   *  String str_prop_suffix    = "', ";
   *  
   *  wl( "\n\n" +  FkProperties.getPropSpaltenAuflistung( prop_eingabe, anzahl_spalten, 2, 1, str_zeilen_praefix, str_zeilen_suffix, str_prop_praefix, str_prop_suffix ) + "\n\n" );
   *  wl( "\n\n" +  FkProperties.getPropSpaltenAuflistung( prop_eingabe, anzahl_spalten, 2, 2, str_zeilen_praefix, str_zeilen_suffix, str_prop_praefix, str_prop_suffix ) + "\n\n" );
   *
   *  
   * Ausgabewert 1 = alle Schluessel ausgeben:
   *  
   * x_string += "  'KEY_0_AA',     'KEY_0_BBBB',   'KEY_0_CCCCC',  "; 
   * x_string += "  'KEY_1_AA',     'KEY_1_BBBB',   'KEY_1_CCCCC',  "; 
   * x_string += "  'KEY_234_EA',   'KEY_2_AA',     'KEY_2_BBBB',   "; 
   * x_string += "  'KEY_2_CCCCC',  'KEY_3_AA',     'KEY_3_BBBB',   "; 
   * x_string += "  'KEY_3_CCCCC',  "; 
   * 
   * Ausgabewert ungleich 1 = alle Schluessel-Werte ausgeben:
   *  
   * x_string += "  'VALUE_0_A',     'VALUE_0_B',     'VALUE_0_C',     "; 
   * x_string += "  'VALUE_1_A',     'VALUE_1_B',     'VALUE_1_C',     "; 
   * x_string += "  'VALUE_234_EA',  'VALUE_2_A',     'VALUE_2_B',     "; 
   * x_string += "  'VALUE_2_C',     'VALUE_3_A',     'VALUE_3_B',     "; 
   * x_string += "  'VALUE_3_C',     ";
   *  
   *  
   * Ausgabeart "Spaltenorientiert"                |  Ausgabeart "Zeilenorientiert"  
   * Immer dann notwendig, wenn die Sortierung     |  Die Sortierung steigt je Zeile an. 
   * in einer Spalte sein soll (Telefonbuch)       |  
   *                                               |  
   * x_string += "  'KEY_0000',  'KEY_0005',  ";   |  x_string += "  'KEY_0000',  'KEY_0001',  "; 
   * x_string += "  'KEY_0001',  'KEY_0006',  ";   |  x_string += "  'KEY_0002',  'KEY_0003',  "; 
   * x_string += "  'KEY_0002',  'KEY_0007',  ";   |  x_string += "  'KEY_0004',  'KEY_0005',  "; 
   * x_string += "  'KEY_0003',  ";                |  x_string += "  'KEY_0006',  'KEY_0007',  "; 
   * x_string += "  'KEY_0004',  ";                |  
   * </pre>
   *  
   * @param pProperties die Propertieinstanz mit den auszugebenden Werten 
   * @param pAnzahlSpalten die Anzahl der je zeile auszugebenden Werte
   * @param pKnzAusgabeArt spezifiziert wie die auszugebenden Werte orientiert sind (1 = Spaltenorientiert, sonst Zeilenorientiert) 
   * @param pKnzAusgabewert spezifiziert den auszugebenden Wert (1 = Key, sonst Value) 
   * @param pZeilenPraefix der String, welcher je Zeile am Anfang hinzugefuegt werden soll (null = Leerstring) 
   * @param pZeilenSuffix der String, welcher je Zeile am Ende hinzugefuegt werden soll (null = Leerstring) 
   * @param pPropPraefix der String, welcher je Ausgabewert vorangestellt werden soll (null = Leerstring)
   * @param pPropSuffix der String, welcher je Ausgabewert hinten angefuegt werden soll (null = Leerstring) 
   * @return eine Ausgabe der Keys oder Values, sind keine Ausgabwerte vorhanden, wird ein Leerstring zurueckgegeben.
   */
  public static String getPropSpaltenAuflistung( Properties pProperties, int pAnzahlSpalten, int pKnzAusgabeArt, int pKnzAusgabewert, String pZeilenPraefix, String pZeilenSuffix, String pPropPraefix, String pPropSuffix )
  {
    if ( pProperties == null )
    {
      return "";
    }

    String property_key = null;
    String property_value = null;

    int max_len_key = 3;
    int max_len_value = 3;
    int max_grenze_len_key = 150;
    int max_grenze_len_value = 150;
    int anzahl_keys = 0;
    int vektor_index = 0;

    /*
     * MOEGLICHE OPTIMIERUNG 
     * In Vektorkeys gleich den Parameter pKnzAusgabewert hinterlegen.
     * In der zweiten While-Schleife kann dann das rumhantieren mit den Eingabeproperties entfallen.
     * Es wuerde die Abarbeitung vereinfachen.
     * (Die aktuelle Version stellt die Mindetsarbeitsweise dar, sofen nicht mit einem Vector gearbeitet wird.)
     * 
     * if ( pKnzAusgabewert == 1 ) 
     * {
     *   vektor_keys = getSortedKeys( pProperties );
     * }
     * else
     * {
     *   vektor_keys = getSortedValues( pProperties );
     * }
     * 
     */
    Vector vektor_keys = getSortedKeys( pProperties );

    anzahl_keys = vektor_keys.size();

    while ( vektor_index < anzahl_keys )
    {
      try
      {
        property_key = (String) ( vektor_keys.get( vektor_index ) );
      }
      catch ( Exception err_inst )
      {
        property_key = null;
      }

      if ( property_key != null )
      {
        property_value = pProperties.getProperty( property_key );

        if ( property_value == null )
        {
          property_value = "null";
        }
      }
      else
      {
        property_key = "";
        property_value = "";
      }

      max_len_key = ( property_key.length() > max_len_key ) ? property_key.length() : max_len_key;

      max_len_value = ( property_value.length() > max_len_value ) ? property_value.length() : max_len_value;

      vektor_index++;
    }

    if ( anzahl_keys == 0 )
    {
      return "";
    }

    max_len_key = ( max_len_key > max_grenze_len_key ) ? max_grenze_len_key : max_len_key;

    max_len_value = ( max_len_value > max_grenze_len_value ) ? max_grenze_len_value : max_len_value;

    int anzahl_spalten = pAnzahlSpalten;

    if ( anzahl_spalten == 0 )
    {
      anzahl_spalten = 1;
    }

    int anzahl_zeilen = ( (int) ( ( anzahl_keys / anzahl_spalten ) + 0.9 ) ) + 1;

    StringBuffer str_buffer = new StringBuffer();

    String str_zeilen_praefix = ( pZeilenPraefix == null ? "" : pZeilenPraefix );
    String str_zeilen_suffix = ( pZeilenSuffix == null ? "" : pZeilenSuffix );

    String str_prop_praefix = ( pPropPraefix == null ? "" : pPropPraefix );
    String str_prop_suffix = ( pPropSuffix == null ? "" : pPropSuffix );

    String str_aktuelles_feld = null;
    String str_ausgabe_zeile_akt = "";

    int breite_ausgabe_feld = max_len_key;

    if ( pKnzAusgabewert != 1 )
    {
      breite_ausgabe_feld = max_len_value;
    }

    breite_ausgabe_feld += str_prop_suffix.length();

    int akt_index = 0;
    int zeilen_zaehler = 1;
    int spalten_zaehler = 0;

    while ( ( zeilen_zaehler <= anzahl_zeilen ) )
    {
      str_ausgabe_zeile_akt = "";

      spalten_zaehler = 1;

      while ( ( spalten_zaehler <= anzahl_spalten ) )
      {
        try
        {
          if ( pKnzAusgabeArt == 1 )
          {
            /*
             * Ausgabeart "Spaltenweise"
             * Sorgt dafuer, dass in der Ausgabe die Werte je Spalte in der Sortierung anzeigen.
             * 
             * Der Index muss berechnet werden.
             * 
             * Index = Zeilen-Nr + ( Spalten-Nr - 1 ) * Zeilenanzahl
             */
            property_key = (String) ( vektor_keys.get( ( zeilen_zaehler + ( spalten_zaehler - 1 ) * anzahl_zeilen ) - 1 ) );
          }
          else
          {
            /*
             * Ausgabeart "Zeilenweise"
             * Sorgt dafuer, dass in der Ausgabe die Werte je Zeile ansteigen.
             * 
             * Der Index ist hier fortlaufend. 
             */
            property_key = (String) ( vektor_keys.get( akt_index ) );

            akt_index++;
          }
        }
        catch ( Exception err_inst )
        {
          property_key = null;
        }

        /*
         * Bestimmung Ausgabewert
         */
        if ( pKnzAusgabewert == 1 )
        {
          /*
           * Ausgabewert = 1 = Property Key
           */
          str_aktuelles_feld = property_key;
        }
        else
        {
          /*
           * Ausgabewert anders als 1 = Property-Value
           * In diesem Fall wird der Property-Wert aus den Eingabeprops geholt.
           */
          if ( property_key != null )
          {
            property_value = pProperties.getProperty( property_key );
          }
          else
          {
            property_value = null;
          }

          str_aktuelles_feld = property_value;
        }

        /*
         * Ausgabe erstellen
         */
        if ( str_aktuelles_feld != null )
        {
          str_ausgabe_zeile_akt += str_prop_praefix + FkStringFeld.getFeldLinksMin( str_aktuelles_feld + str_prop_suffix, breite_ausgabe_feld );
        }

        spalten_zaehler = spalten_zaehler + 1;
      }

      if ( str_ausgabe_zeile_akt.trim().length() > 0 )
      {
        str_buffer.append( str_zeilen_praefix );
        str_buffer.append( str_ausgabe_zeile_akt );
        str_buffer.append( str_zeilen_suffix );
        str_buffer.append( "\n" );
      }

      zeilen_zaehler = zeilen_zaehler + 1;
    }

    vektor_keys.clear();
    vektor_keys = null;

    property_key = null;
    property_value = null;

    return str_buffer.toString();
  }

  /**
   * <pre>
   * Liefert die Worte aus dem Parameter "pString" als Schluessel und deren Anzahl als Value zurueck.
   * Zaehlt die Worte im Eingabestring und gibt das Ergebnis in einer Propertie-Instanz zurueck.
   * 
   * Srting j_str = "";
   * 
   * j_str += "private static String getTestObjektArtLang( String pObjektartKurz )";
   * j_str += "{";
   * j_str += "  if ( pObjektartKurz.equalsIgnoreCase( \"GST\" ) ) return \"Baugrundstueck\";";
   * j_str += "  if ( pObjektartKurz.equalsIgnoreCase( \"EFH\" ) ) return \"Einfamilienhaus\";";
   * j_str += "  if ( pObjektartKurz.equalsIgnoreCase( \"ZFH\" ) ) return \"Zweifamilienhaus\";";
   * j_str += "  if ( pObjektartKurz.equalsIgnoreCase( \"MRH\" ) ) return \"Mittelreihenhaus\";";
   * j_str += "  if ( pObjektartKurz.equalsIgnoreCase( \"ERH\" ) ) return \"Endreihenhaus\";";
   * j_str += "  if ( pObjektartKurz.equalsIgnoreCase( \"DHH\" ) ) return \"Doppelhaushaelfte\";";
   * j_str += "  if ( pObjektartKurz.equalsIgnoreCase( \"ETW\" ) ) return \"Eigentumswohnung\";";
   * j_str += "";
   * j_str += "  return \"Einfamilienhaus\";";
   * j_str += "}";
   *
   * wl( FkProperties.getStrWertAuflistung( FkProperties.grepKeysWorte( j_str, 2 ), 40 ) );
   * 
   * Baugrundstueck       : 1   |  Endreihenhaus        : 1   |  getTestObjektArtLang : 1   |  
   * DHH                  : 1   |  GST                  : 1   |  if                   : 7   |  
   * Doppelhaushaelfte    : 1   |  MRH                  : 1   |  pObjektartKurz       : 8   |  
   * EFH                  : 1   |  Mittelreihenhaus     : 1   |  private              : 1   |  
   * ERH                  : 1   |  String               : 2   |  return               : 8   |  
   * ETW                  : 1   |  ZFH                  : 1   |  static               : 1   |  
   * Eigentumswohnung     : 1   |  Zweifamilienhaus     : 1   |                       :     |  
   * Einfamilienhaus      : 2   |  equalsIgnoreCase     : 7   |                       :     |  
   * 
   * <hl> 
   * 
   * try
   * {
   *   String datei_eingabe  = "C:/Daten/datei_input.txt";
   *
   *   String dat_inhalt     = FkDatei.getStringDateiInhalt( datei_eingabe );
   *
   *   String property_key   = null;
   *   String property_value = null;
   *
   *   int zaehler = 0;
   *
   *   Properties prop_worte = FkProperties.grepKeysWorte( dat_inhalt, 1 );
   *
   *   Enumeration enumeration_keys = prop_worte.keys();
   *
   *   FkLogger.wl( "zaehler | property_key | property_value " );
   *
   *   while ( enumeration_keys.hasMoreElements() )
   *   {
   *     zaehler++;
   *
   *     property_key = (String) enumeration_keys.nextElement();
   *
   *     property_value = prop_worte.getProperty( property_key );
   *
   *     if ( property_value != null )
   *     {
   *       FkLogger.wl( zaehler + " | " + property_key + " | " + property_value );
   *     }
   *   }
   *
   *   prop_worte       = null;
   *   enumeration_keys = null;
   * }
   * catch ( Exception err_inst )
   * {
   *   FkLogger.wl( "Fehler", err_inst );
   * }
   * 
   * </pre>
   * 
   * @param pString der String aus dem die Worte zu extrahieren sind
   * @param pMindestlaenge die Mindestlaenge, welche die gefundenen Worte fuer eine Aufnahme ins Ergebnis haben muessen
   * @return eine Propinstanz mit den Worten, null, wenn die Eingabe null ist.
   */
  public static Properties grepKeysWorte( String pString, int pMindestlaenge )
  {
    /*
     * Pruefung: Sind die Parameter ungleich null?
     */
    if ( pString != null )
    {
      Properties prop_erg = new Properties();

      String temp_wort = "";

      String wort_trennzeichen = " ()-+=:.;,\\/[]{}=*?!#<>|&%\"";

      char akt_zeichen = ' ';

      int mindest_anzahl_zeichen = ( pMindestlaenge < 1 ? 1 : ( pMindestlaenge > 100 ? 100 : pMindestlaenge ) );

      int prop_value_zaehler = 0;

      int len_eingabe = pString.length();

      int akt_index = 0;

      while ( akt_index < len_eingabe )
      {
        akt_zeichen = pString.charAt( akt_index );

        if ( wort_trennzeichen.indexOf( akt_zeichen ) >= 0 )
        {
          if ( temp_wort.length() >= mindest_anzahl_zeichen )
          {
            /*
             * Hochzaehlen der bisherigen Anzahl.
             * Ist die Anzahl noch nicht gesetzt, ist die Anzahl 0.
             */
            prop_value_zaehler = Integer.parseInt( prop_erg.getProperty( temp_wort, "0" ) ) + 1;

            /*
             * Neue Anzahl im Ergebnis hinterlegen
             */
            prop_erg.setProperty( temp_wort, "" + prop_value_zaehler );
          }
          //else
          //{
          //  FkLogger.wl( temp_wort + " " + temp_wort.length() + " " + mindest_anzahl_zeichen );
          //}

          temp_wort = "";
        }
        else if ( ( ( akt_zeichen >= 'a' ) && ( akt_zeichen <= 'z' ) ) || ( ( akt_zeichen >= 'A' ) && ( akt_zeichen <= 'Z' ) ) || ( ( akt_zeichen >= '0' ) && ( akt_zeichen <= '9' ) ) || ( akt_zeichen == '_' ) )
        {
          /*
           * Wortzusammenbau fuer Zeichen a-z A-Z 0-9 _
           */
          temp_wort += akt_zeichen;
        }

        /*
         * Leseposition weiterschalten
         */
        akt_index++;
      }

      if ( temp_wort.length() > 0 )
      {
        if ( temp_wort.length() >= mindest_anzahl_zeichen )
        {
          /*
           * Hochzaehlen der bisherigen Anzahl.
           * Ist die Anzahl noch nicht gesetzt, ist die Anzahl 0.
           */
          prop_value_zaehler = Integer.parseInt( prop_erg.getProperty( temp_wort, "0" ) ) + 1;

          /*
           * Neue Anzahl im Ergebnis hinterlegen
           */
          prop_erg.setProperty( temp_wort, "" + prop_value_zaehler );
        }
      }

      temp_wort = null;

      /*
       * Rueckgabe der Propertie-Instanz.
       */
      return prop_erg;
    }

    /*
     * Als Standardrueckgabe wird eine null-Referenz zurueckgegeben. 
     */
    return null;
  }


  public static String getStringPropertieValues( Properties pProperties, boolean pKnzSortieren )
  {
    if ( pProperties == null )
    {
      return null;
    } // keine Quelle -> null zurueckgeben

    StringBuffer str_buffer = new StringBuffer();

    Enumeration enumeration_keys = pProperties.keys();

    if ( pKnzSortieren )
    {
      Vector vector_keys = getSortedKeys( pProperties );

      if ( vector_keys != null )
      {
        enumeration_keys = vector_keys.elements();
      }
    }

    String property_key = null;
    String property_value = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      /*
       * Den naechsten Propertie-Key aus der Eingabe ermitteln
       */
      property_key = (String) enumeration_keys.nextElement();

      /*
       * Fuer den Propertie-Key dessen Propertie-Value ermitteln
       */
      if ( property_key != null )
      {
        property_value = pProperties.getProperty( property_key );
      }
      else
      {
        property_value = null;
      }

      /*
       * Es werden nur gesetzte Propertie-Values gezaehlt.
       */
      if ( property_value != null )
      {
        str_buffer.append( "\n" );
        str_buffer.append( property_value );
      }
    }

    enumeration_keys = null;

    property_key = null;
    property_value = null;

    return str_buffer.toString();
  }

}
