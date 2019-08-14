/*
 * Copyright (c) 2002-2019, Mairie de Paris
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

import java.io.File;
import junit.framework.TestCase;
import org.apache.maven.doxia.module.xhtml.XhtmlSinkFactory;
import org.apache.maven.doxia.sink.Sink;

/**
 * TemplateReportTest
 */
public class TemplateReportTest extends TestCase
{
    

    /**
     * Test of executeReport method, of class TemplateReport.
     * @throws java.lang.Exception
     */
    public void testAnalyze() throws Exception
    {
        System.out.println( "testAnalyze" );
        TemplateReport instance = new TemplateReport();
        XhtmlSinkFactory factory = new XhtmlSinkFactory();
        String strCurrent = System.getProperty( "user.dir" );
        File outputDir = new File( strCurrent + "/target" );
        Sink sink = factory.createSink( outputDir , "lutece-report-test.html" );

        ReportData data = new ReportData();
        data.setReportName( "Template analysis test report" );
        data.addAnalyzer( new DeprecatedMacrosAnalyzer() );
        data.addAnalyzer( new MacroRequiredAnalyzer() );
        String strTemplates = strCurrent + "/src/test/resources/templates";
        instance.analyze( data, strTemplates, strTemplates );
        RenderService renderer = new RenderService();
        renderer.renderReport( sink , data );

    }
    
}
