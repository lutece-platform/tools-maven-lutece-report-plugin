/*
 * Copyright (c) 2002-2020, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */


package fr.paris.lutece.tools.maven.report;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.maven.plugin.logging.Log;

/**
 * FileViewerService
 */
public class FileViewerService 
{
    private static final String PATH_VIEW_TEMPLATES = "/target/site/templates";
    private static final String HEADER = "<!doctype html>\n<html lang=\"en\">\n<head>\n" +
        "  <meta charset=\"utf-8\">\n  <title>{0}</title>\n  <link rel=\"stylesheet\" href=\"{1}css/stylesheet.css\">\n" +
        "</head>\n<body>\n<h1>{0}</h1>\n<pre>\n";
    
    private static final String FOOTER = "</pre>\n</body>\n</html>";

    /**
     * Initialize the view files directories
     * @param strProjectPath The project path
     */
    public static void initViewFilesDirectory( String strProjectPath )
    {
        File dirSite = new File( strProjectPath + "/target/site" );
        if( ! dirSite.exists() )
        {
            dirSite.mkdir();
        }
        File dirTemplates = new File( strProjectPath + "/target/site/templates" );
        if( ! dirTemplates.exists() )
        {
            dirTemplates.mkdir();
        }
    }

    /**
     * Create a view template
     * @param logger the logger
     * @param strProjectDirectory The project directory
     * @param strTemplatesDirectory The template directory
     * @param file The template as file
     * @param nDepth The depth in the template tree
     */
    public static void createViewFile( Log logger, String strProjectDirectory , String strTemplatesDirectory, File file, int nDepth )
    {
        String strDestFile = getDestFilePath( strProjectDirectory, strTemplatesDirectory, file );
        if( file.isDirectory() )
        {
            File fileDirectory = new File( strDestFile );
            fileDirectory.mkdir();
        }
        else
        {
            File fileDest = new File( strDestFile );
            try ( FileWriter writer = new FileWriter( fileDest , false );
                BufferedReader reader = new BufferedReader( new FileReader( file ) );
                    )
            {
                String strHeader = MessageFormat.format( HEADER, file.getName() , getCssPath( nDepth ) );
                writer.write( strHeader );
                String strLine;
                int nLine = 1;
                while( ( strLine = reader.readLine() ) != null )
                {
                    writer.write( " <a name=\"" + nLine + "\" href=\"#L" + nLine + "\">" + nLine + "</a> &nbsp; " + StringEscapeUtils.escapeHtml( strLine ) + "\n" );
                    nLine++;
                }
                writer.write( FOOTER );
            }
            catch( FileNotFoundException ex )
            {
                logger.info( "Error creating view file. ", ex );
            }
            catch( IOException ex )
            {
                logger.info( "Error creating view file. ", ex );
            }           
        }
    }
    
    /**
     * Calculate the CSS path from the depth
     * @param nDepth The depth
     * @return The path
     */
    private static String getCssPath( int nDepth )
    {
        String strPath = "";
        for( int i = 0 ; i < nDepth ; i++ )
        {
            strPath += "../";
        }
        return strPath;
    }

    /**
     * Calculate the destination path for a template
     * @param strProjectDirectory The project directory
     * @param strTemplatesDirectory The template directory
     * @param file The template as file
     * @return The path of the template
     */
    private static String getDestFilePath( String strProjectDirectory, String strTemplatesDirectory, File file )
    {
        StringBuilder sbPath = new StringBuilder();
        sbPath.append( strProjectDirectory.replace( '\\', '/') )
                .append( PATH_VIEW_TEMPLATES )
                .append( file.getPath().substring( strTemplatesDirectory.length() ).replace( '\\', '/') );
        return sbPath.toString();
    }

 
}
