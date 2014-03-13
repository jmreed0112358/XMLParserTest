package xmlparsertest;

import java.io.IOException;
import java.util.List;

import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

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
		try
		{
			XMLParser xmlParser = new XMLParser( "data/testdata.xml" );
			
			xmlParser.printDocument( );
			
			xmlParser.addUser( );
			
			xmlParser.printDocument( );
			
			xmlParser.writeDocumentToFile( "data/newtestdata.xml" );
			
			xmlParser.removeUser( "Donkey" );
			
			xmlParser.writeDocumentToFile( "data/removeduserdata.xml" );
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
