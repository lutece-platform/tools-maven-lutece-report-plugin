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
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;

/**
 * Lutece Tempate Analysis report
 */
@Mojo(
        name = "lutece-template-report",
        defaultPhase = LifecyclePhase.SITE,
        requiresDependencyResolution = ResolutionScope.RUNTIME,
        requiresProject = true,
        threadSafe = true
)

public class TemplateReport extends AbstractMavenReport
{

    private static final String PATH_SOURCE_TEMPLATES = "/webapp/WEB-INF/templates";

    private String _strProjectPath;
    /**
     * {@inheritDoc }
     */
    @Override
    public String getOutputName()
    {
        // This report will generate lutece-template-report.html when invoked in a project with `mvn site`
        return "lutece-template-report";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getName( Locale locale )
    {
        // Name of the report when listed in the project-reports.html page of a project
        return "Lutece Template Analysis";
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getDescription( Locale locale )
    {
        // Description of the report when listed in the project-reports.html page of a project
        return "Lutece template analysis.";
    }
    
    /**
     * Set the project path
     * @param strProjectPath  the project path
     */
    public void setProjectPath( String strProjectPath )
    {
        _strProjectPath = strProjectPath;
    }

    /**
     * Practical reference to the Maven project
     */
    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    /**
     * {@inheritDoc }
     */
    @Override
    protected void executeReport( Locale locale ) throws MavenReportException
    {
        setProjectPath( project.getBasedir().getAbsolutePath() );
        // Get the logger
        Log logger = getLog();

        String strTemplatePath = getProjectPath() + PATH_SOURCE_TEMPLATES;
        
        if ( !(new File( strTemplatePath ).exists( ) ) )
        {
            logger.warn( "No templates found for :  " + project.getName() + " " + project.getVersion() );
        }
        else
        {
            // Some info
            logger.info( "Generating " + getOutputName() + ".html"
                    + " for " + project.getName() + " " + project.getVersion() );

            ReportData data = new ReportData();
            data.setReportName( "Template analysis report for " + project.getName() );
            data.addAnalyzer( new DeprecatedMacrosAnalyzer() );
            data.addAnalyzer( new MacroRequiredAnalyzer() );


            FileViewerService.initViewFilesDirectory( getProjectPath() );
            analyze( logger, data, strTemplatePath, strTemplatePath , 0 );
            Sink mainSink = getSink();
            RenderService renderer = new RenderService();
            renderer.renderReport( mainSink, data );
            
            // Style Guide 
            StyleGuideDeployer.deploy( _strProjectPath , logger );
        }

    }

    /**
     * Analyze a path
     *
     * @param data The report data
     * @param strRoot The root path
     * @param strPath The current path
     */
    void analyze( Log logger, ReportData data, String strRoot, String strPath, int nDepth )
    {
        File file = new File( strPath );
        
        FileViewerService.createViewFile( logger, getProjectPath() , strRoot , file, nDepth );
        if( file.isDirectory() )
        {
            nDepth++;
            for( String strFilePath : file.list() )
            {
                analyze( logger, data, strRoot, file.getAbsolutePath() + "/" + strFilePath , nDepth );
            }
        }
        else
        {
            data.incrementTemplateCount();
            TemplateData dt = new TemplateData();
            dt.setTemplatePath( strPath.substring( strRoot.length() ).replace( '\\', '/' ) );
            AnalysisResult result = new AnalysisResult();
            analyzeFile( logger, data, file, result );
            if( !result.getIssues().isEmpty() )
            {
                dt.setIssues( result.getIssues() );
                data.incrementIssueCount( result.getIssues().size() );
                data.addTemplate( dt );
            }
        }

    }

    /**
     * Analyze a file
     *
     * @param file The file
     * @param result The result object to collect issues
     */
    private void analyzeFile( Log logger, ReportData data, File file, AnalysisResult result )
    {
        try
        {
            logger.info(  "Analyzing template " + file.getPath() );
            BufferedReader b = new BufferedReader( new FileReader( file ) );
            String strLine;
            int nLine = 1;
            while( ( strLine = b.readLine() ) != null )
            {
                data.incrementLineCount();
                for( LineAnalyzer analyzer : data.getAnalyzers() )
                {
                    analyzer.analyzeLine( strLine, nLine, result );
                }
                nLine++;
            }
            b.close();
        }
        catch( IOException ex )
        {
            logger.error( "Error while analyzing file : " + file.getPath() , ex);
        }
    }
    
    
    private String getProjectPath()
    {
        return _strProjectPath;
    }

}
