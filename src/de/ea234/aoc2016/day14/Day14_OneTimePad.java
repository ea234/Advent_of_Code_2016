package de.ea234.aoc2016.day14;

import java.security.MessageDigest;

/**
 * <pre>
 * 
 * --- Day 14: One-Time Pad ---
 * https://adventofcode.com/2016/day/14
 * 
 * https://www.reddit.com/r/adventofcode/comments/5i8pzz/2016_day_14_solutions/
 * 
 * 
 * 
 * 
 * abc18  = 0034e0923cc38887a57bd7b1d4f953df
 * abc39  = 347dac6ee8eeea4652c7476d0f97bee5
 * abc92  = ae2e85dd75d63e916a525df95e999ea0
 * abc200 = 83501e9109999965af11270ef8d61a4f
 * 
 * 
 * Index      18  abc18           = 0034e0923cc38887a57bd7b1d4f953df  = 8
 * Index      39  abc39           = 347dac6ee8eeea4652c7476d0f97bee5  = e
 * Index      45  abc45           = ddd37f736db183b6b4c186b87dd6236c  = d
 * Index      64  abc64           = 5a776356daa2a541c062fa9c65405552  = 5
 * Index      77  abc77           = d07a28650fff793ea551a0c6696ce7db  = f
 * Index      79  abc79           = 6aadbb9dff757eaaaec01fb36852aacd  = a
 * Index      88  abc88           = 3607fff5c196e1ae3eb1768fdc67c7d8  = f
 * Index      91  abc91           = c4b5b21401204280e2f079d3000f56a9  = 0
 * Index      92  abc92           = ae2e85dd75d63e916a525df95e999ea0  = 9
 * Index     110  abc110          = 7af7fa13b999d68bbce565971ddbae27  = 9
 * Index     118  abc118          = bbb7006c6639b7c7e52a86497d286e51  = b
 * Index     122  abc122          = 6b130168c1ea8db621b6b92111c30baa  = 1
 * Index     138  abc138          = 829074e3fd06a236a2aaada262ec9bf5  = a
 * Index     141  abc141          = 77d7774fc5457b381e03d07a5816b782  = 7
 * Index     148  abc148          = 34b6068343666739a73d06eb2c73e525  = 6
 * Index     160  abc160          = 473e2c0aaaa842a876e956a0605862b6  = a
 * Index     161  abc161          = 16bdd910d1ce66bcccae077af611e602  = c
 * Index     181  abc181          = e95903ec7d5109022201dddd84bb138a  = 2
 * Index     184  abc184          = 02164648eee4fd760cdab7e343effd74  = e
 * Index     193  abc193          = 590dd7546c592ac410003007ea113094  = 0
 * Index     200  abc200          = 83501e9109999965af11270ef8d61a4f  = 9
 * Index     212  abc212          = 40f00b9fe9f1666d4e0602171a1a00ea  = 6
 * Index     249  abc249          = f299a6ccc2cf6d9aba661f540fe50231  = c
 * Index     267  abc267          = 098c1395c46efbe677755719df9fd4c0  = 7
 * Index     281  abc281          = bf26a82b8b57bfdf4b34797e7ccc643a  = c
 * Index     283  abc283          = 938cfaf68bbf2e349c5ccc759292961b  = c
 * Index     291  abc291          = d7c86a1c1af63c851fe876e44427da83  = 4
 * Index     314  abc314          = b76a587a333a5de4fbd566b426e1254c  = 3
 * Index     315  abc315          = 71c830bb6efc1d0453d54f83b9994ec7  = 9
 * Index     340  abc340          = 69c230f6bcbba5130415160deccc09b7  = c
 * Index     343  abc343          = d3c939a6e3739444983e1961fad091d8  = 4
 * Index     345  abc345          = 6df546ce5a70dc4385556597e04eb274  = 5
 * Index     353  abc353          = 649aa00034b97dba95397de24fd1eb99  = 0
 * Index     360  abc360          = c4abfdc2f7fffe4559142d8ff635296c  = f
 * Index     366  abc366          = b6492f4bd304eca9978cf777bea52a8c  = 7
 * Index     368  abc368          = 5777b7a654b6523cada9fd1a3ebb69ed  = 7
 * Index     373  abc373          = 1b31aedd79993214b70de07c0de9bbff  = 9
 * Index     385  abc385          = d847a4440f2a4423b79fe9bdc1cde659  = 4
 * Index     400  abc400          = f0f82f836662b70a209b3710f8498c3a  = 6
 * Index     402  abc402          = f658aa2864a59a1cb9691cc08889ee3f  = 8
 * Index     404  abc404          = e76654220111d1bb1fc25429ff58a8bd  = 1
 * Index     410  abc410          = 8ec6a2c0365d20f2261730b500077f8a  = 0
 * Index     441  abc441          = 0ff627d82225f24598a128cc453900f7  = 2
 * Index     443  abc443          = 3351f1bf5df96dd1365ed7b1545cccd5  = c
 * Index     449  abc449          = c252d95bf187ed4220471c0ae6d77737  = 7
 * Index     450  abc450          = a003376f5abbcda67772e00b928e6609  = 7
 * Index     459  abc459          = 77b05e16607a333bae2dee537ce16b9a  = 3
 * Index     461  abc461          = d2c1333bc7b740a6af0c51eded05e70f  = 3
 * Index     489  abc489          = 3168351b76dee9d74442c60fb7947758  = 4
 * Index     494  abc494          = 183aa7110ae7b357b1ff78fc5faaa4ca  = a
 * Index     502  abc502          = 5ef53879b68588834e9d80b42aa09b49  = 8
 * Index     514  abc514          = 6409ff5bfd9b461be517de5d43d6fccc  = c
 * Index     534  abc534          = 4fc7df57777400b3eeef5350a6eecbdf  = 7
 * Index     536  abc536          = 61c6e98563999d0c47498aad247d04a2  = 9
 * Index     564  abc564          = 2fc47b6c6e835c6ce930c04399937f01  = 9
 * Index     589  abc589          = a9248ddd044d68799b5afb4489a0cf00  = d
 * Index     611  abc611          = 69831039316ef0624a1e604aecd66000  = 0
 * Index     630  abc630          = 34360c1f58ede6509d83bf5c34222fae  = 2
 * Index     640  abc640          = a41e6bbbc537c14b5b71550dd53beb2b  = b
 * Index     655  abc655          = 4bc9da358068aaf5aa8074aa88ae8fff  = f
 * Index     712  abc712          = 567716845a5617ad777b02cab9409ebe  = 7
 * Index     716  abc716          = 9493bbb4344bca4ad747bf982a72d10e  = b
 * Index     717  abc717          = b378b0e0afbf0e3bb10cdd95266654b7  = 6
 * Index     720  abc720          = ef484ae62e9725906fa999e703025e22  = 9
 * Index     725  abc725          = ac55570b02a2bef51d0124be57a769cf  = 5
 * Index     762  abc762          = 9662100183ee75b31994782f57f9ccc7  = c
 * Index     764  abc764          = 1d40ff37418f20775fd615553cb97f50  = 5
 * Index     770  abc770          = f7ae45bd6f01407b45c3fff43e9c226e  = f
 * Index     771  abc771          = f4fc8ddb4c3336ad9fc5e6985bf10bfc  = 3
 * Index     772  abc772          = a1f4134b4c875fb40f96eddd34166bb3  = d
 * Index     777  abc777          = 594263c5324b1e24edeb812bbf700111  = 1
 * Index     781  abc781          = 05cc3a44479f138930aef93d09adb271  = 4
 * Index     791  abc791          = 46888d59a86314786d3cce2ec84a74d4  = 8
 * Index     792  abc792          = d3037333338c0e36482facef46a5d375  = 3
 * Index     801  abc801          = 83b2823bc66d2ed49555b92148d65ab2  = 5
 * Index     816  abc816          = 3aeeeee1367614f3061d165a5fe3cac3  = e
 * Index     838  abc838          = 8e247e598810d5ddd6cd37d3f1f3fe07  = d
 * Index     860  abc860          = 6ea76b29e777ddd70968a0b5b721d009  = 7
 * Index     877  abc877          = 3585dc5c9d1f7260fb119511821112de  = 1
 * Index     887  abc887          = 6224443f4f20b285f198a5928240b8f9  = 4
 * Index     902  abc902          = 1830bbb4672b0f6ce76c600dde600d5d  = b
 * Index     906  abc906          = 5b6779103eb12fffe8765f53a18f1263  = f
 * Index     909  abc909          = a34277798532e1cd78a3266711e56a1b  = 7
 * Index     912  abc912          = bc6a590eee4cf92859b81bd096b4796e  = e
 * Index     921  abc921          = 0cf3121c29f42db2d192ee99090888ef  = 8
 * Index     924  abc924          = 7dda7ee65c172ebbb5bd1cbf5af94973  = b
 * Index     925  abc925          = 83765555443a2801988c832e20239023  = 5
 * Index     933  abc933          = d666c95d334124bd8421213ee750f298  = 6
 * Index     939  abc939          = 8fff5c06db476ae61273999a89bf7d06  = f
 * Index     953  abc953          = 5df970cdf3be5557f96668b8b856e2a2  = 5
 * Index     955  abc955          = 067e32c47bb94441fb1ddb164028f1a0  = 4
 * Index     967  abc967          = 32826f82c36b12b3e603e970aaa96d6f  = a
 * Index     968  abc968          = 3ffbe9599ba93a02d270d7e5dddc2510  = d
 * Index     980  abc980          = ab50c7994b7043555138aba0c978abe3  = 5
 * Index     994  abc994          = 57c9eabb7e55b921295163333433ce3a  = 3
 * Index     995  abc995          = 8b48fd9ec78190bee111a39fd329bb70  = 1
 * 
 * 
 * </pre> 
 */
public class Day14_OneTimePad
{
  public static void main( String[] args )
  {
    calculate01( "abc", true );

    testMD5( "abc18" );
    testMD5( "abc39" );
    testMD5( "abc92" );
    testMD5( "abc200" );

    System.exit( 0 );
  }

  private static void calculate01( String pSalt, boolean pKnzDebug )
  {
    int result_part_01 = 0;
    int result_part_02 = 0;

    int cur_index = 0;

    while ( cur_index < 1000 )
    {
      String cur_md5 = getMD5( pSalt + cur_index );

      String triple_char = getFirstTriple( cur_md5 );

      if ( triple_char != null )
      {
        wl( String.format( "Index %7d  %-15s = %s  = %s", cur_index, pSalt + cur_index, cur_md5, triple_char ) );
      }

      cur_index++;
    }

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static String getFirstTriple( String pInput )
  {
    for ( int idx = 2; idx < pInput.length(); idx++ )
    {
      if ( ( pInput.charAt( idx ) == pInput.charAt( idx - 1 ) ) && ( pInput.charAt( idx ) == pInput.charAt( idx - 2 ) ) )
      {
        return "" + pInput.charAt( idx );
      }
    }

    return null;
  }

  private static String testMD5( String pInput )
  {
    String md5_hash = getMD5( pInput );

    wl( pInput + " = " + md5_hash );

    return md5_hash;
  }

  private static String getMD5( String pInput )
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance( "MD5" );

      byte[] md5_digest = md.digest( pInput.getBytes( "UTF-8" ) );

      StringBuilder hex_string = new StringBuilder();

      for ( byte cur_byte : md5_digest )
      {
        //hex_string.append( String.format( "%x", cur_byte ) ); // false if value is 0, you get only a 0

        //String hex_number = Integer.toHexString( 0xFF & cur_byte ); // uneccessary, because input cur_byte is already byte (=below 256)

        String hex_number = Integer.toHexString( 0xFF & cur_byte );

        if ( hex_number.length() == 1 ) hex_string.append( '0' );

        hex_string.append( hex_number );
      }

      return hex_string.toString();
    }
    catch ( Exception e )
    {
      wl( e.getMessage() );
    }

    return null;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
