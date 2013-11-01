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
package uk.org.raje.maven.plugin.msbuild.configuration;

import java.io.File;

import org.apache.maven.plugins.annotations.Parameter;


/**
 * Configuration holder for CppCheck configuration values.
 */
public class CppCheckConfiguration
{
    /**
     * The name to output on debug/information messages
     */    
    public static final String TOOL_NAME = "CppCheck";

    /**
     * The name of the environment variable that can store the PATH TO CppCheck.
     */
    public static final String PATH_ENVVAR = "CPPCHECK_PATH";
    
    /**
     * The name of the property that can store the PATH TO CppCheck.
     */
    public static final String PATH_PROPERTY = "cppcheck.path";

    /**
     * The message to use when skipping CppCheck execution
     */
    public static final String SKIP_MESSAGE = "Skipping static code analysis";
   
    /**
     * Set of known check types
     */
    public enum CppCheckType
    {
        all,
        style,
        performance,
        protability,
        information,
        /**
         * Unused function checking
         */
        unusedFunction,
        missingInclude
    }
    
    /**
     * Get the configured value for skip 
     * @return the configured value or false if not configured
     */
    public final boolean skip()
    {
        return skip;
    }

    /**
     * Get the configured path to CppCheck
     * @return the path to CppCheck
     */
    public final File getCppCheckPath()
    {
        return cppCheckPath;
    }

    /**
     * Set the path to CppCheck
     * @param newCppCheckPath the new path to store, replaces any existing value
     */
    public final void setCppCheckPath( File newCppCheckPath )
    {
        cppCheckPath = newCppCheckPath;
    }

    /**
     * Get the configured report name prefix
     * @return the prefix for report file names
     */
    public final String getReportName()
    {
        return reportName;
    }

    /**
     * Get the configured check type, if not configured the default CppCheckType.all is returned.
     * @return the CppCheckType
     */
    public final CppCheckType getCppCheckType()
    {
        return cppCheckType;
    }

    /**
     * Get the configured exclusion regex, may be null.
     * @return the configured String or null
     */
    public final String getExcludeProjectRegex()
    {
        return excludeProjectRegex;
    }

    /**
     * Set to true to skip CppCheck functionality.
     */
    @Parameter( 
            defaultValue = "false", 
            readonly = false )
    private boolean skip = false; 

    /**
     * The path to CppCheck.
     * Note: The property name specified here is only for documentation, this doesn't work and needs to be manually
     * fixed in {@link AbstractMSBuildPluginMojo}
     */
    @Parameter( 
            property = PATH_PROPERTY,
            readonly = false, 
            required = false )
    private File cppCheckPath;

    /**
     * The prefix for the CppCheck report output file.
     */
    @Parameter( 
            defaultValue = "cppcheck-report", 
            readonly = false, 
            required = false )
    private String reportName = "cppcheck-report";
    
    @Parameter( 
            readonly = false, 
            required = false )
    private CppCheckType cppCheckType = CppCheckType.all;
    
    @Parameter( 
            readonly = false, 
            required = false )
    private String excludeProjectRegex;
}
