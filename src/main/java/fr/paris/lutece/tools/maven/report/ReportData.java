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

import java.util.ArrayList;
import java.util.List;

/**
 * ReportData
 */
public class ReportData
{

    private String _strReportName;
    private int _nTemplateCount;
    private int _nLineCount;
    private int _nIssueCount;
    private List<TemplateData> _listTemplates = new ArrayList<TemplateData>();
    private List<LineAnalyzer> _listAnalyzers = new ArrayList<LineAnalyzer>();

    /**
     * Returns the ReportName
     *
     * @return The ReportName
     */
    public String getReportName()
    {
        return _strReportName;
    }

    /**
     * Sets the ReportName
     *
     * @param strReportName The ReportName
     */
    public void setReportName( String strReportName )
    {
        _strReportName = strReportName;
    }

    /**
     * Returns the TemplateCount
     *
     * @return The TemplateCount
     */
    public int getTemplateCount()
    {
        return _nTemplateCount;
    }

    /**
     * Returns the LineCount
     *
     * @return The LineCount
     */
    public int getLineCount()
    {
        return _nLineCount;
    }

    /**
     * Returns the IssueCount
     *
     * @return The IssueCount
     */
    public int getIssueCount()
    {
        return _nIssueCount;
    }

    /**
     * Returns the LineAverage
     *
     * @return The LineAverage
     */
    public int getLineAverage()
    {
        return _nLineCount / _nTemplateCount;
    }

    /**
     * Returns the Templates
     *
     * @return The Templates
     */
    public List<TemplateData> getTemplates()
    {
        return _listTemplates;
    }

    /**
     * Add a Templates Data
     *
     * @param dt The Template data
     */
    public void addTemplate( TemplateData dt )
    {
        _listTemplates.add( dt );
    }

    /**
     * Returns the Analyzers
     *
     * @return The Analyzers
     */
    public List<LineAnalyzer> getAnalyzers()
    {
        return _listAnalyzers;
    }

    /**
     * Add an Analyzers
     *
     * @param analyzer The Analyzer
     */
    public void addAnalyzer( LineAnalyzer analyzer )
    {
        _listAnalyzers.add( analyzer );
    }
    
    /**
     * Increment template count
     */
    public void incrementTemplateCount()
    {
        _nTemplateCount++;
    }
    
    /**
     * Increment template count
     */
    public void incrementLineCount()
    {
        _nLineCount++;
    }
    
    /**
     * Increment template count
     * @param nCount increment
     */
    public void incrementIssueCount( int nCount )
    {
        _nIssueCount += nCount;
    }
    
}
