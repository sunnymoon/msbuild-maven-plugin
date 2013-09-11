/*
 * Copyright 2013 Andrew Everitt, Andrew Heckford, Daniele Masato
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.org.raje.maven.plugin.msbuild;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.plexus.util.cli.StreamConsumer;

/**
 * @author dmasato
 *
 */
public class CppCheckRunner extends CommandLineRunner
{
    public CppCheckRunner( File cppCheckPath, File vcProjectPath, StreamConsumer outputConsumer, 
            StreamConsumer errorConsumer )
    {
        super( outputConsumer, errorConsumer );
        this.cppCheckPath = cppCheckPath;
        this.vcProjectPath = vcProjectPath;
    }
    
    public CppCheckType getCppCheckType() 
    {
        return cppCheckType;
    }

    public void setCppCheckType( CppCheckType cppCheckType ) 
    {
        this.cppCheckType = cppCheckType;
    }

    public List<String> getIncludeDirectories() 
    {
        return includeDirectories;
    }

    public void setIncludeDirectories( List<String> includeDirectories ) 
    {
        this.includeDirectories = includeDirectories;
    }

    public List<String> getExcludeDirectories() 
    {
        return excludeDirectories;
    }

    public void setExcludeDirectories( List<String> excludeDirectories ) 
    {
        this.excludeDirectories = excludeDirectories;
    }

    public List<String> getPreprocessorDefs() 
    {
        return preprocessorDefs;
    }

    public void setPreprocessorDefs( List<String> preprocessorDefs ) 
    {
        this.preprocessorDefs = preprocessorDefs;
    }    
    
    @Override
    protected List<String> getCommandLineArguments() 
    {
        List<String> commandLineArguments = new LinkedList<String>();
        
        commandLineArguments.add( cppCheckPath.getAbsolutePath() );
        commandLineArguments.add( "--quiet" );
        commandLineArguments.add( "--xml" );
        commandLineArguments.add( "--xml-version=" + CPPCHECK_XML_VERSION );
        commandLineArguments.add( "--enable=" + cppCheckType.name() );
        
        for ( String includeDirectory : includeDirectories ) 
        {
            commandLineArguments.add( "-I" );
            commandLineArguments.add( "\"" + includeDirectory + "\"" );
        }

        for ( String excludeDirectory : excludeDirectories ) 
        {
            commandLineArguments.add( "-i" );
            commandLineArguments.add( "\"" + excludeDirectory + "\"" );
        }

        for ( String preprocessorDef : preprocessorDefs ) 
        {
            commandLineArguments.add( "-D" + preprocessorDef );
        }
        
        commandLineArguments.add( vcProjectPath.getAbsolutePath() );
        
        return commandLineArguments;
    }
    
    private static final String CPPCHECK_XML_VERSION = "1";
    
    private File cppCheckPath;
    private File vcProjectPath;
    private CppCheckType cppCheckType = CppCheckType.all;
    private List<String> includeDirectories = new LinkedList<String>();
    private List<String> excludeDirectories = new LinkedList<String>();
    private List<String> preprocessorDefs = new LinkedList<String>();
}