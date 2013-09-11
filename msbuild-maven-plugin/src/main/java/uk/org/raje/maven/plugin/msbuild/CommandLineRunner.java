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

import java.io.IOException;
import java.util.List;

import org.codehaus.plexus.util.cli.StreamConsumer;
import org.codehaus.plexus.util.cli.StreamPumper;


/**
 * @author dmasato
 *
 */
public abstract class CommandLineRunner 
{
    public CommandLineRunner( StreamConsumer outputConsumer, StreamConsumer errorConsumer )
    {
        this.outputConsumer = outputConsumer;
        this.errorConsumer = errorConsumer;
    }
    
    public int runCommandLine() throws IOException, InterruptedException
    {
        Process commandLineProc;
        List<String> commandLineArguments = getCommandLineArguments();
        ProcessBuilder pb = new ProcessBuilder( commandLineArguments );
        
        commandLineProc = pb.start();
        final StreamPumper stdoutPumper = new StreamPumper( commandLineProc.getInputStream(), outputConsumer );        
        final StreamPumper stderrPumper = new StreamPumper( commandLineProc.getErrorStream(), errorConsumer );
        stdoutPumper.start();        
        stderrPumper.start();

        return commandLineProc.waitFor();
    }    
    
    public String getCommandLine() 
    {
        StringBuilder commandLine = new StringBuilder();
        
        for ( String arg : getCommandLineArguments() )
        {
            commandLine.append( arg ).append( " " );
        }
        
        return commandLine.toString();
    }
    
    protected abstract List<String> getCommandLineArguments();
    
    private StreamConsumer outputConsumer;
    private StreamConsumer errorConsumer;
}