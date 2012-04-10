package com.gorthaur.cluster;

import javax.inject.Inject;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

public class Boot {

	@Inject
	ClusterStateManager channelManager;
	
	@Inject CommandLine commandLine;
	
	void start() {
		if(commandLine.hasOption("console")) {
			String consolePort = commandLine.getOptionValue("console");
			System.out.println("Console On Port " + consolePort);
		} else {
			System.out.println("Launch Slave");
		}
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		
		Options options = new Options();
		options.addOption(OptionBuilder.withArgName("port")
				.hasArg()
				.withDescription("start a management console")
				.create("console"));
		
		CommandLineParser parser = new GnuParser();
		try {
			final CommandLine line = parser.parse( options, args );
			
		    
			Guice.createInjector(new AbstractModule(){

				@Override
				protected void configure() {
					bind(CommandLine.class).toInstance(line);
				}
				
			}).getInstance(Boot.class).start();	
			
		} catch (Exception ex) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "<jarname>", options );
		}
	
	}
	
}
