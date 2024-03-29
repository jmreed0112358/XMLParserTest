package xmlparsertest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.NoSuchFileException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLParser
{
	private SAXBuilder	jdomBuilder	= null;
	private Document	xmlDocument	= null;

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
	
	public void parseXMLString( String xmlString )
	{
		try
		{
			xmlDocument = new SAXBuilder( ).build( new StringReader( xmlString ) );
		}
		catch( JDOMException jdomException )
		{
			jdomException.printStackTrace();
		}
		catch( IOException ioException )
		{
			ioException.printStackTrace();
		}
	}
	
	/**
	 * Adds a new user to the document. Rough version. In the future, this will
	 * take input from the user. We'll have another version that takes a user
	 * data structure as an argument.
	 */
	public void addUser( )
	{
		Element root = xmlDocument.getRootElement( );

		Element user = new Element( "user" );

		Element userName = new Element( "username" );

		userName.setText( "Donald" );

		Element key = new Element( "key" );

		Element keyid = new Element( "keyid" );

		keyid.setText( "Master Key" );

		Element pubkey = new Element( "pubkey" );

		pubkey.setText( "Fuck" );

		Element privkey = new Element( "privkey" );

		privkey.setText( "You" );

		key.addContent( keyid );

		key.addContent( pubkey );

		key.addContent( privkey );

		user.addContent( userName );

		user.addContent( key );

		root.addContent( user );
	}

	/**
	 * Removes a user from the xml file.
	 * TODO: Think of a better way to do this.  This is an O(n^2) algorithm in the worst case.
	 * I know of one way to improve on this, but we'd increase memory complexity.
	 * @param userName
	 */
	public void removeUser( String UserName )
	{
		Element root = xmlDocument.getRootElement( );

		int index = -1;

		// Find the desired user.
		if ( hasChildren( root ) )
		{
			do
			{
				List<Element> users = root.getChildren( );
				
				index = findUser( UserName, root );

				if ( index != -1 )
				{
					// We've found the user. Detach.
					users.get( index ).detach( );
				}
			} while( index != -1 );
		}
	}

	/**
	 * Searches the JDOM2 structure for a given username.
	 * @param UserName
	 * @param root
	 * @return
	 */
	private int findUser( String UserName, Element root )
	{
		List<Element> users = root.getChildren( );

		for( int i = 0 ; i < users.size( ) ; ++i )
		{
			System.out.println( "userName: "
					+ users.get( i ).getChild( "username" ).getText( ) );

			if ( UserName.equals( users.get( i ).getChild( "username" )
					.getText( ) ) )
			{
				System.out.println( "Found the user!" );
				return i;
			}
		}
		System.out.println( "Didn't find the user" );
		return -1;
	}

	/**
	 * This function walks through the JDOM2 document, and prints its data to
	 * the screen.
	 */
	public void printDocument( )
	{
		System.out
				.println( "Name:  " + xmlDocument.getRootElement( ).getName( ) );

		Element root = xmlDocument.getRootElement( );

		if ( hasChildren( root ) )
		{
			printChildren( root );
		}
		else
		{
			printValue( root );
		}

	}

	/**
	 * Writes the JDOM2 document to an external file using the XMLOutputter
	 * class. TODO: We'll just dump to the screen for now.
	 * 
	 * @param path
	 */
	public void writeDocumentToFile( String path ) throws NullPointerException,
			NoSuchFileException, IOException
	{
		if ( path == null )
		{
			throw new NullPointerException( );
		}

		BufferedWriter newFile = null;

		try
		{
			newFile = new BufferedWriter( new FileWriter( path ) );

			XMLOutputter xmlOutput = new XMLOutputter( );

			xmlOutput.setFormat( Format.getPrettyFormat( ) );

			System.out.println( xmlOutput.outputString( xmlDocument ) );

			newFile.write( xmlOutput.outputString( xmlDocument ) );

			System.out.println( "Successfully wrote to newFile." );
		}
		catch( SecurityException securityException )
		{
			System.out
					.println( "There was a problem creating/writing to the output file." );
			securityException.printStackTrace( );
		}
		catch( IOException ioException )
		{
			System.out.println( "Caught IOException" );
			ioException.printStackTrace( );
		}
		finally
		{
			if ( newFile != null )
			{
				newFile.close( );
			}
		}

	}

	/**
	 * Prints the children of the given element. Precondition: We've already
	 * called hasChildren on this element. Not a huge deal if we don't, but it
	 * serves as our stopping case.
	 * 
	 * @param children
	 */
	private void printChildren( Element element )
	{
		List<Element> children = element.getChildren( );

		for( int i = 0 ; i < children.size( ) ; ++i )
		{
			System.out.println( "Name:  " + children.get( i ).getName( ) );

			if ( hasChildren( children.get( i ) ) )
			{
				printChildren( children.get( i ) );
			}
			else
			{
				printValue( children.get( i ) );
			}
		}

	}

	/**
	 * Gets the value. Dumb getter. This is separated, in case we want to treat
	 * values of different elements in a different manner, eg. encrypted values
	 * for encrypted xml files.
	 * 
	 * @param element
	 */
	private void printValue( Element element )
	{
		System.out.println( "Value: " + element.getText( ) );
	}

	/**
	 * This tells us if an element has more children.
	 * 
	 * @param elementList
	 * @return
	 */
	private boolean hasChildren( Element element )
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
