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
package uk.org.raje.maven.plugin.msbuild.it;

import static org.junit.Assert.assertTrue;
import static uk.org.raje.maven.plugin.msbuild.it.MSBuildMojoITHelper.addPropertiesToVerifier;

import java.io.File;
import java.io.IOException;

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;
import org.junit.Test;

import uk.org.raje.maven.plugin.msbuild.CxxTestBuildMojo;
import uk.org.raje.maven.plugin.msbuild.CxxTestGenMojo;
import uk.org.raje.maven.plugin.msbuild.CxxTestRunnerMojo;

/**
 * Integration test that runs the hello-world-build-test
 *
 */
public class MavenITCxxTestMojoTest 
{
    @Test
    public void testCxxTestGenerateAndBuild() throws Exception
    {
        final File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/compute-pi-cxxtest-test" );
        final File outputFile = getTestRunnerCpp( "/compute-pi-cxxtest-test/compute-pi-test/" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        addPropertiesToVerifier( verifier );
        verifier.getSystemProperties().setProperty( MSBuildMojoITHelper.MSBUILD_PLUGIN_TOOLS_ENABLE, "true" );
        
        verifier.executeGoal( GROUPID + ":" + ARTIFACTID + ":" + CxxTestGenMojo.MOJO_NAME );
        verifier.verifyErrorFreeLog();
        assertTrue( "Test runner not generated", outputFile.exists() );
        verifier.resetStreams();
        // TODO: Add more tests to check the contents of our runner matches
        // what we expect

        verifier.executeGoal( GROUPID + ":" + ARTIFACTID + ":" + CxxTestBuildMojo.MOJO_NAME );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
        
        verifier.executeGoal( GROUPID + ":" + ARTIFACTID + ":" + CxxTestRunnerMojo.MOJO_NAME );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();        
    }
    
    @Test
    public void testCxxTestTemplateGenerateAndBuild() throws Exception
    {
        final File testDir = ResourceExtractor.simpleExtractResources( getClass(), 
                "/compute-pi-cxxtest-template-test" );
        final File outputFile = getTestRunnerCpp( "/compute-pi-cxxtest-template-test/compute-pi-test/" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        addPropertiesToVerifier( verifier );
        verifier.getSystemProperties().setProperty( MSBuildMojoITHelper.MSBUILD_PLUGIN_TOOLS_ENABLE, "true" );
        
        verifier.executeGoal( GROUPID + ":" + ARTIFACTID + ":" + CxxTestGenMojo.MOJO_NAME );
        verifier.verifyErrorFreeLog();
        assertTrue( "Test runner not generated", outputFile.exists() );
        verifier.resetStreams();
        // TODO: Add more tests to check the contents of our runner matches
        // the template we provided to cxxtestgen

        verifier.executeGoal( GROUPID + ":" + ARTIFACTID + ":" + CxxTestBuildMojo.MOJO_NAME );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();
        
        verifier.executeGoal( GROUPID + ":" + ARTIFACTID + ":" + CxxTestRunnerMojo.MOJO_NAME );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();        
    }

    private File getTestRunnerCpp( String directory ) throws IOException
    {
        final File outputDirectory = ResourceExtractor.simpleExtractResources( getClass(), 
                directory );
        return new File( outputDirectory, "cxxtest-runner.cpp" );
    }

    private static final String GROUPID = "uk.org.raje.maven.plugins";
    private static final String ARTIFACTID = "msbuild-maven-plugin";
}
