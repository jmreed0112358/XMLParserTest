package xmlparsertest;

import java.io.IOException;
import org.jdom2.JDOMException;

public class XMLParserMain
{

	public XMLParserMain( )
	{
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		if ( args.length != 2 )
		{
			System.out
					.println( "Usage: java -jar XMLParserMain <path to xml file> <path for rewritten xml document>" );
			System.exit( 0 );
		}
		else
		{
			try
			{
				for ( int i = 0 ; i < args.length ; ++i )
				{
					System.out.println( "args[" + i + "]: " + args[i] );
				}
				
				XMLParser xmlParser = new XMLParser( args[0] );

				xmlParser.printDocument( );

				xmlParser.addUser( );

				xmlParser.printDocument( );

				xmlParser.writeDocumentToFile( args[0] );

				xmlParser.removeUser( "Donald" );

				xmlParser.writeDocumentToFile( args[1] );
			}
			catch( NullPointerException nullPointerException )
			{
				System.out.println( "Caught NullPointerException" );
				nullPointerException.printStackTrace( );
			}
			catch( IOException ioException )
			{
				System.out.println( "Caught IOException" );
				ioException.printStackTrace( );
			}
			catch( JDOMException jdomException )
			{
				System.out.println( "Cought JDOMException" );
				jdomException.printStackTrace( );
			}
		}
	}

}
