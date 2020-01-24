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

import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.reporting.MavenReportException;

/**
 * RenderService
 */
public class RenderService 
{
    void renderReport( Sink sink, ReportData data ) throws MavenReportException
    {
        // Get the Maven Doxia Sink, which will be used to generate the
        // various elements of the document
        if( sink == null )
        {
            throw new MavenReportException( "Could not get the Doxia sink" );
        }

        // Page title
        sink.head();
        sink.title();
        sink.text( data.getReportName() );
        sink.title_();
        sink.head_();

        sink.body();

        // Heading 1
        sink.section1();
        sink.sectionTitle1( );
        sink.text( "Template analysis report" );
        sink.sectionTitle1_();
        
        sink.section2();
        sink.sectionTitle2( );
        sink.text( "General statistics" );
        sink.sectionTitle2_();

        sink.table();
        sink.tableRow();
        sink.tableCell();
        sink.text( "Number of templates" );
        sink.tableCell_();
        sink.tableCell();
        sink.text( String.valueOf( data.getTemplateCount() ) );
        sink.tableCell_();
        sink.tableRow_();
        sink.tableRow();
        sink.tableCell();
        sink.text( "Number of lines" );
        sink.tableCell_();
        sink.tableCell();
        sink.text( String.valueOf( data.getLineCount() ) );
        sink.tableCell_();
        sink.tableRow_();
        sink.tableRow();
        sink.tableCell();
        sink.text( "Average number of lines per template" );
        sink.tableCell_();
        sink.tableCell();
        sink.text( String.valueOf( data.getLineAverage() ) );
        sink.tableCell_();
        sink.tableRow_();
        sink.tableRow();
        sink.tableCell();
        sink.text( "Number of issues" );
        sink.tableCell_();
        sink.tableCell();
        sink.text( String.valueOf( data.getIssueCount() ) );
        sink.tableCell_();
        sink.tableRow_();
        sink.table_();

        sink.section2();
        sink.sectionTitle2( );
        sink.text( "Summary by analysis rules " );
        sink.sectionTitle2_();
        // Content
        for( LineAnalyzer analyzer : data.getAnalyzers() )
        {
            renderAnalyzerData( sink, analyzer.getStats() );
        }
        
        sink.section2();
        sink.sectionTitle2( );
        sink.text( "Detailed analysis by template " );
        sink.sectionTitle2_();
        // Content
        for( TemplateData dt : data.getTemplates() )
        {
            renderTemplateData( sink, dt );
        }

        // Close
        sink.section1_();
        sink.body_();
    }
    
    private void renderAnalyzerData( Sink sink, AnalyzerData data )
    {
        sink.sectionTitle3();
        sink.text( data.getAnalyzerTitle() );
        sink.sectionTitle3_();
        sink.table();
        for( AnalyzerIssueCategoryData category : data.getIssueCategories() )
        {
            sink.tableRow();
            sink.tableCell();
            sink.text( category.getIssueDescription() );
            sink.tableCell_();
            sink.tableCell();
            sink.text( String.valueOf( category.getCount() ) );
            sink.tableCell_();
            sink.tableRow_();
        }
        sink.table_();
        sink.section2_();
        
    }

    private void renderTemplateData( Sink sink, TemplateData dt )
    {
        sink.section2();
        sink.bold();
        sink.text( dt.getTemplatePath() );
        sink.bold_();
        sink.table();
        for( AnalysisIssue issue : dt.getIssues() )
        {
            sink.tableRow();
            sink.tableCell();
            sink.text( issue.getMessage() );
            sink.tableCell_();
            sink.tableCell();
            sink.anchor( "template_link" );
            sink.link( "templates/" + dt.getTemplatePath() + "?#" + issue.getLine() );
            sink.text( String.valueOf( issue.getLine() ) );
            sink.anchor_();
            sink.tableCell_();
            sink.tableRow_();
        }
        sink.table_();
        sink.section2_();

    }

}
