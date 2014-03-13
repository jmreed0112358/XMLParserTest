package xmlparsertest;

import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLParser
{
	SAXBuilder	jdomBuilder	= null;
	Document	xmlDocument	= null;

	public XMLParser( )
	{
	}

	/**
	 * Takes in the filename, and builds an JDOM2 document using the SAXBuilder
	 * parser.
	 * 
	 * @param fileName
	 * @throws JDOMException
	 */
	public XMLParser( String fileName ) throws NullPointerException,
			IOException, JDOMException
	{
		if ( fileName == null )
		{
			throw new NullPointerException( );
		}

		jdomBuilder = new SAXBuilder( );

		xmlDocument = jdomBuilder.build( fileName );
	}

	/**
	 * This function walks through the JDOM2 document, and prints its data to
	 * the screen.
	 */
	public void printDocument( )
	{
		System.out.println( "Name:  "
				+ xmlDocument.getRootElement( ).getName( ) );

		// Get the root element.
		Element root = xmlDocument.getRootElement( );

		if ( hasChildren( root ) )
		{
			printChildren( root.getChildren( ) );
		}
		else
		{
			printValue( root );
		}

	}

	public void printChildren( List<Element> children )
	{
		for( int i = 0 ; i < children.size( ) ; ++i )
		{
			System.out.println( "Name:  " + children.get( i ).getName( ) );

			if ( hasChildren( children.get( i ) ) )
			{
				printChildren( children.get( i ).getChildren( ) );
			}
			else
			{
				printValue( children.get(i) );
			}
		}

	}
	
	public void printValue( Element element )
	{
		System.out.println( "Value: " + element.getText( ) );
	}

	/**
	 * This tells us if an element has more children.
	 * 
	 * @param elementList
	 * @return
	 */
	public boolean hasChildren( Element element )
	{
		List<Element> children = element.getChildren( );

		if ( children.size( ) == 0 )
		{
			return false;
		}
		else
		{
			return true;
		}
	}

}
