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
 * AnalysisIssue
 */
public class AnalysisIssue implements Comparable
{
    // Variables declarations 

    private String _strMessage;
    private int _nLine;

    /**
     * Constructor
     *
     * @param strMessage The message
     * @param nLineNumber The line number
     */
    AnalysisIssue( String strMessage, int nLineNumber )
    {
        _strMessage = strMessage;
        _nLine = nLineNumber;
    }

    /**
     * Returns the Message
     *
     * @return The Message
     */
    public String getMessage()
    {
        return _strMessage;
    }

    /**
     * Sets the Message
     *
     * @param strMessage The Message
     */
    public void setMessage( String strMessage )
    {
        _strMessage = strMessage;
    }

    /**
     * Returns the Line
     *
     * @return The Line
     */
    public int getLine()
    {
        return _nLine;
    }

    /**
     * Sets the Line
     *
     * @param nLine The Line
     */
    public void setLine( int nLine )
    {
        _nLine = nLine;
    }

    /**
     * {@inheritDoc }
     */
    public int compareTo( Object o )
    {
        return _nLine - ((AnalysisIssue) o).getLine();
    }
}
