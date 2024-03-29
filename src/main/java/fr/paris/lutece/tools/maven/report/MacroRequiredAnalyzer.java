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

/**
 * MacroRequiredAnalyzer
 */
public class MacroRequiredAnalyzer extends AbstractLineAnalyzer implements LineAnalyzer
{
    private static final String[] MACROS_REQUIRED = {
        "table |@table",
        "i |@icon",
        "input |@input",
        "select |@select",
        "a |@aButton",
        "button |@button",
        "form |@tForm",
        "ul |@ul",
        "div class=\"row\"|@row",
        "div class=\"col-|@columns",
        "div class=\"box |@box",
        "div class=\"form-group |@formGroup",
    };
    

    /**
     * {@inheritDoc }
     */
    @Override
    public void analyzeLine( String strLine, int nLineNumber, AnalysisResult result )
    {
        for( String strMacroConversion : getMacroConversion() )
        {
            String[] params = strMacroConversion.split( "\\|" );
            String strTag = params[0];
            String strMacro = params[1];
            initRuleCounter( strMacroConversion );
            
            if( strLine.contains( "<" + strTag ))
            {
                AnalysisIssue issue = new AnalysisIssue( "Tag '" + strTag + "' should be replaced by the '" + strMacro + "' macro." ,  nLineNumber );
                result.addIssues( issue );
                incrementRuleCounter( strMacroConversion );
            }
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public AnalyzerData getStats()
    {
        AnalyzerData data = new AnalyzerData( "Macro required usage" );
        for( String strMacroConversion : getMacroConversion() )
        {
            String[] params = strMacroConversion.split( "\\|" );
            String strTag = params[0];
            String strMacro = params[1];
            AnalyzerIssueCategoryData aid = new AnalyzerIssueCategoryData();
            aid.setIssueDescription( "Tag '" + strTag + "' should be replaced by the '" + strMacro + "' macro." );
            aid.setCount( getRuleCounter( strMacroConversion ) );
            data.addIssueCategory( aid );
        }
        return data;
    }
 
    /**
     * Return the macro conversion list
     * @return The list
     */
    private String[] getMacroConversion()
    {
        return MACROS_REQUIRED;
    }


}
